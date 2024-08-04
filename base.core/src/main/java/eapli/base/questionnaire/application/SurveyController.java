package eapli.base.questionnaire.application;/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.questionnaire.domain.*;
import eapli.base.questionnaire.dto.*;
import eapli.base.questionnaire.repositories.SurveyRepository;
import eapli.framework.application.UseCaseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@UseCaseController
public class SurveyController {
    private Questionnaire questionnaire = null;
    private final List<Section> sections = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private final SurveyRepository repo = PersistenceContext.repositories().surveys();
    private Content content;
    private Survey newSurvey;

    public SurveyDTO buildSurvey(final SurveyDTO dto, int flagFile) {
        if (flagFile == 1) {
            newSurvey = new SurveyDTOParser().valueOf(dto);
            content = new Content(dto.content);
            newSurvey.addContentToSurvey(content);
            repo.save(newSurvey);
            return newSurvey.toDTO();
        } else {
            newSurvey = new SurveyDTOParser().valueOf(dto);
            content = new Content(questionnaire);
            newSurvey.addContentToSurvey(content);
            repo.save(newSurvey);
            return newSurvey.toDTO();
        }
    }
    public void saveSurvey(Survey survey) {
        repo.save(survey);
    }

    public QuestionnaireDTO buildQuestionnaire(final QuestionnaireDTO dto) {
        questionnaire = new QuestionnaireDTOParser().valueOf(dto);
        questionnaire.buildSections(sections);
        return questionnaire.toDTO();
    }

    public void buildSections(final SectionDTO dto) {
        final var newSection = new SectionDTOParser().valueOf(dto);
        sections.add(newSection);
        newSection.buildContent(questions);
    }

    public QuestionDTO buildQuestions(final QuestionDTO dto) {
        final var newQuestion = new QuestionDTOParser().valueOf(dto);
        questions.add(newQuestion);
        return newQuestion.toDTO();
    }

    public void buildQuestions(List<Answer> listOfAnswers) {
        //repo.getQuestionnaireUsingAlphanumericCode();
    }

    public void cleanQuestionList() {
        questions = new ArrayList<>();
    }

    public String receiveSurveyString() {
        return newSurvey.toString();
    }

    public String receiveFullQuestionnaireString() {
        return content.toString();
    }

    public Optional<Survey> surveyToBeAnswered(String alphanumericCode) {
        return repo.getQuestionnaireUsingAlphanumericCode(alphanumericCode);
    }

    public List<String> findSurveyByCustomer(Customer customer) {
        return repo.findSurveyByCustomer(customer);
    }

}
