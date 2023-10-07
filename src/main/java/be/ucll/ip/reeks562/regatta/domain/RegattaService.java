package be.ucll.ip.reeks562.regatta.domain;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.regatta.web.RegattaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegattaService {
    @Autowired
    private RegattaRepository regattaRepository;
    private static final int pageSize = 5;

    public List<Regatta> getRegattas() {
        return regattaRepository.findAll();
    }

    public Page<Regatta> getRegattas(int page) {
        Page<Regatta> regattas;
        try {
            regattas = regattaRepository.findAll(getPage(page, pageSize));
        } catch (IllegalArgumentException e) {
            return regattaRepository.findAll(getPage(0, pageSize));
        }
        try {
            checkMaxPage(page, regattas);
        } catch (IllegalArgumentException e) {
            return regattaRepository.findAll(getPage(Math.max(0, regattas.getTotalPages() - 1), pageSize));
        }
        return regattas;
    }

    public List<Regatta> getRegattasSortedName() {
        return regattaRepository.findByOrderByNameCompetitionAsc();
    }

    public Page<Regatta> getRegattasSortedClub(int page) {
        Page<Regatta> regattas;
        try {
            regattas = regattaRepository.findByOrderByNameClubAsc(getPage(page, pageSize));
        } catch (IllegalArgumentException e) {
            return regattaRepository.findByOrderByNameClubAsc(getPage(0, pageSize));
        }
        try {
            checkMaxPage(page, regattas);
        } catch (IllegalArgumentException e) {
            return regattaRepository
                    .findByOrderByNameClubAsc(getPage(Math.max(0, regattas.getTotalPages() - 1), pageSize));
        }
        return regattas;
    }

    public List<Regatta> getRegattasSortedDate() {
        return regattaRepository.findByOrderByDateDesc();
    }

    public Page<Regatta> getRegattasSortedDate(int page) {
        Page<Regatta> regattas;
        try {
            regattas = regattaRepository.findByOrderByDateDesc(getPage(page, pageSize));
        } catch (IllegalArgumentException e) {
            return regattaRepository.findByOrderByDateDesc(getPage(0, pageSize));
        }
        try {
            checkMaxPage(page, regattas);
        } catch (IllegalArgumentException e) {
            return regattaRepository
                    .findByOrderByDateDesc(getPage(Math.max(0, regattas.getTotalPages() - 1), pageSize));
        }
        return regattas;
    }

    public Regatta getRegatta(Long id) {
        return regattaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("regatta.not.found"));
    }

    public Regatta addRegatta(RegattaDto dto) {
        try {
            Regatta regatta = new Regatta();
            checkRegatta(dto, "add");

            regatta.setDate(dto.getDate());
            regatta.setCategory(dto.getCategory());
            regatta.setMaxTeams(dto.getMaxTeams());
            regatta.setNameClub(dto.getNameClub());
            regatta.setNameCompetition(dto.getNameCompetition());

            return regattaRepository.save(regatta);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("add", "regatta.record.already.used");
        } catch (ServiceException e) {
            throw new ServiceException(e.getAction(), e.getMessage());
        }
    }

    public Regatta updateRegatta(Long id, RegattaDto dto) {
        try {
            Regatta regatta = getRegatta(id);
            checkRegatta(dto, "update");

            regatta.setDate(dto.getDate());
            regatta.setCategory(dto.getCategory());
            regatta.setMaxTeams(dto.getMaxTeams());
            regatta.setNameClub(dto.getNameClub());
            regatta.setNameCompetition(dto.getNameCompetition());

            return regattaRepository.save(regatta);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("update", "regatta.record.already.used");
        } catch (ServiceException e) {
            throw new ServiceException(e.getAction(), e.getMessage());
        }
    }

    private void checkRegatta(RegattaDto regattaDto, String method) {
        List<Regatta> regattas = regattaRepository.findByNameClub(regattaDto.getNameClub());
        for (Regatta regatta : regattas) {
            if (regatta.getNameClub().equals(regattaDto.getNameClub()) && regatta.getDate().equals(regattaDto.getDate())
                    && regatta.getId() != regattaDto.getId()) {
                throw new ServiceException(method, "regatta.club.date.already.used");
            }
        }
    }

    public void deleteRegatta(Long id) {
        regattaRepository.deleteById(id);
    }

    public Page<Regatta> searchRegatta(int page, LocalDate start, LocalDate end, String category) {
        System.out.println(page);
        Page<Regatta> regattas;
        try {
            regattas = regattaRepository.findRegattaByStartAndEndAndCategoryNamedParams(start,
                    end, category, getPage(page, pageSize));

        } catch (IllegalArgumentException e) {
            return regattaRepository.findRegattaByStartAndEndAndCategoryNamedParams(start,
                    end, category, getPage(0, pageSize));
        }
        try {
            checkMaxPage(page, regattas);
        } catch (IllegalArgumentException e) {
            return regattaRepository.findRegattaByStartAndEndAndCategoryNamedParams(start,
                    end, category, getPage(Math.max(0, regattas.getTotalPages() - 1), pageSize));
        }
        return regattas;
    }

    private Pageable getPage(int page, int numberOfElements) throws IllegalArgumentException {
        if (page < 0) {
            throw new IllegalArgumentException("page.under.0");
        }
        return PageRequest.of(page, numberOfElements);
    }

    private void checkMaxPage(int page, Page<Regatta> regattas) throws IllegalArgumentException {
        if (page > regattas.getTotalPages() - 1) {
            throw new IllegalArgumentException("page.above.max");
        }
    }
}
