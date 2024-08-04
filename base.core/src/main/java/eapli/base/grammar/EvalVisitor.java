package eapli.base.grammar;

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.questionnaire.application.SurveyController;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.questionnaire.domain.Obligatoriness;
import eapli.base.questionnaire.domain.QuestionType;
import eapli.base.questionnaire.domain.Survey;
import eapli.base.usermanagement.application.AddCustomerController;
import eapli.framework.io.util.Console;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class EvalVisitor extends LabeledExprBaseVisitor<String> {
    //private final SurveyController theController = new SurveyController();

    private final List<Answer> answers = new ArrayList<>();
    private String section;

    private LabeledExprParser.MultipleChoiceContext questionContext;

    private String questionId;

    private Customer customer1;

    private Survey survey;

    private final AddCustomerController controller = new AddCustomerController();
    private final SurveyController surveyController = new SurveyController();
    private List<String> sortingOptionsAnswers = new ArrayList<>();


    @Override
    public String visitSection(LabeledExprParser.SectionContext ctx) {
        String teste = ctx.OBLIGATORINESS().toString().replace("OBLIGATORINESS: ", "");
        String siuuuu = Obligatoriness.OPTIONAL.toString();
        if (teste.equals(siuuuu)) {
            //theController.surveyToBeAnswered();
            System.out.println("---------------------------------------------------------------------");
            System.out.println(ctx.SECTION_ID());
            System.out.println(ctx.SECTION_TITLE());
            if (ctx.SECTION_DESCRIPTION() != null) {
                System.out.println(ctx.SECTION_DESCRIPTION());
            }
            System.out.println(ctx.OBLIGATORINESS());

            System.out.println("---------------------------------------------------------------------");
            System.out.println();

            section = ctx.SECTION_ID().toString();
            if (!treatObligatoriness(ctx.OBLIGATORINESS()).equalsIgnoreCase("N")) {
                for (LabeledExprParser.QuestionContext context : ctx.question()) {
                    visitQuestion(context);
                }
            }
        } else if (!treatObligatoriness(ctx.OBLIGATORINESS()).equalsIgnoreCase("N")) {

            //theController.surveyToBeAnswered();
            System.out.println("---------------------------------------------------------------------");
            System.out.println(ctx.SECTION_ID());
            System.out.println(ctx.SECTION_TITLE());
            if (ctx.SECTION_DESCRIPTION() != null) {
                System.out.println(ctx.SECTION_DESCRIPTION());
            }
            System.out.println(ctx.OBLIGATORINESS());

            System.out.println("---------------------------------------------------------------------");
            System.out.println();

            section = ctx.SECTION_ID().toString();
            for (LabeledExprParser.QuestionContext context : ctx.question()) {
                visitQuestion(context);
            }
        }
        return ctx.getText();
    }


    public void setCustomer(Customer customer1) {
        this.customer1 = customer1;
    }

    @Override
    public String visitQuestion(LabeledExprParser.QuestionContext ctx) {

        survey = surveyController.surveyToBeAnswered("12").get();
        System.out.println(ctx.QUESTION_ID());
        System.out.println(ctx.Q());
        if (!ctx.INSTRUCTION().isEmpty()) {
            System.out.println(ctx.INSTRUCTION());
        }
        System.out.println(ctx.OBLIGATORINESS());
        questionId = ctx.QUESTION_ID().toString();
        String questionType = ctx.questionType().getText();
        questionType = questionType.split(" ")[1].replace("EXTRA", "");
        System.out.println(questionType);
        String yau = "";
        if (!treatObligatoriness(ctx.OBLIGATORINESS()).equalsIgnoreCase("N")) {
            yau = treatQuestionType(questionType, ctx);
        }
        if (yau != null) {
            String response = "QUESTION " + ctx.QUESTION_ID().toString().replace("QUESTION ID: ", "");
        }

        assert yau != null;
        if (questionType.equalsIgnoreCase("SORTING_OPTIONS")) {
            answers.add(new Answer(sortingOptionsAnswers.toString(), section, ctx.QUESTION_ID().toString(), customer1, survey));
        } else if (!yau.equalsIgnoreCase("n")) {
            answers.add(new Answer(yau, section, ctx.QUESTION_ID().toString(), customer1, survey));
        }
        System.out.println();
        return ctx.getText();
    }

    @Override
    public String visitFree_text(LabeledExprParser.Free_textContext ctx) {
        if (ctx.EXTRA_INFO() != null && !ctx.EXTRA_INFO().toString().isBlank()) {
            System.out.println(ctx.EXTRA_INFO() + " " + ctx.SENTENCE());
        }
        return Console.readLine("Answer:");
    }

    @Override
    public String visitNumeric(LabeledExprParser.NumericContext ctx) {
        if (ctx.EXTRA_INFO() != null && !ctx.EXTRA_INFO().toString().isBlank()) {
            System.out.println(ctx.EXTRA_INFO() + " " + ctx.NUMBER());
        }

        return new Scanner(System.in).nextLine();
    }

    @Override
    public String visitSingleChoice(LabeledExprParser.SingleChoiceContext ctx) {
        int i = 0;
        String response;
        String optionToBeShown;
        int numberOfOptions = ctx.CHOOSE().size();
        do {
            while (i < numberOfOptions) {
                optionToBeShown = ctx.CHOOSE(i).toString().contains("|") ? ctx.CHOOSE(i).toString().replace("|", "") : ctx.CHOOSE(i).toString();
                System.out.println(optionToBeShown);
                i++;
            }
            try {
                response = Console.readLine("Answer:");
                if (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0)
                    System.out.println("Option not available");
            } catch (NumberFormatException e) {
                response = String.valueOf(Integer.MAX_VALUE);
                System.out.println("Option not available");
            }
        } while (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0);
        return response;
    }


    @Override
    public String visitSingleChoiceWithInput(LabeledExprParser.SingleChoiceWithInputContext ctx) {
        int i = 0;
        String response;
        String optionToBeShown;
        int numberOfOptions = ctx.CHOOSE().size();
        do {
            while (i < numberOfOptions) {
                optionToBeShown = ctx.CHOOSE(i).toString().contains("|") ? ctx.CHOOSE(i).toString().replace("|", "") : ctx.CHOOSE(i).toString();
                System.out.println(optionToBeShown);
                i++;
            }
            try {
                response = Console.readLine("Answer:");
                if (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0)
                    System.out.println("Option not available");
            } catch (NumberFormatException e) {
                response = String.valueOf(Integer.MAX_VALUE);
                System.out.println("Option not available");
            }
        } while (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0);

        String input;
        if (Integer.parseInt(response) == ctx.CHOOSE().size()) {
            input = Console.readLine("Input");
        }
        return response;
    }

    public String visitMultipleChoice(LabeledExprParser.MultipleChoiceContext ctx) {
        int i = 0;
        String response;
        String optionToBeShown;
        questionContext = ctx;

        int numberOfOptions = ctx.CHOOSE().size();
        do {
            while (i < numberOfOptions) {
                optionToBeShown = ctx.CHOOSE(i).toString().contains("|") ? ctx.CHOOSE(i).toString().replace("|", "") : ctx.CHOOSE(i).toString();
                System.out.println(optionToBeShown);
                i++;
            }
            response = Console.readLine("Answer (Input \"N\" to stop):");
            try {
                if ((!response.equalsIgnoreCase("N") && !response.equalsIgnoreCase("NO")) && (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0))
                    System.out.println("Option not available");
            } catch (NumberFormatException e) {
                response = String.valueOf(Integer.MAX_VALUE);
                System.out.println("Option not available");
            }
            if (!response.equalsIgnoreCase("n")) {
                answers.add(new Answer(response, section, questionId, customer1, survey));
            }
        } while ((answers.size() != numberOfOptions) && (!response.equalsIgnoreCase("N") && !response.equalsIgnoreCase("NO")));
        return response;
    }

    public String visitMultipleChoiceWithInput(LabeledExprParser.MultipleChoiceWithInputContext ctx) {
        int i = 0;
        String response;
        String optionToBeShown;
        int numberOfOptions = ctx.CHOOSE().size();
        do {
            while (i < numberOfOptions) {
                optionToBeShown = ctx.CHOOSE(i).toString().contains("|") ? ctx.CHOOSE(i).toString().replace("|", "") : ctx.CHOOSE(i).toString();
                System.out.println(optionToBeShown);
                i++;
            }
            response = Console.readLine("Answer (Input \"N\" to stop):");
            try {
                if ((!response.equalsIgnoreCase("N") && !response.equalsIgnoreCase("NO")) && (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0))
                    System.out.println("Option not available");
            } catch (NumberFormatException e) {
                response = String.valueOf(Integer.MAX_VALUE);
                System.out.println("Option not available");
            }
            if (!response.equalsIgnoreCase("n")) {
                answers.add(new Answer(response, section, questionId, customer1, survey));
            }
        } while ((answers.size() != numberOfOptions) && (!response.equalsIgnoreCase("N") && !response.equalsIgnoreCase("NO")));

        String input = "";
        if (Integer.parseInt(response) == (numberOfOptions)) {
            input = Console.readLine("Input");
        }

        return response + " " + input;
    }

    public String visitSortingOptions(LabeledExprParser.SortingOptionsContext ctx) {
        int i;
        String response;
        String optionToBeShown;
        int numberOfOptions = ctx.CHOOSE().size();

        List<TerminalNode> list = ctx.CHOOSE();
        List<String> nodesInStringFormat = new ArrayList<>();

        for (TerminalNode node : list) {
            nodesInStringFormat.add(node.toString());
        }
        int contagem = 0;
        sortingOptionsAnswers = new ArrayList<>();
        while (contagem < 5) {
            contagem++;
            i = 0;
            while (i < numberOfOptions) {
                optionToBeShown = nodesInStringFormat.get(i).contains("|") ? nodesInStringFormat.get(i).replace("|", "") : nodesInStringFormat.get(i);
                System.out.println(optionToBeShown);
                i++;
            }
            if (!sortingOptionsAnswers.isEmpty()) {
                System.out.println("Your selection until now:");
                System.out.println(sortingOptionsAnswers);
            }
            response = Console.readLine("Select the options by order of importance to you.");

            try {
                if (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0) {
                    contagem--;
                    System.out.println("You selected an invalid option please try again\n");
                } else {
                    String semReplaced = nodesInStringFormat.get(Integer.parseInt(response) - 1);
                    sortingOptionsAnswers.add(semReplaced.replace("|", ""));
                }
            } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                contagem--;
                System.out.println("You selected an invalid option please try again");
            }


        }

        System.out.println("Your final selection:");
        System.out.println(sortingOptionsAnswers);


        //if the options are to be persisted just delete this for loop.
        sortingOptionsAnswers.replaceAll(s -> String.valueOf(s.charAt(0)));
        return sortingOptionsAnswers.toString();
    }


    public String visitScalingOptions(LabeledExprParser.ScalingOptionsContext ctx) {
        int i;
        String response;
        String optionToBeShown;
        int numberOfOptions = ctx.CHOOSE().size();
        do {
            i = 0;
            while (i < numberOfOptions) {
                optionToBeShown = ctx.CHOOSE(i).toString().contains("|") ? ctx.CHOOSE(i).toString().replace("|", "") : ctx.CHOOSE(i).toString();
                System.out.println(optionToBeShown);
                i++;
            }
            try {
                response = Console.readLine("Answer:");
                if (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0)
                    System.out.println("Option not available");
            } catch (NumberFormatException e) {
                response = String.valueOf(Integer.MAX_VALUE);
                System.out.println("Option not available");
            }
        } while (Integer.parseInt(response) > numberOfOptions || Integer.parseInt(response) <= 0);
        return response;
    }


    private String treatQuestionType(String questionType, LabeledExprParser.QuestionContext ctx) {
        switch (QuestionType.valueOf(questionType)) {
            case FREE_TEXT:
                return visitFree_text((ctx.questionType().free_text()));
            case NUMERIC:
                return visitNumeric(ctx.questionType().numeric());
            case SINGLE_CHOICE:
                return visitSingleChoice(ctx.questionType().singleChoice());
            case SINGLE_CHOICE_INPUT_VALUE:
                return visitSingleChoiceWithInput(ctx.questionType().singleChoiceWithInput());
            case MULTIPLE_CHOICE:
                return visitMultipleChoice(ctx.questionType().multipleChoice());
            case MULTIPLE_CHOICE_INPUT_VALUE:
                return visitMultipleChoiceWithInput(ctx.questionType().multipleChoiceWithInput());
            case SCALING_OPTIONS:
                return visitScalingOptions(ctx.questionType().scalingOptions());
            case SORTING_OPTIONS:
                return visitSortingOptions(ctx.questionType().sortingOptions());
            default:
                return null;
        }
    }


    private String treatObligatoriness(TerminalNode obligatoriness) {
        String treat = obligatoriness.toString().replace("OBLIGATORINESS: ", "").trim();
        if (treat.contains("CONDITION_DEPENDENT")) {
            treat = treat.split(" ")[0];
        }

        Obligatoriness obligatorinessConverted = Obligatoriness.valueOf(treat);

        switch (obligatorinessConverted) {
            case MANDATORY:
                return "S";
            case OPTIONAL:
                String optional = Console.readLine("This is optional! Do you want to answer it?");
                if (optional.equalsIgnoreCase("N") || optional.equalsIgnoreCase("NO")) {
                    return "N";
                } else {
                    return "S";
                }
            case CONDITION_DEPENDENT:
                String debug = obligatoriness.toString().toLowerCase();
                List<TerminalNode> debugExtraInfo = questionContext.CHOOSE();

                for (TerminalNode options : debugExtraInfo) {
                    String resultOptions = options.toString().substring(0, 1).toLowerCase();
                    String obligatorinessOptions = options.toString().substring(3, options.toString().length() - 1).toLowerCase();

                    for (Answer answer : answers) {
                        String pppp = answer.answerToBuildAnswerObject();
                        if (pppp.equals(resultOptions) && debug.contains(obligatorinessOptions)) {
                            return "S";
                        }
                    }
                }

                return "N";
        }
        return "N";
    }


    public List<Answer> getAnswers() {
        return answers;
    }
}
