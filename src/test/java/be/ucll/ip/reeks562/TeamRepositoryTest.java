package be.ucll.ip.reeks562;
import be.ucll.ip.reeks562.team.domain.Team;
import be.ucll.ip.reeks562.team.domain.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TeamRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void givenTeamRegistered_whenFindByCategory_thenReturnTeam(){
        //given
        Team team1 = TeamBuilder.aTeam1().build();
        entityManager.persistAndFlush(team1);
        Team team2 = TeamBuilder.aTeam2().build();
        entityManager.persistAndFlush(team2);

        //when
        List<Team> teams = teamRepository.findAll();

        //then
        assertNotNull(teams);
        assertThat(teams.get(0).getCategory()).isEqualTo("1111111");
        assertThat(teams.get(1).getCategory()).isEqualTo("2222222");

    }

    @Test
    public void showTeamsWithLessThanGivenAmountOfContestantsOrderedAscending(){
        //given
        //amount is 5
        int amount = 5;
        Team team1 = TeamBuilder.aTeam1().build();
        entityManager.persistAndFlush(team1);
        Team team2 = TeamBuilder.aTeam2().build();
        entityManager.persistAndFlush(team2);

        //when
        List<Team> teams = teamRepository.findTeamWithLessContestantsOrderedByAmount(amount);
        assertEquals(1, teams.size());
        assert(teams.size() == 1);

    }

}
