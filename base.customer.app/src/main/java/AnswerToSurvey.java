import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.domain.Gender;
import eapli.base.clientusermanagement.repositories.CustomerRepository;
import eapli.base.grammar.EvalVisitor;
import eapli.base.grammar.LabeledExprLexer;
import eapli.base.grammar.LabeledExprParser;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.application.SurveyController;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.questionnaire.domain.Survey;
import eapli.base.usermanagement.application.AddCustomerController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AnswerToSurvey extends AbstractUI {

    private SurveyController surveyController = new SurveyController();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    protected boolean doShow() {

        AddCustomerController customerController = new AddCustomerController();
        List<Customer> customersOfTheSurvey = customerController.getCustomersWithLessThanAge(25);

        List<Customer> customersOfTheSurveyMale = customerController.getCustomersWithGender(Gender.MALE);

        customersOfTheSurvey.retainAll(customersOfTheSurveyMale);

        Customer customer = customerController.findByUsername(authz.session().get().authenticatedUser().identity()).get();


        List<String> toDo = surveyController.findSurveyByCustomer(customer);
        String option;
        String result;
        int i = 0;
        do {
            System.out.println("Surveys to be answered:");
            System.out.println("-----------------------");
            for (String alphanumericCodesOfTheSurveysToBeAnswered : toDo) {
                Survey survey = surveyController.surveyToBeAnswered(alphanumericCodesOfTheSurveysToBeAnswered).get();
                System.out.println(i + ". " + survey.description());
                i++;
            }

            option = Console.readLine("Select the option from the above:");
        } while (Integer.parseInt(option) > i || Integer.parseInt(option) < 0);
        result = toDo.get(Integer.parseInt(option));

        SurveyController surveyController = new SurveyController();


        EvalVisitor eval = new EvalVisitor();

        eval.setCustomer(customer);

        List<Answer> answerList = eval.getAnswers();
        Survey survey = null;
        if (surveyController.surveyToBeAnswered(result).isPresent()) {
            survey = surveyController.surveyToBeAnswered(result).get();
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
