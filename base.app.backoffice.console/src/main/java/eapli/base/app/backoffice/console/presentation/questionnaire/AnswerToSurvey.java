package eapli.base.app.backoffice.console.presentation.questionnaire;

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.domain.Gender;
import eapli.base.grammar.EvalVisitor;
import eapli.base.grammar.LabeledExprLexer;
import eapli.base.grammar.LabeledExprParser;
import eapli.base.questionnaire.application.SurveyController;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.questionnaire.domain.Survey;
import eapli.base.usermanagement.application.AddCustomerController;
import eapli.framework.presentation.console.AbstractUI;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Objects;

public class AnswerToSurvey extends AbstractUI {

    SurveyController surveyController = new SurveyController();
    AddCustomerController customerController = new AddCustomerController();
    @Override
    protected boolean doShow() {
        AddCustomerController customerController = new AddCustomerController();
        List<Customer> customersOfTheSurvey = customerController.getCustomersWithLessThanAge(25);

        List<Customer> customersOfTheSurveyMale = customerController.getCustomersWithGender(Gender.MALE);

        customersOfTheSurvey.retainAll(customersOfTheSurveyMale);


        SurveyController surveyController = new SurveyController();

        EvalVisitor eval = new EvalVisitor();

        List<Answer> answerList = eval.getAnswers();
        Survey survey = null;
        if (surveyController.surveyToBeAnswered("12").isPresent()) {
            survey = surveyController.surveyToBeAnswered("12").get();
        }
        LabeledExprLexer lexer;
        lexer = new LabeledExprLexer(new ANTLRInputStream(Objects.requireNonNull(survey).content().toString()));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog();
        eval.visit(tree);


        survey.setAnswers(answerList);
        surveyController.saveSurvey(survey);

        return false;
    }

    @Override
    public String headline() {
        return "Answer survey";
    }
}
