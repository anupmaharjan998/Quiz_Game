package Model;

import java.util.UUID;

public class Question {
    private String quesId;
    private String question;
    private String options; // Can be stored as a JSON string
    private String correct;
    private String levelId;
    private String levelName;

    public Question(String question, String options, String correct, String levelId) {
        this.quesId = UUID.randomUUID().toString(); // Generate a unique ID
        this.question = question;
        this.options = options;
        this.correct = correct;
        this.levelId = levelId;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }
}
