package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.team.domain.Team;
import be.ucll.ip.reeks562.team.domain.TeamService;
import be.ucll.ip.reeks562.team.web.TeamDto;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Reeks562Application.class)
@AutoConfigureMockMvc
public class TeamRestControllerTest {
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private TeamService service;

    @Autowired
    private MockMvc TeamRestController;

    private Team team1, team2;
    private TeamDto team1Dto, team2Dto, team3Dto;

    @BeforeEach
    public void setUp() {
        team1 = TeamBuilder.aTeam1().build();
        team2 = TeamBuilder.aTeam2().build();

        team1Dto = TeamDtoBuilder.aTeam1().build();
        team2Dto = TeamDtoBuilder.aTeam2().build();
        team3Dto = TeamDtoBuilder.TeamWithNoName().build();
    }

    @Test
    public void givenTeams_whenGetRequestToAllTeams_thenJSONwithAllTeamsReturned() throws Exception {
        // given
        List<Team> teams = Arrays.asList(team1, team2);

        // mocking

        given(service.getAll()).willReturn(teams);

        // when

        TeamRestController.perform(get("/api/team/overview")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Is.is(team1Dto.getName())))
                .andExpect(jsonPath("$[1].name", Is.is(team2Dto.getName())));

    }

    @Test
    public void givenNoTeams_whenPostRequestToAddAValidTeam_thenJSONisReturned() throws Exception {
        // given
        List<Team> patients = Arrays.asList(team1);

        // mocking
        when(service.createTeam(team1Dto)).thenReturn(team1);
        when(service.getAll()).thenReturn(patients);

        // when
        TeamRestController.perform(post("/api/team/add")
                .content(mapper.writeValueAsString(team1Dto))
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Is.is(team1Dto.getName())));
    }

    @Test
    public void givenNoTeams_whenPostRequestToAddAnInvalidTeam_thenErrorInJSONformatIsReturned() throws Exception {
        // given

        // when
        TeamRestController.perform(post("/api/team/add")
                .content(mapper.writeValueAsString(team3Dto))
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.anyOf(
                        Matchers.is("team.name.missing"),
                        Matchers.is("team.not.5.characters"))));
    }
}
