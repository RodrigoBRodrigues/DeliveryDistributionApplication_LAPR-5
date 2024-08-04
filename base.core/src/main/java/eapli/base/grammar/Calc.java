package eapli.base.grammar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import eapli.base.grammar.LabeledExprLexer;
import eapli.base.grammar.LabeledExprParser;
import eapli.base.questionnaire.application.SurveyController;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class Calc {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("base.core/src/main/java/eapli/base/grammar/teste.txt");
        LabeledExprLexer lexer = new LabeledExprLexer(new ANTLRInputStream(fis));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog();
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);

    }

}
