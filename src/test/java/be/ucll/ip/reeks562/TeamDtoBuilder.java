package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.team.web.TeamDto;

public class TeamDtoBuilder {
    private String name;
    private String club;
    private String category;
    private int seated;

    private TeamDtoBuilder(){
    }

    public static TeamDtoBuilder aTeam(){
        return new TeamDtoBuilder();
    }

    public static TeamDtoBuilder aTeam1(){
        return aTeam()
                .withName("name1")
                .withCategory("1111111")
                .withClub("club1")
                .withseated(1);
    }

    public static TeamDtoBuilder aTeam2(){
        return aTeam()
                .withName("name2")
                .withCategory("2222222")
                .withClub("club2")
                .withseated(7);
    }

    public static TeamDtoBuilder TeamWithNoName(){
        return aTeam()
                .withName("")
                .withCategory("1234567")
                .withClub("club")
                .withseated(7);
    }

    public static TeamDtoBuilder TeamWithNoCategory(){
        return aTeam()
                .withName("name1")
                .withCategory("")
                .withClub("club")
                .withseated(42);
    }

    public static TeamDtoBuilder TeamWithNoSeated(){
        return aTeam()
                .withName("name1")
                .withCategory("1234567")
                .withClub("club")
                .withseated(0);
    }



    public TeamDtoBuilder withName(String name){
        this.name = name;
        return this;
    }

    public TeamDtoBuilder withCategory(String category){
        this.category = category;
        return this;
    }

    public TeamDtoBuilder withClub(String club){
        this.club = club;
        return this;
    }

    public TeamDtoBuilder withseated(int seated){
        this.seated = seated;
        return this;
    }

    public TeamDto build(){
        TeamDto teamDto = new TeamDto();
        teamDto.setSeated(seated);
        teamDto.setClub(club);
        teamDto.setName(name);
        teamDto.setCategory(category);
        return teamDto;
    }
}
