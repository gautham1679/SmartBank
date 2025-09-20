import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, BankAccount> accounts = new HashMap<>();
        loadAccounts(accounts);
        int choice = 0; // Initialize choice

        do {
            try {
                System.out.println("======== Welcome to SmartBank!=========");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Show All Accounts");
                System.out.println("6. Apply for Loan");
                System.out.println("7. Exit");

                choice = sc.nextInt();
                sc.nextLine(); // Consume the leftover newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter Account Holder Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Account Number: ");
                        int number = sc.nextInt();

                        // Step 16: Check for duplicate account number
                        if (accounts.containsKey(number)) {
                            System.out.println("❌ Account number already exists. Please choose a different one.");
                            break;
                        }

                        System.out.print("Enter Starting Balance: ");
                        double balance = sc.nextDouble();
                        System.out.println("Choose Account Type: 1. Savings  2. Current");
                        int type = sc.nextInt();

                        BankAccount newAccount = null;
                        if (type == 1) {
                            System.out.println("Enter Interest Rate (%): ");
                            double interestRate = sc.nextDouble();
                            newAccount = new SavingsAccount(name, number, balance, interestRate);
                        } else if (type == 2) {
                            System.out.print("Enter Overdraft Limit: ");
                            double limit = sc.nextDouble();
                            newAccount = new CurrentAccount(name, number, balance, limit);
                        } else {
                            System.out.println("Invalid account type. Account creation failed.");
                            break;
                        }

                        // Use HashMap's put() method instead of add()
                        accounts.put(number, newAccount);
                        saveAccounts(accounts); // Save accounts after creation
                        System.out.println("✅ Account created successfully! " + name);
                        break;

                    case 2:
                        System.out.println("Enter Account Number");
                        int depNumber = sc.nextInt();
                        sc.nextLine(); // consume newline

                        BankAccount depAc = findAccount(accounts, depNumber);
                        if (depAc != null) {
                            System.out.println("Enter amount to deposit");
                            double depositamount = sc.nextDouble();
                            System.out.println("Enter deposit mode (e.g., Cash, Online, Cheque): ");
                            sc.nextLine();
                            String mode = sc.nextLine();
                            depAc.deposit(depositamount, mode);
                            saveAccounts(accounts); // Save accounts after deposit
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 3:
                        System.out.println("Enter Account Number");
                        int withNumber = sc.nextInt();
                        sc.nextLine(); // consume newline

                        BankAccount withAc = findAccount(accounts, withNumber);
                        if (withAc != null) {
                            System.out.println("Enter amount to withdraw");
                            double withdrawAmount = sc.nextDouble();
                            try {
                                withAc.withdraw(withdrawAmount);
                                saveAccounts(accounts); // Save accounts after withdrawal
                            } catch (InsufficientBalanceException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 4:
                        System.out.println("Enter Account Number");
                        int balNumber = sc.nextInt();
                        BankAccount balAc = findAccount(accounts, balNumber);
                        if (balAc != null) {
                            balAc.checkBalance();
                            System.out.println("AccountHolder Name : " + balAc.getAccountName());
                            System.out.println("Account Type : " + balAc.accountType());
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 5:
                        System.out.println("=== List of All Accounts ===");
                        // Iterate over the values of the HashMap
                        for (BankAccount acc : accounts.values()) {
                            System.out.println("Name: " + acc.getAccountName() +
                                    ", Account No: " + acc.getAccountNo() +
                                    ", Balance: ₹" + acc.getAccountBalance() +
                                    ", Type: " + acc.accountType());
                        }
                        break;

                    case 6:
                        System.out.println("Enter Account Number");
                        int loanNumber = sc.nextInt();
                        BankAccount loanAc = findAccount(accounts, loanNumber);
                        if (loanAc != null && loanAc instanceof Loanservices) {
                            System.out.println("Enter loan amount to apply for:");
                            double loanAmount = sc.nextDouble();
                            ((Loanservices) loanAc).applyForLoan(loanAmount);
                        } else {
                            System.out.println("Account not found or does not support loans.");
                        }
                        break;

                    case 7:
                        System.out.println("Thank you for using SmartBank. Goodbye!");
                        break;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input! Please enter numbers only.");
                sc.nextLine();
            }
        } while (choice != 7);
        sc.close();
    }

    private static BankAccount findAccount(HashMap<Integer, BankAccount> accounts, int number) {
        return accounts.get(number);
    }

    private static void saveAccounts(HashMap<Integer, BankAccount> accounts) {
        try (FileWriter writer = new FileWriter("accounts.txt")) {
            for (BankAccount acc : accounts.values()) {
                writer.write(acc.getAccountNo() + "," +
                        acc.getAccountName() + "," +
                        acc.accountType() + "," +
                        acc.getAccountBalance() + "\n");
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error saving accounts: " + e.getMessage());
        }
    }

    private static void loadAccounts(HashMap<Integer, BankAccount> accounts) {
        try (Scanner fileScanner = new Scanner(new File("accounts.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                int number = Integer.parseInt(parts[0]);
                String name = parts[1];
                String type = parts[2];
                double balance = Double.parseDouble(parts[3]);

                BankAccount acc;
                if (type.equals("Savings Account")) {
                    acc = new SavingsAccount(name, number, balance, 5.0);
                } else {
                    acc = new CurrentAccount(name, number, balance, 2000.0);
                }
                accounts.put(number, acc);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No previous accounts found. Starting fresh.");
        }
    }
}