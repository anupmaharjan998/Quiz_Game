package Model;

public class PlayerReport {
    private String competitorId;
    private String name;
    private String level_name;
    private String level_id;
    private String scores;
    private int totalQuizzes;
    private float avgScore;

    public PlayerReport(String competitorId, String name, String level_name, String level_id, String scores,int totalQuizzes, float avgScore) {
        this.competitorId = competitorId;
        this.name = name;
        this.level_name = level_name;
        this.level_id = level_id;
        this.scores = scores;
        this.totalQuizzes = totalQuizzes;
        this.avgScore = avgScore;
    }

    public String getCompetitorId() {
        return competitorId;
    }

    public String getName() {
        return name;
    }

    public String getLevelName() {
        return level_name;
    }

    public int getTotalQuizzes() {
        return totalQuizzes;
    }

    public String getScores() {
        return scores;
    }

    public float getAvgScore() {
        return avgScore;
    }
}