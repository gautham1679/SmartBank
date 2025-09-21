import java.io.Serializable;
public class InsufficientBalanceException extends RuntimeException implements Serializable{
    public InsufficientBalanceException(String message){
        super(message);
    }
}
