package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.regatta.web.RegattaDto;

import java.time.LocalDate;

public class RegattaDtoBuilder {

    private String nameCompetition;
    private String nameClub;
    private LocalDate date;
    private int maxTeams;
    private String category;

    private RegattaDtoBuilder() {
    }

    public static RegattaDtoBuilder aRegatta() {
        return new RegattaDtoBuilder();
    }

    public static RegattaDtoBuilder aRegattaHenleyRoyal() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder aRegattaCowesWeek() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Cowes Week")
                .withNameClub("Cowes Rowing Club")
                .withDate(LocalDate.of(2022, 1, 21))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder anInvalidRegattaWithNoNameCompetition() {
        return new RegattaDtoBuilder()
                .withNameCompetition("")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder anInvalidRegattaWithNoNameClub() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder anInvalidRegattaWithNoDate() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(null)
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder anInvalidRegattaWithDateInPast() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2020, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder anInvalidRegattaWithNoMaxTeams() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(0)
                .withCategory("Masters");
    }

    public static RegattaDtoBuilder anInvalidRegattaWithNoCategory() {
        return new RegattaDtoBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("");
    }

    public RegattaDtoBuilder withNameCompetition(String nameCompetition) {
        this.nameCompetition = nameCompetition;
        return this;
    }

    public RegattaDtoBuilder withNameClub(String nameClub) {
        this.nameClub = nameClub;
        return this;
    }

    public RegattaDtoBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public RegattaDtoBuilder withMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
        return this;
    }

    public RegattaDtoBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public RegattaDto build() {
        RegattaDto regatta = new RegattaDto();
        regatta.setNameCompetition(nameCompetition);
        regatta.setNameClub(nameClub);
        regatta.setDate(date);
        regatta.setMaxTeams(maxTeams);
        regatta.setCategory(category);
        return regatta;
    }

}