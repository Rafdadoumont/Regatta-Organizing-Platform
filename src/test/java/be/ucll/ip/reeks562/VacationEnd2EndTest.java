package be.ucll.ip.reeks562;

import jakarta.transaction.Transactional;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Transactional
public class VacationEnd2EndTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void validateThatA200IsReturnedWhenPostRequestAdd() {
        VacationBodyValue vacationBodyValue = new VacationBodyValue();
        vacationBodyValue.naam = "testNaam";
        vacationBodyValue.plaats = "testPlaats";
        vacationBodyValue.begindatum = LocalDate.now();
        vacationBodyValue.overnachtingen = 1;

        client.post().uri("/api/vakantie/add").bodyValue(vacationBodyValue).exchange().expectStatus().isOk();
    }

    private static class VacationBodyValue {
        public String naam;
        public String plaats;
        public LocalDate begindatum;
        public Integer overnachtingen;
    }
}
