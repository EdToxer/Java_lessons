//1.	Создать клиента
//2.	Открыть дебетовый счёт
//3.	Открыть кредитный счёт
//4.	Пополнить
//5.	Снять
//6.	Перевести
//7.	Показать счета клиента
//8.	Показать транзакции
//9.	Отчёт банка
//10.	Выход
import java.util.Scanner;



public class Main {
    public static void printMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         БАНКОВСКАЯ СИСТЕМА           ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Создать клиента                  ║");
        System.out.println("║  2. Открыть дебетовый счёт           ║");
        System.out.println("║  3. Открыть кредитный счёт          ║");
        System.out.println("║  4. Пополнить                        ║");
        System.out.println("║  5. Снять                            ║");
        System.out.println("║  6. Перевести                        ║");
        System.out.println("║  7. Показать счета клиента           ║");
        System.out.println("║  8. Показать транзакции              ║");
        System.out.println("║  9. Отчёт банка                      ║");
        System.out.println("║ 10. Выход                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Выберите действие (1-10): ");
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); double value; int limit;
        Bank bank = new Bank(); Customer owner; String owner_num; String owner_trans;
        String enter = scanner.nextLine();
        printMenu();
        while (enter != "10") {
            switch (enter) {
                case "1":
                    System.out.println("Введите имя для создания клиента");
                    enter = scanner.nextLine();
                    bank.createCustomer(enter);
                    System.out.printf("Клиент создан, его номер - %d%n", bank.customers.getLast().getId());
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "2":
                    System.out.println("Введите номер клиента для которого хотите открыть счёт");
                    enter = scanner.nextLine();
                    owner = bank.customers.get(Integer.parseInt(enter));
                    bank.openDebitAccount(owner);
                    System.out.printf("Счёт создан, его номер - %s%n", bank.accounts.getLast().getAccountNumber());
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "3":
                    System.out.println("Введите номер клиента для которого хотите открыть счёт");
                    enter = scanner.nextLine();
                    owner = bank.customers.get(Integer.parseInt(enter));
                    System.out.println("Введите лимит для кредитного счёта");
                    enter = scanner.nextLine();
                    limit = Integer.parseInt(enter);
                    bank.openCreditAccount(owner, limit);
                    System.out.printf("Счёт создан, его номер - %s%n", bank.accounts.getLast().getAccountNumber());
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "4":
                    System.out.println("Введите номер клиента, который хотите пополнить");
                    enter = scanner.nextLine();
                    owner_num = enter;
                    System.out.println("Введите сумму пополнения");
                    enter = scanner.nextLine();
                    value = Double.parseDouble(enter);
                    bank.deposit(owner_num, value);
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "5":
                    System.out.println("Введите номер счета, который хотите обналичить");
                    enter = scanner.nextLine();
                    owner_num = enter;
                    System.out.println("Введите сумму для снятия");
                    enter = scanner.nextLine();
                    value = Double.parseDouble(enter);
                    bank.withdraw(owner_num, value);
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "6":
                    System.out.println("Введите номер счета, с которого желаете перевести");
                    enter = scanner.nextLine();
                    owner_num = enter;
                    System.out.println("Введите номер счета, на который желаете перевести");
                    enter = scanner.nextLine();
                    owner_trans = enter;
                    System.out.println("Введите сумму для перевода");
                    enter = scanner.nextLine();
                    value = Double.parseDouble(enter);
                    bank.transfer(owner_num,owner_trans, value);
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "7":
                    System.out.println("Введите номер клиента, который просмотреть");
                    enter = scanner.nextLine();
                    limit = Integer.parseInt(enter);
                    bank.printCustomerAccounts(limit);
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "8":
                    System.out.println("Вывод всех транзакций:");
                    bank.printTransactions();
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "9":
                    System.out.println("Вывод отчёта банка:");
                    bank.printReport();
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
                case "10":
                    break;
                default:
                    System.out.println("Введите следующую команду:");
                    enter = scanner.nextLine();
                    break;
            }
        }
    }
}