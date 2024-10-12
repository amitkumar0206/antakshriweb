package ca.vancouverdesis.antakshriweb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Scores {
    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("score")
    private int score;

    // Getters and Setters
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}