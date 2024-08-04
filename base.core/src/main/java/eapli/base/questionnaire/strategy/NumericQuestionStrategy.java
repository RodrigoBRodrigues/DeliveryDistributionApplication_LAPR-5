package eapli.base.questionnaire.strategy;

import eapli.base.questionnaire.domain.Question;

public class NumericQuestionStrategy implements QuestionStrategyInterface{
    @Override
    public String display(Question question) {
        return question.questionMessageToBuildContent();
    }
}
