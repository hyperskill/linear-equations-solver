package solver;

public class ParsingArrayException extends Exception{
    public ParsingArrayException(String msg, Exception cause) {
        super(msg, cause);
    }

    public ParsingArrayException(String msg) {
        super(msg);
    }
}
