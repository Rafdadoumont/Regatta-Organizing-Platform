package be.ucll.ip.reeks562.vacation.domain;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.vacation.web.VacationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VacationService {
    @Autowired
    private VacationRepository vacationRepository;

    public List<Vacation> getVacations() {
        return vacationRepository.findAll();
    }

    public Vacation getVacation(long id) {
        return vacationRepository.findById(id).orElseThrow(() -> new ServiceException("add", "vacation.not.found"));
    }

    public Vacation addVacation(VacationDto dto) {
        if (vacationRepository.findVacationByPlaatsAndBegindatum(dto.getPlaats(), dto.getBegindatum()) != null) {
            throw new ServiceException("add", "vacation.place.date.constraint");
        }

        Vacation vacation = new Vacation();

        vacation.setNaam(dto.getNaam());
        vacation.setPlaats(dto.getPlaats());
        vacation.setBegindatum(dto.getBegindatum());
        vacation.setOvernachtingen(dto.getOvernachtingen());
        return vacationRepository.save(vacation);

    }

    public Vacation updateVacation(Long id, VacationDto dto) {
        Vacation constraintVacation = vacationRepository.findVacationByPlaatsAndBegindatum(dto.getPlaats(),
                dto.getBegindatum());

        if (constraintVacation != null && constraintVacation.getId() != id) {
            throw new ServiceException("add", "vacation.place.date.constraint");
        }
        Vacation vacation = getVacation(id);

        vacation.setNaam(dto.getNaam());
        vacation.setPlaats(dto.getPlaats());
        vacation.setBegindatum(dto.getBegindatum());
        vacation.setOvernachtingen(dto.getOvernachtingen());

        return vacationRepository.save(vacation);
    }

    public void deleteVacation(Long id) {
        vacationRepository.delete(getVacation(id));
    }

    public List<Vacation> filterVacations(LocalDate van, LocalDate tot) {
        return vacationRepository.findVacationsByBegindatum(van, tot);
    }

    public List<Vacation> getVacationsByPlaats(String plaats) {
        return vacationRepository.findVacationsByPlaats(plaats);
    }
}
