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

import eapli.base.questionnaire.domain.Obligatoriness;
import eapli.base.questionnaire.domain.Question;
import eapli.base.questionnaire.domain.QuestionType;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.validations.Preconditions;


/**
 * A factory for Customer entities.
 * <p>
 * This class demonstrates the use of the factory (DDD) pattern using a fluent
 * interface. it acts as a Builder (GoF).
 *
 * @author Rui Pina 1201568@isep.ipp.pt 21/04/2022
 */
public class QuestionBuilder implements DomainFactory<Question> {

    private String id;
    private String questionMessage;
    private String instruction;
    private String type;
    private String obligatoriness;
    private String extraInfo;

    public QuestionBuilder() {

    }

    public QuestionBuilder(String id, String questionMessage, String type, String obligatoriness) {
        Preconditions.noneNull(id, questionMessage, type, obligatoriness);
        this.id = id;
        this.questionMessage = questionMessage;
        this.type = type;
        this.obligatoriness = obligatoriness;

    }

    public QuestionBuilder withInstruction(final String instruction) {
        this.instruction = instruction;
        return this;
    }

    public QuestionBuilder withExtraInfo(final String extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }


    @Override
    public Question build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        return new Question(id, questionMessage, instruction, QuestionType.valueOf(type), Obligatoriness.valueOf(obligatoriness), extraInfo);
    }
}
