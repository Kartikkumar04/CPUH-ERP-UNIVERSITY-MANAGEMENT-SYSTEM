package com.cpuh.ui.library;

import com.cpuh.dao.LibraryDAO;
import com.cpuh.model.Library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryPanel extends JPanel {

    private JTable libraryTable;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;

    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnDelete;

    private LibraryDAO libraryDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public LibraryPanel() {

        libraryDAO = new LibraryDAO();

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        initUI();

        loadBooks();
    }


    // ==================================================
    // INITIALIZE UI
    // ==================================================

    private void initUI() {

        // ==================================================
        // TOP PANEL
        // ==================================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        JLabel title =
                new JLabel(
                        "Library Management"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        topPanel.add(
                title,
                BorderLayout.WEST
        );


        // ==================================================
        // SEARCH PANEL
        // ==================================================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        txtSearch =
                new JTextField(15);


        btnSearch =
                new JButton("Search");


        btnRefresh =
                new JButton("Refresh");


        btnAdd =
                new JButton(
                        "+ Add Book"
                );


        searchPanel.add(
                new JLabel("Search:")
        );

        searchPanel.add(
                txtSearch
        );

        searchPanel.add(
                btnSearch
        );

        searchPanel.add(
                btnRefresh
        );

        searchPanel.add(
                btnAdd
        );


        topPanel.add(
                searchPanel,
                BorderLayout.EAST
        );


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // ==================================================
        // TABLE
        // ==================================================

        String[] columns = {

                "Library ID",
                "Book ID",
                "Book Title",
                "Author",
                "Category",
                "ISBN",
                "Quantity",
                "Available",
                "Status"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        libraryTable =
                new JTable(
                        tableModel
                );


        libraryTable.setRowHeight(
                28
        );


        libraryTable.setAutoCreateRowSorter(
                true
        );


        libraryTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        libraryTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // ==================================================
        // BOTTOM PANEL
        // ==================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        btnDelete =
                new JButton(
                        "Delete Selected Book"
                );


        bottomPanel.add(
                btnDelete
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ==================================================
        // BUTTON EVENTS
        // ==================================================

        btnAdd.addActionListener(e -> {

            Window window =
                    SwingUtilities
                            .getWindowAncestor(
                                    this
                            );


            if (window instanceof JFrame) {

                new LibraryForm(
                        (JFrame) window
                );


                loadBooks();
            }
        });


        btnRefresh.addActionListener(e -> {

            loadBooks();

        });


        btnSearch.addActionListener(e -> {

            searchBooks();

        });


        txtSearch.addActionListener(e -> {

            searchBooks();

        });


        btnDelete.addActionListener(e -> {

            deleteSelectedBook();

        });
    }


    // ==================================================
    // LOAD BOOKS
    // ==================================================

    private void loadBooks() {

        tableModel.setRowCount(0);


        List<Library> books =
                libraryDAO.getAllBooks();


        for (Library book : books) {

            addBookToTable(book);
        }
    }


    // ==================================================
    // ADD BOOK TO TABLE
    // ==================================================

    private void addBookToTable(
            Library book
    ) {

        tableModel.addRow(
                new Object[]{

                        book.getLibraryId(),

                        book.getBookId(),

                        book.getBookTitle(),

                        book.getAuthor(),

                        book.getCategory(),

                        book.getIsbn(),

                        book.getQuantity(),

                        book.getAvailableQuantity(),

                        book.getStatus()
                }
        );
    }


    // ==================================================
    // SEARCH BOOKS
    // ==================================================

    private void searchBooks() {

        String search =
                txtSearch
                        .getText()
                        .trim();


        if (search.isEmpty()) {

            loadBooks();

            return;
        }


        tableModel.setRowCount(0);


        List<Library> books =
                libraryDAO.searchBooks(
                        search
                );


        for (Library book : books) {

            addBookToTable(book);
        }
    }


    // ==================================================
    // DELETE BOOK
    // ==================================================

    private void deleteSelectedBook() {

        int selectedRow =
                libraryTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a book first.",
                    "No Book Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Convert sorted row to model row
        int modelRow =
                libraryTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        int libraryId =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );


        String bookId =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );


        String bookTitle =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                2
                        )
                );


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this book?\n\n"
                                + "Book ID: "
                                + bookId
                                + "\n"
                                + "Title: "
                                + bookTitle,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            boolean deleted =
                    libraryDAO.deleteBook(
                            libraryId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Book deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                loadBooks();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete book.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}