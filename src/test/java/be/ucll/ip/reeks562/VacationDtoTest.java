package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.vacation.web.VacationDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VacationDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void teardown() {
        validatorFactory.close();
    }

    @Test
    public void givenValidVacation_whenValidated_thenNoViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacation1().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenVacationWithNoNaam_whenValidated_thenViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacationWithNoNaam().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertEquals(2, violations.size());

        boolean containsVacationNaamMissing = false;
        boolean containsVacationNaamSize = false;

        for (ConstraintViolation<VacationDto> violation : violations) {
            if ("vacation.naam.missing".equals(violation.getMessage())) {
                containsVacationNaamMissing = true;
                assertEquals("naam", violation.getPropertyPath().toString());
                assertEquals("", violation.getInvalidValue());
            }
            if ("vacation.naam.size".equals(violation.getMessage())) {
                containsVacationNaamSize = true;
                assertEquals("naam", violation.getPropertyPath().toString());
                assertEquals("", violation.getInvalidValue());
            }
        }

        assertTrue(containsVacationNaamMissing);
        assertTrue(containsVacationNaamSize);
    }

    @Test
    public void givenVacationWithNaam3Characters_whenValidated_thenViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacationWithNaam3Characters().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertEquals(1, violations.size());
        ConstraintViolation<VacationDto> violation = violations.iterator().next();
        assertEquals("vacation.naam.size", violation.getMessage());
        assertEquals("naam", violation.getPropertyPath().toString());
        assertEquals("Vac", violation.getInvalidValue());
    }

    @Test
    public void givenVacationWithNoPlaats_whenValidated_thenViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacationWithNoPlaats().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertEquals(1, violations.size());
        ConstraintViolation<VacationDto> violation = violations.iterator().next();
        assertEquals("vacation.plaats.missing", violation.getMessage());
        assertEquals("plaats", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void givenVacationWithNoBegindatum_whenValidated_thenViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacationWithNoBegindatum().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertEquals(1, violations.size());
        ConstraintViolation<VacationDto> violation = violations.iterator().next();
        assertEquals("vacation.begindatum.missing", violation.getMessage());
        assertEquals("begindatum", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void givenVacationWithZeroOvernachtingen_whenValidated_thenViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacationWithZeroOvernachtingen().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertEquals(1, violations.size());
        ConstraintViolation<VacationDto> violation = violations.iterator().next();
        assertEquals("vacation.overnachtingen.min", violation.getMessage());
        assertEquals("overnachtingen", violation.getPropertyPath().toString());
        assertEquals(0, violation.getInvalidValue());
    }

    @Test
    public void givenVacationWithNegativeOvernachtingen_whenValidated_thenViolations() {
        // given
        VacationDto vacation = VacationDtoBuilder.aVacationWithNegativeOvernachtingen().build();

        // when
        Set<ConstraintViolation<VacationDto>> violations = validator.validate(vacation);

        // then
        assertEquals(1, violations.size());
        ConstraintViolation<VacationDto> violation = violations.iterator().next();
        assertEquals("vacation.overnachtingen.min", violation.getMessage());
        assertEquals("overnachtingen", violation.getPropertyPath().toString());
        assertEquals(-5, violation.getInvalidValue());
    }

}
