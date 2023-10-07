package be.ucll.ip.reeks562.regatta.web;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.regatta.domain.Regatta;
import be.ucll.ip.reeks562.regatta.domain.RegattaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/regatta")
public class RegattaRestController {
    @Autowired
    private RegattaService regattaService;

    @GetMapping("/overview")
    public Iterable<Regatta> getAll() {
        return regattaService.getRegattas();
    }

    @PostMapping("/add")
    public Iterable<Regatta> add(@Valid @RequestBody RegattaDto regatta) {
        regattaService.addRegatta(regatta);
        return regattaService.getRegattas();
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
