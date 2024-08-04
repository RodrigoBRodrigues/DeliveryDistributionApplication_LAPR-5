package eapli.base.questionnaire.strategy;

import eapli.base.grammar.EvalVisitor;
import eapli.base.grammar.LabeledExprParser;
import eapli.base.questionnaire.domain.QuestionType;
import org.antlr.v4.runtime.ParserRuleContext;

public interface QuestionBehaviour {
    static QuestionStrategyInterface getBehavior(QuestionType questionType) {
        switch (questionType) {
            case FREE_TEXT:
                return new FreeTextQuestionStrategy();
            case NUMERIC:
                return new NumericQuestionStrategy();
            case SINGLE_CHOICE:
                return new SingleChoiceQuestionStrategy();
            case SINGLE_CHOICE_INPUT_VALUE:
                return new SingleChoiceInputQuestionStrategy();
            case MULTIPLE_CHOICE:
                return new MultipleChoiceQuestionStrategy();
            case MULTIPLE_CHOICE_INPUT_VALUE:
                return new MultipleInputChoiceQuestionStrategy();
            case SCALING_OPTIONS:
                return new ScalingOptionsQuestionStrategy();
            case SORTING_OPTIONS:
                return new SortingOptionsQuestionStrategy();
            default:
                return null;
        }
    }
}