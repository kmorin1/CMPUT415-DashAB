// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g 2014-11-23 13:10:50

  package dash; 
  import java.util.ArrayList;
  import CFT.*;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class CFTExtract extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "GENERATOR", "DECL", "PROGRAM", "BLOCK", "PARAMLIST", "CALL", "ARGLIST", "TUPLEEX", "POS", "NEG", "VCONST", "ROW", "COLUMN", "Break", "SemiColon", "Continue", "Identifier", "Assign", "StdOutput", "StdInput", "LParen", "RParen", "RArrow", "LArrow", "Stream", "Vector", "LBracket", "RBracket", "Matrix", "Comma", "Typedef", "LBrace", "RBrace", "Procedure", "Returns", "Function", "Call", "Return", "If", "Else", "Loop", "While", "Boolean", "Integer", "Interval", "String", "Real", "Character", "Tuple", "Const", "Var", "Concat", "Xor", "Or", "And", "Equals", "NEquals", "LThan", "GThan", "LThanE", "GThanE", "By", "Plus", "Minus", "Multiply", "Divide", "Mod", "Exponent", "Not", "Range", "Number", "FPNumber", "True", "False", "Null", "Identity", "Char", "Dot", "As", "Filter", "In", "Bar", "Rows", "Columns", "Length", "Reverse", "Comment", "LBComment", "RBComment", "Digit", "MULTILINE_COMMENT", "COMMENT", "Space"
    };
    public static final int EOF=-1;
    public static final int GENERATOR=4;
    public static final int DECL=5;
    public static final int PROGRAM=6;
    public static final int BLOCK=7;
    public static final int PARAMLIST=8;
    public static final int CALL=9;
    public static final int ARGLIST=10;
    public static final int TUPLEEX=11;
    public static final int POS=12;
    public static final int NEG=13;
    public static final int VCONST=14;
    public static final int ROW=15;
    public static final int COLUMN=16;
    public static final int Break=17;
    public static final int SemiColon=18;
    public static final int Continue=19;
    public static final int Identifier=20;
    public static final int Assign=21;
    public static final int StdOutput=22;
    public static final int StdInput=23;
    public static final int LParen=24;
    public static final int RParen=25;
    public static final int RArrow=26;
    public static final int LArrow=27;
    public static final int Stream=28;
    public static final int Vector=29;
    public static final int LBracket=30;
    public static final int RBracket=31;
    public static final int Matrix=32;
    public static final int Comma=33;
    public static final int Typedef=34;
    public static final int LBrace=35;
    public static final int RBrace=36;
    public static final int Procedure=37;
    public static final int Returns=38;
    public static final int Function=39;
    public static final int Call=40;
    public static final int Return=41;
    public static final int If=42;
    public static final int Else=43;
    public static final int Loop=44;
    public static final int While=45;
    public static final int Boolean=46;
    public static final int Integer=47;
    public static final int Interval=48;
    public static final int String=49;
    public static final int Real=50;
    public static final int Character=51;
    public static final int Tuple=52;
    public static final int Const=53;
    public static final int Var=54;
    public static final int Concat=55;
    public static final int Xor=56;
    public static final int Or=57;
    public static final int And=58;
    public static final int Equals=59;
    public static final int NEquals=60;
    public static final int LThan=61;
    public static final int GThan=62;
    public static final int LThanE=63;
    public static final int GThanE=64;
    public static final int By=65;
    public static final int Plus=66;
    public static final int Minus=67;
    public static final int Multiply=68;
    public static final int Divide=69;
    public static final int Mod=70;
    public static final int Exponent=71;
    public static final int Not=72;
    public static final int Range=73;
    public static final int Number=74;
    public static final int FPNumber=75;
    public static final int True=76;
    public static final int False=77;
    public static final int Null=78;
    public static final int Identity=79;
    public static final int Char=80;
    public static final int Dot=81;
    public static final int As=82;
    public static final int Filter=83;
    public static final int In=84;
    public static final int Bar=85;
    public static final int Rows=86;
    public static final int Columns=87;
    public static final int Length=88;
    public static final int Reverse=89;
    public static final int Comment=90;
    public static final int LBComment=91;
    public static final int RBComment=92;
    public static final int Digit=93;
    public static final int MULTILINE_COMMENT=94;
    public static final int COMMENT=95;
    public static final int Space=96;

    // delegates
    // delegators


        public CFTExtract(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public CFTExtract(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[98+1];
             
             
        }
        

    public String[] getTokenNames() { return CFTExtract.tokenNames; }
    public String getGrammarFileName() { return "/home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g"; }


      ArrayList<ControlFlowTree> cfts;
      //CFTNode currnode;
      public CFTExtract(TreeNodeStream input, ArrayList<ControlFlowTree> cfts) {
        this(input);
        this.cfts = cfts;
      }
      Integer gencounter = 0;



    // $ANTLR start "program"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:28:1: program : ^( PROGRAM ( globalStatement )* ) ;
    public final void program() throws RecognitionException {
        int program_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:29:3: ( ^( PROGRAM ( globalStatement )* ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:29:5: ^( PROGRAM ( globalStatement )* )
            {
            match(input,PROGRAM,FOLLOW_PROGRAM_in_program78); if (state.failed) return ;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return ;
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:29:15: ( globalStatement )*
                loop1:
                do {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==DECL||LA1_0==Typedef||LA1_0==Procedure||LA1_0==Function) ) {
                        alt1=1;
                    }


                    switch (alt1) {
                	case 1 :
                	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: globalStatement
                	    {
                	    pushFollow(FOLLOW_globalStatement_in_program80);
                	    globalStatement();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }
                	    break;

                	default :
                	    break loop1;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return ;
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, program_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "program"


    // $ANTLR start "globalStatement"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:32:1: globalStatement : ( declaration | typedef | procedure | function );
    public final void globalStatement() throws RecognitionException {
        int globalStatement_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:33:3: ( declaration | typedef | procedure | function )
            int alt2=4;
            switch ( input.LA(1) ) {
            case DECL:
                {
                alt2=1;
                }
                break;
            case Typedef:
                {
                alt2=2;
                }
                break;
            case Procedure:
                {
                alt2=3;
                }
                break;
            case Function:
                {
                alt2=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:33:5: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_globalStatement97);
                    declaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:34:5: typedef
                    {
                    pushFollow(FOLLOW_typedef_in_globalStatement103);
                    typedef();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:35:5: procedure
                    {
                    pushFollow(FOLLOW_procedure_in_globalStatement109);
                    procedure();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:36:5: function
                    {
                    pushFollow(FOLLOW_function_in_globalStatement115);
                    function();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, globalStatement_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "globalStatement"


    // $ANTLR start "statement"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:39:1: statement returns [CFTNode cftn] : ( assignment | outputstream | inputstream | ifstatement | loopstatement | block | callStatement | returnStatement | Break | Continue );
    public final CFTNode statement() throws RecognitionException {
        CFTNode cftn = null;
        int statement_StartIndex = input.index();
        CFTNode ifstatement1 = null;

        CFTNode loopstatement2 = null;

        CFTNode block3 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return cftn; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:40:3: ( assignment | outputstream | inputstream | ifstatement | loopstatement | block | callStatement | returnStatement | Break | Continue )
            int alt3=10;
            switch ( input.LA(1) ) {
            case Assign:
                {
                alt3=1;
                }
                break;
            case RArrow:
                {
                alt3=2;
                }
                break;
            case LArrow:
                {
                alt3=3;
                }
                break;
            case If:
                {
                alt3=4;
                }
                break;
            case Loop:
                {
                alt3=5;
                }
                break;
            case BLOCK:
                {
                alt3=6;
                }
                break;
            case CALL:
                {
                alt3=7;
                }
                break;
            case Return:
                {
                alt3=8;
                }
                break;
            case Break:
                {
                alt3=9;
                }
                break;
            case Continue:
                {
                alt3=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return cftn;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:40:5: assignment
                    {
                    pushFollow(FOLLOW_assignment_in_statement135);
                    assignment();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:41:5: outputstream
                    {
                    pushFollow(FOLLOW_outputstream_in_statement143);
                    outputstream();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:42:5: inputstream
                    {
                    pushFollow(FOLLOW_inputstream_in_statement151);
                    inputstream();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;
                case 4 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:43:5: ifstatement
                    {
                    pushFollow(FOLLOW_ifstatement_in_statement159);
                    ifstatement1=ifstatement();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = ifstatement1;
                    }

                    }
                    break;
                case 5 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:44:5: loopstatement
                    {
                    pushFollow(FOLLOW_loopstatement_in_statement167);
                    loopstatement2=loopstatement();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = loopstatement2;
                    }

                    }
                    break;
                case 6 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:45:5: block
                    {
                    pushFollow(FOLLOW_block_in_statement175);
                    block3=block();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = block3.removeChildAtEnd();
                    }

                    }
                    break;
                case 7 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:46:5: callStatement
                    {
                    pushFollow(FOLLOW_callStatement_in_statement183);
                    callStatement();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;
                case 8 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:47:5: returnStatement
                    {
                    pushFollow(FOLLOW_returnStatement_in_statement191);
                    returnStatement();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("return" + gencounter++, null);
                    }

                    }
                    break;
                case 9 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:48:5: Break
                    {
                    match(input,Break,FOLLOW_Break_in_statement199); if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;
                case 10 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:49:5: Continue
                    {
                    match(input,Continue,FOLLOW_Continue_in_statement207); if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, statement_StartIndex); }
        }
        return cftn;
    }
    // $ANTLR end "statement"


    // $ANTLR start "outputstream"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:52:1: outputstream : ^( RArrow expr Identifier ) ;
    public final void outputstream() throws RecognitionException {
        int outputstream_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:53:3: ( ^( RArrow expr Identifier ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:53:5: ^( RArrow expr Identifier )
            {
            match(input,RArrow,FOLLOW_RArrow_in_outputstream225); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            pushFollow(FOLLOW_expr_in_outputstream227);
            expr();

            state._fsp--;
            if (state.failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_outputstream229); if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, outputstream_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "outputstream"


    // $ANTLR start "inputstream"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:56:1: inputstream : ^( LArrow Identifier Identifier ) ;
    public final void inputstream() throws RecognitionException {
        int inputstream_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:57:3: ( ^( LArrow Identifier Identifier ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:57:5: ^( LArrow Identifier Identifier )
            {
            match(input,LArrow,FOLLOW_LArrow_in_inputstream244); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_inputstream246); if (state.failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_inputstream248); if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, inputstream_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "inputstream"


    // $ANTLR start "declaration"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:60:1: declaration returns [CFTNode cftn] : ( ^( DECL ( specifier )? ( type )* Identifier ) | ^( DECL ( specifier )? ( type )* ^( Assign Identifier expr ) ) | ^( DECL specifier ^( Assign Identifier StdInput ) ) | ^( DECL specifier ^( Assign Identifier StdOutput ) ) );
    public final CFTNode declaration() throws RecognitionException {
        CFTNode cftn = null;
        int declaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return cftn; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:3: ( ^( DECL ( specifier )? ( type )* Identifier ) | ^( DECL ( specifier )? ( type )* ^( Assign Identifier expr ) ) | ^( DECL specifier ^( Assign Identifier StdInput ) ) | ^( DECL specifier ^( Assign Identifier StdOutput ) ) )
            int alt8=4;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==DECL) ) {
                int LA8_1 = input.LA(2);

                if ( (synpred16_CFTExtract()) ) {
                    alt8=1;
                }
                else if ( (synpred19_CFTExtract()) ) {
                    alt8=2;
                }
                else if ( (synpred20_CFTExtract()) ) {
                    alt8=3;
                }
                else if ( (true) ) {
                    alt8=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return cftn;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return cftn;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:5: ^( DECL ( specifier )? ( type )* Identifier )
                    {
                    match(input,DECL,FOLLOW_DECL_in_declaration272); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:12: ( specifier )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=Const && LA4_0<=Var)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: specifier
                            {
                            pushFollow(FOLLOW_specifier_in_declaration274);
                            specifier();

                            state._fsp--;
                            if (state.failed) return cftn;

                            }
                            break;

                    }

                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:23: ( type )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==Identifier) ) {
                            int LA5_1 = input.LA(2);

                            if ( (LA5_1==Identifier||LA5_1==Vector||LA5_1==Matrix||(LA5_1>=Boolean && LA5_1<=Tuple)) ) {
                                alt5=1;
                            }


                        }
                        else if ( (LA5_0==Vector||LA5_0==Matrix||(LA5_0>=Boolean && LA5_0<=Tuple)) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: type
                    	    {
                    	    pushFollow(FOLLOW_type_in_declaration277);
                    	    type();

                    	    state._fsp--;
                    	    if (state.failed) return cftn;

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    match(input,Identifier,FOLLOW_Identifier_in_declaration280); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:5: ^( DECL ( specifier )? ( type )* ^( Assign Identifier expr ) )
                    {
                    match(input,DECL,FOLLOW_DECL_in_declaration288); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:12: ( specifier )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( ((LA6_0>=Const && LA6_0<=Var)) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: specifier
                            {
                            pushFollow(FOLLOW_specifier_in_declaration290);
                            specifier();

                            state._fsp--;
                            if (state.failed) return cftn;

                            }
                            break;

                    }

                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:23: ( type )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==Identifier||LA7_0==Vector||LA7_0==Matrix||(LA7_0>=Boolean && LA7_0<=Tuple)) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: type
                    	    {
                    	    pushFollow(FOLLOW_type_in_declaration293);
                    	    type();

                    	    state._fsp--;
                    	    if (state.failed) return cftn;

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match(input,Assign,FOLLOW_Assign_in_declaration297); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    match(input,Identifier,FOLLOW_Identifier_in_declaration299); if (state.failed) return cftn;
                    pushFollow(FOLLOW_expr_in_declaration301);
                    expr();

                    state._fsp--;
                    if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:66:5: ^( DECL specifier ^( Assign Identifier StdInput ) )
                    {
                    match(input,DECL,FOLLOW_DECL_in_declaration310); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_specifier_in_declaration312);
                    specifier();

                    state._fsp--;
                    if (state.failed) return cftn;
                    match(input,Assign,FOLLOW_Assign_in_declaration315); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    match(input,Identifier,FOLLOW_Identifier_in_declaration317); if (state.failed) return cftn;
                    match(input,StdInput,FOLLOW_StdInput_in_declaration319); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    }
                    break;
                case 4 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:67:5: ^( DECL specifier ^( Assign Identifier StdOutput ) )
                    {
                    match(input,DECL,FOLLOW_DECL_in_declaration328); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_specifier_in_declaration330);
                    specifier();

                    state._fsp--;
                    if (state.failed) return cftn;
                    match(input,Assign,FOLLOW_Assign_in_declaration333); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    match(input,Identifier,FOLLOW_Identifier_in_declaration335); if (state.failed) return cftn;
                    match(input,StdOutput,FOLLOW_StdOutput_in_declaration337); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    }
                    break;

            }
            if ( state.backtracking==0 ) {

                cftn = new CFTNode("generic" + gencounter++, null);

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, declaration_StartIndex); }
        }
        return cftn;
    }
    // $ANTLR end "declaration"


    // $ANTLR start "typedef"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:70:1: typedef : ^( Typedef type Identifier ) ;
    public final void typedef() throws RecognitionException {
        int typedef_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:71:3: ( ^( Typedef type Identifier ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:71:5: ^( Typedef type Identifier )
            {
            match(input,Typedef,FOLLOW_Typedef_in_typedef355); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            pushFollow(FOLLOW_type_in_typedef357);
            type();

            state._fsp--;
            if (state.failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_typedef359); if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, typedef_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "typedef"


    // $ANTLR start "block"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:74:1: block returns [CFTNode cftn] : ^( BLOCK ( declaration )* (s= statement )* ) ;
    public final CFTNode block() throws RecognitionException {
        CFTNode cftn = null;
        int block_StartIndex = input.index();
        CFTNode s = null;



          CFTNode subroot = new CFTNode("subroot" + gencounter++, null);

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return cftn; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:82:3: ( ^( BLOCK ( declaration )* (s= statement )* ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:82:5: ^( BLOCK ( declaration )* (s= statement )* )
            {
            match(input,BLOCK,FOLLOW_BLOCK_in_block388); if (state.failed) return cftn;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return cftn;
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:82:13: ( declaration )*
                loop9:
                do {
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==DECL) ) {
                        alt9=1;
                    }


                    switch (alt9) {
                	case 1 :
                	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: declaration
                	    {
                	    pushFollow(FOLLOW_declaration_in_block390);
                	    declaration();

                	    state._fsp--;
                	    if (state.failed) return cftn;

                	    }
                	    break;

                	default :
                	    break loop9;
                    }
                } while (true);

                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:82:26: (s= statement )*
                loop10:
                do {
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==BLOCK||LA10_0==CALL||LA10_0==Break||LA10_0==Continue||LA10_0==Assign||(LA10_0>=RArrow && LA10_0<=LArrow)||(LA10_0>=Return && LA10_0<=If)||LA10_0==Loop) ) {
                        alt10=1;
                    }


                    switch (alt10) {
                	case 1 :
                	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:82:27: s= statement
                	    {
                	    pushFollow(FOLLOW_statement_in_block396);
                	    s=statement();

                	    state._fsp--;
                	    if (state.failed) return cftn;
                	    if ( state.backtracking==0 ) {
                	      subroot.addChildAtEnd(s);
                	    }

                	    }
                	    break;

                	default :
                	    break loop10;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return cftn;
            }

            }

            if ( state.backtracking==0 ) {

                subroot.addChildAtEnd(new CFTNode("endblock" + gencounter++, null));
                cftn = subroot;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, block_StartIndex); }
        }
        return cftn;
    }
    // $ANTLR end "block"


    // $ANTLR start "procedure"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:85:1: procedure : ( ^( Procedure id= Identifier paramlist ^( Returns type ) block ) | ^( Procedure id= Identifier paramlist block ) | ^( Procedure Identifier paramlist ^( Returns type ) ) | ^( Procedure Identifier paramlist ) );
    public final void procedure() throws RecognitionException {
        int procedure_StartIndex = input.index();
        CommonTree id=null;
        CFTNode block4 = null;

        CFTNode block5 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:86:3: ( ^( Procedure id= Identifier paramlist ^( Returns type ) block ) | ^( Procedure id= Identifier paramlist block ) | ^( Procedure Identifier paramlist ^( Returns type ) ) | ^( Procedure Identifier paramlist ) )
            int alt11=4;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==Procedure) ) {
                int LA11_1 = input.LA(2);

                if ( (synpred23_CFTExtract()) ) {
                    alt11=1;
                }
                else if ( (synpred24_CFTExtract()) ) {
                    alt11=2;
                }
                else if ( (synpred25_CFTExtract()) ) {
                    alt11=3;
                }
                else if ( (true) ) {
                    alt11=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:86:5: ^( Procedure id= Identifier paramlist ^( Returns type ) block )
                    {
                    match(input,Procedure,FOLLOW_Procedure_in_procedure417); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    id=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_procedure421); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_procedure423);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,Returns,FOLLOW_Returns_in_procedure426); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_type_in_procedure428);
                    type();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;
                    pushFollow(FOLLOW_block_in_procedure435);
                    block4=block();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      cfts.add(new ControlFlowTree((id!=null?id.getText():null), block4.removeChildAtEnd()));
                    }

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:88:5: ^( Procedure id= Identifier paramlist block )
                    {
                    match(input,Procedure,FOLLOW_Procedure_in_procedure445); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    id=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_procedure449); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_procedure451);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_block_in_procedure458);
                    block5=block();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      cfts.add(new ControlFlowTree((id!=null?id.getText():null), block5.removeChildAtEnd(), false));
                    }

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:90:5: ^( Procedure Identifier paramlist ^( Returns type ) )
                    {
                    match(input,Procedure,FOLLOW_Procedure_in_procedure468); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_procedure470); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_procedure472);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,Returns,FOLLOW_Returns_in_procedure475); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_type_in_procedure477);
                    type();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:91:5: ^( Procedure Identifier paramlist )
                    {
                    match(input,Procedure,FOLLOW_Procedure_in_procedure486); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_procedure488); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_procedure490);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, procedure_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "procedure"


    // $ANTLR start "function"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:94:1: function : ( ^( Function id= Identifier paramlist ^( Returns type ) block ) | ^( Function Identifier paramlist ^( Returns type ) ^( Assign expr ) ) | ^( Function Identifier paramlist ^( Returns type ) ) );
    public final void function() throws RecognitionException {
        int function_StartIndex = input.index();
        CommonTree id=null;
        CFTNode block6 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:95:3: ( ^( Function id= Identifier paramlist ^( Returns type ) block ) | ^( Function Identifier paramlist ^( Returns type ) ^( Assign expr ) ) | ^( Function Identifier paramlist ^( Returns type ) ) )
            int alt12=3;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==Function) ) {
                int LA12_1 = input.LA(2);

                if ( (synpred26_CFTExtract()) ) {
                    alt12=1;
                }
                else if ( (synpred27_CFTExtract()) ) {
                    alt12=2;
                }
                else if ( (true) ) {
                    alt12=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:95:5: ^( Function id= Identifier paramlist ^( Returns type ) block )
                    {
                    match(input,Function,FOLLOW_Function_in_function507); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    id=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_function511); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_function513);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,Returns,FOLLOW_Returns_in_function516); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_type_in_function518);
                    type();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;
                    pushFollow(FOLLOW_block_in_function526);
                    block6=block();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      cfts.add(new ControlFlowTree((id!=null?id.getText():null), block6.removeChildAtEnd()));
                    }

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:97:5: ^( Function Identifier paramlist ^( Returns type ) ^( Assign expr ) )
                    {
                    match(input,Function,FOLLOW_Function_in_function536); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_function538); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_function540);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,Returns,FOLLOW_Returns_in_function543); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_type_in_function545);
                    type();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;
                    match(input,Assign,FOLLOW_Assign_in_function549); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_function551);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:98:5: ^( Function Identifier paramlist ^( Returns type ) )
                    {
                    match(input,Function,FOLLOW_Function_in_function560); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_function562); if (state.failed) return ;
                    pushFollow(FOLLOW_paramlist_in_function564);
                    paramlist();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,Returns,FOLLOW_Returns_in_function567); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_type_in_function569);
                    type();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, function_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "function"


    // $ANTLR start "paramlist"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:101:1: paramlist : ^( PARAMLIST ( parameter )* ) ;
    public final void paramlist() throws RecognitionException {
        int paramlist_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:102:3: ( ^( PARAMLIST ( parameter )* ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:102:5: ^( PARAMLIST ( parameter )* )
            {
            match(input,PARAMLIST,FOLLOW_PARAMLIST_in_paramlist587); if (state.failed) return ;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return ;
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:102:17: ( parameter )*
                loop13:
                do {
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==Identifier) ) {
                        alt13=1;
                    }


                    switch (alt13) {
                	case 1 :
                	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: parameter
                	    {
                	    pushFollow(FOLLOW_parameter_in_paramlist589);
                	    parameter();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }
                	    break;

                	default :
                	    break loop13;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return ;
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, paramlist_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "paramlist"


    // $ANTLR start "parameter"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:105:1: parameter : ^( Identifier ( specifier )? type ) ;
    public final void parameter() throws RecognitionException {
        int parameter_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:106:3: ( ^( Identifier ( specifier )? type ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:106:5: ^( Identifier ( specifier )? type )
            {
            match(input,Identifier,FOLLOW_Identifier_in_parameter607); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:106:18: ( specifier )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=Const && LA14_0<=Var)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: specifier
                    {
                    pushFollow(FOLLOW_specifier_in_parameter609);
                    specifier();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_type_in_parameter612);
            type();

            state._fsp--;
            if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, parameter_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "parameter"


    // $ANTLR start "callStatement"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:109:1: callStatement : ^( CALL Identifier ^( ARGLIST ( expr )* ) ) ;
    public final void callStatement() throws RecognitionException {
        int callStatement_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:110:3: ( ^( CALL Identifier ^( ARGLIST ( expr )* ) ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:110:5: ^( CALL Identifier ^( ARGLIST ( expr )* ) )
            {
            match(input,CALL,FOLLOW_CALL_in_callStatement629); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_callStatement631); if (state.failed) return ;
            match(input,ARGLIST,FOLLOW_ARGLIST_in_callStatement634); if (state.failed) return ;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return ;
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:110:33: ( expr )*
                loop15:
                do {
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==CALL||LA15_0==TUPLEEX||LA15_0==Identifier||(LA15_0>=Xor && LA15_0<=Not)||(LA15_0>=Number && LA15_0<=As)) ) {
                        alt15=1;
                    }


                    switch (alt15) {
                	case 1 :
                	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: expr
                	    {
                	    pushFollow(FOLLOW_expr_in_callStatement636);
                	    expr();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }
                	    break;

                	default :
                	    break loop15;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return ;
            }

            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, callStatement_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "callStatement"


    // $ANTLR start "returnStatement"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:113:1: returnStatement : ^( Return ( expr )? ) ;
    public final void returnStatement() throws RecognitionException {
        int returnStatement_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:114:3: ( ^( Return ( expr )? ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:114:5: ^( Return ( expr )? )
            {
            match(input,Return,FOLLOW_Return_in_returnStatement655); if (state.failed) return ;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return ;
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:114:14: ( expr )?
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==CALL||LA16_0==TUPLEEX||LA16_0==Identifier||(LA16_0>=Xor && LA16_0<=Not)||(LA16_0>=Number && LA16_0<=As)) ) {
                    alt16=1;
                }
                switch (alt16) {
                    case 1 :
                        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: expr
                        {
                        pushFollow(FOLLOW_expr_in_returnStatement657);
                        expr();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;

                }


                match(input, Token.UP, null); if (state.failed) return ;
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, returnStatement_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "returnStatement"


    // $ANTLR start "assignment"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:117:1: assignment : ^( Assign Identifier expr ) ;
    public final void assignment() throws RecognitionException {
        int assignment_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:118:3: ( ^( Assign Identifier expr ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:118:5: ^( Assign Identifier expr )
            {
            match(input,Assign,FOLLOW_Assign_in_assignment675); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_assignment677); if (state.failed) return ;
            pushFollow(FOLLOW_expr_in_assignment679);
            expr();

            state._fsp--;
            if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, assignment_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "assignment"


    // $ANTLR start "ifstatement"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:121:1: ifstatement returns [CFTNode cftn] : ( ^( If expr s1= slist ^( Else s2= slist ) ) | ^( If expr slist ) );
    public final CFTNode ifstatement() throws RecognitionException {
        CFTNode cftn = null;
        int ifstatement_StartIndex = input.index();
        CFTNode s1 = null;

        CFTNode s2 = null;



          CFTNode ifnode = new CFTNode("ifnode" + gencounter++, null);

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return cftn; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:125:3: ( ^( If expr s1= slist ^( Else s2= slist ) ) | ^( If expr slist ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==If) ) {
                int LA17_1 = input.LA(2);

                if ( (synpred32_CFTExtract()) ) {
                    alt17=1;
                }
                else if ( (true) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return cftn;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return cftn;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:125:5: ^( If expr s1= slist ^( Else s2= slist ) )
                    {
                    match(input,If,FOLLOW_If_in_ifstatement705); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_expr_in_ifstatement707);
                    expr();

                    state._fsp--;
                    if (state.failed) return cftn;
                    pushFollow(FOLLOW_slist_in_ifstatement711);
                    s1=slist();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      ifnode.addChild(s1);
                    }
                    match(input,Else,FOLLOW_Else_in_ifstatement716); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_slist_in_ifstatement720);
                    s2=slist();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      ifnode.addChild(s2); cftn = ifnode;
                    }

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:126:5: ^( If expr slist )
                    {
                    match(input,If,FOLLOW_If_in_ifstatement732); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_expr_in_ifstatement734);
                    expr();

                    state._fsp--;
                    if (state.failed) return cftn;
                    pushFollow(FOLLOW_slist_in_ifstatement736);
                    slist();

                    state._fsp--;
                    if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, ifstatement_StartIndex); }
        }
        return cftn;
    }
    // $ANTLR end "ifstatement"


    // $ANTLR start "loopstatement"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:129:1: loopstatement returns [CFTNode cftn] : ( ^( Loop ^( While expr ) slist ) | ^( Loop slist ^( While expr ) ) | ^( Loop slist ) );
    public final CFTNode loopstatement() throws RecognitionException {
        CFTNode cftn = null;
        int loopstatement_StartIndex = input.index();
        CFTNode slist7 = null;

        CFTNode slist8 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return cftn; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:130:3: ( ^( Loop ^( While expr ) slist ) | ^( Loop slist ^( While expr ) ) | ^( Loop slist ) )
            int alt18=3;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==Loop) ) {
                int LA18_1 = input.LA(2);

                if ( (synpred33_CFTExtract()) ) {
                    alt18=1;
                }
                else if ( (synpred34_CFTExtract()) ) {
                    alt18=2;
                }
                else if ( (true) ) {
                    alt18=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return cftn;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return cftn;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:130:5: ^( Loop ^( While expr ) slist )
                    {
                    match(input,Loop,FOLLOW_Loop_in_loopstatement759); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    match(input,While,FOLLOW_While_in_loopstatement762); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_expr_in_loopstatement764);
                    expr();

                    state._fsp--;
                    if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_slist_in_loopstatement767);
                    slist();

                    state._fsp--;
                    if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = new CFTNode("generic" + gencounter++, null);
                    }

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:131:5: ^( Loop slist ^( While expr ) )
                    {
                    match(input,Loop,FOLLOW_Loop_in_loopstatement777); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_slist_in_loopstatement779);
                    slist7=slist();

                    state._fsp--;
                    if (state.failed) return cftn;
                    match(input,While,FOLLOW_While_in_loopstatement782); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_expr_in_loopstatement784);
                    expr();

                    state._fsp--;
                    if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = slist7;
                    }

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:132:5: ^( Loop slist )
                    {
                    match(input,Loop,FOLLOW_Loop_in_loopstatement795); if (state.failed) return cftn;

                    match(input, Token.DOWN, null); if (state.failed) return cftn;
                    pushFollow(FOLLOW_slist_in_loopstatement797);
                    slist8=slist();

                    state._fsp--;
                    if (state.failed) return cftn;

                    match(input, Token.UP, null); if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = slist8;
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, loopstatement_StartIndex); }
        }
        return cftn;
    }
    // $ANTLR end "loopstatement"


    // $ANTLR start "slist"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:135:1: slist returns [CFTNode cftn] : ( block | statement | declaration );
    public final CFTNode slist() throws RecognitionException {
        CFTNode cftn = null;
        int slist_StartIndex = input.index();
        CFTNode block9 = null;

        CFTNode statement10 = null;

        CFTNode declaration11 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return cftn; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:136:3: ( block | statement | declaration )
            int alt19=3;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:136:5: block
                    {
                    pushFollow(FOLLOW_block_in_slist819);
                    block9=block();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {
                      cftn = block9;
                    }

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:137:5: statement
                    {
                    pushFollow(FOLLOW_statement_in_slist827);
                    statement10=statement();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {

                          CFTNode temp = statement10;
                          temp.addChildAtEnd(new CFTNode("endblock" + gencounter++, null));
                          cftn = temp;
                        
                    }

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:142:5: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_slist835);
                    declaration11=declaration();

                    state._fsp--;
                    if (state.failed) return cftn;
                    if ( state.backtracking==0 ) {

                          CFTNode temp = declaration11;
                          temp.addChildAtEnd(new CFTNode("endblock" + gencounter++, null));
                          cftn = temp;
                        
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, slist_StartIndex); }
        }
        return cftn;
    }
    // $ANTLR end "slist"


    // $ANTLR start "type"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:149:1: type : ( Boolean | Integer | Matrix | Interval | String | Vector | Real | Character | tuple | Identifier );
    public final void type() throws RecognitionException {
        int type_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:150:3: ( Boolean | Integer | Matrix | Interval | String | Vector | Real | Character | tuple | Identifier )
            int alt20=10;
            switch ( input.LA(1) ) {
            case Boolean:
                {
                alt20=1;
                }
                break;
            case Integer:
                {
                alt20=2;
                }
                break;
            case Matrix:
                {
                alt20=3;
                }
                break;
            case Interval:
                {
                alt20=4;
                }
                break;
            case String:
                {
                alt20=5;
                }
                break;
            case Vector:
                {
                alt20=6;
                }
                break;
            case Real:
                {
                alt20=7;
                }
                break;
            case Character:
                {
                alt20=8;
                }
                break;
            case Tuple:
                {
                alt20=9;
                }
                break;
            case Identifier:
                {
                alt20=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:150:5: Boolean
                    {
                    match(input,Boolean,FOLLOW_Boolean_in_type852); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:151:5: Integer
                    {
                    match(input,Integer,FOLLOW_Integer_in_type858); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:152:5: Matrix
                    {
                    match(input,Matrix,FOLLOW_Matrix_in_type864); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:153:5: Interval
                    {
                    match(input,Interval,FOLLOW_Interval_in_type870); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:154:5: String
                    {
                    match(input,String,FOLLOW_String_in_type876); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:155:5: Vector
                    {
                    match(input,Vector,FOLLOW_Vector_in_type882); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:156:5: Real
                    {
                    match(input,Real,FOLLOW_Real_in_type888); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:157:5: Character
                    {
                    match(input,Character,FOLLOW_Character_in_type894); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:158:5: tuple
                    {
                    pushFollow(FOLLOW_tuple_in_type900);
                    tuple();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:159:5: Identifier
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_type906); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, type_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "type"


    // $ANTLR start "tuple"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:162:1: tuple : ^( Tuple ( type )+ ) ;
    public final void tuple() throws RecognitionException {
        int tuple_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:163:3: ( ^( Tuple ( type )+ ) )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:163:5: ^( Tuple ( type )+ )
            {
            match(input,Tuple,FOLLOW_Tuple_in_tuple922); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:163:13: ( type )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Identifier||LA21_0==Vector||LA21_0==Matrix||(LA21_0>=Boolean && LA21_0<=Tuple)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: type
            	    {
            	    pushFollow(FOLLOW_type_in_tuple924);
            	    type();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, tuple_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "tuple"


    // $ANTLR start "specifier"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:166:1: specifier : ( Const | Var );
    public final void specifier() throws RecognitionException {
        int specifier_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:167:3: ( Const | Var )
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:
            {
            if ( (input.LA(1)>=Const && input.LA(1)<=Var) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, specifier_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "specifier"


    // $ANTLR start "expr"
    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:171:1: expr : ( ^( Plus expr expr ) | ^( Minus expr expr ) | ^( Multiply expr expr ) | ^( Divide expr expr ) | ^( Mod expr expr ) | ^( Exponent expr expr ) | ^( Equals expr expr ) | ^( NEquals expr expr ) | ^( GThan expr expr ) | ^( LThan expr expr ) | ^( GThanE expr expr ) | ^( LThanE expr expr ) | ^( Or expr expr ) | ^( Xor expr expr ) | ^( And expr expr ) | ^( Not expr ) | ^( By expr expr ) | ^( CALL Identifier ^( ARGLIST ( expr )* ) ) | ^( As type expr ) | Identifier | Number | FPNumber | True | False | Null | Identity | Char | ^( TUPLEEX expr ) | ^( Dot Identifier ) );
    public final void expr() throws RecognitionException {
        int expr_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return ; }
            // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:172:3: ( ^( Plus expr expr ) | ^( Minus expr expr ) | ^( Multiply expr expr ) | ^( Divide expr expr ) | ^( Mod expr expr ) | ^( Exponent expr expr ) | ^( Equals expr expr ) | ^( NEquals expr expr ) | ^( GThan expr expr ) | ^( LThan expr expr ) | ^( GThanE expr expr ) | ^( LThanE expr expr ) | ^( Or expr expr ) | ^( Xor expr expr ) | ^( And expr expr ) | ^( Not expr ) | ^( By expr expr ) | ^( CALL Identifier ^( ARGLIST ( expr )* ) ) | ^( As type expr ) | Identifier | Number | FPNumber | True | False | Null | Identity | Char | ^( TUPLEEX expr ) | ^( Dot Identifier ) )
            int alt23=29;
            switch ( input.LA(1) ) {
            case Plus:
                {
                alt23=1;
                }
                break;
            case Minus:
                {
                alt23=2;
                }
                break;
            case Multiply:
                {
                alt23=3;
                }
                break;
            case Divide:
                {
                alt23=4;
                }
                break;
            case Mod:
                {
                alt23=5;
                }
                break;
            case Exponent:
                {
                alt23=6;
                }
                break;
            case Equals:
                {
                alt23=7;
                }
                break;
            case NEquals:
                {
                alt23=8;
                }
                break;
            case GThan:
                {
                alt23=9;
                }
                break;
            case LThan:
                {
                alt23=10;
                }
                break;
            case GThanE:
                {
                alt23=11;
                }
                break;
            case LThanE:
                {
                alt23=12;
                }
                break;
            case Or:
                {
                alt23=13;
                }
                break;
            case Xor:
                {
                alt23=14;
                }
                break;
            case And:
                {
                alt23=15;
                }
                break;
            case Not:
                {
                alt23=16;
                }
                break;
            case By:
                {
                alt23=17;
                }
                break;
            case CALL:
                {
                alt23=18;
                }
                break;
            case As:
                {
                alt23=19;
                }
                break;
            case Identifier:
                {
                alt23=20;
                }
                break;
            case Number:
                {
                alt23=21;
                }
                break;
            case FPNumber:
                {
                alt23=22;
                }
                break;
            case True:
                {
                alt23=23;
                }
                break;
            case False:
                {
                alt23=24;
                }
                break;
            case Null:
                {
                alt23=25;
                }
                break;
            case Identity:
                {
                alt23=26;
                }
                break;
            case Char:
                {
                alt23=27;
                }
                break;
            case TUPLEEX:
                {
                alt23=28;
                }
                break;
            case Dot:
                {
                alt23=29;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:172:5: ^( Plus expr expr )
                    {
                    match(input,Plus,FOLLOW_Plus_in_expr961); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr963);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr965);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:173:5: ^( Minus expr expr )
                    {
                    match(input,Minus,FOLLOW_Minus_in_expr973); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr975);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr977);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:174:5: ^( Multiply expr expr )
                    {
                    match(input,Multiply,FOLLOW_Multiply_in_expr985); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr987);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr989);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:175:5: ^( Divide expr expr )
                    {
                    match(input,Divide,FOLLOW_Divide_in_expr997); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr999);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1001);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:176:5: ^( Mod expr expr )
                    {
                    match(input,Mod,FOLLOW_Mod_in_expr1009); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1011);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1013);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:177:5: ^( Exponent expr expr )
                    {
                    match(input,Exponent,FOLLOW_Exponent_in_expr1021); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1023);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1025);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:178:5: ^( Equals expr expr )
                    {
                    match(input,Equals,FOLLOW_Equals_in_expr1033); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1035);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1037);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:179:5: ^( NEquals expr expr )
                    {
                    match(input,NEquals,FOLLOW_NEquals_in_expr1045); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1047);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1049);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:180:5: ^( GThan expr expr )
                    {
                    match(input,GThan,FOLLOW_GThan_in_expr1057); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1059);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1061);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:181:5: ^( LThan expr expr )
                    {
                    match(input,LThan,FOLLOW_LThan_in_expr1069); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1071);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1073);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 11 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:182:5: ^( GThanE expr expr )
                    {
                    match(input,GThanE,FOLLOW_GThanE_in_expr1081); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1083);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1085);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 12 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:183:5: ^( LThanE expr expr )
                    {
                    match(input,LThanE,FOLLOW_LThanE_in_expr1093); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1095);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1097);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 13 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:184:5: ^( Or expr expr )
                    {
                    match(input,Or,FOLLOW_Or_in_expr1105); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1107);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1109);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 14 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:185:5: ^( Xor expr expr )
                    {
                    match(input,Xor,FOLLOW_Xor_in_expr1117); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1119);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1121);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 15 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:186:5: ^( And expr expr )
                    {
                    match(input,And,FOLLOW_And_in_expr1129); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1131);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1133);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 16 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:187:5: ^( Not expr )
                    {
                    match(input,Not,FOLLOW_Not_in_expr1141); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1143);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 17 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:188:5: ^( By expr expr )
                    {
                    match(input,By,FOLLOW_By_in_expr1151); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1153);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1155);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 18 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:189:5: ^( CALL Identifier ^( ARGLIST ( expr )* ) )
                    {
                    match(input,CALL,FOLLOW_CALL_in_expr1163); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_expr1165); if (state.failed) return ;
                    match(input,ARGLIST,FOLLOW_ARGLIST_in_expr1168); if (state.failed) return ;

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); if (state.failed) return ;
                        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:189:33: ( expr )*
                        loop22:
                        do {
                            int alt22=2;
                            int LA22_0 = input.LA(1);

                            if ( (LA22_0==CALL||LA22_0==TUPLEEX||LA22_0==Identifier||(LA22_0>=Xor && LA22_0<=Not)||(LA22_0>=Number && LA22_0<=As)) ) {
                                alt22=1;
                            }


                            switch (alt22) {
                        	case 1 :
                        	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: expr
                        	    {
                        	    pushFollow(FOLLOW_expr_in_expr1170);
                        	    expr();

                        	    state._fsp--;
                        	    if (state.failed) return ;

                        	    }
                        	    break;

                        	default :
                        	    break loop22;
                            }
                        } while (true);


                        match(input, Token.UP, null); if (state.failed) return ;
                    }

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 19 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:190:5: ^( As type expr )
                    {
                    match(input,As,FOLLOW_As_in_expr1180); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_type_in_expr1182);
                    type();

                    state._fsp--;
                    if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1184);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 20 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:191:5: Identifier
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_expr1191); if (state.failed) return ;

                    }
                    break;
                case 21 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:192:5: Number
                    {
                    match(input,Number,FOLLOW_Number_in_expr1197); if (state.failed) return ;

                    }
                    break;
                case 22 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:193:5: FPNumber
                    {
                    match(input,FPNumber,FOLLOW_FPNumber_in_expr1203); if (state.failed) return ;

                    }
                    break;
                case 23 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:194:5: True
                    {
                    match(input,True,FOLLOW_True_in_expr1209); if (state.failed) return ;

                    }
                    break;
                case 24 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:195:5: False
                    {
                    match(input,False,FOLLOW_False_in_expr1215); if (state.failed) return ;

                    }
                    break;
                case 25 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:196:5: Null
                    {
                    match(input,Null,FOLLOW_Null_in_expr1221); if (state.failed) return ;

                    }
                    break;
                case 26 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:197:5: Identity
                    {
                    match(input,Identity,FOLLOW_Identity_in_expr1227); if (state.failed) return ;

                    }
                    break;
                case 27 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:198:5: Char
                    {
                    match(input,Char,FOLLOW_Char_in_expr1233); if (state.failed) return ;

                    }
                    break;
                case 28 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:199:5: ^( TUPLEEX expr )
                    {
                    match(input,TUPLEEX,FOLLOW_TUPLEEX_in_expr1240); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    pushFollow(FOLLOW_expr_in_expr1242);
                    expr();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 29 :
                    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:200:5: ^( Dot Identifier )
                    {
                    match(input,Dot,FOLLOW_Dot_in_expr1250); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_expr1252); if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, expr_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "expr"

    // $ANTLR start synpred16_CFTExtract
    public final void synpred16_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:5: ( ^( DECL ( specifier )? ( type )* Identifier ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:5: ^( DECL ( specifier )? ( type )* Identifier )
        {
        match(input,DECL,FOLLOW_DECL_in_synpred16_CFTExtract272); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:12: ( specifier )?
        int alt24=2;
        int LA24_0 = input.LA(1);

        if ( ((LA24_0>=Const && LA24_0<=Var)) ) {
            alt24=1;
        }
        switch (alt24) {
            case 1 :
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: specifier
                {
                pushFollow(FOLLOW_specifier_in_synpred16_CFTExtract274);
                specifier();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:64:23: ( type )*
        loop25:
        do {
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==Identifier) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==Identifier||LA25_1==Vector||LA25_1==Matrix||(LA25_1>=Boolean && LA25_1<=Tuple)) ) {
                    alt25=1;
                }


            }
            else if ( (LA25_0==Vector||LA25_0==Matrix||(LA25_0>=Boolean && LA25_0<=Tuple)) ) {
                alt25=1;
            }


            switch (alt25) {
        	case 1 :
        	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: type
        	    {
        	    pushFollow(FOLLOW_type_in_synpred16_CFTExtract277);
        	    type();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop25;
            }
        } while (true);

        match(input,Identifier,FOLLOW_Identifier_in_synpred16_CFTExtract280); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_CFTExtract

    // $ANTLR start synpred19_CFTExtract
    public final void synpred19_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:5: ( ^( DECL ( specifier )? ( type )* ^( Assign Identifier expr ) ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:5: ^( DECL ( specifier )? ( type )* ^( Assign Identifier expr ) )
        {
        match(input,DECL,FOLLOW_DECL_in_synpred19_CFTExtract288); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:12: ( specifier )?
        int alt26=2;
        int LA26_0 = input.LA(1);

        if ( ((LA26_0>=Const && LA26_0<=Var)) ) {
            alt26=1;
        }
        switch (alt26) {
            case 1 :
                // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: specifier
                {
                pushFollow(FOLLOW_specifier_in_synpred19_CFTExtract290);
                specifier();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:65:23: ( type )*
        loop27:
        do {
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==Identifier||LA27_0==Vector||LA27_0==Matrix||(LA27_0>=Boolean && LA27_0<=Tuple)) ) {
                alt27=1;
            }


            switch (alt27) {
        	case 1 :
        	    // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:0:0: type
        	    {
        	    pushFollow(FOLLOW_type_in_synpred19_CFTExtract293);
        	    type();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop27;
            }
        } while (true);

        match(input,Assign,FOLLOW_Assign_in_synpred19_CFTExtract297); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred19_CFTExtract299); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred19_CFTExtract301);
        expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_CFTExtract

    // $ANTLR start synpred20_CFTExtract
    public final void synpred20_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:66:5: ( ^( DECL specifier ^( Assign Identifier StdInput ) ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:66:5: ^( DECL specifier ^( Assign Identifier StdInput ) )
        {
        match(input,DECL,FOLLOW_DECL_in_synpred20_CFTExtract310); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_specifier_in_synpred20_CFTExtract312);
        specifier();

        state._fsp--;
        if (state.failed) return ;
        match(input,Assign,FOLLOW_Assign_in_synpred20_CFTExtract315); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred20_CFTExtract317); if (state.failed) return ;
        match(input,StdInput,FOLLOW_StdInput_in_synpred20_CFTExtract319); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_CFTExtract

    // $ANTLR start synpred23_CFTExtract
    public final void synpred23_CFTExtract_fragment() throws RecognitionException {   
        CommonTree id=null;

        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:86:5: ( ^( Procedure id= Identifier paramlist ^( Returns type ) block ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:86:5: ^( Procedure id= Identifier paramlist ^( Returns type ) block )
        {
        match(input,Procedure,FOLLOW_Procedure_in_synpred23_CFTExtract417); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        id=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_synpred23_CFTExtract421); if (state.failed) return ;
        pushFollow(FOLLOW_paramlist_in_synpred23_CFTExtract423);
        paramlist();

        state._fsp--;
        if (state.failed) return ;
        match(input,Returns,FOLLOW_Returns_in_synpred23_CFTExtract426); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred23_CFTExtract428);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;
        pushFollow(FOLLOW_block_in_synpred23_CFTExtract435);
        block();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_CFTExtract

    // $ANTLR start synpred24_CFTExtract
    public final void synpred24_CFTExtract_fragment() throws RecognitionException {   
        CommonTree id=null;

        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:88:5: ( ^( Procedure id= Identifier paramlist block ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:88:5: ^( Procedure id= Identifier paramlist block )
        {
        match(input,Procedure,FOLLOW_Procedure_in_synpred24_CFTExtract445); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        id=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_synpred24_CFTExtract449); if (state.failed) return ;
        pushFollow(FOLLOW_paramlist_in_synpred24_CFTExtract451);
        paramlist();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_block_in_synpred24_CFTExtract458);
        block();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_CFTExtract

    // $ANTLR start synpred25_CFTExtract
    public final void synpred25_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:90:5: ( ^( Procedure Identifier paramlist ^( Returns type ) ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:90:5: ^( Procedure Identifier paramlist ^( Returns type ) )
        {
        match(input,Procedure,FOLLOW_Procedure_in_synpred25_CFTExtract468); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred25_CFTExtract470); if (state.failed) return ;
        pushFollow(FOLLOW_paramlist_in_synpred25_CFTExtract472);
        paramlist();

        state._fsp--;
        if (state.failed) return ;
        match(input,Returns,FOLLOW_Returns_in_synpred25_CFTExtract475); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred25_CFTExtract477);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_CFTExtract

    // $ANTLR start synpred26_CFTExtract
    public final void synpred26_CFTExtract_fragment() throws RecognitionException {   
        CommonTree id=null;

        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:95:5: ( ^( Function id= Identifier paramlist ^( Returns type ) block ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:95:5: ^( Function id= Identifier paramlist ^( Returns type ) block )
        {
        match(input,Function,FOLLOW_Function_in_synpred26_CFTExtract507); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        id=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_synpred26_CFTExtract511); if (state.failed) return ;
        pushFollow(FOLLOW_paramlist_in_synpred26_CFTExtract513);
        paramlist();

        state._fsp--;
        if (state.failed) return ;
        match(input,Returns,FOLLOW_Returns_in_synpred26_CFTExtract516); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred26_CFTExtract518);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;
        pushFollow(FOLLOW_block_in_synpred26_CFTExtract526);
        block();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_CFTExtract

    // $ANTLR start synpred27_CFTExtract
    public final void synpred27_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:97:5: ( ^( Function Identifier paramlist ^( Returns type ) ^( Assign expr ) ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:97:5: ^( Function Identifier paramlist ^( Returns type ) ^( Assign expr ) )
        {
        match(input,Function,FOLLOW_Function_in_synpred27_CFTExtract536); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred27_CFTExtract538); if (state.failed) return ;
        pushFollow(FOLLOW_paramlist_in_synpred27_CFTExtract540);
        paramlist();

        state._fsp--;
        if (state.failed) return ;
        match(input,Returns,FOLLOW_Returns_in_synpred27_CFTExtract543); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred27_CFTExtract545);
        type();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;
        match(input,Assign,FOLLOW_Assign_in_synpred27_CFTExtract549); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred27_CFTExtract551);
        expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_CFTExtract

    // $ANTLR start synpred32_CFTExtract
    public final void synpred32_CFTExtract_fragment() throws RecognitionException {   
        CFTNode s1 = null;

        CFTNode s2 = null;


        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:125:5: ( ^( If expr s1= slist ^( Else s2= slist ) ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:125:5: ^( If expr s1= slist ^( Else s2= slist ) )
        {
        match(input,If,FOLLOW_If_in_synpred32_CFTExtract705); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred32_CFTExtract707);
        expr();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_slist_in_synpred32_CFTExtract711);
        s1=slist();

        state._fsp--;
        if (state.failed) return ;
        match(input,Else,FOLLOW_Else_in_synpred32_CFTExtract716); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_slist_in_synpred32_CFTExtract720);
        s2=slist();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred32_CFTExtract

    // $ANTLR start synpred33_CFTExtract
    public final void synpred33_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:130:5: ( ^( Loop ^( While expr ) slist ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:130:5: ^( Loop ^( While expr ) slist )
        {
        match(input,Loop,FOLLOW_Loop_in_synpred33_CFTExtract759); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,While,FOLLOW_While_in_synpred33_CFTExtract762); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred33_CFTExtract764);
        expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;
        pushFollow(FOLLOW_slist_in_synpred33_CFTExtract767);
        slist();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred33_CFTExtract

    // $ANTLR start synpred34_CFTExtract
    public final void synpred34_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:131:5: ( ^( Loop slist ^( While expr ) ) )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:131:5: ^( Loop slist ^( While expr ) )
        {
        match(input,Loop,FOLLOW_Loop_in_synpred34_CFTExtract777); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_slist_in_synpred34_CFTExtract779);
        slist();

        state._fsp--;
        if (state.failed) return ;
        match(input,While,FOLLOW_While_in_synpred34_CFTExtract782); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred34_CFTExtract784);
        expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred34_CFTExtract

    // $ANTLR start synpred35_CFTExtract
    public final void synpred35_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:136:5: ( block )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:136:5: block
        {
        pushFollow(FOLLOW_block_in_synpred35_CFTExtract819);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred35_CFTExtract

    // $ANTLR start synpred36_CFTExtract
    public final void synpred36_CFTExtract_fragment() throws RecognitionException {   
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:137:5: ( statement )
        // /home/linux-vm/workspace_indigo/git/CMPUT415-DashAB/DashAB Compiler Project/src/dash/CFTExtract.g:137:5: statement
        {
        pushFollow(FOLLOW_statement_in_synpred36_CFTExtract827);
        statement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred36_CFTExtract

    // Delegated rules

    public final boolean synpred20_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred35_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred35_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred32_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred32_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred33_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred33_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred36_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred36_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_CFTExtract() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_CFTExtract_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA19_eotS =
        "\15\uffff";
    static final String DFA19_eofS =
        "\15\uffff";
    static final String DFA19_minS =
        "\1\5\1\0\13\uffff";
    static final String DFA19_maxS =
        "\1\54\1\0\13\uffff";
    static final String DFA19_acceptS =
        "\2\uffff\1\2\10\uffff\1\3\1\1";
    static final String DFA19_specialS =
        "\1\uffff\1\0\13\uffff}>";
    static final String[] DFA19_transitionS = {
            "\1\13\1\uffff\1\1\1\uffff\1\2\7\uffff\1\2\1\uffff\1\2\1\uffff"+
            "\1\2\4\uffff\2\2\15\uffff\2\2\1\uffff\1\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "135:1: slist returns [CFTNode cftn] : ( block | statement | declaration );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TreeNodeStream input = (TreeNodeStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_1 = input.LA(1);

                         
                        int index19_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred35_CFTExtract()) ) {s = 12;}

                        else if ( (synpred36_CFTExtract()) ) {s = 2;}

                         
                        input.seek(index19_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_PROGRAM_in_program78 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_globalStatement_in_program80 = new BitSet(new long[]{0x000000A400000028L});
    public static final BitSet FOLLOW_declaration_in_globalStatement97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typedef_in_globalStatement103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedure_in_globalStatement109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_globalStatement115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_statement135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_outputstream_in_statement143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inputstream_in_statement151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifstatement_in_statement159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loopstatement_in_statement167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStatement_in_statement183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_statement191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Break_in_statement199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Continue_in_statement207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RArrow_in_outputstream225 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_outputstream227 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_Identifier_in_outputstream229 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LArrow_in_inputstream244 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_inputstream246 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_Identifier_in_inputstream248 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_declaration272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_declaration274 = new BitSet(new long[]{0x001FC00120100000L});
    public static final BitSet FOLLOW_type_in_declaration277 = new BitSet(new long[]{0x001FC00120100000L});
    public static final BitSet FOLLOW_Identifier_in_declaration280 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_declaration288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_declaration290 = new BitSet(new long[]{0x001FC00120300000L});
    public static final BitSet FOLLOW_type_in_declaration293 = new BitSet(new long[]{0x001FC00120300000L});
    public static final BitSet FOLLOW_Assign_in_declaration297 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_declaration299 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_declaration301 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_declaration310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_declaration312 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_Assign_in_declaration315 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_declaration317 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_StdInput_in_declaration319 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_declaration328 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_declaration330 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_Assign_in_declaration333 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_declaration335 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_StdOutput_in_declaration337 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Typedef_in_typedef355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_typedef357 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_Identifier_in_typedef359 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BLOCK_in_block388 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declaration_in_block390 = new BitSet(new long[]{0x000016000C2A02A8L});
    public static final BitSet FOLLOW_statement_in_block396 = new BitSet(new long[]{0x000016000C2A0288L});
    public static final BitSet FOLLOW_Procedure_in_procedure417 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_procedure421 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_procedure423 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_procedure426 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_procedure428 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_block_in_procedure435 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Procedure_in_procedure445 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_procedure449 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_procedure451 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_block_in_procedure458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Procedure_in_procedure468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_procedure470 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_procedure472 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_procedure475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_procedure477 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Procedure_in_procedure486 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_procedure488 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_procedure490 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_function507 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_function511 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_function513 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_function516 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_function518 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_block_in_function526 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_function536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_function538 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_function540 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_function543 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_function545 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Assign_in_function549 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_function551 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_function560 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_function562 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_function564 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_function567 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_function569 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PARAMLIST_in_paramlist587 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_parameter_in_paramlist589 = new BitSet(new long[]{0x0000000000100008L});
    public static final BitSet FOLLOW_Identifier_in_parameter607 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_parameter609 = new BitSet(new long[]{0x001FC00120100000L});
    public static final BitSet FOLLOW_type_in_parameter612 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CALL_in_callStatement629 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_callStatement631 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ARGLIST_in_callStatement634 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_callStatement636 = new BitSet(new long[]{0xFF00000000100A08L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_Return_in_returnStatement655 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_returnStatement657 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Assign_in_assignment675 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_assignment677 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_assignment679 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_If_in_ifstatement705 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_ifstatement707 = new BitSet(new long[]{0x000016000C2A02A0L});
    public static final BitSet FOLLOW_slist_in_ifstatement711 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_Else_in_ifstatement716 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_slist_in_ifstatement720 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_If_in_ifstatement732 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_ifstatement734 = new BitSet(new long[]{0x000016000C2A02A0L});
    public static final BitSet FOLLOW_slist_in_ifstatement736 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Loop_in_loopstatement759 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_While_in_loopstatement762 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_loopstatement764 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_slist_in_loopstatement767 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Loop_in_loopstatement777 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_slist_in_loopstatement779 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_While_in_loopstatement782 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_loopstatement784 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Loop_in_loopstatement795 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_slist_in_loopstatement797 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_block_in_slist819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_slist827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_slist835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Boolean_in_type852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Integer_in_type858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Matrix_in_type864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Interval_in_type870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_type876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Vector_in_type882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Real_in_type888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Character_in_type894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tuple_in_type900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_type906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Tuple_in_tuple922 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_tuple924 = new BitSet(new long[]{0x001FC00120100008L});
    public static final BitSet FOLLOW_set_in_specifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Plus_in_expr961 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr963 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr965 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Minus_in_expr973 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr975 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr977 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Multiply_in_expr985 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr987 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr989 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Divide_in_expr997 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr999 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1001 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Mod_in_expr1009 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1011 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1013 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Exponent_in_expr1021 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1023 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1025 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Equals_in_expr1033 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1035 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1037 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEquals_in_expr1045 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1047 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1049 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GThan_in_expr1057 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1059 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1061 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LThan_in_expr1069 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1071 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1073 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GThanE_in_expr1081 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1083 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1085 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LThanE_in_expr1093 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1095 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1097 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Or_in_expr1105 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1107 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1109 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Xor_in_expr1117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1119 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_And_in_expr1129 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1131 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1133 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Not_in_expr1141 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1143 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_By_in_expr1151 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1153 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1155 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CALL_in_expr1163 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_expr1165 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ARGLIST_in_expr1168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1170 = new BitSet(new long[]{0xFF00000000100A08L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_As_in_expr1180 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_expr1182 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_expr1184 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Identifier_in_expr1191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Number_in_expr1197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FPNumber_in_expr1203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_True_in_expr1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_False_in_expr1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Null_in_expr1221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identity_in_expr1227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Char_in_expr1233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TUPLEEX_in_expr1240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1242 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Dot_in_expr1250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_expr1252 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_synpred16_CFTExtract272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_synpred16_CFTExtract274 = new BitSet(new long[]{0x001FC00120100000L});
    public static final BitSet FOLLOW_type_in_synpred16_CFTExtract277 = new BitSet(new long[]{0x001FC00120100000L});
    public static final BitSet FOLLOW_Identifier_in_synpred16_CFTExtract280 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_synpred19_CFTExtract288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_synpred19_CFTExtract290 = new BitSet(new long[]{0x001FC00120300000L});
    public static final BitSet FOLLOW_type_in_synpred19_CFTExtract293 = new BitSet(new long[]{0x001FC00120300000L});
    public static final BitSet FOLLOW_Assign_in_synpred19_CFTExtract297 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred19_CFTExtract299 = new BitSet(new long[]{0xFF00000000100A00L,0x000000000007FDFFL});
    public static final BitSet FOLLOW_expr_in_synpred19_CFTExtract301 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_synpred20_CFTExtract310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifier_in_synpred20_CFTExtract312 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_Assign_in_synpred20_CFTExtract315 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred20_CFTExtract317 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_StdInput_in_synpred20_CFTExtract319 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Procedure_in_synpred23_CFTExtract417 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred23_CFTExtract421 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_synpred23_CFTExtract423 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_synpred23_CFTExtract426 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_synpred23_CFTExtract428 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_block_in_synpred23_CFTExtract435 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Procedure_in_synpred24_CFTExtract445 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred24_CFTExtract449 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_synpred24_CFTExtract451 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_block_in_synpred24_CFTExtract458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Procedure_in_synpred25_CFTExtract468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred25_CFTExtract470 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_synpred25_CFTExtract472 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_synpred25_CFTExtract475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_synpred25_CFTExtract477 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_synpred26_CFTExtract507 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred26_CFTExtract511 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_synpred26_CFTExtract513 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_synpred26_CFTExtract516 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_synpred26_CFTExtract518 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_block_in_synpred26_CFTExtract526 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Function_in_synpred27_CFTExtract536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred27_CFTExtract538 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramlist_in_synpred27_CFTExtract540 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_Returns_in_synpred27_CFTExtract543 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_synpred27_CFTExtract545 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Assign_in_synpred27_CFTExtract549 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred27_CFTExtract551 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_If_in_synpred32_CFTExtract705 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred32_CFTExtract707 = new BitSet(new long[]{0x000016000C2A02A0L});
    public static final BitSet FOLLOW_slist_in_synpred32_CFTExtract711 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_Else_in_synpred32_CFTExtract716 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_slist_in_synpred32_CFTExtract720 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Loop_in_synpred33_CFTExtract759 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_While_in_synpred33_CFTExtract762 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred33_CFTExtract764 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_slist_in_synpred33_CFTExtract767 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_Loop_in_synpred34_CFTExtract777 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_slist_in_synpred34_CFTExtract779 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_While_in_synpred34_CFTExtract782 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred34_CFTExtract784 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_block_in_synpred35_CFTExtract819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_synpred36_CFTExtract827 = new BitSet(new long[]{0x0000000000000002L});

}