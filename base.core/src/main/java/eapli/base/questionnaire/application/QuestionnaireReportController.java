package eapli.base.questionnaire.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.questionnaire.domain.Survey;
import eapli.base.questionnaire.repositories.AnswerRepository;
import eapli.base.questionnaire.repositories.SurveyRepository;

import java.util.ArrayList;
import java.util.List;


public class QuestionnaireReportController {

    private final SurveyRepository surveyRepository = PersistenceContext.repositories().surveys();
    private final AnswerRepository answerRepository = PersistenceContext.repositories().answers();

    public Iterable <Survey> getSurveys () {
        return surveyRepository.findAll();
    }

    public Iterable <Answer> getAnswersByQuestionnaireId (AlphaNumericCode id) {
        return answerRepository.getAnswersByQuestionnaireId (id);
    }

    public List <Integer> universe () {
        List <Integer> universe = new ArrayList<>();
        List <String> aux = new ArrayList<>();
        List <Answer> answerList = (List<Answer>) answerRepository.findAll();
        for (Answer a : answerList) {
            if (!aux.contains(a.getCustomer().toString())) {
                aux.add(a.getCustomer().toString());
            }
        }
        universe.add(aux.size());
        universe.add((int)PersistenceContext.repositories().customers().size());
        return universe;
    }
}
