package eapli.base.questionnaire.strategy;

import eapli.base.questionnaire.domain.Question;

public class SortingOptionsQuestionStrategy implements QuestionStrategyInterface {
    @Override
    public String display(Question question) {
        return question.questionMessageToBuildContent();
    }
}
