package eapli.base.questionnaire.domain;

import eapli.base.questionnaire.dto.QuestionDTO;
import eapli.base.questionnaire.dto.QuestionnaireDTO;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Questionnaire implements DTOable<QuestionnaireDTO>, DomainEntity<String> {
    @Transient
    private String id;
    @Transient
    private String title;
    @Transient
    private String welcomeMessage;
    @Transient
    private List<Section> sections;
    @Transient
    private String finalMessage;

    protected Questionnaire() {
        //ORM
    }


    protected String idToBuildContent() {
        return id;
    }


    protected String titleToBuildContent() {
        return title;
    }


    protected String welcomeMessageToBuildContent() {
        return welcomeMessage;
    }


    protected List<Section> buildSections() {
        return sections;
    }

    public void buildSections(List<Section> sections) {
        this.sections = sections;
    }

    public String finalMessageToBuildContent() {
        return finalMessage;
    }


    @Override
    public String toString() {
        if (welcomeMessage != null) {
            return "Questionnaire " +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", welcomeMessage='" + welcomeMessage + '\'' +
                    ", sections=" + sections +
                    ", finalMessage='" + finalMessage;
        } else {
            return "Questionnaire " +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", sections=" + sections +
                    ", finalMessage='" + finalMessage;
        }
    }

    public Questionnaire(String id, String title, String welcomeMessage, List<Section> sections, String finalMessage) {
        Preconditions.noneNull(id, title, finalMessage);
        this.id = id;
        this.title = title;
        this.welcomeMessage = welcomeMessage;
        this.sections = sections;
        this.finalMessage = finalMessage;
    }

    @Override
    public QuestionnaireDTO toDTO() {
        return new QuestionnaireDTO(id, title, welcomeMessage, finalMessage);
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String identity() {
        return id;
    }
}
