import java.util.InputMismatchException;
public abstract class BankAccount implements Transaction{
    private String accountName;
    private int accountNo;
    protected double accountBalance;

    public BankAccount(String name, int number, double balance){
        this.accountName = name;
        this.accountNo = number;
        this.accountBalance = balance;
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
    }
    
    @Override
    public void deposit(double amount, String mode) {
        accountBalance += amount;
        System.out.println("Deposited: ₹" + amount + " via " + mode);
        System.out.println("New Balance: ₹" + accountBalance);
    }

    @Override
    public void withdraw(double amount) { // No 'throws' clause here
        if (amount > accountBalance){
            throw new InsufficientBalanceException("❌ Withdrawal failed! Insufficient balance.");
        }else{
            accountBalance -= amount;
            System.out.println("Withdrew: " + amount);
            System.out.println("New Balance: " + accountBalance);
        }
    }
    
    @Override
    public void checkBalance(){
        System.out.println("Account Balance: " + accountBalance);
    }
    
    public abstract String accountType();
}