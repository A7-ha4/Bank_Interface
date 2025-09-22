import java.util.InputMismatchException;
import java.util.Scanner;

class Account {
    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private String email;
    private String phoneNumber;

    public Account(int accountNumber, String accountHolderName, double initialDeposit,
                   String email, String phoneNumber) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName != null ? accountHolderName.trim() : "";
        this.balance = initialDeposit >= 0 ? initialDeposit : 0;
        this.email = email != null ? email.trim() : "";
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : "";
    }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public void displayAccountDetails() {
        System.out.println("----- Account Details -----");
        System.out.println("Account Number    : " + accountNumber);
        System.out.println("Account Holder    : " + accountHolderName);
        System.out.printf("Balance           : %.2f%n", balance);
        System.out.println("Email             : " + email);
        System.out.println("Phone Number      : " + phoneNumber);
        System.out.println("---------------------------");
    }

    public void updateContactDetails(String email, String phoneNumber) {
        if (email != null && !email.trim().isEmpty()) this.email = email.trim();
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) this.phoneNumber = phoneNumber.trim();
    }

    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getAccountHolderName() { return accountHolderName; }

    public void setAccountHolderName(String name) {
        if (name != null && !name.trim().isEmpty()) this.accountHolderName = name.trim();
    }
}


/* -------------------------------------------User Interface-------------------------------------------------------*/


public class Main {
    private Account[] accounts;
    private int accountCount;
    private static int nextAccountNumber = 1001;
    private Scanner scanner;

    public Main(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 5;
        accounts = new Account[initialCapacity];
        accountCount = 0;
        scanner = new Scanner(System.in);
    }

    private int findAccountIndex(int accNum) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accNum) return i;
        }
        return -1;
    }

    private void ensureCapacity() {
        if (accountCount < accounts.length) return;
        int newCap = accounts.length * 2;
        Account[] newArr = new Account[newCap];
        for (int i = 0; i < accounts.length; i++) newArr[i] = accounts[i];
        accounts = newArr;
    }

    public void createAccount() {
        System.out.println("=== Create New Account ===");
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Account creation cancelled.");
            return;
        }

        double initialDeposit = 0.0;
        while (true) {
            System.out.print("Enter initial deposit amount: ");
            try {
                String amtStr = scanner.nextLine().trim();
                initialDeposit = Double.parseDouble(amtStr);
                if (initialDeposit < 0) {
                    System.out.println("Initial deposit cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric amount.");
            }
        }

        System.out.print("Enter email address: ");
        String email = scanner.nextLine().trim();
        if (!isValidEmail(email)) {
            System.out.println("Warning: Email format looks incorrect. You can update it later.");
        }

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        if (!isValidPhone(phone)) {
            System.out.println("Warning: Phone number format looks unusual. You can update it later.");
        }

        ensureCapacity();
        Account acc = new Account(nextAccountNumber++, name, initialDeposit, email, phone);
        accounts[accountCount++] = acc;
        System.out.println("Account created successfully with Account Number: " + acc.getAccountNumber());
    }

    public void performDeposit() {
        System.out.println("=== Deposit Money ===");
        int accNum = readInt("Enter account number: ");
        int idx = findAccountIndex(accNum);
        if (idx == -1) {
            System.out.println("Account not found.");
            return;
        }

        double amount = readDouble("Enter amount to deposit: ");
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        boolean ok = accounts[idx].deposit(amount);
        if (ok) {
            System.out.printf("Deposit successful. New balance: %.2f%n", accounts[idx].getBalance());
        } else {
            System.out.println("Deposit failed.");
        }
    }

    public void performWithdrawal() {
        System.out.println("=== Withdraw Money ===");
        int accNum = readInt("Enter account number: ");
        int idx = findAccountIndex(accNum);
        if (idx == -1) {
            System.out.println("Account not found.");
            return;
        }

        double amount = readDouble("Enter amount to withdraw: ");
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > accounts[idx].getBalance()) {
            System.out.println("Insufficient balance. Current balance: " + accounts[idx].getBalance());
            return;
        }
        boolean ok = accounts[idx].withdraw(amount);
        if (ok) {
            System.out.printf("Withdrawal successful. New balance: %.2f%n", accounts[idx].getBalance());
        } else {
            System.out.println("Withdrawal failed.");
        }
    }

    public void showAccountDetails() {
        System.out.println("=== View Account Details ===");
        int accNum = readInt("Enter account number: ");
        int idx = findAccountIndex(accNum);
        if (idx == -1) {
            System.out.println("Account not found.");
            return;
        }
        accounts[idx].displayAccountDetails();
    }

    public void updateContact() {
        System.out.println("=== Update Contact Details ===");
        int accNum = readInt("Enter account number: ");
        int idx = findAccountIndex(accNum);
        if (idx == -1) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter new email (leave blank to keep existing): ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter new phone number (leave blank to keep existing): ");
        String phone = scanner.nextLine().trim();

        if (email.isEmpty() && phone.isEmpty()) {
            System.out.println("No changes provided.");
            return;
        }

        accounts[idx].updateContactDetails(email.isEmpty() ? null : email,
                phone.isEmpty() ? null : phone);
        System.out.println("Contact details updated.");
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Banking Application!");
            System.out.println("1. Create a new account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. View account details");
            System.out.println("5. Update contact details");
            System.out.println("6. Exit");
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: createAccount(); break;
                case 2: performDeposit(); break;
                case 3: performWithdrawal(); break;
                case 4: showAccountDetails(); break;
                case 5: updateContact(); break;
                case 6:
                    System.out.println("Exiting... Thank you for using the Banking Application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric amount.");
            }
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        email = email.trim();
        return email.contains("@") && email.contains(".") && email.length() >= 5;
    }

    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        phone = phone.trim();
        return phone.matches("\\d{10,13}");
    }

    public static void main(String[] args) {
        Main app = new Main(5);
        app.mainMenu();
    }
}

