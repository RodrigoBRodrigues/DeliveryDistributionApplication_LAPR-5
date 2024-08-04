package eapli.base.questionnaire.domain;

import eapli.base.questionnaire.builder.QuestionnaireBuilder;
import eapli.base.questionnaire.dto.QuestionnaireDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionnaireTest {

    @Test
    public void withOptionalArgumentsPasses() {
        Questionnaire questionnaire = new Questionnaire("1"
                , "titulo do questionario"
                , null,
                null
                , "mensagem final");

    }

    @Test(expected = IllegalArgumentException.class)
    public void withoutMandatoryArgumentsFails() {
        Questionnaire questionnaire = new Questionnaire("1"
                , null
                , null,
                null
                , "mensagem final");

    }

    @Test(expected = IllegalArgumentException.class)
    public void withoutAnyMandatoryArgumentsFails() {
        Questionnaire questionnaire = new Questionnaire(null
                , null
                , "Welcome Message",
                null
                , null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void questionnaireBuilderWithoutAnyMandatoryArgumentsFails() {
        QuestionnaireBuilder questionnaireBuilder = new QuestionnaireBuilder("2","titulo");
        questionnaireBuilder.withWelcomeMessage("welcomeMessage");
        questionnaireBuilder.build();

    }

    @Test
    public void buildSections() {
        QuestionnaireBuilder questionnaireBuilder = new QuestionnaireBuilder("2","titulo");
        questionnaireBuilder.withWelcomeMessage("welcomeMessage");
        questionnaireBuilder.withFinalMessage("finalMessage");
        questionnaireBuilder.build();
    }


}