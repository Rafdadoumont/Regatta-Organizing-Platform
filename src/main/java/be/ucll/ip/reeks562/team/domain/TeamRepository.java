package be.ucll.ip.reeks562.team.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findTeamByName(String name);
    List<Team> findByName(String name);

    @Query("SELECT t from Team t where t.seated < :amount order by t.name asc")
    List<Team> findTeamWithLessContestantsOrderedByAmount(int amount);

    @Query("SELECT t FROM Team t WHERE t.seated < :number")
    List<Team> getTeamsWithLessThanGivenNumber(long number);

    @Query("SELECT t FROM Team t WHERE t.category = :category")
    List<Team> getTeamByCategory(String category);

}
