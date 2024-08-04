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
import eapli.base.questionnaire.domain.Section;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.Description;
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
public class SectionBuilder implements DomainFactory<Section> {

    private String sectionId;
    private String sectionTitle;
    private String sectionDescription;
    private String obligatoriness;
    private String repeatability;
    private final List<Question> questions = new ArrayList<>();

    protected SectionBuilder() {

    }

    public SectionBuilder(String sectionId, String sectionTitle, String obligatoriness) {
        Preconditions.noneNull(sectionId, sectionTitle, obligatoriness);
        this.sectionId = sectionId;
        this.sectionTitle = sectionTitle;
        this.obligatoriness = obligatoriness;
    }

    public SectionBuilder withSectionDescription(final String sectionDescription) {
        this.sectionDescription = sectionDescription;
        return this;
    }

    public SectionBuilder withRepeability(final String repeatability) {
        this.repeatability = repeatability;
        return this;
    }


    @Override
    public Section build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        return new Section(sectionId, sectionTitle, Description.valueOf(sectionDescription), Obligatoriness.valueOf(obligatoriness), repeatability, questions);
    }
}
