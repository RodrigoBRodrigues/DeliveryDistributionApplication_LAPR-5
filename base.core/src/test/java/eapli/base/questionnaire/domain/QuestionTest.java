package eapli.base.questionnaire.domain;

import eapli.base.questionnaire.builder.QuestionBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void questionWithAllMandatoryArgumentsPasses() {
        Question question = new Question("id", "questionMessage", null, QuestionType.FREE_TEXT, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void questionWithSomeMandatoryArgumentsNullFails() {
        Question question = new Question(null, "questionMessage", null, QuestionType.FREE_TEXT, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void questionWithAllArgumentsNullFails() {
        Question question = new Question(null, null, null, null, null, null);

    }

    @Test
    public void questionBuilderWithAllMandatoryArgumentsPass() {
        QuestionBuilder question = new QuestionBuilder("id", "questionMessage", QuestionType.MULTIPLE_CHOICE.toString(), Obligatoriness.OPTIONAL.toString());
        question.build();
    }

    @Test
    public void questionBuilderWithAllArgumentsPass() {
        QuestionBuilder question = new QuestionBuilder("id", "questionMessage", QuestionType.MULTIPLE_CHOICE.toString(), Obligatoriness.OPTIONAL.toString());
        question.withExtraInfo("extra info");
        question.withInstruction("instruction");
        question.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void questionBuilderWithNullMandatoryArgumentoFails() {
        QuestionBuilder question = new QuestionBuilder(null, "questionMessage", QuestionType.MULTIPLE_CHOICE.toString(), Obligatoriness.OPTIONAL.toString());
        question.withExtraInfo("extra info");
        question.withInstruction("instruction");
        question.build();
    }
}