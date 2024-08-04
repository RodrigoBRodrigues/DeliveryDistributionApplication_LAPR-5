package eapli.base.questionnaire.dto;

public class SectionDTO {
    public String sectionId;
    public String sectionTitle;
    public String descriptionString;
    public String obligatoriness;
    public String repeatability;

    public SectionDTO(String sectionId, String sectionTitle, String descriptionString, String obligatoriness, String repeatability) {
        this.sectionId = sectionId;
        this.sectionTitle = sectionTitle;
        this.descriptionString = descriptionString;
        this.obligatoriness = obligatoriness;
        this.repeatability = repeatability;
    }
}
