package be.ucll.ip.reeks562.storage_boat;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.boat.domain.BoatService;
import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.storage.domain.Storage;
import be.ucll.ip.reeks562.storage.domain.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/storage-boat")
public class StorageBoatRestController {
    @Autowired
    private final StorageService storageService;
    @Autowired
    private final BoatService boatService;

    public StorageBoatRestController(StorageService storageService, BoatService boatService) {
        this.storageService = storageService;
        this.boatService = boatService;
    }

    @PostMapping("/add/boat/{boatId}/to/storage/{storageId}")
    public Boat addBoatToStorage(@PathVariable long boatId, @PathVariable long storageId) {
        Boat boat = boatService.getById(boatId);
        return storageService.addBoat(storageId, boat);
    }

    @GetMapping("boats")
    public List<Boat> getStorageBoats(@RequestParam long storageId) {
        Storage storage = storageService.getStorage(storageId);
        return boatService.getByStorage(storage);
    }

    @PostMapping("/remove/boat/{boatId}/from/storage/{storageId}")
    public Boat removeBoatFromStorage(@PathVariable long boatId, @PathVariable long storageId) {
        Boat boat = boatService.getById(boatId);
        Boat boat1 = storageService.removeBoat(storageId, boat);
        boatService.updateBoat(boat1);
        return boat;
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
