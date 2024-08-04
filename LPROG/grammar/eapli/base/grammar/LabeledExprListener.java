// Generated from C:/Users/ruiri/IdeaProjects/lei21_22_s4_2dh_05/base.core/src/main/java/eapli/base/grammar\LabeledExpr.g4 by ANTLR 4.10.1
package eapli.base.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LabeledExprParser}.
 */
public interface LabeledExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LabeledExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LabeledExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#info}.
	 * @param ctx the parse tree
	 */
	void enterInfo(LabeledExprParser.InfoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#info}.
	 * @param ctx the parse tree
	 */
	void exitInfo(LabeledExprParser.InfoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(LabeledExprParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(LabeledExprParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(LabeledExprParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(LabeledExprParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#questionType}.
	 * @param ctx the parse tree
	 */
	void enterQuestionType(LabeledExprParser.QuestionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#questionType}.
	 * @param ctx the parse tree
	 */
	void exitQuestionType(LabeledExprParser.QuestionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#free_text}.
	 * @param ctx the parse tree
	 */
	void enterFree_text(LabeledExprParser.Free_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#free_text}.
	 * @param ctx the parse tree
	 */
	void exitFree_text(LabeledExprParser.Free_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#multipleChoice}.
	 * @param ctx the parse tree
	 */
	void enterMultipleChoice(LabeledExprParser.MultipleChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#multipleChoice}.
	 * @param ctx the parse tree
	 */
	void exitMultipleChoice(LabeledExprParser.MultipleChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#multipleChoiceWithInput}.
	 * @param ctx the parse tree
	 */
	void enterMultipleChoiceWithInput(LabeledExprParser.MultipleChoiceWithInputContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#multipleChoiceWithInput}.
	 * @param ctx the parse tree
	 */
	void exitMultipleChoiceWithInput(LabeledExprParser.MultipleChoiceWithInputContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#singleChoice}.
	 * @param ctx the parse tree
	 */
	void enterSingleChoice(LabeledExprParser.SingleChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#singleChoice}.
	 * @param ctx the parse tree
	 */
	void exitSingleChoice(LabeledExprParser.SingleChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#singleChoiceWithInput}.
	 * @param ctx the parse tree
	 */
	void enterSingleChoiceWithInput(LabeledExprParser.SingleChoiceWithInputContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#singleChoiceWithInput}.
	 * @param ctx the parse tree
	 */
	void exitSingleChoiceWithInput(LabeledExprParser.SingleChoiceWithInputContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#sortingOptions}.
	 * @param ctx the parse tree
	 */
	void enterSortingOptions(LabeledExprParser.SortingOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#sortingOptions}.
	 * @param ctx the parse tree
	 */
	void exitSortingOptions(LabeledExprParser.SortingOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#scalingOptions}.
	 * @param ctx the parse tree
	 */
	void enterScalingOptions(LabeledExprParser.ScalingOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#scalingOptions}.
	 * @param ctx the parse tree
	 */
	void exitScalingOptions(LabeledExprParser.ScalingOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#numeric}.
	 * @param ctx the parse tree
	 */
	void enterNumeric(LabeledExprParser.NumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#numeric}.
	 * @param ctx the parse tree
	 */
	void exitNumeric(LabeledExprParser.NumericContext ctx);
}