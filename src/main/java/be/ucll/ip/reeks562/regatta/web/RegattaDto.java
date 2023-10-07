package be.ucll.ip.reeks562.regatta.web;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class RegattaDto {

    @NotBlank(message = "nameCompetition.missing")
    private String nameCompetition;

    @NotBlank(message = "nameClub.missing")
    private String nameClub;

    @NotNull(message = "not.a.number")
    @Min(value = 2, message = "minTeams")
    private int maxTeams;

    @NotBlank(message = "category.missing")
    @Pattern(regexp = "^[A-Za-z0-9]+", message = "category.not.alphanumeric")
    @Size(min = 7, max = 7, message = "category.not.7.characters")
    private String category;

    @NotNull(message = "date.missing")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @FutureOrPresent(message = "date.in.past")
    private LocalDate date;

    private long id;

    public String getNameCompetition() {
        return nameCompetition;
    }

    public void setNameCompetition(String nameCompetition) { this.nameCompetition = nameCompetition; }

    public String getNameClub() {
        return nameClub;
    }

    public void setNameClub(String nameClub) {
        this.nameClub = nameClub;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
