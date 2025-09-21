import java.io.Serializable;
public class SavingsAccount extends BankAccount implements Serializable{
    private double interestRate;

    public SavingsAccount(String accountname, int accountNo, double AccountBalance, double interestRate) {
        super(accountname,accountNo,AccountBalance);  // call parent constructor also this doiesnt have to be of the same name as that oif bank account class
        this.interestRate = interestRate;
    }

    // Extra method
    public void addInterest() {
        double interest = getAccountBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("💸 Interest added: ₹" + interest);
    }

    @Override
    public String accountType(){
        return "Savings Account";
    }
}
