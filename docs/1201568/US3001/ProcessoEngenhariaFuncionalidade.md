# US3001
=======================================


# 1. Requisitos

**US3001** As Sales Manager, I want to create a new questionnaire to be further answered by customers meeting the specified criteria (e.g.: have ordered a given product; belong to a given age group).

A interpretação feita deste requisito foi no sentido de criar um questionário (txt ou pela aplicação), este questionário estará associado a uma população alvo. (Pertence a um certo grupo etário, ter pedido um certo produto etc.).

# 2. Análise

## 2.1 Sequência das ações

* O sistema irá perguntar para introduzir os elementos constituintes de um questionário ou caso o questionário esteja num txt,
irá ser pedido apenas .

## 2.2 Pré Condições

Ter customers presentes no sistema, que fazem orders (para se tornarem numa população alvo).

## 2.3 Pós Condições

###Implementado já neste sprint
Validar o questionário usando uma gramática (US3000).

###Próximos sprints
Enviar notificações aos clientes de quando têm questionários disponíveis para responderem.
Responder aos questionários.
Consultar respostas dos questionários.


## 2.4 Perguntas ao cliente

### 2.4.1 US3001 - Section
Q1: When a Section has a Question tagged as "Mandatory" should the section become "Mandatory" as well?
Q2: Can you specify what you mean when a Question/Section is tagged with "condition dependent" and the type of conditions to be set.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16388))

### 2.4.2 US3001
I have some doubts about the questionnaires.

Q1: The same section can be present in more than one questionnaire?
Q2: The same question can be present in more than one section?

A:
Yes, that can happen.

However, there is no intend to reutilize questions and/or sections. If that happens, the user will type the question/section again.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16401#p21104))

### 2.4.3 US3001

Dear client,

In the document, we have "Repeatability - Optional condition stating if the questions of this section are to be answered more than once. At least two kinds of conditions need to be supported: (i) based on numeric answers or (ii) on a set of selected values."


Q: Could you better specify what you mean by "repeatability"? Maybe give an example of how you wanted it to work in a user perspective. (Do you have to answer each question X amount of times / the section repeats itself X times).

Q: Is this "repeatability" attribute just repeating the same question to the customer?  Or does something change?

A:
Please, check the example presented on section 5.1.3 of the specifications' document.

In particular, consider the last sentence regarding section 6.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16391))

### 2.4.4 Answers of Surveys


Q:
Dear Client,

Do the answers of surveys need to be linked to the user that answered the specific survey, is it anonymous or does it not matter?

Best regards, 2DK_G04

A:

While specifying a survey (US3001) the need (or not) to preserve a link to the user answering the questionnaire is defined.

Some surveys will be answered anonymously (not having a link to the user) while other might be answered identifying the users.

In either cases, the system must know if a user has (or not) already answered a given questionnaire.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=15806))


### 2.4.5 US3001 - Questionnaires, Sections and Questions

Q: Can you specify / define what business rules are associated to Questionnaire, Section and Question? (Eg: Questionnaire ID only has 9 characters / follows an expression).


Basic business rules related with Questionnaire, Section and Question are already available on the specifications document, namely on Table 1, 2 and 3.

Teams must adopt common-sense when applying other criteria such as min/max chars length and support/explain the rationale taken.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16387))

### 2.4.6 US3001 - Questionnaires, Sections and Questions

Q: Can you specify / define what business rules are associated to Questionnaire, Section and Question? (Eg: Questionnaire ID only has 9 characters / follows an expression).


Basic business rules related with Questionnaire, Section and Question are already available on the specifications document, namely on Table 1, 2 and 3.

Teams must adopt common-sense when applying other criteria such as min/max chars length and support/explain the rationale taken.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16387))

Q:
Dear Client,

Do the answers of surveys need to be linked to the user that answered the specific survey, is it anonymous or does it not matter?

Best regards, 2DK_G04

A:

While specifying a survey (US3001) the need (or not) to preserve a link to the user answering the questionnaire is defined.

Some surveys will be answered anonymously (not having a link to the user) while other might be answered identifying the users.

In either cases, the system must know if a user has (or not) already answered a given questionnaire.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=15806))


### 2.4.7 US3001 - Section

Q1: When a Section has a Question tagged as "Mandatory" should the section become "Mandatory" as well?
Q2: Can you specify what you mean when a Question/Section is tagged with "condition dependent" and the type of conditions to be set.


Answers:

Answering (starting from the last question):

Q2: If the "obligatoriness" of a question/section is "condition dependent", it means it is "mandatory" when the associated condition evaluates as "true" and it is "optional" otherwise.

Q1: The question/answer is not straightforward. However, some consistency/coherence must exist/be assured between the "obligatoriness" of the section and of the question. You must also assure consistency with the "repeatability" information of the section.

