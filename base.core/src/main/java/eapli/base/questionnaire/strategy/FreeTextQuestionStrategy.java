package eapli.base.questionnaire.strategy;

import eapli.base.grammar.EvalVisitor;
import eapli.base.grammar.LabeledExprParser;
import eapli.base.questionnaire.domain.Question;
import org.antlr.v4.runtime.ParserRuleContext;

public class FreeTextQuestionStrategy implements QuestionStrategyInterface {
    @Override
    public String display(Question question) {
        return new EvalVisitor().visitFree_text(new LabeledExprParser.Free_textContext(new ParserRuleContext(),0));
    }
}
