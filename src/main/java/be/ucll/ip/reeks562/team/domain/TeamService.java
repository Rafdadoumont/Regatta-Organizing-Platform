package be.ucll.ip.reeks562.team.domain;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.team.web.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team createTeam(TeamDto dto) {
        Team team = new Team(dto.getName(), dto.getCategory(), dto.getClub(), dto.getSeated(), dto.getRegattaList());
        try {
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("add", "team.name.category.already.used");
        }
    }

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public List<Team> getLessSeatedThanGivenAmount(long amount) {
        return teamRepository.getTeamsWithLessThanGivenNumber(amount);
    }

    public List<Team> getByCategory(String category) {
        return teamRepository.getTeamByCategory(category);
    }

    public Team getById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ServiceException("get", "team.not.found"));
    }

    public Team updateTeam(Long id, TeamDto teamDto) {
        Team team = getById(id);

        team.setCategory(teamDto.getCategory());
        team.setClub(teamDto.getClub());
        team.setName(teamDto.getName());
        team.setSeated(teamDto.getSeated());
        return teamRepository.save(team);
    }

    public Team deleteTeam(Long id) {
        Team team = getById(id);
        teamRepository.deleteById(id);
        return team;
    }

}
