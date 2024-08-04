// Generated from C:/Users/Asus/Documents/lei21_22_s4_2dh_05/base.core/src/main/java/eapli/base/grammar\LabeledExpr.g4 by ANTLR 4.10.1
package eapli.base.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LabeledExprParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ALPHANUMERIC=1, SENTENCE=2, NUMBER=3, STRING=4, RANGE_OPTIONS=5, OBLIGATORINESS_ENUM=6, 
		CHOOSE=7, FREE_TEXT=8, MULTIPLE_CHOICE=9, MULTIPLE_CHOICE_INPUT_VALUE=10, 
		SINGLE_CHOICE=11, SINGLE_CHOICE_INPUT_VALUE=12, NUMERIC=13, SORTING_OPTIONS=14, 
		SCALING_OPTIONS=15, ID=16, TITLE=17, WELCOME_MESSAGE=18, FINAL_MESSAGE=19, 
		LIST_OF_SECTIONS=20, CONTENT=21, SECTION_ID=22, SECTION_TITLE=23, SECTION_DESCRIPTION=24, 
		OBLIGATORINESS=25, REPEATABILITY=26, INSTRUCTION=27, QUESTION_ID=28, Q=29, 
		EXTRA_INFO=30, WS=31;
	public static final int
		RULE_prog = 0, RULE_info = 1, RULE_section = 2, RULE_question = 3, RULE_questionType = 4, 
		RULE_free_text = 5, RULE_multipleChoice = 6, RULE_multipleChoiceWithInput = 7, 
		RULE_singleChoice = 8, RULE_singleChoiceWithInput = 9, RULE_sortingOptions = 10, 
		RULE_scalingOptions = 11, RULE_numeric = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "info", "section", "question", "questionType", "free_text", "multipleChoice", 
			"multipleChoiceWithInput", "singleChoice", "singleChoiceWithInput", "sortingOptions", 
			"scalingOptions", "numeric"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "'TYPE: FREE_TEXT'", 
			"'TYPE: MULTIPLE_CHOICE'", "'TYPE: MULTIPLE_CHOICE_INPUT_VALUE'", "'TYPE: SINGLE_CHOICE'", 
			"'TYPE: SINGLE_CHOICE_INPUT_VALUE'", "'TYPE: NUMERIC'", "'TYPE: SORTING_OPTIONS'", 
			"'TYPE: SCALING_OPTIONS'", null, null, null, null, "'LIST OF SECTIONS:'", 
			"'CONTENT:'", null, null, null, null, null, null, null, null, "'EXTRA INFO: '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ALPHANUMERIC", "SENTENCE", "NUMBER", "STRING", "RANGE_OPTIONS", 
			"OBLIGATORINESS_ENUM", "CHOOSE", "FREE_TEXT", "MULTIPLE_CHOICE", "MULTIPLE_CHOICE_INPUT_VALUE", 
			"SINGLE_CHOICE", "SINGLE_CHOICE_INPUT_VALUE", "NUMERIC", "SORTING_OPTIONS", 
			"SCALING_OPTIONS", "ID", "TITLE", "WELCOME_MESSAGE", "FINAL_MESSAGE", 
			"LIST_OF_SECTIONS", "CONTENT", "SECTION_ID", "SECTION_TITLE", "SECTION_DESCRIPTION", 
			"OBLIGATORINESS", "REPEATABILITY", "INSTRUCTION", "QUESTION_ID", "Q", 
			"EXTRA_INFO", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "LabeledExpr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LabeledExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public List<InfoContext> info() {
			return getRuleContexts(InfoContext.class);
		}
		public InfoContext info(int i) {
			return getRuleContext(InfoContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(26);
				info();
				}
				}
				setState(29); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfoContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LabeledExprParser.ID, 0); }
		public TerminalNode TITLE() { return getToken(LabeledExprParser.TITLE, 0); }
		public TerminalNode LIST_OF_SECTIONS() { return getToken(LabeledExprParser.LIST_OF_SECTIONS, 0); }
		public List<TerminalNode> WELCOME_MESSAGE() { return getTokens(LabeledExprParser.WELCOME_MESSAGE); }
		public TerminalNode WELCOME_MESSAGE(int i) {
			return getToken(LabeledExprParser.WELCOME_MESSAGE, i);
		}
		public List<TerminalNode> FINAL_MESSAGE() { return getTokens(LabeledExprParser.FINAL_MESSAGE); }
		public TerminalNode FINAL_MESSAGE(int i) {
			return getToken(LabeledExprParser.FINAL_MESSAGE, i);
		}
		public List<SectionContext> section() {
			return getRuleContexts(SectionContext.class);
		}
		public SectionContext section(int i) {
			return getRuleContext(SectionContext.class,i);
		}
		public InfoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterInfo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitInfo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitInfo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InfoContext info() throws RecognitionException {
		InfoContext _localctx = new InfoContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_info);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			match(ID);
			setState(32);
			match(TITLE);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WELCOME_MESSAGE) {
				{
				{
				setState(33);
				match(WELCOME_MESSAGE);
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL_MESSAGE) {
				{
				{
				setState(39);
				match(FINAL_MESSAGE);
				}
				}
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(45);
			match(LIST_OF_SECTIONS);
			setState(47); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(46);
				section();
				}
				}
				setState(49); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SECTION_ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SectionContext extends ParserRuleContext {
		public TerminalNode SECTION_ID() { return getToken(LabeledExprParser.SECTION_ID, 0); }
		public TerminalNode SECTION_TITLE() { return getToken(LabeledExprParser.SECTION_TITLE, 0); }
		public TerminalNode OBLIGATORINESS() { return getToken(LabeledExprParser.OBLIGATORINESS, 0); }
		public TerminalNode CONTENT() { return getToken(LabeledExprParser.CONTENT, 0); }
		public List<TerminalNode> SECTION_DESCRIPTION() { return getTokens(LabeledExprParser.SECTION_DESCRIPTION); }
		public TerminalNode SECTION_DESCRIPTION(int i) {
			return getToken(LabeledExprParser.SECTION_DESCRIPTION, i);
		}
		public List<TerminalNode> REPEATABILITY() { return getTokens(LabeledExprParser.REPEATABILITY); }
		public TerminalNode REPEATABILITY(int i) {
			return getToken(LabeledExprParser.REPEATABILITY, i);
		}
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(SECTION_ID);
			setState(52);
			match(SECTION_TITLE);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SECTION_DESCRIPTION) {
				{
				{
				setState(53);
				match(SECTION_DESCRIPTION);
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(OBLIGATORINESS);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==REPEATABILITY) {
				{
				{
				setState(60);
				match(REPEATABILITY);
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
			match(CONTENT);
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				question();
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==QUESTION_ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuestionContext extends ParserRuleContext {
		public TerminalNode QUESTION_ID() { return getToken(LabeledExprParser.QUESTION_ID, 0); }
		public TerminalNode Q() { return getToken(LabeledExprParser.Q, 0); }
		public TerminalNode OBLIGATORINESS() { return getToken(LabeledExprParser.OBLIGATORINESS, 0); }
		public QuestionTypeContext questionType() {
			return getRuleContext(QuestionTypeContext.class,0);
		}
		public List<TerminalNode> INSTRUCTION() { return getTokens(LabeledExprParser.INSTRUCTION); }
		public TerminalNode INSTRUCTION(int i) {
			return getToken(LabeledExprParser.INSTRUCTION, i);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_question);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(QUESTION_ID);
			setState(73);
			match(Q);
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INSTRUCTION) {
				{
				{
				setState(74);
				match(INSTRUCTION);
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(80);
			match(OBLIGATORINESS);
			setState(81);
			questionType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuestionTypeContext extends ParserRuleContext {
		public Free_textContext free_text() {
			return getRuleContext(Free_textContext.class,0);
		}
		public MultipleChoiceContext multipleChoice() {
			return getRuleContext(MultipleChoiceContext.class,0);
		}
		public MultipleChoiceWithInputContext multipleChoiceWithInput() {
			return getRuleContext(MultipleChoiceWithInputContext.class,0);
		}
		public SingleChoiceContext singleChoice() {
			return getRuleContext(SingleChoiceContext.class,0);
		}
		public SingleChoiceWithInputContext singleChoiceWithInput() {
			return getRuleContext(SingleChoiceWithInputContext.class,0);
		}
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public SortingOptionsContext sortingOptions() {
			return getRuleContext(SortingOptionsContext.class,0);
		}
		public ScalingOptionsContext scalingOptions() {
			return getRuleContext(ScalingOptionsContext.class,0);
		}
		public QuestionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterQuestionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitQuestionType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitQuestionType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionTypeContext questionType() throws RecognitionException {
		QuestionTypeContext _localctx = new QuestionTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_questionType);
		try {
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FREE_TEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				free_text();
				}
				break;
			case MULTIPLE_CHOICE:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				multipleChoice();
				}
				break;
			case MULTIPLE_CHOICE_INPUT_VALUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				multipleChoiceWithInput();
				}
				break;
			case SINGLE_CHOICE:
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				singleChoice();
				}
				break;
			case SINGLE_CHOICE_INPUT_VALUE:
				enterOuterAlt(_localctx, 5);
				{
				setState(87);
				singleChoiceWithInput();
				}
				break;
			case NUMERIC:
				enterOuterAlt(_localctx, 6);
				{
				setState(88);
				numeric();
				}
				break;
			case SORTING_OPTIONS:
				enterOuterAlt(_localctx, 7);
				{
				setState(89);
				sortingOptions();
				}
				break;
			case SCALING_OPTIONS:
				enterOuterAlt(_localctx, 8);
				{
				setState(90);
				scalingOptions();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Free_textContext extends ParserRuleContext {
		public TerminalNode FREE_TEXT() { return getToken(LabeledExprParser.FREE_TEXT, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public TerminalNode SENTENCE() { return getToken(LabeledExprParser.SENTENCE, 0); }
		public Free_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_free_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterFree_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitFree_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitFree_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Free_textContext free_text() throws RecognitionException {
		Free_textContext _localctx = new Free_textContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_free_text);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(FREE_TEXT);
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTRA_INFO) {
				{
				setState(94);
				match(EXTRA_INFO);
				}
			}

			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SENTENCE) {
				{
				setState(97);
				match(SENTENCE);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleChoiceContext extends ParserRuleContext {
		public TerminalNode MULTIPLE_CHOICE() { return getToken(LabeledExprParser.MULTIPLE_CHOICE, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public List<TerminalNode> CHOOSE() { return getTokens(LabeledExprParser.CHOOSE); }
		public TerminalNode CHOOSE(int i) {
			return getToken(LabeledExprParser.CHOOSE, i);
		}
		public MultipleChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleChoice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterMultipleChoice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitMultipleChoice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitMultipleChoice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleChoiceContext multipleChoice() throws RecognitionException {
		MultipleChoiceContext _localctx = new MultipleChoiceContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_multipleChoice);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(MULTIPLE_CHOICE);
			setState(101);
			match(EXTRA_INFO);
			setState(103); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(102);
				match(CHOOSE);
				}
				}
				setState(105); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CHOOSE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleChoiceWithInputContext extends ParserRuleContext {
		public TerminalNode MULTIPLE_CHOICE_INPUT_VALUE() { return getToken(LabeledExprParser.MULTIPLE_CHOICE_INPUT_VALUE, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public List<TerminalNode> CHOOSE() { return getTokens(LabeledExprParser.CHOOSE); }
		public TerminalNode CHOOSE(int i) {
			return getToken(LabeledExprParser.CHOOSE, i);
		}
		public MultipleChoiceWithInputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleChoiceWithInput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterMultipleChoiceWithInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitMultipleChoiceWithInput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitMultipleChoiceWithInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleChoiceWithInputContext multipleChoiceWithInput() throws RecognitionException {
		MultipleChoiceWithInputContext _localctx = new MultipleChoiceWithInputContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_multipleChoiceWithInput);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(MULTIPLE_CHOICE_INPUT_VALUE);
			setState(108);
			match(EXTRA_INFO);
			setState(110); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(109);
				match(CHOOSE);
				}
				}
				setState(112); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CHOOSE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleChoiceContext extends ParserRuleContext {
		public TerminalNode SINGLE_CHOICE() { return getToken(LabeledExprParser.SINGLE_CHOICE, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public List<TerminalNode> CHOOSE() { return getTokens(LabeledExprParser.CHOOSE); }
		public TerminalNode CHOOSE(int i) {
			return getToken(LabeledExprParser.CHOOSE, i);
		}
		public SingleChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleChoice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterSingleChoice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitSingleChoice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitSingleChoice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleChoiceContext singleChoice() throws RecognitionException {
		SingleChoiceContext _localctx = new SingleChoiceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_singleChoice);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(SINGLE_CHOICE);
			setState(115);
			match(EXTRA_INFO);
			setState(117); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(116);
				match(CHOOSE);
				}
				}
				setState(119); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CHOOSE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleChoiceWithInputContext extends ParserRuleContext {
		public TerminalNode SINGLE_CHOICE_INPUT_VALUE() { return getToken(LabeledExprParser.SINGLE_CHOICE_INPUT_VALUE, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public List<TerminalNode> CHOOSE() { return getTokens(LabeledExprParser.CHOOSE); }
		public TerminalNode CHOOSE(int i) {
			return getToken(LabeledExprParser.CHOOSE, i);
		}
		public SingleChoiceWithInputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleChoiceWithInput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterSingleChoiceWithInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitSingleChoiceWithInput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitSingleChoiceWithInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleChoiceWithInputContext singleChoiceWithInput() throws RecognitionException {
		SingleChoiceWithInputContext _localctx = new SingleChoiceWithInputContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_singleChoiceWithInput);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(SINGLE_CHOICE_INPUT_VALUE);
			setState(122);
			match(EXTRA_INFO);
			setState(124); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(123);
				match(CHOOSE);
				}
				}
				setState(126); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CHOOSE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SortingOptionsContext extends ParserRuleContext {
		public TerminalNode SORTING_OPTIONS() { return getToken(LabeledExprParser.SORTING_OPTIONS, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public List<TerminalNode> CHOOSE() { return getTokens(LabeledExprParser.CHOOSE); }
		public TerminalNode CHOOSE(int i) {
			return getToken(LabeledExprParser.CHOOSE, i);
		}
		public SortingOptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sortingOptions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterSortingOptions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitSortingOptions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitSortingOptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SortingOptionsContext sortingOptions() throws RecognitionException {
		SortingOptionsContext _localctx = new SortingOptionsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_sortingOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(SORTING_OPTIONS);
			setState(129);
			match(EXTRA_INFO);
			setState(131); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(130);
				match(CHOOSE);
				}
				}
				setState(133); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CHOOSE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScalingOptionsContext extends ParserRuleContext {
		public TerminalNode SCALING_OPTIONS() { return getToken(LabeledExprParser.SCALING_OPTIONS, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public List<TerminalNode> CHOOSE() { return getTokens(LabeledExprParser.CHOOSE); }
		public TerminalNode CHOOSE(int i) {
			return getToken(LabeledExprParser.CHOOSE, i);
		}
		public ScalingOptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scalingOptions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterScalingOptions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitScalingOptions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitScalingOptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScalingOptionsContext scalingOptions() throws RecognitionException {
		ScalingOptionsContext _localctx = new ScalingOptionsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_scalingOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(SCALING_OPTIONS);
			setState(136);
			match(EXTRA_INFO);
			setState(138); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(137);
				match(CHOOSE);
				}
				}
				setState(140); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CHOOSE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericContext extends ParserRuleContext {
		public TerminalNode NUMERIC() { return getToken(LabeledExprParser.NUMERIC, 0); }
		public TerminalNode EXTRA_INFO() { return getToken(LabeledExprParser.EXTRA_INFO, 0); }
		public TerminalNode NUMBER() { return getToken(LabeledExprParser.NUMBER, 0); }
		public NumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numeric; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).enterNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LabeledExprListener ) ((LabeledExprListener)listener).exitNumeric(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LabeledExprVisitor ) return ((LabeledExprVisitor<? extends T>)visitor).visitNumeric(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericContext numeric() throws RecognitionException {
		NumericContext _localctx = new NumericContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_numeric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(NUMERIC);
			setState(144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTRA_INFO) {
				{
				setState(143);
				match(EXTRA_INFO);
				}
			}

			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NUMBER) {
				{
				setState(146);
				match(NUMBER);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001f\u0096\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0001\u0000\u0004\u0000\u001c\b\u0000\u000b\u0000\f\u0000"+
		"\u001d\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001#\b\u0001\n\u0001"+
		"\f\u0001&\t\u0001\u0001\u0001\u0005\u0001)\b\u0001\n\u0001\f\u0001,\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0004\u00010\b\u0001\u000b\u0001\f\u0001"+
		"1\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u00027\b\u0002\n\u0002\f\u0002"+
		":\t\u0002\u0001\u0002\u0001\u0002\u0005\u0002>\b\u0002\n\u0002\f\u0002"+
		"A\t\u0002\u0001\u0002\u0001\u0002\u0004\u0002E\b\u0002\u000b\u0002\f\u0002"+
		"F\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003L\b\u0003\n\u0003\f\u0003"+
		"O\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004\\\b\u0004\u0001\u0005\u0001\u0005\u0003\u0005`\b\u0005\u0001"+
		"\u0005\u0003\u0005c\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0004"+
		"\u0006h\b\u0006\u000b\u0006\f\u0006i\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0004\u0007o\b\u0007\u000b\u0007\f\u0007p\u0001\b\u0001\b\u0001\b\u0004"+
		"\bv\b\b\u000b\b\f\bw\u0001\t\u0001\t\u0001\t\u0004\t}\b\t\u000b\t\f\t"+
		"~\u0001\n\u0001\n\u0001\n\u0004\n\u0084\b\n\u000b\n\f\n\u0085\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0004\u000b\u008b\b\u000b\u000b\u000b\f\u000b"+
		"\u008c\u0001\f\u0001\f\u0003\f\u0091\b\f\u0001\f\u0003\f\u0094\b\f\u0001"+
		"\f\u0000\u0000\r\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u0000\u0000\u00a1\u0000\u001b\u0001\u0000\u0000\u0000\u0002"+
		"\u001f\u0001\u0000\u0000\u0000\u00043\u0001\u0000\u0000\u0000\u0006H\u0001"+
		"\u0000\u0000\u0000\b[\u0001\u0000\u0000\u0000\n]\u0001\u0000\u0000\u0000"+
		"\fd\u0001\u0000\u0000\u0000\u000ek\u0001\u0000\u0000\u0000\u0010r\u0001"+
		"\u0000\u0000\u0000\u0012y\u0001\u0000\u0000\u0000\u0014\u0080\u0001\u0000"+
		"\u0000\u0000\u0016\u0087\u0001\u0000\u0000\u0000\u0018\u008e\u0001\u0000"+
		"\u0000\u0000\u001a\u001c\u0003\u0002\u0001\u0000\u001b\u001a\u0001\u0000"+
		"\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000"+
		"\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e\u0001\u0001\u0000"+
		"\u0000\u0000\u001f \u0005\u0010\u0000\u0000 $\u0005\u0011\u0000\u0000"+
		"!#\u0005\u0012\u0000\u0000\"!\u0001\u0000\u0000\u0000#&\u0001\u0000\u0000"+
		"\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%*\u0001\u0000"+
		"\u0000\u0000&$\u0001\u0000\u0000\u0000\')\u0005\u0013\u0000\u0000(\'\u0001"+
		"\u0000\u0000\u0000),\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000"+
		"*+\u0001\u0000\u0000\u0000+-\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000"+
		"\u0000-/\u0005\u0014\u0000\u0000.0\u0003\u0004\u0002\u0000/.\u0001\u0000"+
		"\u0000\u000001\u0001\u0000\u0000\u00001/\u0001\u0000\u0000\u000012\u0001"+
		"\u0000\u0000\u00002\u0003\u0001\u0000\u0000\u000034\u0005\u0016\u0000"+
		"\u000048\u0005\u0017\u0000\u000057\u0005\u0018\u0000\u000065\u0001\u0000"+
		"\u0000\u00007:\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u000089\u0001"+
		"\u0000\u0000\u00009;\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000"+
		";?\u0005\u0019\u0000\u0000<>\u0005\u001a\u0000\u0000=<\u0001\u0000\u0000"+
		"\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000?@\u0001\u0000"+
		"\u0000\u0000@B\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000BD\u0005"+
		"\u0015\u0000\u0000CE\u0003\u0006\u0003\u0000DC\u0001\u0000\u0000\u0000"+
		"EF\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000"+
		"\u0000G\u0005\u0001\u0000\u0000\u0000HI\u0005\u001c\u0000\u0000IM\u0005"+
		"\u001d\u0000\u0000JL\u0005\u001b\u0000\u0000KJ\u0001\u0000\u0000\u0000"+
		"LO\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000"+
		"\u0000NP\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000PQ\u0005\u0019"+
		"\u0000\u0000QR\u0003\b\u0004\u0000R\u0007\u0001\u0000\u0000\u0000S\\\u0003"+
		"\n\u0005\u0000T\\\u0003\f\u0006\u0000U\\\u0003\u000e\u0007\u0000V\\\u0003"+
		"\u0010\b\u0000W\\\u0003\u0012\t\u0000X\\\u0003\u0018\f\u0000Y\\\u0003"+
		"\u0014\n\u0000Z\\\u0003\u0016\u000b\u0000[S\u0001\u0000\u0000\u0000[T"+
		"\u0001\u0000\u0000\u0000[U\u0001\u0000\u0000\u0000[V\u0001\u0000\u0000"+
		"\u0000[W\u0001\u0000\u0000\u0000[X\u0001\u0000\u0000\u0000[Y\u0001\u0000"+
		"\u0000\u0000[Z\u0001\u0000\u0000\u0000\\\t\u0001\u0000\u0000\u0000]_\u0005"+
		"\b\u0000\u0000^`\u0005\u001e\u0000\u0000_^\u0001\u0000\u0000\u0000_`\u0001"+
		"\u0000\u0000\u0000`b\u0001\u0000\u0000\u0000ac\u0005\u0002\u0000\u0000"+
		"ba\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000c\u000b\u0001\u0000"+
		"\u0000\u0000de\u0005\t\u0000\u0000eg\u0005\u001e\u0000\u0000fh\u0005\u0007"+
		"\u0000\u0000gf\u0001\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000ig\u0001"+
		"\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000j\r\u0001\u0000\u0000\u0000"+
		"kl\u0005\n\u0000\u0000ln\u0005\u001e\u0000\u0000mo\u0005\u0007\u0000\u0000"+
		"nm\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000"+
		"\u0000pq\u0001\u0000\u0000\u0000q\u000f\u0001\u0000\u0000\u0000rs\u0005"+
		"\u000b\u0000\u0000su\u0005\u001e\u0000\u0000tv\u0005\u0007\u0000\u0000"+
		"ut\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wu\u0001\u0000\u0000"+
		"\u0000wx\u0001\u0000\u0000\u0000x\u0011\u0001\u0000\u0000\u0000yz\u0005"+
		"\f\u0000\u0000z|\u0005\u001e\u0000\u0000{}\u0005\u0007\u0000\u0000|{\u0001"+
		"\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000"+
		"~\u007f\u0001\u0000\u0000\u0000\u007f\u0013\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0005\u000e\u0000\u0000\u0081\u0083\u0005\u001e\u0000\u0000\u0082"+
		"\u0084\u0005\u0007\u0000\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0084"+
		"\u0085\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000\u0000\u0000\u0085"+
		"\u0086\u0001\u0000\u0000\u0000\u0086\u0015\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0005\u000f\u0000\u0000\u0088\u008a\u0005\u001e\u0000\u0000\u0089"+
		"\u008b\u0005\u0007\u0000\u0000\u008a\u0089\u0001\u0000\u0000\u0000\u008b"+
		"\u008c\u0001\u0000\u0000\u0000\u008c\u008a\u0001\u0000\u0000\u0000\u008c"+
		"\u008d\u0001\u0000\u0000\u0000\u008d\u0017\u0001\u0000\u0000\u0000\u008e"+
		"\u0090\u0005\r\u0000\u0000\u008f\u0091\u0005\u001e\u0000\u0000\u0090\u008f"+
		"\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u0093"+
		"\u0001\u0000\u0000\u0000\u0092\u0094\u0005\u0003\u0000\u0000\u0093\u0092"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0019"+
		"\u0001\u0000\u0000\u0000\u0013\u001d$*18?FM[_bipw~\u0085\u008c\u0090\u0093";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}