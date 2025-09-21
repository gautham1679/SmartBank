import java.io.Serializable;

public class CurrentAccount extends BankAccount implements Loanservices,Serializable {
    private double overdraftLimit;

    public CurrentAccount(String accountHolder, int accountNumber, double balance, double overdraftLimit) {
        super(accountHolder, accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    // Override withdraw method (Polymorphism)
    @Override
    public void withdraw(double amount) {
        if (amount > (getAccountBalance() + overdraftLimit)) {
            System.out.println("❌ Overdraft limit exceeded!");
        } else {
            accountBalance -= amount;
            System.out.println("Withdrawn: ₹" + amount);
        }
    }
    @Override
    public String accountType(){
        return "Current Account";
    }

    @Override
    public void applyForLoan(double amount) {
        System.out.println("Loan application for ₹" + amount + " submitted for Current Account.");
    }
}