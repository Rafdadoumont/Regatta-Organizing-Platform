package be.ucll.ip.reeks562.regatta_team;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.team.domain.Team;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/regatta-team/")
public class RegattaTeamRestController {
    private final RegattaTeamService regattaTeamService;

    public RegattaTeamRestController(RegattaTeamService regattaTeamService){
        this.regattaTeamService = regattaTeamService;
    }

    @PostMapping("/add/team/{teamId}/to/regatta/{regattaID}")
    public Team addTeamToRegatta(@PathVariable Long teamId, @PathVariable Long regattaID){
            return regattaTeamService.addTeamToRegatta(teamId, regattaID);

    }

    @PostMapping("/remove/team/{teamId}/from/regatta/{regattaID}")
    public Team removeTeamFromRegatta(@PathVariable Long teamId, @PathVariable Long regattaID){
        return regattaTeamService.removeTeamFromRegatta(teamId, regattaID);
    }

    @GetMapping("teams")
    public List<Team> teamsMetRegattaId(@RequestParam long regattaId) {
        List<Team> regattaNames = regattaTeamService.getTeamsBelongingToRegatta(regattaId);
        return regattaNames;
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        else if (ex instanceof ServiceException) {
            errors.put(((ServiceException) ex).getAction(), ex.getMessage());
        }
        else {
            errors.put(((ResponseStatusException) ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }


}
