package eapli.base.questionnaire.strategy;

import eapli.base.questionnaire.domain.Question;

public class SingleChoiceInputQuestionStrategy implements QuestionStrategyInterface{
    @Override
    public String display(Question question) {
        return question.questionMessageToBuildContent();
    }
}
