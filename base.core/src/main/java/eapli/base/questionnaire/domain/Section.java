package eapli.base.questionnaire.domain;

import eapli.base.questionnaire.dto.SectionDTO;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.general.domain.model.Description;
import eapli.framework.representations.dto.DTOable;

import java.util.List;

public class Section implements DTOable<SectionDTO>, DomainEntity<String> {
    private String id;
    private String title;
    private Description description;
    private Obligatoriness obligatoriness;
    private String repeatability;
    private List<Question> content;

    public Section(String id, String title, Description description, Obligatoriness obligatoriness, String repeatability, List<Question> content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.obligatoriness = obligatoriness;
        this.repeatability = repeatability;
        this.content = content;
    }

    public String idToBuildContent() {
        return id;
    }

    public String titleToBuildContent() {
        return title;
    }

    public Description descriptionToBuildContent() {
        return description;
    }

    public Obligatoriness obligatorinessToBuildContent() {
        return obligatoriness;
    }

    public String repeatabilityToBuildContent() {
        return repeatability;
    }


    public List<Question> contentToBuildContent() {
        return content;
    }

    public void buildContent(List<Question> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        if(repeatability!=null) {
            return "Section{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", description=" + description +
                    ", obligatoriness=" + obligatoriness +
                    ", repeatability='" + repeatability + '\'' +
                    ", content=" + content;
        }else {
            return "Section" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", description=" + description +
                    ", obligatoriness=" + obligatoriness +
                    ", content=" + content;
        }
    }

    @Override
    public SectionDTO toDTO() {
        return new Section(id, title, description, obligatoriness, repeatability, content).toDTO();
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
