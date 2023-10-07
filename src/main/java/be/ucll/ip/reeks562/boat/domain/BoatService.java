package be.ucll.ip.reeks562.boat.domain;

import be.ucll.ip.reeks562.boat.web.BoatDto;
import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.storage.domain.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {
    @Autowired
    private BoatRepository boatRepository;

    public Boat createBoat(BoatDto boatDto) {
        Boat boat = new Boat(boatDto.getName(), boatDto.getEmail(), boatDto.getLength(), boatDto.getWidth(),
                boatDto.getHeight(), boatDto.getAssuranceNumber());
        try {
            return boatRepository.save(boat);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("add", "boat.name.email.constraint");
        }
    }

    public List<Boat> getAll() {
        return boatRepository.findAll();
    }

    public Boat getById(Long id) {
        return boatRepository.findById(id)
                .orElseThrow(() -> new ServiceException("get", "boat.not.found"));
    }

    public List<Boat> getByStorage(Storage storage) {
        return boatRepository.findBoatByStorage(storage);
    }

    public Boat updateBoat(Long id, BoatDto boatDto) {
        Boat boat = getById(id);

        boat.setName(boatDto.getName());
        boat.setEmail(boatDto.getEmail());
        boat.setLength(boatDto.getLength());
        boat.setWidth(boatDto.getWidth());
        boat.setHeight(boatDto.getHeight());
        boat.setAssuranceNumber(boatDto.getAssuranceNumber());
        return boatRepository.save(boat);
    }

    public Boat updateBoat(Boat boat) {
        return boatRepository.save(boat);
    }

    public Boat deleteBoat(Long id) {
        Boat boat = getById(id);
        boatRepository.deleteById(id);
        return boat;
    }

    public Boat getBoatByAssuranceNumber(String assuranceNumber) {
        if (!assuranceNumber.matches("^[0-9a-zA-Z]{10}$")) {
            throw new ServiceException("get", "");
        }

        Boat boat = boatRepository.findBoatByAssuranceNumber(assuranceNumber);

        if (boat == null) {
            throw new ServiceException("get", "boat.not.found");
        }

        return boat;
    }

    public List<Boat> getBoatsByHeightAndWidth(double height, double width) {
        List<Boat> boats = boatRepository.findBoatByHeightAndWidth(height, width);

        if (boats == null || boats.isEmpty()) {
            throw new ServiceException("get", "boat.not.found");
        }

        return boats;
    }
}
