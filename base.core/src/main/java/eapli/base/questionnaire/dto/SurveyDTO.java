package eapli.base.questionnaire.dto;

import eapli.framework.representations.dto.DTO;

@DTO
public class SurveyDTO {

    public String alphanumericCodeString;
    public String descriptionString;
    public String period;
    public String content;



    public SurveyDTO(String alphanumericCodeString
            , String descriptionString
            , String period, String content) {
        this.alphanumericCodeString = alphanumericCodeString;
        this.descriptionString = descriptionString;
        this.period = period;
        this.content = content;
    }
}
