package be.ucll.ip.reeks562;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import be.ucll.ip.reeks562.vacation.domain.Vacation;
import be.ucll.ip.reeks562.vacation.domain.VacationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VacationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VacationRepository vacationRepository;

    @Test
    public void givenVacationRegistered_whenFindAll_thenReturnVacations() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);
        Vacation vacation2 = VacationBuilder.aVacation2().build();
        entityManager.persistAndFlush(vacation2);

        // when
        List<Vacation> vacations = vacationRepository.findAll();

        // then
        assertNotNull(vacations);
        assertEquals(vacation1.getNaam(), vacations.get(0).getNaam());
        assertEquals(vacation2.getNaam(), vacations.get(1).getNaam());

    }

    @Test
    public void givenVacationRegistered_whenFindById_thenReturnVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);
        Vacation vacation2 = VacationBuilder.aVacation2().build();
        entityManager.persistAndFlush(vacation2);

        // when
        Vacation vacation = vacationRepository.findById(vacation1.getId()).orElse(null);

        // then
        assertNotNull(vacation);
        assertEquals(vacation1.getNaam(), vacation.getNaam());

    }

    @Test
    public void givenVacationRegistered_whenSave_thenReturnVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        Vacation vacation = vacationRepository.save(vacation1);

        // then
        assertNotNull(vacation);
        assertEquals(vacation1.getNaam(), vacation.getNaam());
        assertEquals(vacation1.getNaam(), vacationRepository.findById(vacation1.getId()).orElse(null).getNaam());

    }

    @Test
    public void givenVacationRegistered_whenDelete_thenVacationIsDeleted() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);
        Vacation vacation2 = VacationBuilder.aVacation2().build();
        entityManager.persistAndFlush(vacation2);

        // when
        vacationRepository.delete(vacation1);

        // then
        List<Vacation> vacations = vacationRepository.findAll();
        assertNotNull(vacations);
        assertEquals(1, vacations.size());
        assertEquals(vacation2.getNaam(), vacations.get(0).getNaam());

    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatum_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(vacation1.getBegindatum().minusDays(1),
                vacation1.getBegindatum().plusDays(1));

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertTrue(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatumNotInRange_thenNotContainVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(vacation1.getBegindatum().minusDays(30),
                vacation1.getBegindatum().minusDays(30));

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertFalse(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatumOnlyVanAndCorrect_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(vacation1.getBegindatum().minusDays(1),
                null);

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertTrue(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatumOnlyTotAndCorrect_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(null,
                vacation1.getBegindatum().plusDays(1));

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertTrue(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatumOnlyVanAndNotCorrect_thenNotContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(vacation1.getBegindatum().plusDays(1),
                null);

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertFalse(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatumOnlyTotAndNotCorrect_thenNotContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(null,
                vacation1.getBegindatum().minusDays(1));

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertFalse(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByBegindatumNoVanAndNoTot_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByBegindatum(null, null);

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertTrue(containsVacation1);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByPlaats_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);
        Vacation vacation2 = VacationBuilder.aVacation2().build();
        entityManager.persistAndFlush(vacation2);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByPlaats(vacation1.getPlaats());

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        boolean containsVacation2 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
            if (v.getId() == vacation2.getId()) {
                containsVacation2 = true;
            }
        }
        assertTrue(containsVacation1);
        assertFalse(containsVacation2);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByPlaatsLowerCase_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);
        Vacation vacation2 = VacationBuilder.aVacation2().build();
        entityManager.persistAndFlush(vacation2);

        // when
        List<Vacation> vacations = vacationRepository.findVacationsByPlaats(vacation1.getPlaats().toLowerCase());

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        boolean containsVacation2 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
            if (v.getId() == vacation2.getId()) {
                containsVacation2 = true;
            }
        }
        assertTrue(containsVacation1);
        assertFalse(containsVacation2);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByPlaatsOneChar_thenContainsVacation() {
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        List<Vacation> vacations = vacationRepository
                .findVacationsByPlaats(vacation1.getPlaats().substring(vacation1.getPlaats().length() - 1));

        // then
        assertNotNull(vacations);

        boolean containsVacation1 = false;
        boolean containsVacation2 = false;
        for (Vacation v : vacations) {
            if (v.getId() == vacation1.getId()) {
                containsVacation1 = true;
            }
        }
        assertTrue(containsVacation1);
        assertFalse(containsVacation2);
    }

    @Test
    public void givenVacationRegistered_whenFindVacationByPlaatsAndBegindatum_ThenReturnVacation(){
        // given
        Vacation vacation1 = VacationBuilder.aVacation1().build();
        entityManager.persistAndFlush(vacation1);

        // when
        Vacation vacation = vacationRepository.findVacationByPlaatsAndBegindatum(vacation1.getPlaats(), vacation1.getBegindatum());

        // then
        assertNotNull(vacation);
        assertEquals(vacation1.getNaam(), vacation.getNaam());
    }

}
