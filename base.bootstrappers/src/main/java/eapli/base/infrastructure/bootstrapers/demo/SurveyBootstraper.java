package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.application.SpecifyNewProductController;
import eapli.base.product.domain.*;
import eapli.base.productCategory.application.RegisterNewCategoryController;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;
import eapli.base.questionnaire.application.SurveyController;
import eapli.base.questionnaire.domain.Questionnaire;
import eapli.base.questionnaire.dto.SurveyDTO;
import eapli.base.questionnaire.repositories.SurveyRepository;
import eapli.framework.actions.Action;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SurveyBootstraper implements Action {

    private String questionnaire = null;

    @Override
    public boolean execute() {
        String questionnairePath;
        questionnairePath = "questionnaire/Question.txt";
        SurveyDTO surveyDTO = new SurveyDTO("12","Electronics Questionnaire", "3",null);
        try {
            questionnaire = Files.readString(Path.of(questionnairePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        surveyDTO.content = questionnaire;
        SurveyController controller = new SurveyController();
        controller.buildSurvey(surveyDTO, 1);

        /*String questionnairePath2;
        questionnairePath2 = "questionnaire/QuestionnaireSmartphones.txt";
        SurveyDTO surveyDTO2 = new SurveyDTO("0","Smartphones Questionnaire", "1",null);
        try {
            questionnaire = Files.readString(Path.of(questionnairePath2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        surveyDTO2.content = questionnaire;
        controller.buildSurvey(surveyDTO2, 1);*/
        return false;
    }


}
