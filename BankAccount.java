public abstract class BankAccount implements Transaction{
    private String Accountname;
    private int AccountNo;
    protected double AccountBalance;

    public BankAccount(String hi,int hello,double hey){
        Accountname=hi;
        AccountNo=hello;
        AccountBalance=hey;
    }


    public String getAccountName(){
        return Accountname;
    }

    public int getAccountNo(){
        return AccountNo;
    }

    public double getAccountBalance(){
        return AccountBalance;
    }
    @Override
    public void deposit(double amount){
        AccountBalance+=amount;
        System.out.println("Deposited: " + amount);
        System.out.println("New Balance: " + AccountBalance);
    }

    
    @Override
    public void deposit(double amount, String mode) {
        AccountBalance += amount;
        System.out.println("Deposited: ₹" + amount + " via " + mode);
        System.out.println("New Balance: ₹" + AccountBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount>AccountBalance){
            throw new InsufficientBalanceException("❌ Withdrawal failed! Insufficient balance.");
        }else{
            AccountBalance-=amount;
            System.out.println("Withdrew: " + amount);
            System.out.println("New Balance: " + AccountBalance);
        }
    }
    @Override
    public void checkBalance(){
        System.out.println("Account Balance: " + AccountBalance);
    }
    public abstract String accountType();
}