import java.io.Serializable;
import java.time.LocalDateTime;
public class BankTransaction implements Serializable{
    private Double amount;
    private String type;
    private LocalDateTime timestamp;
    
    
    public BankTransaction(double amount,String type){
        this.amount=amount;
        this.type=type;
        this.timestamp=LocalDateTime.now();
    }

    public Double getAmount(){
        return this.amount;
    }
    public String getType(){
        return this.type;
    }

    // Inside BankTransaction.java

    @Override
    public String toString() {
        // Format the timestamp for better readability
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTimestamp = this.timestamp.format(formatter);
        
        // Format the output string
        return String.format("Transaction Type: %s | Amount: ₹%.2f | Date: %s", 
                            this.type, this.amount, formattedTimestamp);
    }
}
