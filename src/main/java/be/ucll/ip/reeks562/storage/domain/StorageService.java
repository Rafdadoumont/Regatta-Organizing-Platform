package be.ucll.ip.reeks562.storage.domain;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.regatta.domain.Regatta;
import be.ucll.ip.reeks562.storage.web.StorageDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;
    private static final int pageSize = 5;

    public List<Storage> getAll() {
        return storageRepository.findAll();
    }

    public Page<Storage> getAll(String order, int page) {
        Page<Storage> storages;

        if (order != null) {
            if (order.equals("name")) {
                storages = fetchStorageByOrder("name", page, pageSize);
            } else if (order.equals("height")) {
                storages = fetchStorageByOrder("height", page, pageSize);
            } else {
                storages = fetchStorageByDefault(page, pageSize);
            }
        } else {
            storages = fetchStorageByDefault(page, pageSize);
        }

        return storages;
    }

    private Page<Storage> fetchStorageByOrder(String order, int page, int pageSize) {
        try {
            if ("name".equals(order)) {
                return storageRepository.findAllByOrderByNameAsc(getPage(page, pageSize));
            } else if ("height".equals(order)) {
                return storageRepository.findAllByOrderByHeightDesc(getPage(page, pageSize));
            }
        } catch (IllegalArgumentException e) {
            return fetchStorageByDefault(0, pageSize);
        }

        return fetchStorageByDefault(page, pageSize);
    }

    private Page<Storage> fetchStorageByDefault(int page, int pageSize) {
        try {
            Page<Storage> storages = storageRepository.findAll(getPage(page, pageSize));
            checkMaxPage(page, storages);
            return storages;
        } catch (IllegalArgumentException e) {
            return storageRepository.findAll(getPage(0, pageSize));
        }
    }

    public Storage getStorage(Long id) {
        return storageRepository.findById(id)
                .orElseThrow(() -> new ServiceException("get", "invalid.id"));
    }

    public Page<Storage> searchStorage(String name, int page) {
        Page<Storage> storages;
        try {
            storages = storageRepository.findStoragesByNameContainsIgnoreCase(name, getPage(page, pageSize));
        } catch (IllegalArgumentException e) {
            return storageRepository.findStoragesByNameContainsIgnoreCase(name, getPage(0, pageSize));
        }
        try {
            checkMaxPage(page, storages);
        } catch (IllegalArgumentException e) {
            return storageRepository.findStoragesByNameContainsIgnoreCase(name,
                    getPage(Math.max(0, storages.getTotalPages() - 1), pageSize));
        }

        return storages;
    }

    public Storage addStorage(StorageDto dto) {
        Storage storage = new Storage(dto.getName(), dto.getPostalCode(), dto.getSpace(), dto.getHeight());

        try {
            return storageRepository.save(storage);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("add", "storage.already.exists");
        }
    }

    public Storage updateStorage(Long id, StorageDto dto) {
        Storage storage = getStorage(id);

        storage.setName(dto.getName());
        storage.setPostalCode(dto.getPostalCode());
        storage.setSpace(dto.getSpace());
        storage.setHeight(dto.getHeight());

        return storageRepository.save(storage);
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }

    public Boat addBoat(Long storageId, Boat boat) {
        Storage storage = getStorage(storageId);

        storage.addBoat(boat);
        storageRepository.save(storage);
        return boat;
    }

    public Boat removeBoat(Long storageId, Boat boat) {
        Storage storage = getStorage(storageId);

        Boat boat1 = storage.removeBoat(boat);
        storageRepository.save(storage);
        return boat1;
    }

    private Pageable getPage(int page, int numberOfElements) throws IllegalArgumentException {
        if (page < 0) {
            throw new IllegalArgumentException("page.under.0");
        }
        return PageRequest.of(page, numberOfElements);
    }

    private void checkMaxPage(int page, Page<Storage> storages) throws IllegalArgumentException {
        int maxPage = Math.max(0, storages.getTotalPages() - 1);
        if (page > maxPage) {
            throw new IllegalArgumentException("page.above.max");
        }
    }
}
