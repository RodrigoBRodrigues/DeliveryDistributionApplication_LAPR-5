package eapli.base.questionnaire.dto;

public class QuestionDTO {
    public String questionId;
    public String questionMessage;
    public String instruction;
    public String questionType;
    public String obligatoriness;
    public String extraInfo;

    public QuestionDTO(String questionId, String questionMessage, String instruction, String questionType, String obligatoriness, String extraInfo) {
        this.questionId = questionId;
        this.questionMessage = questionMessage;
        this.instruction = instruction;
        this.questionType = questionType;
        this.obligatoriness = obligatoriness;
        this.extraInfo = extraInfo;
    }
}
