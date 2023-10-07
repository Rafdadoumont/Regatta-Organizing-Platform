package be.ucll.ip.reeks562;

import java.time.LocalDate;

import be.ucll.ip.reeks562.vacation.domain.Vacation;

public class VacationBuilder {
    private String naam;
    private String plaats;
    private LocalDate begindatum;
    private int overnachtingen;

    private VacationBuilder() {
    }

    public static VacationBuilder aVacation() {
        return new VacationBuilder();
    }

    public static VacationBuilder aVacation1() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationBuilder aVacation2() {
        return aVacation()
                .withNaam("Vacation2")
                .withPlaats("Plaats2")
                .withBegindatum(LocalDate.of(2020, 2, 2))
                .withOvernachtingen(5);
    }

    public static VacationBuilder aVacationWithNoNaam() {
        return aVacation()
                .withNaam("")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationBuilder aVacationWithNaam3Characters() {
        return aVacation()
                .withNaam("Vac")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationBuilder aVacationWithNoPlaats() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(10);
    }

    public static VacationBuilder aVacationWithNoBegindatum() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(null)
                .withOvernachtingen(10);
    }

    public static VacationBuilder aVacationWithZeroOvernachtingen() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(0);
    }

    public static VacationBuilder aVacationWithNegativeOvernachtingen() {
        return aVacation()
                .withNaam("Vacation1")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(-5);
    }

    public static VacationBuilder aVacationWithSamePlaatsAndBegindatumAsVacation1() {
        return aVacation()
                .withNaam("Vacation1Clone")
                .withPlaats("Plaats1")
                .withBegindatum(LocalDate.of(2020, 1, 1))
                .withOvernachtingen(100);
    }

    public VacationBuilder withNaam(String naam) {
        this.naam = naam;
        return this;
    }

    public VacationBuilder withPlaats(String plaats) {
        this.plaats = plaats;
        return this;
    }

    public VacationBuilder withBegindatum(LocalDate begindatum) {
        this.begindatum = begindatum;
        return this;
    }

    public VacationBuilder withOvernachtingen(int overnachtingen) {
        this.overnachtingen = overnachtingen;
        return this;
    }

    public Vacation build() {
        Vacation vacation = new Vacation();
        vacation.setNaam(naam);
        vacation.setPlaats(plaats);
        vacation.setBegindatum(begindatum);
        vacation.setOvernachtingen(overnachtingen);
        return vacation;
    }
}
