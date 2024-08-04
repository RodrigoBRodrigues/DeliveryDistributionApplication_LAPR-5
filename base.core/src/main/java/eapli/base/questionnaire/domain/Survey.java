package eapli.base.questionnaire.domain;

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.domain.Gender;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.questionnaire.dto.SurveyDTO;
import eapli.base.usermanagement.application.AddCustomerController;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Description;
import eapli.framework.representations.dto.DTOable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Survey implements AggregateRoot<AlphaNumericCode>, DTOable<SurveyDTO> {
    @Id
    @Column(nullable = false)
    private AlphaNumericCode alphaNumericCode;

    @Embedded
    private Description description;

    @Embedded
    @Column(name = "period_in_days")
    private Period period;

    @Embedded
    private Questionnaire questionnaire;

    @Embedded
    private Content content;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "answers")
    private List<Answer> answers;

    @OneToMany
    @Column(name = "customers_in_survey")
    private List<Customer> customersSelected;


    protected Survey() {
        //ORM
    }


    @Override
    public boolean sameAs(Object other) {
        Survey otherSurvey = (Survey) other;
        return alphaNumericCode.equals(otherSurvey.alphaNumericCode);
    }

    public AlphaNumericCode alphaNumericCode() {
        return alphaNumericCode;
    }

    public Description description() {
        return description;
    }

    public Period period() {
        return period;
    }

    public Questionnaire questionnaire() {
        return questionnaire;
    }

    public Content content() {
        return content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public AlphaNumericCode identity() {
        return alphaNumericCode;
    }

    public void addContentToSurvey(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        if (questionnaire != null) {
            return "Survey{" +
                    "alphaNumericCode=" + alphaNumericCode +
                    ", description=" + description +
                    ", period=" + period +
                    ", questionnaire=" + questionnaire +
                    ", content=" + content +
                    '}';
        } else {
            return "Survey{" +
                    "alphaNumericCode=" + alphaNumericCode +
                    ", description=" + description +
                    ", period=" + period +
                    ", content=" + content +
                    '}';
        }
    }

    public Survey(AlphaNumericCode alphaNumericCode, Description description, Period period, Questionnaire questionnaire) {
        this.alphaNumericCode = alphaNumericCode;
        this.description = description;
        this.period = period;
        this.questionnaire = questionnaire;
        this.content = new Content(questionnaire);
        setRules();
    }

    public Survey(AlphaNumericCode alphaNumericCode, Description description, Period period) {
        this.alphaNumericCode = alphaNumericCode;
        this.description = description;
        this.period = period;
        setRules();
    }

    public Survey(AlphaNumericCode alphaNumericCode, Description description, Period period, Content content) {
        this.alphaNumericCode = alphaNumericCode;
        this.description = description;
        this.period = period;
        this.content = content;
        setRules();
    }

    public Survey(AlphaNumericCode alphaNumericCode, Description description, Period period, Content content, List<Answer> answers) {
        this.alphaNumericCode = alphaNumericCode;
        this.description = description;
        this.period = period;
        this.content = content;
        this.answers = answers;
        setRules();
    }

    public void setRules() {
        AddCustomerController customerController = new AddCustomerController();
        List<Customer> customersOfTheSurvey = customerController.getCustomersWithLessThanAge(25);

        List<Customer> customersOfTheSurveyMale = customerController.getCustomersWithGender(Gender.MALE);
        customersOfTheSurvey.retainAll(customersOfTheSurveyMale);

        this.customersSelected = customersOfTheSurveyMale;
    }

    /**
     * Showcase the {@link DTOable} interface. In this case it is the Dish class
     * that dictates the DTO structure.
     */
    @Override
    public SurveyDTO toDTO() {
        return new SurveyDTO(alphaNumericCode.code(),
                description.toString(),
                period.toString(), content.toString());
    }
}
