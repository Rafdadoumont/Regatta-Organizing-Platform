package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.regatta.domain.Regatta;
import be.ucll.ip.reeks562.regatta.domain.RegattaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RegattaRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegattaRepository regattaRepository;

    @Test
    public void givenRegattas_whenFindByOrderByNameCompetitionAsc_thenRegattasAreReturnedInAscendingOrder() {
        // given
        // name competition is Henley Royal Regatta 
        Regatta henleyRoyal = RegattaBuilder.aRegattaHenleyRoyal().build();
        entityManager.persist(henleyRoyal);
        entityManager.flush();

        // name competition is Cowes Week
        Regatta cowesWeek = RegattaBuilder.aRegattaCowesWeek().build();
        entityManager.persist(cowesWeek);
        entityManager.flush();

        // when
        List<Regatta> regattas = regattaRepository.findByOrderByNameCompetitionAsc();

        // then
        assertNotNull(regattas);
        assertEquals(2, regattas.size());
        assertThat(regattas.get(0).getNameCompetition()).isEqualTo("Cowes Week");
        assertThat(regattas.get(1).getNameCompetition()).isEqualTo("Henley Royal Regatta");
    }

    @Test
    public void givenRegattas_whenFindByOrderDateDesc() {
        // given
        // date is 01/12/2023
        Regatta henleyRoyal = RegattaBuilder.aRegattaHenleyRoyal().build();
        entityManager.persist(henleyRoyal);
        entityManager.flush();

        // given
        // date is 13/12/2023
        Regatta cowesWeek = RegattaBuilder.aRegattaCowesWeek().build();
        entityManager.persist(cowesWeek);
        entityManager.flush();

        // when
        List<Regatta> regattas = regattaRepository.findByOrderByDateDesc();

        //then
        assertNotNull(regattas);
        assertEquals(2, regattas.size());
        assertThat(regattas.get(0).getDate()).isEqualTo(LocalDate.of(2023, 12, 13));
        assertThat(regattas.get(1).getDate()).isEqualTo(LocalDate.of(2023, 12, 1));

    }

    @Test
    public void givenRegattas_whenFindRegattaByStartAndEndAndCategoryNamedParams() {
        // given
        // date is 01/12/2023
        Regatta henleyRoyal = RegattaBuilder.aRegattaHenleyRoyal().build();
        entityManager.persist(henleyRoyal);
        entityManager.flush();

        // given
        // date is 13/12/2023
        Regatta cowesWeek = RegattaBuilder.aRegattaCowesWeek().build();
        entityManager.persist(cowesWeek);
        entityManager.flush();

        // when
        List<Regatta> regattas = regattaRepository.findRegattaByStartAndEndAndCategoryNamedParams(LocalDate.of(2022, 11, 1), LocalDate.of(2024, 12,1), "masters");

        //then
        assertNotNull(regattas);
        assertEquals(1, regattas.size());
        assertThat(regattas.get(0).getDate()).isEqualTo(LocalDate.of(2023, 12, 1));

    }
}
