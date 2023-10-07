package be.ucll.ip.reeks562;

import java.time.LocalDate;

import be.ucll.ip.reeks562.vacation.web.VacationDto;

public class VacationDtoBuilder {
    private String naam;
    private String plaats;
    private LocalDate begindatum;
    private int overnachtingen;

    private VacationDtoBuilder() {
    }

    public static VacationDtoBuilder aVacation() {
        return new VacationDtoBuilder();
    }

    public static VacationDtoBuilder aVacation1() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationDtoBuilder aVacation2() {
        return aVacation()
                .withNaam("Vacation2")
                .withPlaats("Plaats2")
                .withBegindatum(LocalDate.of(2020, 2, 2))
                .withOvernachtingen(5);
    }

    public static VacationDtoBuilder aVacationWithNoNaam() {
        return aVacation()
                .withNaam("")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationDtoBuilder aVacationWithNaam3Characters() {
        return aVacation()
                .withNaam("Vac")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationDtoBuilder aVacationWithNoPlaats() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationDtoBuilder aVacationWithNoBegindatum() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(null)
                .withOvernachtingen(10);
    }

    public static VacationDtoBuilder aVacationWithZeroOvernachtingen() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(0);
    }

    public static VacationDtoBuilder aVacationWithNegativeOvernachtingen() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(-5);
    }

    public static VacationDtoBuilder aVacationWithSamePlaatsAndBegindatumAsVacation1() {
        return aVacation()
                .withNaam("Vacation1Clone")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(100);
    }

    public VacationDtoBuilder withNaam(String naam) {
        this.naam = naam;
        return this;
    }

    public VacationDtoBuilder withPlaats(String plaats) {
        this.plaats = plaats;
        return this;
    }

    public VacationDtoBuilder withBegindatum(LocalDate begindatum) {
        this.begindatum = begindatum;
        return this;
    }

    public VacationDtoBuilder withOvernachtingen(int overnachtingen) {
        this.overnachtingen = overnachtingen;
        return this;
    }

    public VacationDto build() {
        VacationDto vacationDto = new VacationDto();
        vacationDto.setNaam(naam);
        vacationDto.setPlaats(plaats);
        vacationDto.setBegindatum(begindatum);
        vacationDto.setOvernachtingen(overnachtingen);
        return vacationDto;
    }
}
