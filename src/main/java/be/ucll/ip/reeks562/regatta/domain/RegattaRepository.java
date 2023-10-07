package be.ucll.ip.reeks562.regatta.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegattaRepository extends JpaRepository<Regatta, Long> {

    List<Regatta> findByOrderByNameCompetitionAsc();

    List<Regatta> findByOrderByDateDesc();

    List<Regatta> findByNameClub(String nameClub);

    @Query("SELECT r FROM Regatta r WHERE (:start IS NULL OR r.date >= :start) AND (:end IS NULL OR r.date <= :end) AND LOWER(r.category) LIKE %:category%")
    List<Regatta> findRegattaByStartAndEndAndCategoryNamedParams(LocalDate start, LocalDate end, String category);

    Page<Regatta> findAll(Pageable pageable);

    Page<Regatta> findRegattasByCategoryContainsIgnoreCase(String name, Pageable pageable);

    Page<Regatta> findByOrderByNameClubAsc(Pageable pageable);

    Page<Regatta> findByOrderByDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM Regatta r WHERE (:start IS NULL OR r.date >= :start) AND (:end IS NULL OR r.date <= :end) AND LOWER(r.category) LIKE %:category%", countQuery = "SELECT count(*) FROM Regatta r WHERE r.date >= :start and r.date <= :end and LOWER(r.category) LIKE %:category%", nativeQuery = true)
    Page<Regatta> findRegattaByStartAndEndAndCategoryNamedParams(LocalDate start, LocalDate end, String category,
            Pageable pageable);
}