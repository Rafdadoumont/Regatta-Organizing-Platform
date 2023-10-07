package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.regatta.domain.Regatta;
import java.time.LocalDate;

public class RegattaBuilder {

    private String nameCompetition;
    private String nameClub;
    private LocalDate date;
    private int maxTeams;
    private String category;

    private RegattaBuilder() {
    }

    public static RegattaBuilder aRegatta() {
        return new RegattaBuilder();
    }

    public static RegattaBuilder aRegattaHenleyRoyal() {
        return new RegattaBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaBuilder aRegattaCowesWeek() {
        return new RegattaBuilder()
                .withNameCompetition("Cowes Week")
                .withNameClub("Cowes Rowing Club")
                .withDate(LocalDate.of(2023, 12, 13))
                .withMaxTeams(100)
                .withCategory("Champions");
    }

    public static RegattaBuilder anInvalidRegattaWithNoNameCompetition() {
        return new RegattaBuilder()
                .withNameCompetition("")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaBuilder anInvalidRegattaWithNoNameClub() {
        return new RegattaBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaBuilder anInvalidRegattaWithNoDate() {
        return new RegattaBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(null)
                .withMaxTeams(100)
                .withCategory("Masters");
    }

    public static RegattaBuilder anInvalidRegattaWithNoMaxTeams() {
        return new RegattaBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(0)
                .withCategory("Masters");
    }

    public static RegattaBuilder anInvalidRegattaWithNoCategory() {
        return new RegattaBuilder()
                .withNameCompetition("Henley Royal Regatta")
                .withNameClub("Henley Rowing Club")
                .withDate(LocalDate.of(2023, 12, 1))
                .withMaxTeams(100)
                .withCategory("");
    }

    public RegattaBuilder withNameCompetition(String nameCompetition) {
        this.nameCompetition = nameCompetition;
        return this;
    }

    public RegattaBuilder withNameClub(String nameClub) {
        this.nameClub = nameClub;
        return this;
    }

    public RegattaBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public RegattaBuilder withMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
        return this;
    }

    public RegattaBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public Regatta build() {
        Regatta regatta = new Regatta();
        regatta.setNameCompetition(nameCompetition);
        regatta.setNameClub(nameClub);
        regatta.setDate(date);
        regatta.setMaxTeams(maxTeams);
        regatta.setCategory(category);
        return regatta;
    }

}