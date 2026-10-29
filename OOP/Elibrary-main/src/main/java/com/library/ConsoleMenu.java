package com.library;

import java.util.*;

import static com.library.DBConnection.closeConnection;

public class ConsoleMenu {
    private final BookSQL bookSQL = new BookSQL();
    private final ReaderSQL readerSQL = new ReaderSQL();
    private final LoanDAO loanDAO = new LoanDAO();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            printMainMenu();
            int choice = getIntInput("Выберите пункт меню: ");

            switch (choice) {
                case 1:
                    bookMenu();
                    break;
                case 2:
                    readerMenu();
                    break;
                case 3:
                    loanMenu();
                    break;
                case 4:
                    statisticsMenu();
                    break;
                case 0:
                    closeConnection();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== БИБЛИОТЕЧНАЯ СИСТЕМА ===");
        System.out.println("1. Работа с книгами");
        System.out.println("2. Работа с читателями");
        System.out.println("3. Операции выдачи");
        System.out.println("4. Статистика");
        System.out.println("0. Выход");
    }

    private void bookMenu() {
        while (true) {
            System.out.println("\n--- РАБОТА С КНИГАМИ ---");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Посмотреть список всех книг");
            System.out.println("3. Найти книгу по названию");
            System.out.println("0. Назад");

            int choice = getIntInput("Выберите пункт: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    findBookByTitle();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n--- Добавление новой книги ---");
        System.out.print("Введите название: ");
        String title = scanner.nextLine();

        System.out.print("Введите автора: ");
        String author = scanner.nextLine();

        int year = getIntInput("Введите год издания: ");

        System.out.print("Введите ISBN: ");
        String isbn = scanner.nextLine();

        Book book = new Book(title, author, year, isbn);

        if (bookSQL.addBook(book)) {
            System.out.println("Книга успешно добавлена! ID: " + book.getId());
        } else {
            System.out.println("Ошибка при добавлении книги.");
        }
    }

    private void showAllBooks() {
        System.out.println("\n--- Список всех книг ---");
        List<Book> books = bookSQL.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Книг в библиотеке нет.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void findBookByTitle() {
        System.out.print("\nВведите название книги (или его часть): ");
        String title = scanner.nextLine();

        List<Book> books = bookSQL.findBooksByTitle(title);

        if (books.isEmpty()) {
            System.out.println("Книги с таким названием не найдены.");
        } else {
            System.out.println("Найденные книги:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void readerMenu() {
        while (true) {
            System.out.println("\n--- РАБОТА С ЧИТАТЕЛЯМИ ---");
            System.out.println("1. Зарегистрировать читателя");
            System.out.println("2. Посмотреть список всех читателей");
            System.out.println("0. Назад");

            int choice = getIntInput("Выберите пункт: ");

            switch (choice) {
                case 1:
                    registerReader();
                    break;
                case 2:
                    showAllReaders();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void registerReader() {
        System.out.println("\n--- Регистрация нового читателя ---");
        System.out.print("Введите ФИО: ");
        String fullName = scanner.nextLine();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine();

        Reader reader = new Reader(fullName, email, phone);

        if (readerSQL.addReader(reader)) {
            System.out.println("Читатель успешно зарегистрирован! ID: " + reader.getId());
        } else {
            System.out.println("Ошибка при регистрации читателя.");
        }
    }

    private void showAllReaders() {
        System.out.println("\n--- Список всех читателей ---");
        List<Reader> readers = readerSQL.getAllReaders();

        if (readers.isEmpty()) {
            System.out.println("Читателей нет.");
        } else {
            for (Reader reader : readers) {
                System.out.println(reader);
            }
        }
    }

    private void loanMenu() {
        while (true) {
            System.out.println("\n--- ОПЕРАЦИИ ВЫДАЧИ ---");
            System.out.println("1. Выдать книгу читателю");
            System.out.println("2. Вернуть книгу");
            System.out.println("3. Посмотреть книги, выданные читателю");
            System.out.println("0. Назад");

            int choice = getIntInput("Выберите пункт: ");

            switch (choice) {
                case 1:
                    loanBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    showReaderBooks();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void loanBook() {
        System.out.println("\n--- Выдача книги ---");
        showAllBooks();
        int bookId = getIntInput("Введите ID книги: ");

        showAllReaders();
        int readerId = getIntInput("Введите ID читателя: ");

        if (loanDAO.loanBook(bookId, readerId)) {
            System.out.println("Книга успешно выдана!");
        } else {
            System.out.println("Не удалось выдать книгу.");
        }
    }

    private void returnBook() {
        System.out.println("\n--- Возврат книги ---");
        showAllLoanedBooks();

        int loanId = getIntInput("Введите ID выдачи для возврата: ");

        if (loanDAO.returnBook(loanId)) {
            System.out.println("Книга успешно возвращена!");
        } else {
            System.out.println("Не удалось вернуть книгу.");
        }
    }

    private void showReaderBooks() {
        System.out.println("\n--- Книги, выданные читателю ---");
        showAllReaders();

        int readerId = getIntInput("Введите ID читателя: ");

        List<Map<String, Object>> books = loanDAO.getBooksByReader(readerId);

        if (books.isEmpty()) {
            System.out.println("У этого читателя нет выданных книг.");
        } else {
            System.out.println("Книги, выданные читателю:");
            for (Map<String, Object> book : books) {
                System.out.printf("ID выдачи: %d | %s | %s | Дата выдачи: %s%n",
                        book.get("loan_id"),
                        book.get("title"),
                        book.get("author"),
                        book.get("loan_date"));
            }
        }
    }

    private void statisticsMenu() {
        while (true) {
            System.out.println("\n--- СТАТИСТИКА ---");
            System.out.println("1. Показать популярные книги (топ 5)");
            System.out.println("2. Показать список выданных книг");
            System.out.println("0. Назад");

            int choice = getIntInput("Выберите пункт: ");

            switch (choice) {
                case 1:
                    showPopularBooks();
                    break;
                case 2:
                    showAllLoanedBooks();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void showPopularBooks() {
        System.out.println("\n--- Популярные книги (топ 5) ---");
        List<Map<String, Object>> books = loanDAO.getPopularBooks();

        if (books.isEmpty()) {
            System.out.println("Нет данных о выдачах книг.");
        } else {
            int rank = 1;
            for (Map<String, Object> book : books) {
                System.out.printf("%d. %s | %s | Количество выдач: %d%n",
                        rank++,
                        book.get("title"),
                        book.get("author"),
                        book.get("loan_count"));
            }
        }
    }

    private void showAllLoanedBooks() {
        System.out.println("\n--- Список всех выданных книг ---");
        List<Map<String, Object>> loans = loanDAO.getAllLoanedBooks();

        if (loans.isEmpty()) {
            System.out.println("Нет выданных книг.");
        } else {
            for (Map<String, Object> loan : loans) {
                System.out.printf("ID выдачи: %d | %s | %s | Читатель: %s | Дата выдачи: %s%n",
                        loan.get("loan_id"),
                        loan.get("title"),
                        loan.get("author"),
                        loan.get("reader_name"),
                        loan.get("loan_date"));
            }
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите число.");
            scanner.next();
            System.out.print(prompt);
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
}