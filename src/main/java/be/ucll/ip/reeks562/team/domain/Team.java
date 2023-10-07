package be.ucll.ip.reeks562.team.domain;

import be.ucll.ip.reeks562.regatta.domain.Regatta;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "team", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "category" })})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<String> teamAsString = new ArrayList<>();

    private String name;

    private String category;

    private String club;

    private int seated;

    public List<Regatta> getRegattaNames() {
        if(regattaNames == null){
            return new ArrayList<>();
        }
        return regattaNames;
    }

    public void setRegattaNames(List<Regatta> regattaList) {
        this.regattaNames = regattaList;
    }

    @ManyToMany(mappedBy = "teamList")
    private List<Regatta> regattaNames;

    @ElementCollection
    private List<String> teamCompetitionAsString = new ArrayList<>();


    public Team() {
    }

    public Team(String name, String category, String club, int seated, List<String> regattaList) {
        this.name = name;
        this.category = category;
        this.club = club;
        this.seated = seated;
        this.teamCompetitionAsString = regattaList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getSeated() {
        return seated;
    }

    public void setSeated(int seated) {
        this.seated = seated;
    }

    public void addRegatta(Regatta regatta) {
        teamAsString.add(regatta.getNameCompetition());
    }

}