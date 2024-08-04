package eapli.base.questionnaire.dto;/*
 * Copyright (c) 2013-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import eapli.base.questionnaire.builder.QuestionBuilder;
import eapli.base.questionnaire.domain.*;
import eapli.framework.representations.dto.DTOParser;

/**
 * @author Paulo Gandra de Sousa 2021/04/28
 */
public class QuestionDTOParser implements DTOParser<QuestionDTO, Question> {


    public QuestionDTOParser() {
    }

    @Override
    public Question valueOf(final QuestionDTO dto) {
        return new QuestionBuilder(dto.questionId,
                dto.questionMessage, dto.questionType, dto.obligatoriness)
                .withInstruction(dto.instruction)
                .withExtraInfo(dto.extraInfo)
                .build();

    }


}
