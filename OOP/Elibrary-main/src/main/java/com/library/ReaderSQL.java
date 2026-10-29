package com.library;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderSQL {

    public boolean addReader(Reader reader) {
        String sql = "INSERT INTO readers (full_name, email, phone) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, reader.getFullName());
            pstmt.setString(2, reader.getEmail());
            pstmt.setString(3, reader.getPhone());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        reader.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при регистрации читателя: " + e.getMessage());
        }
        return false;
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reader reader = new Reader();
                reader.setId(rs.getInt("id"));
                reader.setFullName(rs.getString("full_name"));
                reader.setEmail(rs.getString("email"));
                reader.setPhone(rs.getString("phone"));
                reader.setRegisteredDate(rs.getDate("registered_date"));
                readers.add(reader);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка читателей: " + e.getMessage());
        }
        return readers;
    }

    public Reader getReaderById(int id) {
        String sql = "SELECT * FROM readers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Reader reader = new Reader();
                reader.setId(rs.getInt("id"));
                reader.setFullName(rs.getString("full_name"));
                reader.setEmail(rs.getString("email"));
                reader.setPhone(rs.getString("phone"));
                reader.setRegisteredDate(rs.getDate("registered_date"));
                return reader;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении читателя: " + e.getMessage());
        }
        return null;
    }
}