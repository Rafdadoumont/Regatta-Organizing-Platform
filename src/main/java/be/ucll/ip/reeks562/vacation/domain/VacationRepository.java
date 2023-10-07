package be.ucll.ip.reeks562.vacation.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Vacation findVacationById(long id);

    Vacation findVacationByPlaatsAndBegindatum(String plaats, LocalDate begindatum);

    @Query("SELECT v FROM Vacation v WHERE (:van IS NULL OR v.begindatum >= :van) AND (:tot IS NULL OR v.begindatum <= :tot)")
    List<Vacation> findVacationsByBegindatum(@Param("van") LocalDate van, @Param("tot") LocalDate tot);

    @Query("SELECT v FROM Vacation v WHERE LOWER(v.plaats) LIKE CONCAT('%', LOWER(:plaats), '%')")
    List<Vacation> findVacationsByPlaats(@Param("plaats") String plaats);
}
