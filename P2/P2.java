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

        System.out.println("\n\n--- Results ---");
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
