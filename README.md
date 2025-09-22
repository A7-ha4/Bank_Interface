# Banking Application in Java

## Overview
This is a console-based **Banking Application** implemented in Java that allows users to manage bank accounts. It provides basic functionalities like creating accounts, depositing and withdrawing money, viewing account details, and updating contact information. The application emphasizes **data validation** and a **user-friendly interface**.

---

## Features
- **Create a new account**
  - Input account holder's name, initial deposit, email, and phone number.
  - Auto-generates a unique account number for each new account.
  - Validates email and phone number formats.
  
- **Deposit Money**
  - Add funds to an existing account.
  - Ensures deposit amount is positive.

- **Withdraw Money**
  - Withdraw funds from an existing account.
  - Prevents overdrawing beyond the available balance.

- **View Account Details**
  - Displays all account information including account number, holder name, balance, email, and phone number.

- **Update Contact Details**
  - Update email and phone number for an account.
  - Allows keeping existing details unchanged.

- **Dynamic Account Management**
  - Automatically expands storage when more accounts are added.

- **Robust Input Handling**
  - Prevents invalid numeric inputs for amounts and account numbers.
  - Graceful handling of invalid menu choices.

---

## Technologies Used
- **Programming Language:** Java  
- **Core Concepts:** Object-Oriented Programming (OOP), Exception Handling, Arrays, Input Validation  
- **User Interface:** Console-based (CLI)

---

## How to Run
1. Clone or download the project files.
2. Compile the Java files using:
   ```bash
   javac Main.java
