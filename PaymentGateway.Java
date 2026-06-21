import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.UUID;

public class PaymentGateway extends JFrame implements ActionListener {

    private Connection connection;
    private int customerId;
    private String meterNumber;
    private String billingMonth;
    private double amount;
    JTextField tfCardNumber, tfCardHolder, tfCvv, tfExpiry;
    JLabel lblAmount;
    JButton btnPay, btnCancel;

    public PaymentGateway(String meterNumber, String billingMonth, double amount) {
        this.meterNumber = meterNumber;
        this.billingMonth = billingMonth;
        this.amount = amount;

        // Initialize database connection
        initializeDatabaseConnection();

        setTitle("Electricity Billing System - Secure Payment");
        setSize(600, 700);
        setLocation(400, 80);
        setLayout(null);

        // Create gradient background panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(102, 126, 234);
                Color color2 = new Color(118, 75, 162);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // Payment Card Panel
        JPanel paymentPanel = new JPanel();
        paymentPanel.setBackground(Color.WHITE);
        paymentPanel.setBounds(50, 30, 500, 600);
        paymentPanel.setLayout(null);
        paymentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // Header with lock icon
        JLabel heading = new JLabel("🔒 SECURE PAYMENT");
        heading.setBounds(120, 20, 300, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(102, 126, 234));
        paymentPanel.add(heading);

        // Payment methods
        JLabel lblPaymentMethods = new JLabel("We Accept: VISA | Mastercard | RuPay | UPI", SwingConstants.CENTER);
        lblPaymentMethods.setBounds(50, 60, 400, 20);
        lblPaymentMethods.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPaymentMethods.setForeground(new Color(150, 150, 150));
        paymentPanel.add(lblPaymentMethods);

        // Amount Display Panel
        JPanel amountPanel = new JPanel();
        amountPanel.setBackground(new Color(240, 248, 255));
        amountPanel.setBounds(50, 100, 400, 70);
        amountPanel.setLayout(null);
        amountPanel.setBorder(BorderFactory.createLineBorder(new Color(102, 126, 234), 2));

        JLabel lblAmountLabel = new JLabel("Total Amount:");
        lblAmountLabel.setBounds(20, 10, 150, 25);
        lblAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblAmountLabel.setForeground(new Color(100, 100, 100));
        amountPanel.add(lblAmountLabel);

        lblAmount = new JLabel("₹ " + amount);
        lblAmount.setBounds(20, 35, 360, 30);
        lblAmount.setFont(new Font("Arial", Font.BOLD, 26));
        lblAmount.setForeground(new Color(102, 126, 234));
        amountPanel.add(lblAmount);

        paymentPanel.add(amountPanel);

        // Card Number
        JLabel lblCardNumber = new JLabel("Card Number");
        lblCardNumber.setBounds(50, 190, 150, 25);
        lblCardNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCardNumber.setForeground(new Color(100, 100, 100));
        paymentPanel.add(lblCardNumber);

        tfCardNumber = new JTextField();
        tfCardNumber.setBounds(50, 215, 400, 40);
        tfCardNumber.setFont(new Font("Arial", Font.PLAIN, 16));
        tfCardNumber.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        paymentPanel.add(tfCardNumber);

        // Card Holder Name
        JLabel lblCardHolder = new JLabel("Card Holder Name");
        lblCardHolder.setBounds(50, 270, 150, 25);
        lblCardHolder.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCardHolder.setForeground(new Color(100, 100, 100));
        paymentPanel.add(lblCardHolder);

        tfCardHolder = new JTextField();
        tfCardHolder.setBounds(50, 295, 400, 40);
        tfCardHolder.setFont(new Font("Arial", Font.PLAIN, 16));
        tfCardHolder.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        paymentPanel.add(tfCardHolder);

        // Expiry Date
        JLabel lblExpiry = new JLabel("Expiry Date (MM/YY)");
        lblExpiry.setBounds(50, 350, 180, 25);
        lblExpiry.setFont(new Font("Arial", Font.PLAIN, 14));
        lblExpiry.setForeground(new Color(100, 100, 100));
        paymentPanel.add(lblExpiry);

        tfExpiry = new JTextField();
        tfExpiry.setBounds(50, 375, 180, 40);
        tfExpiry.setFont(new Font("Arial", Font.PLAIN, 16));
        tfExpiry.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        paymentPanel.add(tfExpiry);

        // CVV
        JLabel lblCvv = new JLabel("CVV");
        lblCvv.setBounds(270, 350, 100, 25);
        lblCvv.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCvv.setForeground(new Color(100, 100, 100));
        paymentPanel.add(lblCvv);

        tfCvv = new JPasswordField();
        tfCvv.setBounds(270, 375, 180, 40);
        tfCvv.setFont(new Font("Arial", Font.PLAIN, 16));
        tfCvv.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        paymentPanel.add(tfCvv);

        // Security Note
        JLabel lblSecurity = new JLabel("🔒 Your payment is secure and encrypted", SwingConstants.CENTER);
        lblSecurity.setBounds(50, 435, 400, 20);
        lblSecurity.setFont(new Font("Arial", Font.ITALIC, 12));
        lblSecurity.setForeground(new Color(150, 150, 150));
        paymentPanel.add(lblSecurity);

        // Pay Button
        btnPay = new JButton("💳 PAY ₹" + amount);
        btnPay.setBounds(50, 480, 190, 50);
        btnPay.setBackground(new Color(40, 167, 69));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Arial", Font.BOLD, 16));
        btnPay.addActionListener(this);
        btnPay.setFocusPainted(false);
        btnPay.setBorderPainted(false);
        btnPay.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for pay button
        btnPay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPay.setBackground(new Color(33, 136, 56));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPay.setBackground(new Color(40, 167, 69));
            }
        });
        paymentPanel.add(btnPay);

        // Cancel Button
        btnCancel = new JButton("✕ CANCEL");
        btnCancel.setBounds(260, 480, 190, 50);
        btnCancel.setBackground(new Color(220, 53, 69));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
        btnCancel.addActionListener(this);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for cancel button
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(new Color(200, 35, 51));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(new Color(220, 53, 69));
            }
        });
        paymentPanel.add(btnCancel);

        mainPanel.add(paymentPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnPay) {
            // Validate inputs
            String cardNumber = tfCardNumber.getText().trim();
            String cardHolder = tfCardHolder.getText().trim();
            String expiry = tfExpiry.getText().trim();
            String cvv = tfCvv.getText().trim();

            if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cardNumber.length() < 12) {
                JOptionPane.showMessageDialog(this, "Invalid card number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cvv.length() != 3) {
                JOptionPane.showMessageDialog(this, "Invalid CVV!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Process payment
            processPayment();

        } else if (ae.getSource() == btnCancel) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel the payment?",
                    "Cancel Payment",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    private void processPayment() {
        // Create styled progress dialog
        JDialog progressDialog = new JDialog(this, "Processing Payment", true);
        progressDialog.setSize(400, 180);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.setLayout(null);
        progressDialog.getContentPane().setBackground(Color.WHITE);

        JLabel lblProcessing = new JLabel("Processing your payment...", SwingConstants.CENTER);
        lblProcessing.setBounds(0, 30, 400, 30);
        lblProcessing.setFont(new Font("Arial", Font.BOLD, 16));
        lblProcessing.setForeground(new Color(102, 126, 234));
        progressDialog.add(lblProcessing);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(50, 70, 300, 30);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(102, 126, 234));
        progressBar.setBackground(new Color(240, 240, 240));
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        progressDialog.add(progressBar);

        JLabel lblWait = new JLabel("Please wait...", SwingConstants.CENTER);
        lblWait.setBounds(0, 110, 400, 20);
        lblWait.setFont(new Font("Arial", Font.ITALIC, 12));
        lblWait.setForeground(Color.GRAY);
        progressDialog.add(lblWait);

        // Process in background thread
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i += 5) {
                    Thread.sleep(100);
                    final int progress = i;
                    SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
                }

                // Save payment and update bill status
                savePaymentHistory();

                SwingUtilities.invokeLater(() -> {
                    progressDialog.dispose();
                    showSuccessDialog();
                });

            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    progressDialog.dispose();
                    JOptionPane.showMessageDialog(this, "Payment failed! Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();

        progressDialog.setVisible(true);
    }

    private void showSuccessDialog() {
        // Generate transaction ID
        String transactionId = "TXN" + System.currentTimeMillis();

        JDialog successDialog = new JDialog(this, "Payment Successful", true);
        successDialog.setSize(450, 350);
        successDialog.setLocationRelativeTo(this);
        successDialog.setLayout(null);

        // Gradient background
        JPanel successPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(102, 126, 234);
                Color color2 = new Color(118, 75, 162);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        successPanel.setLayout(null);
        successPanel.setBounds(0, 0, 450, 350);

        // White card inside
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBounds(40, 40, 370, 270);
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Success Icon
        JLabel lblSuccess = new JLabel("✓", SwingConstants.CENTER);
        lblSuccess.setBounds(0, 20, 370, 80);
        lblSuccess.setFont(new Font("Arial", Font.BOLD, 80));
        lblSuccess.setForeground(new Color(40, 167, 69));
        cardPanel.add(lblSuccess);

        JLabel lblMessage = new JLabel("Payment Successful!", SwingConstants.CENTER);
        lblMessage.setBounds(0, 110, 370, 30);
        lblMessage.setFont(new Font("Arial", Font.BOLD, 22));
        lblMessage.setForeground(new Color(60, 60, 60));
        cardPanel.add(lblMessage);

        JLabel lblTransaction = new JLabel("Transaction ID: " + transactionId, SwingConstants.CENTER);
        lblTransaction.setBounds(0, 145, 370, 20);
        lblTransaction.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTransaction.setForeground(Color.GRAY);
        cardPanel.add(lblTransaction);

        JLabel lblAmountPaid = new JLabel("Amount Paid: ₹" + amount, SwingConstants.CENTER);
        lblAmountPaid.setBounds(0, 170, 370, 25);
        lblAmountPaid.setFont(new Font("Arial", Font.BOLD, 16));
        lblAmountPaid.setForeground(new Color(102, 126, 234));
        cardPanel.add(lblAmountPaid);

        JButton btnOk = new JButton("✓ Done");
        btnOk.setBounds(110, 210, 150, 40);
        btnOk.setBackground(new Color(40, 167, 69));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFont(new Font("Arial", Font.BOLD, 16));
        btnOk.setFocusPainted(false);
        btnOk.setBorderPainted(false);
        btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOk.addActionListener(e -> {
            successDialog.dispose();
            dispose();
        });

        // Hover effect
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOk.setBackground(new Color(33, 136, 56));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOk.setBackground(new Color(40, 167, 69));
            }
        });
        cardPanel.add(btnOk);

        successPanel.add(cardPanel);
        successDialog.add(successPanel);
        successDialog.setVisible(true);
    }

    private void savePaymentHistory() {
        try {
            if (connection == null || connection.isClosed()) {
                initializeDatabaseConnection();
            }

            if (connection == null) {
                JOptionPane.showMessageDialog(this,
                        "Database connection could not be established!",
                        "Connection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String findCustomerSQL = "SELECT customer_id FROM customer WHERE meter_number = ?";
            PreparedStatement findStmt = connection.prepareStatement(findCustomerSQL);
            findStmt.setString(1, meterNumber);
            ResultSet rs = findStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this,
                        "Customer not found for meter number: " + meterNumber,
                        "Error", JOptionPane.ERROR_MESSAGE);
                rs.close();
                findStmt.close();
                return;
            }

            customerId = rs.getInt("customer_id");
            System.out.println("Found customer_id: " + customerId + " for meter: " + meterNumber);
            rs.close();
            findStmt.close();

            String findBillSQL = "SELECT bill_id FROM bill WHERE customer_id = ? AND billing_month = ?";
            PreparedStatement billStmt = connection.prepareStatement(findBillSQL);
            billStmt.setInt(1, customerId);
            billStmt.setString(2, billingMonth);
            ResultSet billRs = billStmt.executeQuery();

            int billId = 0;
            if (billRs.next()) {
                billId = billRs.getInt("bill_id");
            }
            billRs.close();
            billStmt.close();

            // --- TRANSACTION START: Disable auto-commit ---
            connection.setAutoCommit(false);

            if (billId == 0) {
                JOptionPane.showMessageDialog(this,
                        "Bill not found for this month!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String transactionId = "TXN" + System.currentTimeMillis();

            String insertSQL = "INSERT INTO payment (bill_id, customer_id, amount, payment_date, payment_method, transaction_id, payment_gateway) VALUES (?, ?, ?, NOW(), ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
            pstmt.setInt(1, billId);
            pstmt.setInt(2, customerId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, "Online");
            pstmt.setString(5, transactionId);
            pstmt.setString(6, "Online");

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                String updateBillSQL = "UPDATE bill SET status = 'Paid' WHERE bill_id = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateBillSQL);
                updateStmt.setInt(1, billId);
                int updatedRows=updateStmt.executeUpdate();
                updateStmt.close();

                System.out.println("✅ UPDATE executed for bill_id: " + billId);
                System.out.println("✅ Rows affected by UPDATE: " + updatedRows);

                // --- TRANSACTION CONTROL LOGIC ---
                if (updatedRows > 0) {
                    connection.commit(); // COMMIT the INSERT and UPDATE
                    System.out.println("✅ Transaction committed successfully.");
                } else {
                    System.err.println("⚠️ WARNING: UPDATE returned 0 rows! Bill status NOT changed! Rolling back payment INSERT.");
                    connection.rollback(); // ROLLBACK the INSERT payment
                }
                // --- END TRANSACTION CONTROL LOGIC ---

            }

        } catch (SQLException e) {
            e.printStackTrace();
            // --- ROLLBACK ON ERROR ---
            try {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback(); // ROLLBACK on any SQL error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // --- END ROLLBACK ON ERROR ---
            JOptionPane.showMessageDialog(this,
                    "Error saving payment: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // --- RESET AUTO-COMMIT ---
            try {
                if (connection != null) connection.setAutoCommit(true); // Reset connection
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // --- END RESET AUTO-COMMIT ---
        }
    }

    private void initializeDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/ebs";
            String username = "root";
            String password = "Nandini@2004#";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found!");
            e.printStackTrace();
            connection = null;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            connection = null;
        }
    }

    public static void main(String[] args) {
        String meterNumber = "172232";
        String billingMonth = "April";
        int billAmount = 1230;
        new PaymentGateway(meterNumber, billingMonth, billAmount);
    }
}
