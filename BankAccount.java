import java.util.InputMismatchException;
import java.io.Serializable;
import java.util.ArrayList;
public abstract class BankAccount implements Transaction,Serializable{
    private String accountName;
    private int accountNo;
    protected double accountBalance;
    private ArrayList<BankTransaction> transactionHistory;

    public BankAccount(String name, int number, double balance){
        this.accountName = name;
        this.accountNo = number;
        this.accountBalance = balance;
        this.transactionHistory=new ArrayList<>();
    }

    public String getAccountName(){
        return accountName;
    }

    public int getAccountNo(){
        return accountNo;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    @Override
    public void deposit(double amount){
        accountBalance+=amount;
        System.out.println("Deposited: " + amount);
        System.out.println("New Balance: " + accountBalance);
        this.transactionHistory.add(new BankTransaction(amount,"Deposit"));
    }
    
    @Override
    public void deposit(double amount, String mode) {
        accountBalance += amount;
        System.out.println("Deposited: ₹" + amount + " via " + mode);
        System.out.println("New Balance: ₹" + accountBalance);
        this.transactionHistory.add(new BankTransaction(amount,"Deposit"));
    }

    @Override
    public void withdraw(double amount) { // No 'throws' clause here
        if (amount > accountBalance){
            throw new InsufficientBalanceException("❌ Withdrawal failed! Insufficient balance.");
        }else{
            accountBalance -= amount;
            System.out.println("Withdrew: " + amount);
            System.out.println("New Balance: " + accountBalance);
            this.transactionHistory.add(new BankTransaction(amount, "Withdrawal"));
        }
    }
    
    @Override
    public void checkBalance(){
        System.out.println("Account Balance: " + accountBalance);
    }

    public void showTransactionHistory() {
        System.out.println("--- Transaction History ---");
        if (this.transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (BankTransaction t : this.transactionHistory) {
                System.out.println(t.toString());
            }
        }
    }
    
    public abstract String accountType();
}