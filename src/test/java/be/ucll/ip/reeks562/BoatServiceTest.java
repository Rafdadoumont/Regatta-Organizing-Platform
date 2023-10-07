package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.boat.domain.BoatRepository;
import be.ucll.ip.reeks562.boat.domain.BoatService;
import be.ucll.ip.reeks562.boat.web.BoatDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoatServiceTest {

    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private BoatService boatService;

    @Test
    public void givenBoatDto_whenAdd_thenBoatIsAdded() {
        // given
        Boat boat = BoatBuilder.aBoat1().build();
        BoatDto boatDto = BoatDtoBuilder.aBoat1().build();
        when(boatRepository.save(any(Boat.class))).thenReturn(boat);

        // when
        Boat result = boatService.createBoat(boatDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(boat.getId());
    }
}
