/*
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
package eapli.base.questionnaire.builder;

import eapli.base.questionnaire.domain.Questionnaire;
import eapli.base.questionnaire.domain.Section;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.validations.Preconditions;

import java.util.ArrayList;
import java.util.List;


/**
 * A factory for Customer entities.
 *
 * This class demonstrates the use of the factory (DDD) pattern using a fluent
 * interface. it acts as a Builder (GoF).
 *
 * @author Rui Pina 1201568@isep.ipp.pt 21/04/2022
 */
public class QuestionnaireBuilder implements DomainFactory<Questionnaire> {

    private String welcomeMessage;
    private String finalMessage;
    private String id;
    private String title;
    private List<Section> sections = new ArrayList<>();

    public QuestionnaireBuilder() {

    }

    public QuestionnaireBuilder(String id, String title) {
        Preconditions.noneNull(id, title);
        this.id = id;
        this.title = title;
    }

    public QuestionnaireBuilder withWelcomeMessage(final String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
        return this;
    }

    public QuestionnaireBuilder withFinalMessage(final String finalMessage) {
        this.finalMessage = finalMessage;
        return this;
    }


    @Override
    public Questionnaire build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        return new Questionnaire(id, title, welcomeMessage, sections, finalMessage);
    }
}
