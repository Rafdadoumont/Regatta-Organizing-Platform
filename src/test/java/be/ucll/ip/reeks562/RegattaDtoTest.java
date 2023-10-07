package be.ucll.ip.reeks562;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import be.ucll.ip.reeks562.regatta.web.RegattaDto;

import java.util.Set;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegattaDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void tearDown() {
        validatorFactory.close();
    }

    @Test
    public void givenValidRegatta_shouldHaveNoViolations() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.aRegattaHenleyRoyal().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenRegattaWithNoNameCompetition_shouldDetectInvalidNameError() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.anInvalidRegattaWithNoNameCompetition().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertEquals(1, violations.size());
        ConstraintViolation<RegattaDto> violation = violations.iterator().next();
        assertEquals("nameCompetition.missing", violation.getMessage());
        assertEquals("nameCompetition", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
        
    }

    @Test
    public void givenRegattaWithNoNameClub_shouldDetectInvalidNameError() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.anInvalidRegattaWithNoNameClub().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertEquals(1, violations.size());
        ConstraintViolation<RegattaDto> violation = violations.iterator().next();
        assertEquals("nameClub.missing", violation.getMessage());
        assertEquals("nameClub", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void givenRegattaWithNoDate_shouldDetectInvalidDateError() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.anInvalidRegattaWithNoDate().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertEquals(1, violations.size());
        ConstraintViolation<RegattaDto> violation = violations.iterator().next();
        assertEquals("date.missing", violation.getMessage());
        assertEquals("date", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void givenRegattaWithDateInPast_shouldDetectInvalidDateError() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.anInvalidRegattaWithDateInPast().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertEquals(1, violations.size());
        ConstraintViolation<RegattaDto> violation = violations.iterator().next();
        assertEquals("date.in.past", violation.getMessage());
        assertEquals("date", violation.getPropertyPath().toString());
        assertEquals(LocalDate.of(2020, 12, 01), violation.getInvalidValue());
    }

    @Test
    public void givenRegattaWithNoMaxTeams_shouldDetectInvalidMaxTeamsError() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.anInvalidRegattaWithNoMaxTeams().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertEquals(1, violations.size());
        ConstraintViolation<RegattaDto> violation = violations.iterator().next();
        assertEquals("minTeams", violation.getMessage());
        assertEquals(0, violation.getInvalidValue());
    }

    @Test
    public void givenRegattaWithNoCategory_shouldDetectInvalidCategoryError() {
        //given
        RegattaDto regatta = RegattaDtoBuilder.anInvalidRegattaWithNoCategory().build();

        //when
        Set<ConstraintViolation<RegattaDto>> violations = validator.validate(regatta);

        //then
        assertEquals(3, violations.size());

        boolean containsCategoryMissing = false;
        boolean containsCategoryNotAlphanumeric = false;
        boolean containsCategoryNot7Characters = false;
        
        for (ConstraintViolation<RegattaDto> violation : violations) {
            if (violation.getPropertyPath().toString().equals("category")) {
                if (violation.getMessage().equals("category.missing")) {
                    containsCategoryMissing = true;
                } else if (violation.getMessage().equals("category.not.alphanumeric")) {
                    containsCategoryNotAlphanumeric = true;
                } else if (violation.getMessage().equals("category.not.7.characters")) {
                    containsCategoryNot7Characters = true;
                }
            }
        }

        assertTrue(containsCategoryMissing);
        assertTrue(containsCategoryNotAlphanumeric);
        assertTrue(containsCategoryNot7Characters);
    }
}
