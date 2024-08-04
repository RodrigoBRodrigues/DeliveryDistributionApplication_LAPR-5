package eapli.base.grammar;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.questionnaire.domain.Answer;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class AnswerVisitor extends LabeledExprBaseVisitor<String> {

    private final Map<String,List<List<String>>> singleChoice = new HashMap<>();
    private final Map<List<String>,List<List<String>>> multipleChoice = new HashMap<>();
    private final Map<List<String>,List<List<String>>> sortingOptions = new HashMap<>();
    private final Map<List<String>,List<List<String>>> scalingOptions = new HashMap<>();
    private List<String> answerSingleChoice = new ArrayList<>();
    private final List<List<String>> answerListMultipleChoice = new ArrayList<>();
    private List<List<String>> answerListSortingOptions = new ArrayList<>();
    private final List<String> answerListScalingOptions = new ArrayList<>();
    private List<Answer> answerList;
    private String questionID;

    public void questionnaireAnswers(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String visitSection(LabeledExprParser.SectionContext ctx) {
        visitChildren(ctx);
        return "Section";
    }

    @Override
    public String visitQuestion(LabeledExprParser.QuestionContext ctx) {
        questionID = ctx.QUESTION_ID().toString();
        visitChildren(ctx);
        return "Question";
    }

    @Override
    public String visitScalingOptions(LabeledExprParser.ScalingOptionsContext ctx){
        getContextAnswersScalingOptions();
        if (scalingOptions.containsKey(answerListScalingOptions)) {
            List <List<String>> opt = sortingOptions.get(answerListScalingOptions);
            scalingOptions.remove(answerListScalingOptions);
            opt.add(getAnswerOptionsScalingOptions(ctx));
            scalingOptions.put(answerListScalingOptions, opt);
        } else {
            List<List<String>> opt = new ArrayList<>();
            opt.add(getAnswerOptionsScalingOptions(ctx));
            scalingOptions.put(answerListScalingOptions, opt);
        }
        return ctx.getText();
    }

    public List <String> getAnswerOptionsScalingOptions (LabeledExprParser.ScalingOptionsContext ctx) {
        List <String> options = new ArrayList<>();
        for (TerminalNode option : ctx.CHOOSE()){
            options.add(String.valueOf(option.toString().charAt(0)));
        }
        return options;
    }

    public void getContextAnswersScalingOptions () {
        for (Answer a : answerList) {
            if (a.questionID().equals(questionID)) {
                answerListScalingOptions.add(a.answer());
            }
        }
    }

    //--------SORTING OPTIONS---------//

    @Override
    public String visitSortingOptions(LabeledExprParser.SortingOptionsContext ctx){
        getContextAnswersSortingOptions();
        List<String> options = new ArrayList<>();
        options.add("1");options.add("2");options.add("3");options.add("4");options.add("5");
        for (int i = 0; i < answerListSortingOptions.size(); i++) {
            if (sortingOptions.containsKey(answerListSortingOptions.get(i))) {
                List<List<String>> opt = sortingOptions.get(answerListSortingOptions.get(i));
                sortingOptions.remove(answerListSortingOptions.get(i));
                opt.add(options );
                sortingOptions.put(answerListSortingOptions.get(i), opt);
            } else {
                List<List<String>> opt = new ArrayList<>();
                opt.add(options );
                sortingOptions.put(answerListSortingOptions.get(i), opt);
            }
        }
        answerListSortingOptions = new ArrayList<>();
        return ctx.getText();
    }

    public List <String> getAnswerOptionsSortingOptions (LabeledExprParser.SortingOptionsContext ctx) {
        List <String> options = new ArrayList<>();
        for (TerminalNode option : ctx.CHOOSE()){
            options.add(String.valueOf(option.toString().charAt(0)));
        }
        return options;
    }

    public void getContextAnswersSortingOptions () {
        List<String> cost = (List<String>) PersistenceContext.repositories().answers().costumersId();
        List<String> aux = new ArrayList<>();
        for (String id : cost) {
            for (Answer a : answerList) {
                if (a.questionID().equals(questionID) && a.getCustomer().identity().toString().equals(id)) {
                    String[] oo = a.answer().split(",");
                    for (int i = 0; i < oo.length; i++) {
                        aux.add(String.valueOf(oo[i].charAt(1)));
                    }
                }
            }
            answerListSortingOptions.add(aux);
            aux = new ArrayList<>();
        }
    }


    //--------MULTIPLE CHOICE---------//

    @Override
    public String visitMultipleChoice(LabeledExprParser.MultipleChoiceContext ctx) {
        getContextAnswersMultipleChoice();
        for (int i = 0; i < answerListMultipleChoice.size(); i++) {
            if (multipleChoice.containsKey(answerListMultipleChoice.get(i))) {
                List<List<String>> opt = multipleChoice.get(answerListMultipleChoice.get(i));
                multipleChoice.remove(answerListMultipleChoice.get(i));
                opt.add(getAnswerOptionsMultipleChoice(ctx));
                multipleChoice.put(answerListMultipleChoice.get(i), opt);
            } else {
                List<List<String>> opt = new ArrayList<>();
                opt.add(getAnswerOptionsMultipleChoice(ctx));
                multipleChoice.put(answerListMultipleChoice.get(i), opt);
            }
        }
        return ctx.getText();
    }

    public List <String> getAnswerOptionsMultipleChoice (LabeledExprParser.MultipleChoiceContext ctx) {
        List <String> options = new ArrayList<>();
        for (TerminalNode option : ctx.CHOOSE()){
            options.add(String.valueOf(option.toString().charAt(0)));
        }
        return options;
    }

    public void getContextAnswersMultipleChoice () {
        List <String> cost = (List<String>) PersistenceContext.repositories().answers().costumersId();
        List <String> aux = new ArrayList<>();
        for (String id : cost) {
            for (Answer a : answerList) {
                if (a.questionID().equals(questionID) && a.getCustomer().identity().toString().equals(id)) {
                    aux.add(a.answer());
                }
            }
            answerListMultipleChoice.add(aux);
            aux = new ArrayList<>();
        }
    }

    //--------SINGLE CHOICE---------//

    @Override
    public String visitSingleChoice(LabeledExprParser.SingleChoiceContext ctx) {
        getContextAnswer();
        if(answerSingleChoice.size() > 0) {
            for (int i = 0; i < answerSingleChoice.size(); i++) {
                if (singleChoice.containsKey(answerSingleChoice.get(i))) {
                    List<List<String>> opt = singleChoice.get(answerSingleChoice.get(i));
                    singleChoice.remove(answerSingleChoice.get(i));
                    opt.add(getAnswerOptionsSingleChoice(ctx));
                    singleChoice.put(answerSingleChoice.get(i), opt);
                } else {
                    List<List<String>> opt = new ArrayList<>();
                    opt.add(getAnswerOptionsSingleChoice(ctx));
                    singleChoice.put(answerSingleChoice.get(i), opt);
                }
            }
        }
        answerSingleChoice = new ArrayList<>();
        return ctx.getText();
    }

    public List <String> getAnswerOptionsSingleChoice (LabeledExprParser.SingleChoiceContext ctx) {
        List <String> options = new ArrayList<>();
        for (TerminalNode option : ctx.CHOOSE()){
            options.add(String.valueOf(option.toString().charAt(0)));
        }
        return options;
    }

    public void getContextAnswer() {
            for (Answer a : answerList) {
                if (a.questionID().equals(questionID)) {
                   answerSingleChoice.add(a.answer());
                }
            }
    }

    public Map<String, List<List<String>>> singleChoice() {
        return singleChoice;
    }

    public Map<List<String>, List<List<String>>> multipleChoice() {
        return multipleChoice;
    }

    public Map<List<String>, List<List<String>>> sortingOptions() {
        return sortingOptions;
    }

    public Map<List<String>, List<List<String>>> scalingOptions() {
        return scalingOptions;
    }

}
