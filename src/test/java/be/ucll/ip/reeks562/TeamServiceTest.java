package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.team.domain.Team;
import be.ucll.ip.reeks562.team.web.TeamDto;
import be.ucll.ip.reeks562.team.domain.TeamRepository;
import be.ucll.ip.reeks562.team.domain.TeamService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    public void givenTeamDto_whenAdd_thenTeamIsAdded() {
        // given
        Team team = TeamBuilder.aTeam1().build();
        TeamDto teamDto = TeamDtoBuilder.aTeam1().build();
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        // when
        Team result = teamService.createTeam(teamDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(team.getId());
    }

}
