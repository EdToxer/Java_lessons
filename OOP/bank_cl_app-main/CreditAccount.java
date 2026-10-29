public class CreditAccount extends  Account{
    private double creditLimit;
    CreditAccount(String accountNumber, double balance, Customer owner, double creditLimit){
        super(accountNumber, balance, owner);
        this.creditLimit = creditLimit;
    }

    @Override
    boolean withdraw(double amount){
        if (amount <= 0 ) {
            System.out.println("Сумма равна нулю или меньше");
            return false;
        } else if (this.getBalance() - amount < creditLimit * -1) {
            System.out.println("Снимается больше, чем на лимите");
            return false;
        }
        setBalance(this.getBalance() - amount);
        return true;
    }
}
