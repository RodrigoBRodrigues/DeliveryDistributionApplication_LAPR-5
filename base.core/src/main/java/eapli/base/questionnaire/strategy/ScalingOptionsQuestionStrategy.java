package eapli.base.questionnaire.strategy;

import eapli.base.questionnaire.domain.Question;

public class ScalingOptionsQuestionStrategy implements QuestionStrategyInterface {
    @Override
    public String display(Question question) {
        return question.questionMessageToBuildContent();
    }
}
