package be.ucll.ip.reeks562;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.boat.domain.BoatRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BoatRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BoatRepository boatRepository;
    
    @Test
    public void givenBoatRegistered_whenFindAll_thenReturnBoats(){
        //given
        Boat boat1 = BoatBuilder.aBoat1().build();
        entityManager.persistAndFlush(boat1);
        Boat boat2 = BoatBuilder.aBoat2().build();
        entityManager.persistAndFlush(boat2);

        //when
        List<Boat> boats = boatRepository.findAll();

        //then
        assertNotNull(boats);
        assertThat(boats.get(0).getAssuranceNumber()).isEqualTo("1111111111");
        assertThat(boats.get(1).getAssuranceNumber()).isEqualTo("2222222222");

    }
}
