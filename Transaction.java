public interface Transaction {
    void deposit(double amount);           // basic deposit
    void deposit(double amount, String mode); // overloaded deposit
    void withdraw(double amount);
    void checkBalance();
}
