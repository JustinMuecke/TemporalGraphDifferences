package network;

public class DBConnectionFailedException extends Exception{
    public DBConnectionFailedException(String message) {
        super(message);
    }
}
