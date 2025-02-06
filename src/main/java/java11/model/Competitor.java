package java11.model;

public class Competitor {
    private int competitorId;
    private Name name;
    private String level;
    private String country;
    private int age;
    private String scores;
    private double avg_score;

    public Competitor(int competitorId, Name name, String level, String country, int age, String scores, Double avg_score) {
        this.competitorId = competitorId;
        this.name = name;
        this.level = level;
        this.country = country;
        this.age = age;
        this.scores = scores;
        this.avg_score = avg_score;
    }

    public Competitor(Name name, String level, String country, int age, String scores) {
        this.name = name;
        this.level = level;
        this.country = country;
        this.age = age;
        this.scores = scores;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public Double getAvgScore() {
        return avg_score;
    }


    //ToDO Unit Test
    public double getOverallScore() {
        int sum = 0;
        String[] parts = scores.split(",");
        int[] numbers = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i]);
            sum += numbers[i];
        }
        return (double) sum / numbers.length;
    }

    public String getCompetitorLevel(){
        double avg_score = getOverallScore();
        if (avg_score>0 && avg_score<4){
            return "Beginner";
        }else if (avg_score<8){
            return "Intermediate";
        } else if (avg_score<=10) {
            return "Advanced";
        }else {
            return "Invalid";
        }
    }

    public String getFullDetails() {
        return String.format("Competitor number %d, name %s, country %s. %s is a %s aged %d and has an overall score of %.1f.",
                competitorId, name.getFullName(), country, name.getFirstName(), level, age, getOverallScore());
    }
}