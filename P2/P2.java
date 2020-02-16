import java.util.*;
import java.io.*;
import java_cup.runtime.*;  // defines Symbol

/**
 * This program is to be used to test the Wumbo scanner.
 * This version is set up to test all tokens, but you are required to test 
 * other aspects of the scanner (e.g., input that causes errors, character 
 * numbers, values associated with tokens)
 */
public class P2 {
    public static void main(String[] args) throws IOException {
                                           // exception may be thrown by yylex
        // test all tokens
        testAllTokens();
        CharNum.num = 1;
    
        // ADD CALLS TO OTHER TEST METHODS HERE
        // test other files to be input
        for (String filename : args) {
            testAllTokens(filename);
            CharNum.num = 1;
        }

        testComments();
        CharNum.num = 1;

        testErrorCases();
        CharNum.num = 1;

        testLineAndCharacterNumbers();
        CharNum.num = 1;
    }

    /**
     * testLineAndCharacterNumbers
     *
     * Open and read from file lineAndCharNumberTests
     *
     * Each line includes various symbols, some symbols being on the same line
     * separated by spaces much like an actual c++ program. It is assumed that
     * operator symbols such as && or - do not separate identifiers 
     */
    private static void testLineAndCharacterNumbers() throws IOException {
        FileReader inFile = null;
        try {
            inFile = new FileReader("lineAndCharNumberTests");
        } catch (FileNotFoundException ex) {
            System.err.println("File lineAndCharNumberTests not found.");
            System.exit(-1);
        }

        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        TokenVal ttmp = (TokenVal)token.value;
        while (token.sym != sym.EOF) {
            switch (token.sym) {
                case sym.ID:
                    IdTokenVal t = (IdTokenVal) ttmp;
                    if (t.idVal.equals("identifier")) {
                        assert t.linenum == 2 : "identifier id has incorrect line num";
                        assert t.charnum == 5 : "identifier id has incorrect char num";
                    } else if (t.idVal.equals("val")) {
                        assert t.linenum == 3 : "val id has incorrect line num";
                        assert t.charnum == 6 : "val id has incorrect char num";
                    } else if (t.idVal.equals("identifier_2")) {
                        assert t.linenum == 4 : "identifier_2 id has incorrect line num";
                        assert t.charnum == 3 : "identifier_2 id has incorrect char num";
                    } else if (t.idVal.equals("id")) {
                        assert t.linenum == 7 : "id id has incorrect line num";
                        assert t.charnum == 25 : "id id has incorrect char num";
                    } else if (t.idVal.equals("_identifier")) {
                        assert t.linenum == 7 : "_identifier id has incorrect line num";
                        assert t.charnum == 31 : "_identifier id has incorrect char num";
                    } else {
                        System.err.println("Unknown identifier name: " + t.idVal);
                    }
                    break;
                case sym.STRINGLITERAL:
                    StrLitTokenVal tstr = (StrLitTokenVal)ttmp;
                    if (tstr.strVal.equals("\"\\t\"")) {
                        assert tstr.linenum == 1 : "tab strlit has incorrect line num";
                        assert tstr.charnum == 1 : "tab strlit has incorrect char num";
                    } else if (tstr.strVal.equals("\"String literal\"")) {
                        assert tstr.linenum == 1 : "String literal strlit has incorrect line num";
                        assert tstr.charnum == 6 : "String literal strlit has incorrect char num";
                    } else if (tstr.strVal.equals("\"another string lit \\n\"")) {
                        assert tstr.linenum == 7 : "strlit has incorrect line num";
                        assert tstr.charnum == 1 : "strlit has incorrect char num";
                    } else {
                        System.err.println("Unrecognized string literal: " + tstr.strVal);
                    }
                    break;
                case sym.PLUS:
                    assert ttmp.linenum == 4 : "PLUS has incorrect line num";
                    assert ttmp.charnum == 22 : "PLUS has incorrect char num";
                    break;
                case sym.MINUS:
                    assert ttmp.linenum == 4 : "MINUS has incorrect line num";
                    assert ttmp.charnum == 24 : "MINUS has incorrect char num";
                    break;
                case sym.AND:
                    assert ttmp.linenum == 4 : "AND has incorrect line num";
                    assert ttmp.charnum == 28 : "AND has incorrect char num";
                    break;
                case sym.SEMICOLON:
                    assert ttmp.linenum == 4 : "SEMICOLON has incorrect line num";
                    assert ttmp.charnum == 32 : "SEMICOLON has incorrect char num";
                    break;
                case sym.TRUE:
                    assert ttmp.linenum == 3 : "TRUE has incorrect line num";
                    assert ttmp.charnum == 10 : "TRUE has incorrect char num";
                    break;
                case sym.OR:
                    assert ttmp.linenum == 7 : "OR has incorrect line num";
                    assert ttmp.charnum == 28 : "OR has incorrect char num";
                    break;
                case sym.EQUALS:
                    assert ttmp.linenum == 4 : "EQUALS has incorrect line num";
                    assert ttmp.charnum == 18 : "EQUALS has incorrect char num";
                    break;
                case sym.INT:
                    assert ttmp.linenum == 2 : "INT has incorrect line num";
                    assert ttmp.charnum == 1 : "INT has incorrect char num";
                    break;
                case sym.BOOL:
                    assert ttmp.linenum == 3 : "BOOL has incorrect line num";
                    assert ttmp.charnum == 1 : "BOOL has incorrect char num";
                    break;
                case sym.INTLITERAL:
                    IntLitTokenVal tint = (IntLitTokenVal) ttmp;
                    switch (tint.intVal) {
                        case 1234:
                            assert tint.linenum == 2 : "1234 has incorrect line num";
                            assert tint.charnum == 16 : "1234 has incorrect char num";
                            break;
                        case 5:
                            assert tint.linenum == 4 : "5 has incorrect line num";
                            assert tint.charnum == 21 : "5 has incorrect char num";
                            break;
                        case 6:
                            assert tint.linenum == 4 : "6 has incorrect line num";
                            assert tint.charnum == 23 : "6 has incorrect char num";
                            break;
                        case 7:
                            assert tint.linenum == 4 : "7 has incorrect line num";
                            assert tint.charnum == 25 : "7 has incorrect char num";
                            break;
                        case 8:
                            assert tint.linenum == 4 : "8 has incorrect line num";
                            assert tint.charnum == 31 : "8 has incorrect char num";
                            break;
                        default:
                            System.err.println("Unrecognized intlit: " + tint.intVal);
                    }
                    break;
                default:
                    System.err.println("Unrecognized token at:");
                    System.err.println("Line number: " + ttmp.linenum);
                    System.err.println("Char number: " + ttmp.charnum);
            }
            token = scanner.next_token();
            ttmp = (TokenVal) token.value;
        }
    }

