package com.library;

import java.sql.*;
import java.util.*;

public class LoanDAO {
    private final BookSQL bookSQL = new BookSQL();
    private final ReaderSQL readerSQL = new ReaderSQL();

    public boolean loanBook(int bookId, int readerId) {
        Book book = bookSQL.getBookById(bookId);
        if (book == null || !book.isAvailable()) {
            System.err.println("Книга не найдена или уже выдана");
            return false;
        }

        Reader reader = readerSQL.getReaderById(readerId);
        if (reader == null) {
            System.err.println("Читатель не найден");
            return false;
        }

        String sql = "INSERT INTO loans (book_id, reader_id, loan_date, is_returned) VALUES (?, ?, CURRENT_DATE, false)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            pstmt.setInt(2, readerId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                bookSQL.updateBookAvailability(bookId, false);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выдаче книги: " + e.getMessage());
        }
        return false;
    }

    public boolean returnBook(int loanId) {
        String sql = "UPDATE loans SET return_date = CURRENT_DATE, is_returned = true WHERE id = ? AND is_returned = false";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loanId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                String getBookSql = "SELECT book_id FROM loans WHERE id = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(getBookSql)) {
                    pstmt2.setInt(1, loanId);
                    ResultSet rs = pstmt2.executeQuery();
                    if (rs.next()) {
                        int bookId = rs.getInt("book_id");
                        bookSQL.updateBookAvailability(bookId, true);
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при возврате книги: " + e.getMessage());
        }
        return false;
    }

    public List<Map<String, Object>> getBooksByReader(int readerId) {
        List<Map<String, Object>> loans = new ArrayList<>();
        String sql = "SELECT l.id as loan_id, b.id as book_id, b.title, b.author, l.loan_date " +
                "FROM loans l JOIN books b ON l.book_id = b.id " +
                "WHERE l.reader_id = ? AND l.is_returned = false";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, readerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> loan = new HashMap<>();
                loan.put("loan_id", rs.getInt("loan_id"));
                loan.put("book_id", rs.getInt("book_id"));
                loan.put("title", rs.getString("title"));
                loan.put("author", rs.getString("author"));
                loan.put("loan_date", rs.getDate("loan_date"));
                loans.add(loan);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении книг читателя: " + e.getMessage());
        }
        return loans;
    }

    public List<Map<String, Object>> getPopularBooks() {
        List<Map<String, Object>> popularBooks = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.author, COUNT(l.id) as loan_count " +
                "FROM books b LEFT JOIN loans l ON b.id = l.book_id " +
                "GROUP BY b.id, b.title, b.author " +
                "ORDER BY loan_count DESC LIMIT 5";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> book = new HashMap<>();
                book.put("id", rs.getInt("id"));
                book.put("title", rs.getString("title"));
                book.put("author", rs.getString("author"));
                book.put("loan_count", rs.getLong("loan_count"));
                popularBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении популярных книг: " + e.getMessage());
        }
        return popularBooks;
    }

    public List<Map<String, Object>> getAllLoanedBooks() {
        List<Map<String, Object>> loanedBooks = new ArrayList<>();
        String sql = "SELECT l.id as loan_id, b.title, b.author, r.full_name as reader_name, " +
                "l.loan_date FROM loans l " +
                "JOIN books b ON l.book_id = b.id " +
                "JOIN readers r ON l.reader_id = r.id " +
                "WHERE l.is_returned = false ORDER BY l.loan_date";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> loan = new HashMap<>();
                loan.put("loan_id", rs.getInt("loan_id"));
                loan.put("title", rs.getString("title"));
                loan.put("author", rs.getString("author"));
                loan.put("reader_name", rs.getString("reader_name"));
                loan.put("loan_date", rs.getDate("loan_date"));
                loanedBooks.add(loan);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка выданных книг: " + e.getMessage());
        }
        return loanedBooks;
    }
}