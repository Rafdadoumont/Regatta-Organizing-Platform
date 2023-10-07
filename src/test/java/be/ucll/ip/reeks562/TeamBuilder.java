package be.ucll.ip.reeks562;
import be.ucll.ip.reeks562.team.domain.Team;

public class TeamBuilder {
    private String name;
    private String club;
    private String category;
    private int seated;

    private TeamBuilder(){
    }

    public static TeamBuilder aTeam(){
        return new TeamBuilder();
    }

    public static TeamBuilder aTeam1(){
        return aTeam()
                .withName("name1")
                .withCategory("1111111")
                .withClub("club1")
                .withseated(1);
    }

    public static TeamBuilder aTeam2(){
        return aTeam()
                .withName("name2")
                .withCategory("2222222")
                .withClub("club2")
                .withseated(7);
    }

    public static TeamBuilder TeamWithNoName(){
        return aTeam()
                .withName("")
                .withCategory("1234567")
                .withClub("club")
                .withseated(7);
    }

    public static TeamBuilder TeamWithNoCategory(){
        return aTeam()
                .withName("name1")
                .withCategory("")
                .withClub("club")
                .withseated(42);
    }

    public static TeamBuilder TeamWithNoSeated(){
        return aTeam()
                .withName("name1")
                .withCategory("1234567")
                .withClub("club")
                .withseated(0);
    }



    public TeamBuilder withName(String name){
        this.name = name;
        return this;
    }

    public TeamBuilder withCategory(String category){
        this.category = category;
        return this;
    }

    public TeamBuilder withClub(String club){
        this.club = club;
        return this;
    }

    public TeamBuilder withseated(int seated){
        this.seated = seated;
        return this;
    }

    public Team build(){
        Team team = new Team();
        team.setSeated(seated);
        team.setClub(club);
        team.setName(name);
        team.setCategory(category);
        return team;
    }








}
