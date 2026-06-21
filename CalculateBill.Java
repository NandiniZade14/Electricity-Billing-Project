import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class CalculateBill extends JFrame implements ActionListener {

    JTextField tfunits;
    JButton next, cancel;
    JLabel lblname, labeladdress;
    Choice meternumber, cbilling_month;

    CalculateBill() {
        setTitle("Electricity Billing System - Calculate Bill");
        setSize(900, 600);
        setLocation(300, 120);
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

        // Left Panel - Image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(255, 255, 255, 200));
        imagePanel.setBounds(30, 30, 220, 510);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 490, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3, SwingConstants.CENTER);
        imagePanel.add(image, BorderLayout.CENTER);

        mainPanel.add(imagePanel);

        // Right Panel - Form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(280, 30, 590, 510);
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        // Heading
        JLabel heading = new JLabel("⚡ Calculate Electricity Bill");
        heading.setBounds(120, 20, 400, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(102, 126, 234));
        formPanel.add(heading);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(30, 70, 530, 1);
        separator.setForeground(new Color(200, 200, 200));
        formPanel.add(separator);

        // Meter Number
        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(50, 100, 150, 25);
        lblmeternumber.setFont(new Font("Arial", Font.PLAIN, 15));
        lblmeternumber.setForeground(new Color(100, 100, 100));
        formPanel.add(lblmeternumber);

        meternumber = new Choice();
        meternumber.setBounds(220, 100, 310, 25);
        meternumber.setFont(new Font("Arial", Font.PLAIN, 14));
        meternumber.setBackground(Color.WHITE);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            while (rs.next()) {
                meternumber.add(rs.getString("meter_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        formPanel.add(meternumber);

        // Name
        JLabel lblmeterno = new JLabel("Customer Name");
        lblmeterno.setBounds(50, 150, 150, 25);
        lblmeterno.setFont(new Font("Arial", Font.PLAIN, 15));
        lblmeterno.setForeground(new Color(100, 100, 100));
        formPanel.add(lblmeterno);

        lblname = new JLabel("");
        lblname.setBounds(220, 150, 310, 25);
        lblname.setFont(new Font("Arial", Font.BOLD, 15));
        lblname.setForeground(new Color(60, 60, 60));
        formPanel.add(lblname);

        // Address
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(50, 200, 150, 25);
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 15));
        lbladdress.setForeground(new Color(100, 100, 100));
        formPanel.add(lbladdress);

        labeladdress = new JLabel();
        labeladdress.setBounds(220, 200, 310, 25);
        labeladdress.setFont(new Font("Arial", Font.BOLD, 15));
        labeladdress.setForeground(new Color(60, 60, 60));
        formPanel.add(labeladdress);

        // Load initial customer data
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meternumber.getSelectedItem() + "'");
            while (rs.next()) {
                lblname.setText(rs.getString("name"));
                labeladdress.setText(rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Meter number change listener
        meternumber.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meternumber.getSelectedItem() + "'");
                    while (rs.next()) {
                        lblname.setText(rs.getString("name"));
                        labeladdress.setText(rs.getString("address"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Separator
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(30, 240, 530, 1);
        separator2.setForeground(new Color(200, 200, 200));
        formPanel.add(separator2);

        // Units Consumed
        JLabel lblunits_consumed = new JLabel("Units Consumed");
        lblunits_consumed.setBounds(50, 260, 150, 25);
        lblunits_consumed.setFont(new Font("Arial", Font.PLAIN, 15));
        lblunits_consumed.setForeground(new Color(100, 100, 100));
        formPanel.add(lblunits_consumed);

        tfunits = new JTextField();
        tfunits.setBounds(220, 260, 310, 35);
        tfunits.setFont(new Font("Arial", Font.PLAIN, 15));
        tfunits.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(tfunits);

        // Month
        JLabel lblbilling_month = new JLabel("Billing Month");
        lblbilling_month.setBounds(50, 310, 150, 25);
        lblbilling_month.setFont(new Font("Arial", Font.PLAIN, 15));
        lblbilling_month.setForeground(new Color(100, 100, 100));
        formPanel.add(lblbilling_month);

        cbilling_month = new Choice();
        cbilling_month.setBounds(220, 310, 310, 25);
        cbilling_month.setFont(new Font("Arial", Font.PLAIN, 14));
        cbilling_month.setBackground(Color.WHITE);
        cbilling_month.add("January");
        cbilling_month.add("February");
        cbilling_month.add("March");
        cbilling_month.add("April");
        cbilling_month.add("May");
        cbilling_month.add("June");
        cbilling_month.add("July");
        cbilling_month.add("August");
        cbilling_month.add("September");
        cbilling_month.add("October");
        cbilling_month.add("November");
        cbilling_month.add("December");
        formPanel.add(cbilling_month);

        // Buttons
        next = new JButton("📊 Calculate & Submit");
        next.setBounds(50, 390, 230, 45);
        next.setBackground(new Color(102, 126, 234));
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Arial", Font.BOLD, 15));
        next.setFocusPainted(false);
        next.setBorderPainted(false);
        next.setCursor(new Cursor(Cursor.HAND_CURSOR));
        next.addActionListener(this);

        // Hover effect
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                next.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                next.setBackground(new Color(102, 126, 234));
            }
        });
        formPanel.add(next);

        cancel = new JButton("✕ Cancel");
        cancel.setBounds(300, 390, 230, 45);
        cancel.setBackground(new Color(108, 117, 125));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Arial", Font.BOLD, 15));
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(this);

        // Hover effect
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancel.setBackground(new Color(90, 98, 104));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancel.setBackground(new Color(108, 117, 125));
            }
        });
        formPanel.add(cancel);

        mainPanel.add(formPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String meter = meternumber.getSelectedItem();
            String units = tfunits.getText();
            String month = cbilling_month.getSelectedItem();

            // Validate input
            if (units.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter units consumed!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int unit_consumed = Integer.parseInt(units);

                if (unit_consumed < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Units consumed cannot be negative!",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int totalbill = 0;
                String query = "select * from tax";

                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery(query);

                    while (rs.next()) {
                        totalbill += unit_consumed * Integer.parseInt(rs.getString("cost_per_unit"));
                        totalbill += Integer.parseInt(rs.getString("meter_rent"));
                        totalbill += Integer.parseInt(rs.getString("service_charge"));
                        totalbill += Integer.parseInt(rs.getString("service_tax"));
                        totalbill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
                        totalbill += Integer.parseInt(rs.getString("fixed_tax"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Error calculating bill: " + e.getMessage(),
                            "Calculation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Conn c = new Conn();

                    // Check if a bill for this meter and month already exists
                    String checkQuery = "SELECT * FROM bill WHERE meter_number = ? AND billing_month = ?";
                    PreparedStatement checkStmt = c.c.prepareStatement(checkQuery);
                    checkStmt.setString(1, meter);
                    checkStmt.setString(2, month);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        // If bill exists, update it
                        String updateQuery = "UPDATE bill SET units_consumed= ?, amount= ?, status = 'Unpaid' WHERE meter_number = ? AND billing_month = ?";
                        PreparedStatement updateStmt = c.c.prepareStatement(updateQuery);
                        updateStmt.setInt(1, unit_consumed);
                        updateStmt.setInt(2, totalbill);
                        updateStmt.setString(3, meter);
                        updateStmt.setString(4, month);
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(this,
                                "Bill Updated Successfully!\n\nMeter: " + meter + "\nMonth: " + month + "\nUnits: " + unit_consumed + "\nTotal: ₹" + totalbill,
                                "Bill Updated",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // If no bill exists, insert a new one
                        String getCustomerIdQuery = "SELECT customer_id FROM customer WHERE meter_number = ?";
                        PreparedStatement custStmt = c.c.prepareStatement(getCustomerIdQuery);
                        custStmt.setString(1, meter);
                        ResultSet custRs = custStmt.executeQuery();
                        int customerId = 0;
                        if (custRs.next()) {
                            customerId = custRs.getInt("customer_id");
                        }

                        String insertQuery = "INSERT INTO bill (customer_id, meter_number, billing_month, units_consumed, amount, status) VALUES (?, ?, ?, ?, ?, 'Unpaid')";
                        PreparedStatement insertStmt = c.c.prepareStatement(insertQuery);
                        insertStmt.setInt(1, customerId);
                        insertStmt.setString(2, meter);
                        insertStmt.setString(3, month);
                        insertStmt.setInt(4, unit_consumed);
                        insertStmt.setInt(5, totalbill);
                        insertStmt.executeUpdate();

                        JOptionPane.showMessageDialog(this,
                                "Bill Generated Successfully!\n\nMeter: " + meter + "\nMonth: " + month + "\nUnits: " + unit_consumed + "\nTotal: ₹" + totalbill,
                                "Bill Generated",
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                    setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Error saving bill: " + e.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for units!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new CalculateBill();
    }
}
