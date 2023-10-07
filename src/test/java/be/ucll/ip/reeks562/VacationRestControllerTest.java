package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.vacation.domain.Vacation;
import be.ucll.ip.reeks562.vacation.domain.VacationService;
import be.ucll.ip.reeks562.vacation.web.VacationDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Reeks562Application.class)
@AutoConfigureMockMvc
public class VacationRestControllerTest {

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private VacationService service;

    @Autowired
    MockMvc vacationRestController;

    private Vacation vacation1;
    private VacationDto vacation1Dto, vacationWithNoNaamDto, vacationWithNegativeOvernachtingenDto;

    @BeforeEach
    public void setup() {
        vacation1 = VacationBuilder.aVacation1().build();

        vacation1Dto = VacationDtoBuilder.aVacation1().build();
        vacationWithNoNaamDto = VacationDtoBuilder.aVacationWithNoNaam().build();
        vacationWithNegativeOvernachtingenDto = VacationDtoBuilder.aVacationWithNegativeOvernachtingen().build();
    }

    @Test
    public void givenNoVacations_whenPostRequestToAddAValidVacation_thenJSONisReturned() throws Exception {
        // given
        List<Vacation> allVacations = Arrays.asList(vacation1);

        // mocking
        when(service.addVacation(vacation1Dto)).thenReturn(vacation1);
        when(service.getVacations()).thenReturn(allVacations);

        // when
        vacationRestController.perform(post("/api/vakantie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vacation1Dto)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].naam", Is.is(vacation1.getNaam())));
    }

    @Test
    public void givenNoVacations_whenPostRequestToAddAVacationWithNoNaam_thenErrorInJSONformatIsReturned()
            throws Exception {
        // given

        // when
        vacationRestController.perform(post("/api/vakantie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vacationWithNoNaamDto)))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.naam", Matchers.anyOf(
                        Matchers.is("vacation.naam.size"),
                        Matchers.is("vacation.naam.missing"))));
    }

    @Test
    public void givenNoVacations_whenPostRequestToAddAVacationWithNegativeOvernachtingen_thenErrorInJSONformatIsReturned()
            throws Exception {
        // given

        // when
        vacationRestController.perform(post("/api/vakantie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vacationWithNegativeOvernachtingenDto)))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.overnachtingen", Is.is("vacation.overnachtingen.min")));
    }

}