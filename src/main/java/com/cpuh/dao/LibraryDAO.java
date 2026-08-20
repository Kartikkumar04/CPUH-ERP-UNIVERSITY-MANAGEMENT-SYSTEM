package com.cpuh.dao;

import com.cpuh.db.DBConnection;
import com.cpuh.model.Library;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {

    // ==========================================
    // ADD BOOK
    // ==========================================

    public boolean addBook(Library book) {

        String sql = """
                INSERT INTO library
                (
                    book_id,
                    book_title,
                    author,
                    category,
                    isbn,
                    quantity,
                    available_quantity,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, book.getBookId());
            pst.setString(2, book.getBookTitle());
            pst.setString(3, book.getAuthor());
            pst.setString(4, book.getCategory());
            pst.setString(5, book.getIsbn());

            pst.setInt(6, book.getQuantity());
            pst.setInt(7, book.getAvailableQuantity());

            pst.setString(8, book.getStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to add book.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // GET ALL BOOKS
    // ==========================================

    public List<Library> getAllBooks() {

        List<Library> books =
                new ArrayList<>();

        String sql = """
                SELECT
                    library_id,
                    book_id,
                    book_title,
                    author,
                    category,
                    isbn,
                    quantity,
                    available_quantity,
                    status
                FROM library
                ORDER BY library_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                Library book =
                        new Library();

                book.setLibraryId(
                        rs.getInt("library_id")
                );

                book.setBookId(
                        rs.getString("book_id")
                );

                book.setBookTitle(
                        rs.getString("book_title")
                );

                book.setAuthor(
                        rs.getString("author")
                );

                book.setCategory(
                        rs.getString("category")
                );

                book.setIsbn(
                        rs.getString("isbn")
                );

                book.setQuantity(
                        rs.getInt("quantity")
                );

                book.setAvailableQuantity(
                        rs.getInt("available_quantity")
                );

                book.setStatus(
                        rs.getString("status")
                );

                books.add(book);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to load library books.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return books;
    }


    // ==========================================
    // DELETE BOOK
    // ==========================================

    public boolean deleteBook(int libraryId) {

        String sql =
                "DELETE FROM library WHERE library_id = ?";

        try (
                Connection con = DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, libraryId);

            int rowsAffected =
                    pst.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to delete book.\n\n"
                            + "Library ID: "
                            + libraryId
                            + "\n\nMySQL Error:\n"
                            + e.getMessage(),
                    "Database Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }


    // ==========================================
    // SEARCH BOOKS
    // ==========================================

    public List<Library> searchBooks(String search) {

        List<Library> books =
                new ArrayList<>();

        String sql = """
                SELECT
                    library_id,
                    book_id,
                    book_title,
                    author,
                    category,
                    isbn,
                    quantity,
                    available_quantity,
                    status
                FROM library
                WHERE
                    book_id LIKE ?
                    OR book_title LIKE ?
                    OR author LIKE ?
                    OR category LIKE ?
                    OR isbn LIKE ?
                    OR status LIKE ?
                ORDER BY library_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            String keyword =
                    "%" + search + "%";

            pst.setString(1, keyword);
            pst.setString(2, keyword);
            pst.setString(3, keyword);
            pst.setString(4, keyword);
            pst.setString(5, keyword);
            pst.setString(6, keyword);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Library book =
                        new Library();

                book.setLibraryId(
                        rs.getInt("library_id")
                );

                book.setBookId(
                        rs.getString("book_id")
                );

                book.setBookTitle(
                        rs.getString("book_title")
                );

                book.setAuthor(
                        rs.getString("author")
                );

                book.setCategory(
                        rs.getString("category")
                );

                book.setIsbn(
                        rs.getString("isbn")
                );

                book.setQuantity(
                        rs.getInt("quantity")
                );

                book.setAvailableQuantity(
                        rs.getInt("available_quantity")
                );

                book.setStatus(
                        rs.getString("status")
                );

                books.add(book);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to search books.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return books;
    }
}