package eapli.base.questionnaire.repositories;

import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.domain.Answer;
import eapli.framework.domain.repositories.DomainRepository;

public interface AnswerRepository extends DomainRepository <AlphaNumericCode, Answer> {

    Iterable <Answer> getAnswersByQuestionnaireId(AlphaNumericCode alphaNumericCode);

    Iterable <String> costumersId ();

}
