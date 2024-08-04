package eapli.base.questionnaire.repositories;

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.domain.Survey;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends DomainRepository<AlphaNumericCode, Survey> {
    Optional<Survey> getQuestionnaireUsingAlphanumericCode(String alphaNumericCode);

    List<String> findSurveyByCustomer(Customer customer);
}
