package ru.Gorodentsev.parser;

import ru.Gorodentsev.ast.*;
import ru.Gorodentsev.tokens.Token;
import ru.Gorodentsev.tokens.TokenType;
import ru.Gorodentsev.tokens.Tokenizer;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Parser implements Closeable {

    private final Tokenizer tokenizer;
    private Token token;

    public Parser(Tokenizer tokenizer) throws IOException {
        this.tokenizer = tokenizer;
        this.token = tokenizer.nextToken();
    }

    private void consume() throws IOException {
        token = tokenizer.nextToken();
    }

    private boolean accept(TokenType type) throws IOException {
        if (token.getType() == type) {
            consume();
            return true;
        }

        return false;
    }

    private void expect(TokenType type) throws IOException {
        if (!accept(type))
            throw new IOException("Unexpected " + token.getType() + ", expecting " + type);
    }

    public Program parse() throws IOException {
        List<Line> lines = new ArrayList<>();
        while (!accept(TokenType.EOF)) {
            lines.add(nextLine());
        }
        return new Program(lines);
    }

    Line nextLine() throws IOException {
        if (token.getType() != TokenType.NUMBER)
            throw new IOException("Unexpected " + token.getType() + ", expecting NUMBER");

        int lineNumber = Integer.parseInt(token.getValue().get());
        consume();

        Statement stmt = nextStatement(lineNumber);

        if (token.getType() != TokenType.LF && token.getType() != TokenType.EOF)
            throw new IOException("Unexpected " + token.getType() + ", expecting LF or EOF");

        consume();
        return new Line(lineNumber, stmt);
    }

    Statement nextStatement(int lineNumber) throws IOException {
        if (token.getType() != TokenType.KEYWORD)
            throw new IOException("Unexpected " + token.getType() + ", expecting KEYWORD");

        String keyword = token.getValue().get();
        switch (keyword) {
            case "PRINT":
                consume();

                List<StringExpression> values = new ArrayList<>();
                do {
                    if (token.getType() == TokenType.STRING) {
                        values.add(new ImmediateString(token.getValue().get()));
                        consume();
                    } else {
                        values.add(nextExpression());
                    }
                } while (accept(TokenType.COMMA));

                return new PrintStatement(values);

            case "IF":
                consume();

                StringExpression left = nextExpression();

                ComparisonOperator operator;
                switch (token.getType()) {
                    case EQUALS:
                        operator = ComparisonOperator.EQUALS;
                        break;
                    case NOT_EQUALS:
                        operator = ComparisonOperator.NOT_EQUALS;
                        break;
                    case LESS:
                        operator = ComparisonOperator.LESS;
                        break;
                    case LESS_OR_EQUALS:
                        operator = ComparisonOperator.LESS_OR_EQUALS;
                        break;
                    case MORE:
                        operator = ComparisonOperator.MORE;
                        break;
                    case MORE_OR_EQUALS:
                        operator = ComparisonOperator.MORE_OR_EQUALS;
                        break;
                    default:
                        throw new IOException("Unexpected " + token.getType() + ", expecting EQ, NE, LT, LTE, GT or GTE");
                }
                consume();

                StringExpression right = nextExpression();

                if (token.getType() != TokenType.KEYWORD)
                    throw new IOException("Unexpected " + token.getType() + ", expecting KEYWORD");

                String thenKeyword = token.getValue().get();
                if (!thenKeyword.equals("THEN"))
                    throw new IOException("Unexpected keyword " + keyword + ", expecting THEN");

                consume();

                List<Statement> statementList = new ArrayList<>();
                String nextKeyword = token.getValue().get();

                do {
                    Statement statement = nextStatement(lineNumber);
                    statementList.add(statement);
                } while(accept(TokenType.SEMICOLON));

                //Statement statement = nextStatement();
                //return new IfStatement(operator, left, right, statement);
                return new IfStatement(operator, left, right, statementList);

            case "GOTO":
            case "GOSUB":
                consume();

                if (token.getType() != TokenType.NUMBER)
                    throw new IOException("Unexpected " + token.getType() + ", expecting NUMBER");

                int target = Integer.parseInt(token.getValue().get());
                consume();

                BranchType type = keyword.equals("GOTO") ? BranchType.GOTO : BranchType.GOSUB;
                return new BranchStatement(type, target);

            case "INPUT":
                consume();

                List<String> names = new ArrayList<>();
                do {
                    if (token.getType() != TokenType.VAR)
                        throw new IOException("Unexpected " + token.getType() + ", expecting VAR");

                    names.add(token.getValue().get());
                    consume();
                } while (accept(TokenType.COMMA));

                return new InputStatement(names);

            case "LET":
                consume();

                if (token.getType() != TokenType.VAR)
                    throw new IOException("Unexpected " + token.getType() + ", expecting VAR");

                String name = token.getValue().get();
                consume();

                expect(TokenType.EQUALS);

                return new LetStatement(name, nextExpression());

            case "DIM":
                consume();

                if (token.getType() != TokenType.ARRAY)
                    throw new IOException("Unexpected " + token.getType() + ", expecting VAR");

                String arrName = token.getValue().get();
                consume();

                expect(TokenType.LBRACKET);

                Integer arrayLength = Integer.parseInt(token.getValue().get());
                expect(TokenType.NUMBER);

                expect(TokenType.RBRACKET);

                expect(TokenType.EQUALS);

                expect(TokenType.LBRACE);

                ArrayList<Integer> arrayValues = nextArrayValues();

                expect(TokenType.RBRACE);

                return new DivStatement(arrName, arrayValues, arrayLength);

            case "CDIM":
                consume();

                if (token.getType() != TokenType.CARRAY)
                    throw new IOException("Unexpected " + token.getType() + ", expecting VAR");

                String carrName = token.getValue().get();
                consume();

                expect(TokenType.LBRACKET);

                Integer carrayLength = Integer.parseInt(token.getValue().get());
                expect(TokenType.NUMBER);

                expect(TokenType.RBRACKET);

                expect(TokenType.EQUALS);

                expect(TokenType.LBRACE);

                ArrayList<Character> carrayValues = nextCArrayValues();

                expect(TokenType.RBRACE);

                return new CharDimStatement(carrName, carrayValues, carrayLength);

            case "RETURN":
                consume();
                return new ReturnStatement();

            case "END":
                consume();
                return new EndStatement();

            case "WHILE":
                consume();

                StringExpression whileLeft = nextExpression();

                ComparisonOperator whileOperator;
                switch (token.getType()) {
                    case EQUALS:
                        operator = ComparisonOperator.EQUALS;
                        break;
                    case NOT_EQUALS:
                        operator = ComparisonOperator.NOT_EQUALS;
                        break;
                    case LESS:
                        operator = ComparisonOperator.LESS;
                        break;
                    case LESS_OR_EQUALS:
                        operator = ComparisonOperator.LESS_OR_EQUALS;
                        break;
                    case MORE:
                        operator = ComparisonOperator.MORE;
                        break;
                    case MORE_OR_EQUALS:
                        operator = ComparisonOperator.MORE_OR_EQUALS;
                        break;
                    default:
                        throw new IOException("Unexpected " + token.getType() + ", expecting EQ, NE, LT, LTE, GT or GTE");
                }
                consume();

                StringExpression whileRight = nextExpression();

                if (token.getType() != TokenType.KEYWORD)
                    throw new IOException("Unexpected " + token.getType() + ", expecting KEYWORD");

                String doKeyword = token.getValue().get();
                if (!doKeyword.equals("DO"))
                    throw new IOException("Unexpected keyword " + keyword + ", expecting DO");

                consume();

                List<Statement> whileStatementList = new ArrayList<>();
                String wnextKeyword = token.getValue().get();

                do {
                    Statement statement = nextStatement(lineNumber);
                    whileStatementList.add(statement);
                } while(accept(TokenType.SEMICOLON));

                //Statement statement = nextStatement();
                //return new IfStatement(operator, left, right, statement);
                return new WhileStatement(operator, whileLeft, whileRight, whileStatementList, lineNumber);

            case "ENDIF":
                consume();
                consume();

            case ";":
                consume();
                nextStatement(lineNumber);

            default:
                throw new IOException("Unknown keyword: " + keyword);
        }
    }

    private ArrayList<Integer> nextArrayValues() throws IOException {
        ArrayList<Integer> values = new ArrayList<>();
        Integer value;

        while (true) {
            TokenType type = token.getType();

            if (type == TokenType.NUMBER) {
                value = Integer.parseInt(token.getValue().get());
                values.add(value);
                consume();
                continue;
            }

            if (type == TokenType.COMMA) {
                consume();
                continue;
            }

            if (type == TokenType.RBRACE) {
                break;
            }

            if ( (type != TokenType.NUMBER) || (type != TokenType.RBRACE) || (type != TokenType.COMMA) ) {
                throw new IOException("Wrong Array declaration");
            }
        }
        return values;
    }

    private ArrayList<Character> nextCArrayValues() throws IOException {
        ArrayList<Character> values = new ArrayList<>();
        Character value;

        while (true) {
            //System.out.println(" my Char% " + token.getValue().get());

            TokenType type = token.getType();

            if (type == TokenType.CHAR) {
                value = token.getValue().get().charAt(0);
                values.add(value);
                consume();
                continue;
            }


            if (type == TokenType.COMMA) {
                consume();
                continue;
            }

            if (type == TokenType.RBRACE) {
                break;
            }

            if ( (type != TokenType.CHAR) || (type != TokenType.RBRACE) || (type != TokenType.COMMA) ) {
                throw new IOException("Wrong Array declaration");
            }
        }
        System.out.println(values);
        return values;
    }

    private StringExpression nextExpression() throws IOException {
        StringExpression left;

        if (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS) {
            UnaryOperator operator = token.getType() == TokenType.PLUS ? UnaryOperator.PLUS : UnaryOperator.MINUS;
            consume();

            left = new UnaryExpression(operator, nextTerm());
        } else {
            left = nextTerm();
        }

        while (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS) {
            BinaryOperator operator = token.getType() == TokenType.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS;
            consume();

            StringExpression right = nextTerm();
            left = new BinaryExpression(operator, left, right);
        }

        return left;
    }

    private StringExpression nextTerm() throws IOException {
        StringExpression left = nextFactor();

        while (token.getType() == TokenType.MULT || token.getType() == TokenType.DIV) {
            BinaryOperator operator = token.getType() == TokenType.MULT ? BinaryOperator.MULT : BinaryOperator.DIV;
            consume();

            left = new BinaryExpression(operator, left, nextFactor());
        }

        return left;
    }

    private StringExpression nextFactor() throws IOException {
        switch (token.getType()) {
            case VAR:
                StringExpression expr = new VariableExpression(token.getValue().get());
                consume();
                return expr;

            case NUMBER:
                expr = new ImmediateExpression(Integer.parseInt(token.getValue().get()));
                consume();
                return expr;

            case LPAREN:
                consume();
                expr = nextExpression();
                expect(TokenType.RPAREN);
                return expr;
            case ARRAY:
                String name = token.getValue().get();
                consume();

                expect(TokenType.LBRACKET);

                int index = Integer.parseInt(token.getValue().get());
                expect(TokenType.NUMBER);
/*
                int index;
                if (token.getType() == TokenType.VAR) {
                    System.out.println("tokenvar");
                    index = Integer.parseInt(VariableRepository.getValueByName(token.getValue()));
                } else if (token.getType() == TokenType.NUMBER) {
                    System.out.println("tokenNumber");
                    index = Integer.parseInt(token.getValue().get());
                } else {
                    throw new IOException("Wrong Array Declaration");
                }

 */
                expect(TokenType.RBRACKET);
                expr = new ArrayExpression(name, index);

                //consume();
                return expr;

            case CARRAY:
                String cname = token.getValue().get();
                consume();

                expect(TokenType.LBRACKET);

                int cindex = Integer.parseInt(token.getValue().get());
                expect(TokenType.NUMBER);

                expect(TokenType.RBRACKET);
                expr = new CharArrayExpression(cname, cindex);

                return expr;

            default:
                throw new IOException("Unexpected " + token.getType() + ", expecting VAR, NUMBER or LPAREN");
        }
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }

}
