# 🏦 Banking System Simulator (Core Java & OOPs)

A modular **Core Java** console application that simulates core banking operations such as account creation, deposit, withdrawal, fund transfer, and balance inquiry.  
The project is built following **Object-Oriented Principles**, **Exception Handling**, **Collections**, **Functional Programming (Streams)**, and **Multithreading** concepts — as part of the **Virtusa Java Full Stack Development Program Mini Project**.

---

## 🚀 Features

### 👤 Account Management
- Create new accounts using the customer’s name.
- Automatically generates a **unique account number** (user initials + random 4-digit number).
- Initializes with **zero balance**.

### 💰 Banking Operations
- **Deposit** funds (only positive amounts accepted).
- **Withdraw** funds (ensures sufficient balance).
- **Transfer** funds between two accounts with validation.
- **Show Balance** for any account.

### ⚙️ Technical Highlights
- **Encapsulation:** Secure account fields (`private`) with controlled access.
- **Collections & Generics:** Uses `HashMap<String, Account>` to store all accounts.
- **Exception Handling:**
  - `InvalidNameException`
  - `InvalidAmountException`
  - `InsufficientBalanceException`
  - `AccountNotFoundException`
- **Functional Programming:** Streams for filtering/searching accounts.
- **Multithreading:** Demonstrates synchronized deposit/withdraw operations on the same account.
- **Menu-Driven Interface:** Interactive user prompts for seamless operations.
