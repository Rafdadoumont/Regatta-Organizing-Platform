package be.ucll.ip.reeks562.regatta.domain;

import be.ucll.ip.reeks562.team.domain.Team;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "regatta", uniqueConstraints = { @UniqueConstraint(columnNames = { "regatta", "nameClub", "date" })})
public class Regatta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "regatta")
    private String nameCompetition;

    private String nameClub;

    private LocalDate date;

    private int maxTeams;

    private String category;

    @ManyToMany
    @JoinTable(
            name = "regatta_team",
            joinColumns = @JoinColumn(name = "regatta_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teamList;


    public String getNameCompetition() {
        return nameCompetition;
    }

    public void setNameCompetition(String nameCompetition) {
        this.nameCompetition = nameCompetition;
    }

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


    public void addTeam(Team team) {
        teamList.add(team);
    }

    public void removeTeam(Team team){
        teamList.remove(team);
    }

    public List<Team> returnAllTeams(long regattaId){
        return teamList;
    }




}