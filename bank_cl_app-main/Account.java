public class Account {
    private String accountNumber;
    private double balance;
    private Customer owner;

    Account(String accountNumber, double balance, Customer owner){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }


    boolean deposit(double amount){
        if (amount <= 0){
            System.out.println("Сумма равна нулю или меньше");
            return false;
        }
        setBalance(this.getBalance() + amount);
        return true;
    }

    boolean withdraw(double amount){
        if (amount <= 0 ) {
            System.out.println("Сумма равна нулю или меньше");
            return false;
        } else if (this.getBalance() < amount) {
            System.out.println("Снимается больше, чем на балансе");
            return false;
        }
        setBalance(this.getBalance() - amount);
        return true;
    }

    boolean transfer(Account to, double amount){
        if ((amount > 0 ) && (this.getBalance() >= amount))  {
            this.withdraw(amount);
            to.deposit(amount);
            return true;
        } else return false;
    }

    void setBalance(double amount){
        this.balance = amount;
    }
    double getBalance(){
        return this.balance;
    }
    String getAccountNumber(){
        return this.accountNumber;
    }
    Customer getOwner(){
        return this.owner;
    }
}
