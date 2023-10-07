package be.ucll.ip.reeks562.team.web;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.team.domain.Team;
import be.ucll.ip.reeks562.team.domain.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team")
public class TeamRestController {
    @Autowired
    private final TeamService teamService;

    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
        //createSampleData();
    }

    @GetMapping("/overview")
    List<Team> all() {
        return teamService.getAll();
    }

    @PostMapping("/add")
    public Team add(@Valid @RequestBody TeamDto team) {
        teamService.createTeam(team);
        List<Team> teams = teamService.getAll();
        return teams.get(teams.size() - 1);
    }

    @PutMapping("/update/{id}")
    public Team UpdateTeam(@Valid @RequestBody TeamDto newTeam, @PathVariable Long id) {
        Team updatedTeam = teamService.getById(id);
        teamService.updateTeam(id, newTeam);
        return updatedTeam;
    }

    @DeleteMapping("/delete/{id}")
    public Team deleteTeam(@PathVariable("id") long id) {
        Team deletedTeam = teamService.getById(id);
        teamService.deleteTeam(id);
        return deletedTeam;
    }

    @GetMapping("/search/{number}")
    List<Team> searchTeams(@PathVariable("number") long number) {
        return teamService.getLessSeatedThanGivenAmount(number);
    }

    @GetMapping("/search")
    public List<Team> searchTeamsByCategory(@RequestParam("category") String category) {
        return teamService.getByCategory(category);
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
        TeamDto SpeedRacers = new TeamDto();
        SpeedRacers.setName("Speed Racers");
        SpeedRacers.setCategory("1234567");
        SpeedRacers.setSeated(6);
        SpeedRacers.setClub("Paddle Adventures Club");
        teamService.createTeam(SpeedRacers);

        TeamDto WaveBreakers = new TeamDto();
        WaveBreakers.setName("Wave Breakers");
        WaveBreakers.setCategory("abcdefg");
        WaveBreakers.setSeated(4);
        WaveBreakers.setClub("Rapids Rowing Club");
        teamService.createTeam(WaveBreakers);

        TeamDto AquaRacers = new TeamDto();
        AquaRacers.setName("Aqua Racers");
        AquaRacers.setCategory("9g0h1i2");
        AquaRacers.setSeated(10);
        AquaRacers.setClub("Lakeside Paddlers Union");
        teamService.createTeam(AquaRacers);

        TeamDto StormRiders = new TeamDto();
        StormRiders.setName("Storm Riders");
        StormRiders.setCategory("3j4k5l6");
        StormRiders.setSeated(8);
        StormRiders.setClub("Whitewater Thrill Seekers Club");
        teamService.createTeam(StormRiders);

        TeamDto SeaChallengers = new TeamDto();
        SeaChallengers.setName("Sea Challengers");
        SeaChallengers.setCategory("7m8n9o0");
        SeaChallengers.setSeated(12);
        SeaChallengers.setClub("Canoe Enthusiasts Alliance");
        teamService.createTeam(SeaChallengers);
    }

}
