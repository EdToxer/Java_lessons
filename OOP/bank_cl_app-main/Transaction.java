import java.time.LocalDateTime;
import java.util.ArrayList;

enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}

public class Transaction {
    TransactionType type;
    double amount;
    String fromAccountNumber;
    String toAccountNumber;
    LocalDateTime timestamp;
    boolean success;
    String message;

    Transaction(TransactionType type, double amount, String fromAccountNumber, String toAccountNumber, LocalDateTime timestamp, boolean success, String message){
        this.type = type;
        this.amount = amount;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.timestamp = timestamp;
        this.success = success;
        this.message = message;
    }

}
