import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bank {
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayList<Account> accounts = new ArrayList<>();



    Customer createCustomer(String fullName){
        customers.add(new Customer(customers.size(), fullName));
        return customers.getLast();
    }
	Account openDebitAccount(Customer owner){
        String account_number = String.valueOf(accounts.size());
        accounts.add(new DebitAccount(account_number, 0, owner));
        return accounts.getLast();
    }
	Account openCreditAccount(Customer owner, double creditLimit){
        String account_number = String.valueOf(accounts.size());
        accounts.add(new CreditAccount(account_number, 0, owner, creditLimit));
        return accounts.getLast();
    }
	Account findAccount(String accountNumber){
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getAccountNumber().equals(accountNumber)){
                return accounts.get(i);
            }
        }
        return null;
    }
    boolean deposit(String accountNumber, double amount){
        boolean is_success = false;
        if (findAccount(accountNumber).deposit(amount)) {
            is_success = true;
        }
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, accountNumber, null, LocalDateTime.now(), is_success, "OK"));
        return is_success;
    }
	boolean withdraw(String accountNumber, double amount){
        boolean is_success = false;
        if (findAccount(accountNumber).withdraw(amount)) {
            is_success = true;
        }

        return is_success;
    }
	boolean transfer(String from, String to, double amount){
        boolean is_success = false;
        if (findAccount(from).transfer(findAccount(to), amount)) {
            is_success = true;
        }
        transactions.add(new Transaction(TransactionType.TRANSFER, amount, from, to, LocalDateTime.now(), is_success, "OK"));
        return is_success;
    }
	void printCustomerAccounts(int customerId){
        for (int j = 0; j < accounts.size(); j++){
            if (accounts.get(j).getOwner() == customers.get(customerId)) {
                System.out.println(accounts.get(j).getAccountNumber());
            }
        }
    }
	void printTransactions(){
        for (int j = 0; j < transactions.size(); j++){
            System.out.println(transactions.get(j));
        }
    }
	void printReport(){
        int cred_accs = 0;
        int debt_accs = 0;
        int trans_succ = 0;
        int trans_fali = 0;
        double cred_accs_balc = 0; double debt_accs_balc = 0;

        for (int i = 0; i<accounts.size(); i++){
            if (accounts.get(i) instanceof CreditAccount){
                cred_accs += 1; cred_accs_balc += accounts.get(i).getBalance();
            } else {
                debt_accs += 1; debt_accs_balc += accounts.get(i).getBalance();
            }
        }
        for (int i = 0; i<transactions.size(); i++){
            if (transactions.get(i).success){
                trans_succ += 1;
            } else {
                trans_fali += 1;
            }
        }
        System.out.printf("Аккаунтов типа DebitAccount - %d, CreditAccount - %d%n", debt_accs, cred_accs);
        System.out.printf("суммарные балансы DebitAccount - %,.2f, CreditAccount - %,.2f%n", debt_accs_balc, cred_accs_balc);
        System.out.printf("Количество успешных операций - %d, Количество неуспешных - %d%n", trans_succ, trans_fali);

    }
}
