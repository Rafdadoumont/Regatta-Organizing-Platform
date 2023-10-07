package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.regatta.domain.Regatta;
import be.ucll.ip.reeks562.regatta.domain.RegattaRepository;
import be.ucll.ip.reeks562.regatta.domain.RegattaService;
import be.ucll.ip.reeks562.regatta.web.RegattaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegattaServiceTest {

    @Mock
    private RegattaRepository regattaRepository;

    @InjectMocks
    private RegattaService regattaService;

    @Test
    public void givenRegattaDto_whenAdd_thenRegattaIsAdded() {
        // given
        RegattaDto regattaDto = RegattaDtoBuilder.aRegattaHenleyRoyal().build();
        Regatta regatta = RegattaBuilder.aRegattaHenleyRoyal().build();

        when(regattaRepository.save(any(Regatta.class))).thenReturn(regatta);

        // when
        Regatta result = regattaService.addRegatta(regattaDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(regatta.getId());
    }
}
