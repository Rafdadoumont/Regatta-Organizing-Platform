package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.boat.domain.BoatService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Reeks562Application.class)
@AutoConfigureMockMvc
public class BoatRestControllerTest {

    @MockBean
    private BoatService service;

    @Autowired
    private MockMvc BoatRestController;

    private Boat boat1, boat2;

    @BeforeEach
    public void setup() {
        boat1 = BoatBuilder.aBoat1().build();
        boat2 = BoatBuilder.aBoat2().build();
    }

    @Test
    public void givenBoats_whenGetRequestToAllBoats_thenJSONWithAllBoatsReturned() throws Exception {

        // given
        List<Boat> boats = Arrays.asList(boat1, boat2);

        // mocking
        given(service.getAll()).willReturn(boats);

        // when
        BoatRestController.perform(get("/api/boat/overview")
                .contentType(MediaType.APPLICATION_JSON))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", Is.is(boat1.getName())))
                .andExpect(jsonPath("$[0].length", Is.is(boat1.getLength())))
                .andExpect(jsonPath("$[1].name", Is.is(boat2.getName())))
                .andExpect(jsonPath("$[1].length", Is.is(boat2.getLength())));
    }
}
