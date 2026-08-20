package com.cpuh.ui.library;

import com.cpuh.dao.LibraryDAO;
import com.cpuh.model.Library;

import javax.swing.*;
import java.awt.*;

public class LibraryForm extends JDialog {

    private JTextField txtBookId;
    private JTextField txtBookTitle;
    private JTextField txtAuthor;
    private JTextField txtCategory;
    private JTextField txtIsbn;
    private JTextField txtQuantity;
    private JTextField txtAvailableQuantity;

    private JComboBox<String> cmbStatus;

    private JButton btnSave;
    private JButton btnCancel;

    private LibraryDAO libraryDAO;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public LibraryForm(JFrame parent) {

        super(
                parent,
                "Add Library Book",
                true
        );

        libraryDAO =
                new LibraryDAO();

        setSize(600, 650);

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        initUI();

        setVisible(true);
    }


    // ==================================================
    // INITIALIZE UI
    // ==================================================

    private void initUI() {

        JPanel mainPanel =
                new JPanel(
                        new GridBagLayout()
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // ==================================================
        // TITLE
        // ==================================================

        JLabel title =
                new JLabel(
                        "Add Library Book"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(
                title,
                gbc
        );


        // ==================================================
        // BOOK ID
        // ==================================================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Book ID:"),
                gbc
        );


        txtBookId =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtBookId,
                gbc
        );


        // ==================================================
        // BOOK TITLE
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Book Title:"),
                gbc
        );


        txtBookTitle =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtBookTitle,
                gbc
        );


        // ==================================================
        // AUTHOR
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Author:"),
                gbc
        );


        txtAuthor =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtAuthor,
                gbc
        );


        // ==================================================
        // CATEGORY
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Category:"),
                gbc
        );


        txtCategory =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtCategory,
                gbc
        );


        // ==================================================
        // ISBN
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("ISBN:"),
                gbc
        );


        txtIsbn =
                new JTextField();


        gbc.gridx = 1;

        mainPanel.add(
                txtIsbn,
                gbc
        );


        // ==================================================
        // QUANTITY
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Quantity:"),
                gbc
        );


        txtQuantity =
                new JTextField("1");


        gbc.gridx = 1;

        mainPanel.add(
                txtQuantity,
                gbc
        );


        // ==================================================
        // AVAILABLE QUANTITY
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel(
                        "Available Quantity:"
                ),
                gbc
        );


        txtAvailableQuantity =
                new JTextField("1");


        gbc.gridx = 1;

        mainPanel.add(
                txtAvailableQuantity,
                gbc
        );


        // ==================================================
        // STATUS
        // ==================================================

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(
                new JLabel("Status:"),
                gbc
        );


        cmbStatus =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Issued",
                                "Lost"
                        }
                );


        gbc.gridx = 1;

        mainPanel.add(
                cmbStatus,
                gbc
        );


        // ==================================================
        // BUTTONS
        // ==================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );


        btnSave =
                new JButton(
                        "Save Book"
                );


        btnCancel =
                new JButton(
                        "Cancel"
                );


        buttonPanel.add(
                btnSave
        );

        buttonPanel.add(
                btnCancel
        );


        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        mainPanel.add(
                buttonPanel,
                gbc
        );


        add(mainPanel);


        // ==================================================
        // QUANTITY AUTO UPDATE
        // ==================================================

        txtQuantity
                .getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                updateAvailableQuantity();
                            }


                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                updateAvailableQuantity();
                            }


                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                updateAvailableQuantity();
                            }
                        }
                );


        // ==================================================
        // BUTTON EVENTS
        // ==================================================

        btnSave.addActionListener(
                e -> saveBook()
        );


        btnCancel.addActionListener(
                e -> dispose()
        );
    }


    // ==================================================
    // AUTO SET AVAILABLE QUANTITY
    // ==================================================

    private void updateAvailableQuantity() {

        try {

            int quantity =
                    Integer.parseInt(
                            txtQuantity
                                    .getText()
                                    .trim()
                    );


            if (quantity >= 0) {

                txtAvailableQuantity.setText(
                        String.valueOf(quantity)
                );
            }

        } catch (Exception ignored) {
        }
    }


    // ==================================================
    // SAVE BOOK
    // ==================================================

    private void saveBook() {

        String bookId =
                txtBookId
                        .getText()
                        .trim();


        String bookTitle =
                txtBookTitle
                        .getText()
                        .trim();


        String author =
                txtAuthor
                        .getText()
                        .trim();


        String category =
                txtCategory
                        .getText()
                        .trim();


        String isbn =
                txtIsbn
                        .getText()
                        .trim();


        String quantityText =
                txtQuantity
                        .getText()
                        .trim();


        String availableQuantityText =
                txtAvailableQuantity
                        .getText()
                        .trim();


        String status =
                String.valueOf(
                        cmbStatus
                                .getSelectedItem()
                );


        // ==================================================
        // VALIDATION
        // ==================================================

        if (bookId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Book ID.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtBookId.requestFocus();

            return;
        }


        if (bookTitle.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Book Title.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            txtBookTitle.requestFocus();

            return;
        }


        if (quantityText.isEmpty()) {

            quantityText = "1";
        }


        if (availableQuantityText.isEmpty()) {

            availableQuantityText =
                    quantityText;
        }


        int quantity;

        int availableQuantity;


        try {

            quantity =
                    Integer.parseInt(
                            quantityText
                    );

            availableQuantity =
                    Integer.parseInt(
                            availableQuantityText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (quantity <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (availableQuantity < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Available quantity cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (availableQuantity > quantity) {

            JOptionPane.showMessageDialog(
                    this,
                    "Available quantity cannot be greater "
                            + "than total quantity.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==================================================
        // CREATE BOOK OBJECT
        // ==================================================

        Library book =
                new Library();


        book.setBookId(
                bookId
        );


        book.setBookTitle(
                bookTitle
        );


        book.setAuthor(
                author
        );


        book.setCategory(
                category
        );


        book.setIsbn(
                isbn
        );


        book.setQuantity(
                quantity
        );


        book.setAvailableQuantity(
                availableQuantity
        );


        book.setStatus(
                status
        );


        // ==================================================
        // SAVE TO DATABASE
        // ==================================================

        boolean saved =
                libraryDAO.addBook(
                        book
                );


        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add book.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}