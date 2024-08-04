package eapli.base.questionnaire.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.List;

@Embeddable
public class Content implements ValueObject {
    @Column(name = "questionnaire", length = 100000)
    private String fullQuestionnaire;

    @Transient
    private final StringBuilder stringBuilder = new StringBuilder();


    public Content(Questionnaire questionnaire) {
        buildQuestionnaireSyntax(questionnaire);
        stringBuilder.append("\n");
        stringBuilder.append("LIST OF SECTIONS:\n");
        stringBuilder.append("\n");
        buildSectionSyntax(questionnaire.buildSections());

        fullQuestionnaire = stringBuilder.toString();
    }

    public Content(String fullQuestionnaire) {
        this.fullQuestionnaire = fullQuestionnaire;
    }


    private void buildQuestionnaireSyntax(Questionnaire questionnaire) {
        stringBuilder.append(String.format("ID: %s%n", questionnaire.idToBuildContent()));
        stringBuilder.append(String.format("TITLE: %s%n", questionnaire.titleToBuildContent()));
        if (questionnaire.welcomeMessageToBuildContent() != null) {
            stringBuilder.append(String.format("WELCOME MESSAGE: %s%n", questionnaire.welcomeMessageToBuildContent()));
        }
        stringBuilder.append(String.format("FINAL MESSAGE: %s%n", questionnaire.finalMessageToBuildContent()));

    }

    private void buildSectionSyntax(List<Section> sections) {
        for (Section section : sections) {
            stringBuilder.append(String.format("SECTION ID: %s%n", section.idToBuildContent()));
            stringBuilder.append(String.format("SECTION TITLE: %s%n", section.titleToBuildContent()));
            stringBuilder.append(String.format("SECTION DESCRIPTION: %s%n", section.descriptionToBuildContent()));
            stringBuilder.append(String.format("OBLIGATORINESS: %s%n", section.obligatorinessToBuildContent()));
            if (section.repeatabilityToBuildContent() != null) {
                stringBuilder.append(String.format("REPEATABILITY: %s%n", section.repeatabilityToBuildContent()));
            }
            stringBuilder.append("\nCONTENT:\n");
            for (Question question : section.contentToBuildContent()) {
                buildQuestionSyntax(question);
            }
            stringBuilder.append("\n");

        }
    }

    private void buildQuestionSyntax(Question question) {
        stringBuilder.append(String.format("%nQUESTION ID: %s%n", question.idToBuildContent()));
        stringBuilder.append(String.format("Q: %s%n", question.questionMessageToBuildContent()));
        if (question.instructionToBuildContent() != null) {
            stringBuilder.append(String.format("INSTRUCTION: %s%n", question.instructionToBuildContent()));
        }
        stringBuilder.append(String.format("OBLIGATORINESS: %s%n", question.obligatorinessToBuildContent()));
        stringBuilder.append(String.format("TYPE: %s%n", question.typeToBuildContent()));
        if (question.extraInfoToBuildContent() != null) {
            stringBuilder.append(String.format("EXTRA INFO: %s%n", question.extraInfoToBuildContent()));
        }
    }

    @Override
    public String toString() {
        return fullQuestionnaire;
    }

    protected Content() {
        //ORM
    }
}
