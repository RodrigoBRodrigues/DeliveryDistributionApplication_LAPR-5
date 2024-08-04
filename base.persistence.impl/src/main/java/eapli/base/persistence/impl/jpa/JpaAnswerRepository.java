package eapli.base.persistence.impl.jpa;

import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.questionnaire.repositories.AnswerRepository;

import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JpaAnswerRepository extends BasepaRepositoryBase <Answer,Long,Long> implements AnswerRepository {

    public JpaAnswerRepository(String persistenceUnitName) {
        super("id");
    }

    @Override
    public List<Answer> getAnswersByQuestionnaireId(AlphaNumericCode id) {
       List<Answer> answers = new ArrayList<>();
        final TypedQuery<Answer> query = super.createQuery(
                "SELECT p FROM Answer p WHERE p.id != 0",Answer.class);
        for (Answer a : query.getResultList()) {
            if (a.survey().alphaNumericCode().toString().equals(id.toString())) {
                answers.add(a);
            }
        }
        return  answers;
    }

    @Override
    public Iterable<String> costumersId() {
        List<String> c = new ArrayList<>();
        final TypedQuery<Answer> query = super.createQuery(
                "SELECT p FROM Answer p WHERE p.id != 0",Answer.class);
        for (Answer a : query.getResultList()) {
            if (!c.contains(a.getCustomer().identity().toString())) {
                c.add(a.getCustomer().identity().toString());
            }
        }
        return c;
    }

    @Override
    public Optional<Answer> ofIdentity(AlphaNumericCode id) {
        return Optional.empty();
    }

    @Override
    public boolean containsOfIdentity(AlphaNumericCode id) {
        return AnswerRepository.super.containsOfIdentity(id);
    }

    @Override
    public boolean contains(Answer entity) {
        return AnswerRepository.super.contains(entity);
    }

    @Override
    public void deleteOfIdentity(AlphaNumericCode entityId) {
    }

}
