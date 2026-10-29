package com.finance.domain;

public class FinanceApplication {
    private final BankAccountService accountService;
    private final CategoryService categoryService;
    private final OperationService operationService;

    public FinanceApplication(BankAccountService accountService,
                              CategoryService categoryService,
                              OperationService operationService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.operationService = operationService;
    }

    public void run() {
        accountService.createAccount("Основной счет", 10000);
        accountService.createAccount("Сбережения", 5000);


        categoryService.createCategory("Зарплата", "INCOME");
        categoryService.createCategory("Кафе", "EXPENSE");
        categoryService.createCategory("Продукты", "EXPENSE");


        operationService.createOperation("INCOME", 50000, "Зарплата за март",
                "Основной счет", "Зарплата");
        operationService.createOperation("EXPENSE", 1500, "Обед в кафе",
                "Основной счет", "Кафе");
        operationService.createOperation("EXPENSE", 3000, "Продукты на неделю",
                "Основной счет", "Продукты");


        showAnalytics();
    }

    private void showAnalytics() {
        System.out.println("АНАЛИТИКА");


        System.out.println("Счета:");
        for (BankAccount account : accountService.getAllAccounts()) {
            account.checkBalance();
        }

        System.out.println("Категории:");
        for (Category category : categoryService.getAllCategories()) {
            category.describe();
        }

        System.out.println("Операции:");
        for (Operation op : operationService.getAllOperations()) {
            op.showInfo();
        }
    }

    public static void main(String[] args) {
        DIContainer container = new DIContainer();

        container.registerSingleton(BankAccountService.class, BankAccountService::new);
        container.registerSingleton(CategoryService.class, CategoryService::new);
        container.registerSingleton(OperationService.class, () ->
                new OperationService(
                        container.resolve(BankAccountService.class),
                        container.resolve(CategoryService.class)
                )
        );
        container.registerSingleton(FinanceApplication.class, () ->
                new FinanceApplication(
                        container.resolve(BankAccountService.class),
                        container.resolve(CategoryService.class),
                        container.resolve(OperationService.class)
                )
        );

        FinanceApplication app = container.resolve(FinanceApplication.class);
        app.run();
    }
}
