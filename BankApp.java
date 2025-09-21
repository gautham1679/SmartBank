import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;

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
                System.out.println("7. Show Transaction History");
                System.out.println("8. Delete an Account"); // New option
                System.out.println("9. Reset All Records");                
                System.out.println("10. Exit");

                choice = sc.nextInt();
                sc.nextLine(); // Consume the leftover newline character

                switch (choice) {
                    // In BankApp.java, inside the switch statement

                    case 1:
                        System.out.print("Enter Account Holder Name: ");
                        String name = sc.nextLine();
                        
                        int number;
                        while (true) {
                            System.out.print("Enter Account Number (5 digits only): ");
                            try {
                                number = sc.nextInt();
                                if (number >= 10000 && number <= 99999) {
                                    sc.nextLine(); // Consume newline
                                    break; // Exit the loop if input is valid
                                } else {
                                    System.out.println("❌ Invalid account number. Must be exactly 5 digits.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("❌ Invalid input. Please enter a number.");
                                sc.nextLine(); // Clear invalid input from the scanner
                            }
                        }

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
                            System.out.print("Enter Interest Rate (%): ");
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

                        accounts.put(number, newAccount);
                        saveAccounts(accounts);
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
                        System.out.println("Enter Account Number:");
                        int historyNumber = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        BankAccount historyAc = accounts.get(historyNumber);
                        if (historyAc != null) {
                            historyAc.showTransactionHistory();
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    

                    case 8:
                        System.out.println("Enter the Account Number to delete:");
                        int accountToDelete = sc.nextInt();

                        // The HashMap's remove method returns the value that was removed
                        // or null if the key was not found.
                        BankAccount removedAccount = accounts.remove(accountToDelete);

                        if (removedAccount != null) {
                            System.out.println("✅ Account " + accountToDelete + " has been deleted.");
                            saveAccounts(accounts); // Save the updated list to the file
                        } else {
                            System.out.println("❌ Account not found. No account was deleted.");
                        }
                        break;

                    case 9:
                        accounts.clear();
                        deleteRecords();
                        break;
                
                    case 10:
                        System.out.println("Thank you for using SmartBank. Goodbye!");
                        break;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input! Please enter numbers only.");
                sc.nextLine();
            }
        } while (choice != 10);
        sc.close();
    }

    private static BankAccount findAccount(HashMap<Integer, BankAccount> accounts, int number) {
        return accounts.get(number);
    }

    private static void saveAccounts(HashMap<Integer, BankAccount> accounts) {
        try (FileOutputStream fileOut = new FileOutputStream("accounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(accounts);
            System.out.println("✅ Accounts saved to accounts.ser");
        } catch (IOException e) {
            System.out.println("⚠️ Error saving accounts: " + e.getMessage());
        }
    }

    private static void loadAccounts(HashMap<Integer, BankAccount> accounts) {
        try (FileInputStream fileIn = new FileInputStream("accounts.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            HashMap<Integer, BankAccount> loadedAccounts = (HashMap<Integer, BankAccount>) in.readObject();
            accounts.putAll(loadedAccounts);
            System.out.println("✅ Accounts loaded successfully from accounts.ser");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No previous accounts found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Error loading accounts: " + e.getMessage());
        }
    }

    // In BankApp.java, as a new private static method
    private static void deleteRecords() {
        File file = new File("accounts.ser");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ All account records have been deleted.");
            } else {
                System.out.println("❌ Failed to delete the records file.");
            }
        } else {
            System.out.println("ℹ️ No records file found to delete.");
        }
    }




    
}