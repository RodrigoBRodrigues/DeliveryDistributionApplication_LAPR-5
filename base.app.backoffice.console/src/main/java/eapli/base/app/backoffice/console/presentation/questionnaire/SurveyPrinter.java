package eapli.base.app.backoffice.console.presentation.questionnaire;

import eapli.base.questionnaire.domain.Survey;
import eapli.framework.visitor.Visitor;

public class SurveyPrinter implements Visitor<Survey> {

    @Override
    public void visit(final Survey visitee) {
        System.out.printf("ID: %s | Description: %s", visitee.identity(), visitee.description());
    }

}