    /**
     * testErrorCases
     *
     * Open and read from file errorTestCases
     * Each line produces a different error message. The output must be inspected
     * manually and compared to the following to determine proper behavior:
     *
     *   1:1 ***ERROR*** unterminated string literal ignored
     *   2:1 ***ERROR*** unterminated string literal ignored
     *   3:1 ***ERROR*** string literal with bad escaped character ignored
     *   4:1 ***ERROR*** unterminated string literal with bad escaped character ignored
     *   5:1 ***WARNING*** integer literal too large; using max value
     *   6:1 ***ERROR*** ignoring illegal character: $
     *   6:2 ***ERROR*** ignoring illegal character: @
     *   6:3 ***ERROR*** ignoring illegal character: ^ 
     */
    private static void testErrorCases() throws IOException {
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("errorTestCases");
            outFile = new PrintWriter(new FileWriter("errorTestCases.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File errorTestCases not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("errorTestCases.out cannot be opened.");
            System.exit(-1);
        }

        System.out.println("--- Expected output ---");
        System.out.println("1:1 ***ERROR*** unterminated string literal ignored"
            + "\n2:1 ***ERROR*** unterminated string literal ignored"
            + "\n3:1 ***ERROR*** string literal with bad escaped character ignored"
            + "\n4:1 ***ERROR*** unterminated string literal with bad escaped character ignored"
            + "\n5:1 ***WARNING*** integer literal too large; using max value"
            + "\n6:1 ***ERROR*** ignoring illegal character: $"
            + "\n6:2 ***ERROR*** ignoring illegal character: @"
            + "\n6:3 ***ERROR*** ignoring illegal character: ^");

        System.out.println("--- Results ---");
        scan(inFile, outFile);
        outFile.flush();
        outFile.close();
    }

    /**
     * testComments
     *
     * Open and read from file comments
     * Should read each line as a valid comment and produce an empty file
     * named comments.out. If the file is not empty, then the test failed
     * and a comment was interpretted as something else.
     */
    private static void testComments() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("comments");
            outFile = new PrintWriter(new FileWriter("comments.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File comments not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("comments.out cannot be opened.");
            System.exit(-1);
        }

        scan(inFile, outFile);
        outFile.flush();
        outFile.close();
        File result = new File("comments.out");
        assert result.length() == 0 : "Expected empty comments.out file";
    }

    /**
     * testAllTokens
     *  ARGS: filename - a string corresponding to the input file to read
     *
     * Open and read from file filename
     * For each token read, write the corresponding string to filename.out
     * If the input file contains all tokens, one per line, we can verify
     * correctness of the scanner by comparing the input and output files
     * (e.g., using a 'diff' command).
     */
    private static void testAllTokens(String filename) throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader(filename);
            outFile = new PrintWriter(new FileWriter(filename + ".out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File " + filename + "not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println(filename + ".out cannot be opened.");
            System.exit(-1);
        }

        scan(inFile, outFile);

        outFile.close();
    }

    /**
     * testAllTokens
     *
     * Open and read from file allTokens.txt
     * For each token read, write the corresponding string to allTokens.out
     * If the input file contains all tokens, one per line, we can verify
     * correctness of the scanner by comparing the input and output files
     * (e.g., using a 'diff' command).
     */
    private static void testAllTokens() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("allTokens.in");
            outFile = new PrintWriter(new FileWriter("allTokens.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File allTokens.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("allTokens.out cannot be opened.");
            System.exit(-1);
        }

        scan(inFile, outFile);
        
        outFile.close();
    }

    /**
     * scan
     *
     * Open and read from file inFile
     * For each token read, write the corresponding string to outFile
     * If the input file contains all tokens, one per line, we can verify
     * correctness of the scanner by comparing the input and output files
     * (e.g., using a 'diff' command).
     */
    private static void scan(FileReader inFile, PrintWriter outFile) throws IOException {
        // create and call the scanner!
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
            switch (token.sym) {
            case sym.BOOL:
                outFile.println("bool"); 
                break;
            case sym.INT:
                outFile.println("int");
                break;
            case sym.VOID:
                outFile.println("void");
                break;
            case sym.TRUE:
                outFile.println("true"); 
                break;
            case sym.FALSE:
                outFile.println("false"); 
                break;
            case sym.STRUCT:
                outFile.println("struct"); 
                break;
            case sym.CIN:
                outFile.println("cin"); 
                break;
            case sym.COUT:
                outFile.println("cout");
                break;              
            case sym.IF:
                outFile.println("if");
                break;
            case sym.ELSE:
                outFile.println("else");
                break;
            case sym.WHILE:
                outFile.println("while");
                break;
            case sym.RETURN:
                outFile.println("return");
                break;
            case sym.ID:
                outFile.println(((IdTokenVal)token.value).idVal);
                break;
            case sym.INTLITERAL:  
                outFile.println(((IntLitTokenVal)token.value).intVal);
                break;
            case sym.STRINGLITERAL: 
                outFile.println(((StrLitTokenVal)token.value).strVal);
                break;    
            case sym.LCURLY:
                outFile.println("{");
                break;
            case sym.RCURLY:
                outFile.println("}");
                break;
            case sym.LPAREN:
                outFile.println("(");
                break;
            case sym.RPAREN:
                outFile.println(")");
                break;
            case sym.SEMICOLON:
                outFile.println(";");
                break;
            case sym.COMMA:
                outFile.println(",");
                break;
            case sym.DOT:
                outFile.println(".");
                break;
            case sym.WRITE:
                outFile.println("<<");
                break;
            case sym.READ:
                outFile.println(">>");
                break;              
            case sym.PLUSPLUS:
                outFile.println("++");
                break;
            case sym.MINUSMINUS:
                outFile.println("--");
                break;  
            case sym.PLUS:
                outFile.println("+");
                break;
            case sym.MINUS:
                outFile.println("-");
                break;
            case sym.TIMES:
                outFile.println("*");
                break;
            case sym.DIVIDE:
                outFile.println("/");
                break;
            case sym.NOT:
                outFile.println("!");
                break;
            case sym.AND:
                outFile.println("&&");
                break;
            case sym.OR:
                outFile.println("||");
                break;
            case sym.EQUALS:
                outFile.println("==");
                break;
            case sym.NOTEQUALS:
                outFile.println("!=");
                break;
            case sym.LESS:
                outFile.println("<");
                break;
            case sym.GREATER:
                outFile.println(">");
                break;
            case sym.LESSEQ:
                outFile.println("<=");
                break;
            case sym.GREATEREQ:
                outFile.println(">=");
                break;
            case sym.ASSIGN:
                outFile.println("=");
                break;
            default:
                outFile.println("UNKNOWN TOKEN");
            } // end switch

            token = scanner.next_token();
        } // end while
    }
}
