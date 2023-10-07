package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.vacation.domain.Vacation;
import be.ucll.ip.reeks562.vacation.domain.VacationRepository;
import be.ucll.ip.reeks562.vacation.domain.VacationService;
import be.ucll.ip.reeks562.vacation.web.VacationDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VacationServiceTest {

    @Mock
    private VacationRepository vacationRepository;

    @InjectMocks
    private VacationService vacationService;

    @Test
    public void givenVacation_whenAdd_thenVacationIsAdded() {
        // given
        Vacation vacation = VacationBuilder.aVacation1().build();
        VacationDto vacationDto = VacationDtoBuilder.aVacation1().build();

        // mocking
        when(vacationRepository.save(any(Vacation.class))).thenReturn(vacation);

        // when
        Vacation result = vacationService.addVacation(vacationDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNaam()).isEqualTo(vacation.getNaam());
        verify(vacationRepository, times(1)).save(any(Vacation.class));
    }

    @Test
    public void givenVacations_whenValidVacationAddedWithAlreadyUsedPlaatsAndBegindatumCombination_thenVacationIsNotAddedAndErrorIsReturned() {
        // given
        Vacation vacation = VacationBuilder.aVacation1().build();
        VacationDto vacationDto = VacationDtoBuilder.aVacation1().build();

        // mocking
        when(vacationRepository.findVacationByPlaatsAndBegindatum(vacation.getPlaats(), vacation.getBegindatum()))
                .thenReturn(vacation);

        // when
        Throwable thrown = catchThrowable(() -> vacationService.addVacation(vacationDto));

        // then
        assertThat(thrown).isInstanceOf(ServiceException.class);
        assertThat(thrown).message().isEqualTo("vacation.place.date.constraint");
        verify(vacationRepository, times(0)).save(any(Vacation.class));
    }

    @Test
    public void givenVacation_whenDelete_thenVacationIsDeleted() {
        // given
        Vacation vacation = VacationBuilder.aVacation1().build();

        // mocking
        when(vacationRepository.findById(anyLong())).thenReturn(Optional.of(vacation));

        // when
        vacationService.deleteVacation(vacation.getId());

        // then
        verify(vacationRepository, times(1)).findById(vacation.getId());
        verify(vacationRepository, times(1)).delete(vacation);
    }

    @Test
    public void givenInvalidId_whenDelete_thenErrorIsThrown() {
        // given
        Long id = 1L;

        // mocking
        when(vacationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> vacationService.deleteVacation(id));

        // then
        assertThat(thrown).isInstanceOf(ServiceException.class);
        assertThat(thrown).message().isEqualTo("vacation.not.found");
        verify(vacationRepository, times(1)).findById(id);
        verify(vacationRepository, times(0)).delete(any(Vacation.class));
    }
}
