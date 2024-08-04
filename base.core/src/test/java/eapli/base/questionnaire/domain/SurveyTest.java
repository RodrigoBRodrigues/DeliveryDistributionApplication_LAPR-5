package eapli.base.questionnaire.domain;

import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.dto.SurveyDTO;
import eapli.framework.general.domain.model.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SurveyTest {

    @Test
    void createSurvey() {
        new Survey(AlphaNumericCode.valueOf("code"), Description.valueOf("description"), Period.valueOf("2"), new Content("test"));
    }

    @Test
    void addContentToSurvey() {
        Survey survey = new Survey(AlphaNumericCode.valueOf("code"), Description.valueOf("description"), Period.valueOf("2"), new Content("test"));
        survey.addContentToSurvey(new Content("Full Questionnaire"));
    }

    @Test
    void testToString() {
    }

    @Test
    void toDTO() {
        Survey survey = new Survey(AlphaNumericCode.valueOf("code"), Description.valueOf("description"), Period.valueOf("2"), new Content("test"));
        SurveyDTO expected = survey.toDTO();
        SurveyDTO actual = expected;

        assertEquals(expected, actual);
    }


}