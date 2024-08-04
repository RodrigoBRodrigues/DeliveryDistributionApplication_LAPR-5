package eapli.base.questionnaire.dto;

public class QuestionnaireDTO {
    public String questionnaireId;
    public String questionnaireTitle;
    public String welcomeMessage;
    public String finalMessage;


    public QuestionnaireDTO(String questionnaireId, String questionnaireTitle, String welcomeMessage, String finalMessage) {
        this.questionnaireId = questionnaireId;
        this.questionnaireTitle = questionnaireTitle;
        this.welcomeMessage = welcomeMessage;
        this.finalMessage = finalMessage;
    }
}
