package be.ucll.ip.reeks562.regatta_team;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.regatta.domain.Regatta;
import be.ucll.ip.reeks562.regatta.domain.RegattaRepository;
import be.ucll.ip.reeks562.team.domain.Team;
import be.ucll.ip.reeks562.team.domain.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegattaTeamService {
    private RegattaRepository regattaRepository;
    private TeamRepository teamRepository;

    public List<String> getRegattaNamesOnly(Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            throw new IllegalArgumentException("team.not.found");
        }

        Team team = teamOptional.get();
        List<String> regattaNames = team.getRegattaNames().stream()
                .map(Regatta::getNameCompetition)
                .collect(Collectors.toList());

        return regattaNames;
    }

    public RegattaTeamService(TeamRepository teamRepository, RegattaRepository regattaRepository) {
        this.regattaRepository = regattaRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Team addTeamToRegatta(Long teamId, Long regattaId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<Regatta> optionalRegatta = regattaRepository.findById(regattaId);

        if (optionalTeam.isPresent() && optionalRegatta.isPresent()) {
            Team team = optionalTeam.get();
            Regatta regatta = optionalRegatta.get();

            if (regatta.getMaxTeams() == regatta.returnAllTeams(regattaId).size()) {
                throw new ServiceException("add", "team.teams.max.reached");
            }
            if (!regatta.getCategory().equals(team.getCategory())) {
                throw new ServiceException("add", "team.category.no.match");
            }

            for (Regatta item : team.getRegattaNames()) {
                if (item.getDate().isEqual(regatta.getDate())) {
                    throw new ServiceException("add", "team.max.day");
                }
            }

            regatta.addTeam(team);
            team.addRegatta(regatta);

            regattaRepository.save(regatta);
            teamRepository.save(team);
            return team;
        } else if (!optionalTeam.isPresent()) {
            throw new ServiceException("add", "team.not.found");
        } else {
            throw new ServiceException("add", "regatta.not.found");
        }
    }

    @Transactional
    public Team removeTeamFromRegatta(Long teamId, Long regattaId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<Regatta> optionalRegatta = regattaRepository.findById(regattaId);

        if (optionalTeam.isPresent() && optionalRegatta.isPresent()) {
            Team team = optionalTeam.get();
            Regatta regatta = optionalRegatta.get();

            regatta.removeTeam(team);

            regattaRepository.save(regatta);
            teamRepository.save(team);
            return team;
        } else {
            return null;
        }

    }

    public List<Team> getTeamsBelongingToRegatta(long regattaId) {
        Optional<Team> optionalTeam = teamRepository.findById(regattaId);
        Optional<Regatta> optionalRegatta = regattaRepository.findById(regattaId);

        if (optionalTeam.isPresent() && optionalRegatta.isPresent()) {
            Team team = optionalTeam.get();
            Regatta regatta = optionalRegatta.get();

            regattaRepository.save(regatta);
            teamRepository.save(team);
            return regatta.returnAllTeams(regattaId);

        } else {
            return null;
        }

    }

}