Please, check carefully the example provided on section 5.1.3 of the specifications document where you can find "mandatory" sections (e.g.: section 1), "optional" sections (e.g.: section 8) and "condition dependent" sections (e.g.: section 3, 4 and 5). You can also find  "repeatable" sections (e.g.: section 6).

A "mandatory" or "condition dependant" section does not imply any "obligatoriness" on the questions.

An "optional" section implies that all questions are also "optional".

If a question is "mandatory", it means the user needs to answer such question no matter what is stated at the section it appears on.

If a question is "optional", it means that is up to the user to answer or not the question no matter what is stated at the section it appears on.

If a question is "condition dependent", it means the system needs to evaluate the associated condition to determine how to proceed, i.e. as "mandatory" or as "optional" question.

([Link](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16388#p21044))


## 2.5 SSD

![SSD-Diagram](Diagramas/SSD.svg/)

# 3. Design



## 3.1. Realização da Funcionalidade

![SD-Diagram](Diagramas/SD.svg/)

## 3.2. Diagrama de Classes

![CD-Diagram](Diagramas/CD.svg/)

## 3.3. Padrões Aplicados

* Foi utilizado o CRUD (Create, Read, Update, Delete) para trabalhar sobre os surveys.

* Foi utilizado o GRASP: no uso de DTOS, para alta coesão e acoplamento reduzido. Polimorfismo para gerir os métodos que
  criam o survey, caso o survey é criado com o txt ou a introduzir os dados. Pure fabrication (Content) to create the syntax
* o

* Foi utilizado o Builder, já que há certos atributos que são opcionais. O padrão builder dá-nos um processo passo a passo
  para construir um objeto completo. Este processo tem sempre a mesma implementação, porém os objetos finais podem possuir
  diferentes representações. Neste contexto o processo irá passar por criar os atributos obrigatórios de construtor, dando
  a possibilidade de definir apenas alguns atributos opcionais. Exemplo: customer com endereço de residência, mas sem género
  definido, nem data de aniversário.


* Foram utilizados o padrão repository, de modo a isolar os objetos de domínio de lógica de bases de dados. Os nossos objetos
  de domínio, que por já são complexos contendo muitas regras de domínio para impor, beneficia de outra camada onde apenas
  teremos lógica de bases de dados. Isto ajuda-nos a reduzir código duplicado, fazendo com que a layer de repositório
  possua capacidades de fazer querying complexo. Um repositório encapsula a lista de objetos persistidos numa base de dados
  dando-nos uma visão orientada a objetos à camada de persitência.

## 3.4. Testes 


# 4. Implementação

### Builder Section

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

### Survey

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

    protected Survey() {
        //ORM
    }


    @Override
    public boolean sameAs(Object other) {
        Survey otherSurvey = (Survey) other;
        return alphaNumericCode.equals(otherSurvey.alphaNumericCode);
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
        if(questionnaire!= null) {
            return "Survey{" +
                    "alphaNumericCode=" + alphaNumericCode +
                    ", description=" + description +
                    ", period=" + period +
                    ", questionnaire=" + questionnaire +
                    ", content=" + content +
                    '}';
        }else{
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
    }
    public Survey(AlphaNumericCode alphaNumericCode, Description description, Period period ) {
        this.alphaNumericCode = alphaNumericCode;
        this.description = description;
        this.period = period;
    }

    public Survey(AlphaNumericCode alphaNumericCode, Description description, Period period, Content content) {
        this.alphaNumericCode = alphaNumericCode;
        this.description = description;
        this.period = period;
        this.content = content;
    }


    /**
     * Showcase the {@link DTOable} interface. In this case it is the Dish class
     * that dictates the DTO structure.
     *
     *
     */
    @Override
    public SurveyDTO toDTO() {
        return new SurveyDTO(alphaNumericCode.code(),
                description.toString(),
                period.toString());
    }

### Controller
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
            newSurvey.addContentToSurvey(new Content(dto.content));
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

    public QuestionnaireDTO buildQuestionnaire(final QuestionnaireDTO dto) {
        questionnaire = new QuestionnaireDTOParser().valueOf(dto);
        questionnaire.setSections(sections);
        return questionnaire.toDTO();
    }

    public void buildSections(final SectionDTO dto) {
        final var newSection = new SectionDTOParser().valueOf(dto);
        sections.add(newSection);
        newSection.setContent(questions);
    }

    public QuestionDTO buildQuestions(final QuestionDTO dto) {
        final var newQuestion = new QuestionDTOParser().valueOf(dto);
        questions.add(newQuestion);
        return newQuestion.toDTO();
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

# 5. Integração/Demonstração

- Foi adicionada uma opção (Survey -> Define new Survey) ao menu do Sales Manager.

# 6. Observações




