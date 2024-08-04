package eapli.base.questionnaire.domain;

import eapli.base.questionnaire.dto.QuestionDTO;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;

public class Question implements DTOable<QuestionDTO>, DomainEntity<String> {
    private final String id;
    private final String questionMessage;
    private final String instruction;
    private final QuestionType type;
    private final Obligatoriness obligatoriness;
    private final String extraInfo;

    public Question(String id, String questionMessage, String instruction, QuestionType type, Obligatoriness obligatoriness, String extraInfo) {
        Preconditions.noneNull(id, questionMessage, type);
        this.id = id;
        this.questionMessage = questionMessage;
        this.instruction = instruction;
        this.type = type;
        this.obligatoriness = obligatoriness;
        this.extraInfo = extraInfo;
    }

    public String idToBuildContent() {
        return id;
    }


    public String questionMessageToBuildContent() {
        return questionMessage;
    }


    public QuestionType typeToBuildContent() {
        return type;
    }


    public Obligatoriness obligatorinessToBuildContent() {
        return obligatoriness;
    }


    public String extraInfoToBuildContent() {
        return extraInfo;
    }

    public String instructionToBuildContent() {
        return instruction;
    }

    @Override
    public String toString() {
        if (instruction != null) {
            return "Question " +
                    "id='" + id + '\'' +
                    ", questionMessage='" + questionMessage + '\'' +
                    ", instruction='" + instruction + '\'' +
                    ", type=" + type +
                    ", obligatoriness=" + obligatoriness +
                    ", extraInfo='" + extraInfo;
        } else {
            return "Question " +
                    "id='" + id + '\'' +
                    ", questionMessage='" + questionMessage + '\'' +
                    ", type=" + type +
                    ", obligatoriness=" + obligatoriness +
                    ", extraInfo='" + extraInfo;
        }
    }

    @Override
    public QuestionDTO toDTO() {
        return new QuestionDTO(id, questionMessage, instruction, type.toString(), obligatoriness.toString(), extraInfo);
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String identity() {
        return null;
    }
}
