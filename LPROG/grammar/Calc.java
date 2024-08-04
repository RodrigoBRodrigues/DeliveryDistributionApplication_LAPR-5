package eapli.base.grammar;

import java.io.*;

import eapli.base.grammar.LabeledExprLexer;
import eapli.base.grammar.LabeledExprParser;
import org.antlr.v4.runtime.*;

public class Calc {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("base.core/src/main/java/eapli/base/grammar/teste.txt");
        LabeledExprLexer lexer = new LabeledExprLexer(new ANTLRInputStream(fis));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        parser.prog();
    }

}
