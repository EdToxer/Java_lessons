package com.finance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperationService {
    private final List<Operation> operations = new ArrayList<>();
    private final BankAccountService accountService;
    private final CategoryService categoryService;

    public OperationService(BankAccountService accountService, CategoryService categoryService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    public void createOperation(String type, double amount, String description,
                                String accountName, String categoryName) {
        BankAccount account = accountService.findAccountByName(accountName);
        Category category = categoryService.findCategoryByName(categoryName);

        if (account == null || category == null) {
            System.out.println("Ошибка: счет или категория не найдены");
            return;
        }

        Operation operation = new Operation(type, amount, LocalDate.now(),
                description, account.getId(), category.getId());
        operations.add(operation);

        operation.execute(account, category);
    }

    public List<Operation> getAllOperations() {
        return operations;
    }

}
