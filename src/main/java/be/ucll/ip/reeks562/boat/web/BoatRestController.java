package be.ucll.ip.reeks562.boat.web;

import java.util.List;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.boat.domain.BoatService;
import be.ucll.ip.reeks562.generic.ServiceException;
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
@RequestMapping("/api/boat")
public class BoatRestController {
    @Autowired
    private final BoatService boatService;

    public BoatRestController(BoatService boatService) {
        this.boatService = boatService;
        createSampleData();
    }

    @GetMapping("/overview")
    List<Boat> all() {
        return boatService.getAll();
    }

    @PostMapping("/add")
    Boat addBoat(@Valid @RequestBody BoatDto boat) {
        return boatService.createBoat(boat);
    }

    @PutMapping("/update")
    Boat updateBoat(@Valid @RequestBody BoatDto newBoat, @RequestParam Long id) {
        return boatService.updateBoat(id, newBoat);
    }

    @DeleteMapping("/delete")
    Boat deleteBoat(@RequestParam long id) {
        return boatService.deleteBoat(id);
    }

    @GetMapping("/search")
    Boat getBoatByAssuranceNumber(@RequestParam("insurance") String assuranceNumber) {
        return boatService.getBoatByAssuranceNumber(assuranceNumber);
    }

    @GetMapping("/search/{height}/{width}")
    List<Boat> getBoatByHeightAndWidth(@PathVariable double height, @PathVariable double width) {
        return boatService.getBoatsByHeightAndWidth(height, width);
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

    private void createSampleData() {
        BoatDto speedBoat = new BoatDto();
        speedBoat.setName("Speedster");
        speedBoat.setEmail("captain.speed@example.com");
        speedBoat.setHeight(1.5);
        speedBoat.setWidth(1.5);
        speedBoat.setLength(5.5);
        speedBoat.setAssuranceNumber("ab1193Pyz5");
        boatService.createBoat(speedBoat);

        BoatDto oceanExplorer = new BoatDto();
        oceanExplorer.setName("Ocean Explorer");
        oceanExplorer.setEmail("captain.explorer@example.com");
        oceanExplorer.setHeight(4.0);
        oceanExplorer.setWidth(2.2);
        oceanExplorer.setLength(7.0);
        oceanExplorer.setAssuranceNumber("def456uSwg");
        boatService.createBoat(oceanExplorer);

        BoatDto sailMagic = new BoatDto();
        sailMagic.setName("Sail Magic");
        sailMagic.setEmail("captain.sail@example.com");
        sailMagic.setHeight(1.8);
        sailMagic.setWidth(2.5);
        sailMagic.setLength(6.0);
        sailMagic.setAssuranceNumber("ghi7A9rstD");
        boatService.createBoat(sailMagic);

        BoatDto riverChaser = new BoatDto();
        riverChaser.setName("River Chaser");
        riverChaser.setEmail("captain.river@example.com");
        riverChaser.setHeight(1.2);
        riverChaser.setWidth(1.8);
        riverChaser.setLength(4.7);
        riverChaser.setAssuranceNumber("jklL12Qqra");
        boatService.createBoat(riverChaser);

        BoatDto sunsetDream = new BoatDto();
        sunsetDream.setName("Sunset Dream");
        sunsetDream.setEmail("captain.sunset@example.com");
        sunsetDream.setHeight(1.6);
        sunsetDream.setWidth(2.0);
        sunsetDream.setLength(5.8);
        sunsetDream.setAssuranceNumber("mnoS45GbLf");
        boatService.createBoat(sunsetDream);

        BoatDto sunsetOpportunity = new BoatDto();
        sunsetOpportunity.setName("Sunset Opportunity");
        sunsetOpportunity.setEmail("captain.sunset@example.com");
        sunsetOpportunity.setHeight(2.7);
        sunsetOpportunity.setWidth(1.5);
        sunsetOpportunity.setLength(4.8);
        sunsetOpportunity.setAssuranceNumber("71od45fDa1");
        boatService.createBoat(sunsetOpportunity);
    }
}