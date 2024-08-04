package eapli.base.app.backoffice.console.presentation.questionnaire;/*
 * Copyright (c) 2013-2019 the original author or authors.
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

import eapli.base.grammar.LabeledExprLexer;
import eapli.base.grammar.LabeledExprParser;
import eapli.base.questionnaire.application.SurveyController;
import eapli.base.questionnaire.domain.*;
import eapli.base.questionnaire.dto.*;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * UI for adding a questionnaire to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class DefineSurveyUI extends AbstractUI {

    private final SurveyController theController = new SurveyController();
    private String alphanumericCodeString;
    private String descriptionString;
    private String period;
    private String obligatorinessString;
    private String questionTypeString;
    private Obligatoriness obligatoriness;
    private QuestionType questionType;
    private String repeatability;
    private String extraInfo;
    private String questionId;
    private String questionMessage;
    private String sectionTitle;
    private String sectionId;
    private String finalMessage;
    private String welcomeMessage;
    private String questionnaireTitle;
    private String questionnaireId;
    private String response = "";
    private String instruction;
    private int flag;
    String responseOptional;

    @Override
    protected boolean doShow() {
        String option;
        flag = 0;
        System.out.println("Select how you want to define the Questionnaire:");
        System.out.println("1. Input the data");
        System.out.println("2. Import text file");
        do {
            option = Console.readLine("");
        } while (!(option.equals("1") || option.equals("2")));

        if (option.equals("1")) {
            inputData();
            SurveyDTO surveyDTO = new SurveyDTO(alphanumericCodeString, descriptionString, period, null);
            theController.buildSurvey(surveyDTO, flag);
            System.out.println(theController.receiveSurveyString());
        } else {
            importTextFile();
        }
        LabeledExprLexer lexer = new LabeledExprLexer(new ANTLRInputStream(theController.receiveFullQuestionnaireString()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        System.out.println(theController.receiveFullQuestionnaireString());
        parser.prog();
        return false;
    }

    StringBuilder stringBuilder = new StringBuilder();

    private QuestionnaireDTO inputData() {
        insertSurveyData();
        insertQuestionnaireData();
        while (!(response.equalsIgnoreCase("NO") || response.equalsIgnoreCase("N"))) {
            insertSectionsData();
            while (!(response.equalsIgnoreCase("NO") || response.equalsIgnoreCase("N"))) {
                insertQuestionsData();
                response = Console.readLine("Do you want to define another question? (Y/N)");
                stringBuilder = new StringBuilder();
            }
            response = Console.readLine("Do you want to define another section? (Y/N)");

            theController.buildSections(new SectionDTO(sectionId, sectionTitle, descriptionString, obligatoriness.toString(), repeatability));
            theController.cleanQuestionList();

        }
        finalMessage = Console.readLine("Final Message");
        return theController.buildQuestionnaire(new QuestionnaireDTO(questionnaireId, questionnaireTitle, welcomeMessage, finalMessage));
    }

    private void importTextFile() {
        insertSurveyData();
        flag = 1;
        String questionnaire = "";
        String currentDirectory = "";
        String questionnairePath;
        try {
            JFileChooser chooser = new JFileChooser(currentDirectory);
            chooser.showSaveDialog(null);
            questionnairePath = "questionnaire/" + chooser.getSelectedFile().getName();
        } catch (HeadlessException a) {
            questionnairePath = "questionnaire/" + Console.readLine("Insert file name to be imported");
        }
        if (!questionnairePath.contains(".txt")) {
            questionnairePath = questionnairePath + ".txt";
        }
        try {
            questionnaire = Files.readString(Path.of(questionnairePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SurveyDTO surveyDTO = new SurveyDTO(alphanumericCodeString, descriptionString, period, null);
        surveyDTO.content = questionnaire;
        theController.buildSurvey(surveyDTO, flag);
        System.out.println(theController.receiveSurveyString());
    }

    private void insertSurveyData() {
        System.out.println("SURVEY");
        alphanumericCodeString = Console.readLine("Alphanumeric code");
        descriptionString = Console.readLine("Description");
        period = Console.readLine("Period");
    }

    private void insertQuestionnaireData() {
        System.out.println("QUESTIONNAIRE");
        questionnaireId = Console.readLine("Questionnaire id:");
        questionnaireTitle = Console.readLine("Questionnaire title");
        responseOptional = Console.readLine("Welcome Message: This field is optional. Do you want to define it? (y/n)");
        if (responseOptional.equalsIgnoreCase("yes") || responseOptional.equalsIgnoreCase("y")) {
            welcomeMessage = Console.readLine("Welcome Message");
        }
        period = Console.readLine("Period");
    }


    private void insertSectionsData() {
        System.out.println("SECTION");
        sectionId = Console.readLine("Section id:");
        sectionTitle = Console.readLine("Section title");
        responseOptional = Console.readLine("Section Description: This field is optional. Do you want to define it? (y/n)");
        if (responseOptional.equalsIgnoreCase("yes") || responseOptional.equalsIgnoreCase("y")) {
            descriptionString = Console.readLine("Description");
        }
        selectObligatoriness();
        responseOptional = Console.readLine("Repeatability: This field is optional. Do you want to define it? (y/n)");
        if (responseOptional.equalsIgnoreCase("yes") || responseOptional.equalsIgnoreCase("y")) {
            repeatability = Console.readLine("Repeatability");
        } else {
            repeatability = null;
        }
    }


    private void insertQuestionsData() {
        System.out.println("QUESTION");
        questionId = Console.readLine("Question ID");
        questionMessage = Console.readLine("Question message");
        responseOptional = Console.readLine("Instruction: This field is optional. Do you want to define it? (y/n)");
        if (responseOptional.equalsIgnoreCase("yes") || responseOptional.equalsIgnoreCase("y")) {
            instruction = Console.readLine("Instruction");
        } else {
            instruction = null;
        }
        selectObligatoriness();
        selectQuestionType();
        defineExtraInfo();
        theController.buildQuestions(new QuestionDTO(questionId, questionMessage, instruction, questionType.toString(), obligatoriness.toString(), extraInfo));
    }

    private void defineExtraInfo() {
        if (questionType.equals(QuestionType.FREE_TEXT)) {
            extraInfo = null;
        } else if (questionType.equals(QuestionType.NUMERIC)) {
            extraInfo = null;
        } else if (questionType.equals(QuestionType.SINGLE_CHOICE)) {
            extraInfo = defineChoiceQuestions().toString();
        } else if (questionType.equals(QuestionType.SINGLE_CHOICE_INPUT_VALUE)) {
            extraInfo = defineChoiceQuestionWithInput();
        } else if (questionType.equals(QuestionType.MULTIPLE_CHOICE)) {
            extraInfo = defineChoiceQuestions().toString();
        } else if (questionType.equals(QuestionType.MULTIPLE_CHOICE_INPUT_VALUE)) {
            extraInfo = defineChoiceQuestionWithInput();
        } else if (questionType.equals(QuestionType.SORTING_OPTIONS)) {
            extraInfo = defineChoiceQuestions().toString();
        } else if (questionType.equals(QuestionType.SCALING_OPTIONS)) {
            extraInfo = defineChoiceQuestions().toString();
        }
    }


    private void selectObligatoriness() {
        int i = 0;
        System.out.println("Select the obligatoriness");
        for (Obligatoriness obligatorinessValues : Obligatoriness.values()) {
            System.out.println(i + ". " + obligatorinessValues);
            i++;
        }
        do {
            obligatorinessString = Console.readLine("");
            switch (obligatorinessString) {
                case "0":
                    obligatoriness = Obligatoriness.MANDATORY;
                    break;
                case "1":
                    obligatoriness = Obligatoriness.OPTIONAL;
                    break;
                case "2":
                    obligatoriness = Obligatoriness.CONDITION_DEPENDENT;
                    break;
                default:
                    System.out.println("There was an error defining the obligatoriness. Please try again!");
            }
        } while (!(obligatorinessString.equals("0") || obligatorinessString.equals("1") || obligatorinessString.equals("2")));
    }


    private void selectQuestionType() {
        int i = 0;
        System.out.println("Select the Question Type");
        for (QuestionType questionTypeValues : QuestionType.values()) {
            System.out.println(i + ". " + questionTypeValues);
            i++;
        }
        do {
            questionTypeString = Console.readLine("");
            if (questionTypeString.equals("0")) {
                questionType = QuestionType.FREE_TEXT;
            } else if (questionTypeString.equals("1")) {
                questionType = QuestionType.NUMERIC;
            } else if (questionTypeString.equals("2")) {
                questionType = QuestionType.SINGLE_CHOICE;
            } else if (questionTypeString.equals("3")) {
                questionType = QuestionType.SINGLE_CHOICE_INPUT_VALUE;
            } else if (questionTypeString.equals("4")) {
                questionType = QuestionType.MULTIPLE_CHOICE;
            } else if (questionTypeString.equals("5")) {
                questionType = QuestionType.MULTIPLE_CHOICE_INPUT_VALUE;
            } else if (questionTypeString.equals("6")) {
                questionType = QuestionType.SORTING_OPTIONS;
            } else if (questionTypeString.equals("7")) {
                questionType = QuestionType.SCALING_OPTIONS;
            }
        } while (!(questionTypeString.equals("0") || questionTypeString.equals("1") || questionTypeString.equals("2")
                || questionTypeString.equals("3") || questionTypeString.equals("4") || questionTypeString.equals("5")
                || questionTypeString.equals("6") || questionTypeString.equals("7")));
    }


    int i = 0;

    private StringBuilder defineChoiceQuestions() {
        i = 0;
        String option = "";
        List<String> instructions = new ArrayList<>();
        String instruction = "";
        System.out.println("Defining a single choice structure");
        System.out.println("Insert the options after the value. TYPE \"N\" when no more options");
        Scanner scanner = new Scanner(System.in);
        do {
            instruction = String.format("%d- ", i);
            System.out.print(i + "-");
            option = scanner.nextLine();
            if (!option.equalsIgnoreCase("N")) {
                instruction = instruction.concat(option);
                instructions.add(instruction);
                i++;
            }
        } while ((!option.equalsIgnoreCase("N") || instructions.size() == 0));
        for (String instructionString : instructions) {
            stringBuilder.append(String.format("%s|", instructionString));
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder;
    }

    private String defineChoiceQuestionWithInput() {
        StringBuilder stringBuilder = defineChoiceQuestions();
        stringBuilder.append("|" + i + "- Other (please specify)\n");
        return stringBuilder.toString();
    }


    @Override
    public String headline() {
        return "Define new Questionnaire";
    }
}
