package be.ucll.ip.reeks562.vacation.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.vacation.domain.Vacation;
import be.ucll.ip.reeks562.vacation.domain.VacationService;

@RestController
@RequestMapping("/api/vakantie")
public class VacationRestController {
    @Autowired
    private final VacationService vacationService;

    public VacationRestController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping("/all")
    List<Vacation> allVacations() {
        return vacationService.getVacations();
    }

    @PostMapping("/add")
    public List<Vacation> addVacation(@Valid @RequestBody VacationDto vacation) {
        vacationService.addVacation(vacation);
        return allVacations();
    }

    @PutMapping("/update/{id}")
    public Vacation updateVacation(@Valid @RequestBody VacationDto newVacation, @PathVariable Long id) {
        Vacation updatedVacation = vacationService.getVacation(id);
        vacationService.updateVacation(id, newVacation);
        return updatedVacation;
    }

    @DeleteMapping("/delete/{id}")
    public Vacation deleteVacation(@PathVariable("id") long id) {
        Vacation deletedVacation = vacationService.getVacation(id);
        vacationService.deleteVacation(id);
        return deletedVacation;
    }

    @GetMapping("/search/{plaats}")
    List<Vacation> searchVacations(@PathVariable("plaats") String plaats) {
        return vacationService.getVacationsByPlaats(plaats);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class })
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof ServiceException) {
            errors.put(((ServiceException) ex).getAction(), ex.getMessage());
        } else {
            errors.put(((ResponseStatusException) ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }

}
