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
package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.product.domain.Brand;
import eapli.base.product.domain.Product;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.questionnaire.domain.Survey;
import eapli.base.questionnaire.dto.SurveyDTO;
import eapli.base.questionnaire.repositories.SurveyRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
class JpaSurveyRepository
        extends JpaAutoTxRepository<Survey, AlphaNumericCode, AlphaNumericCode>
        implements SurveyRepository {

    public JpaSurveyRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaSurveyRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }


    @Override
    public Optional<Survey> getQuestionnaireUsingAlphanumericCode(String alphaNumericCode) {
        final Map<String, Object> params = new HashMap<>();
        params.put("alphaNumericCode", alphaNumericCode);
        Optional<Survey> result = matchOne("ALPHANUMERICCODE=:alphaNumericCode", params);
        return result;
    }

    @Override
    public List<String> findSurveyByCustomer(Customer customer) {
        EntityManager em = entityManager();
        String sql = "SELECT SURVEY_ALPHANUMERICCODE FROM SURVEY_CUSTOMER WHERE CUSTOMERSSELECTED_CUSTOMER_ID = :CUSTOMER";
        Query query = em.createNativeQuery(sql).setParameter("CUSTOMER", customer);


        return query.getResultList();
    }
}
