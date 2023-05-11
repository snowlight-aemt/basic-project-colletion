package domain;

public class Teams {
    private Long id;
    private String name;
    private String teamToken;

    public Teams() {}

    public Teams(String name) {
        this.name = name;
    }

    public String getTeamToken() {
        return teamToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamToken(String teamToken) {
        this.teamToken = teamToken;
    }
}
