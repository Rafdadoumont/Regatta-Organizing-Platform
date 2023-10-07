package be.ucll.ip.reeks562.team.web;

import be.ucll.ip.reeks562.regatta.domain.Regatta;
import jakarta.validation.constraints.*;

import java.util.List;

import org.hibernate.validator.constraints.Range;

public class TeamDto {
    @NotNull
    private long id;

    private List<String> regattaList;

    private String club;

    @NotEmpty(message = "team.name.missing")
    @Size(min = 5, message = "team.not.5.characters")
    private String name;

    @NotEmpty(message = "category.missing")
    @Pattern(regexp = "^[A-Za-z0-9]+", message = "category.not.alphanumeric")
    @Size(min = 7, max = 7, message = "category.not.7.characters")
    private String category;

    @NotNull(message = "team.seated.missing")
    @Range(min = 1, max = 12, message = "team.seated.not.between.1.and.12")
    private int seated;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSeated() {
        return seated;
    }

    public void setSeated(int seated) {
        this.seated = seated;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegattaList(List<String> regattaList) {
        this.regattaList = regattaList;
    }

    public List<String> getRegattaList() {
        return regattaList;
    }

    public void addRegatta(Regatta regatta) {
        regattaList.add(regatta.getNameCompetition());
    }

}
