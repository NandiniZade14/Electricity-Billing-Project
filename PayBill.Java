import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class PayBill extends JFrame implements ActionListener {

    Choice cmonth;
    JButton pay, back;
    String meter;
    JLabel labelunits, labelamount, labelstatus;

    PayBill(String meter) {
        this.meter = meter;
        setTitle("Electricity Billing System - Pay Bill");
        setLayout(null);
        setBounds(250, 100, 1000, 650);

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

        // Left Panel - Bill Details
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(30, 30, 450, 550);
        leftPanel.setLayout(null);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Heading
        JLabel heading = new JLabel("💳 Electricity Bill Payment");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(102, 126, 234));
        heading.setBounds(30, 20, 400, 35);
        leftPanel.add(heading);

        // Meter Number
        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setFont(new Font("Arial", Font.PLAIN, 14));
        lblmeternumber.setForeground(new Color(100, 100, 100));
        lblmeternumber.setBounds(30, 80, 150, 25);
        leftPanel.add(lblmeternumber);

        JLabel meternumber = new JLabel("");
        meternumber.setFont(new Font("Arial", Font.BOLD, 15));
        meternumber.setForeground(new Color(60, 60, 60));
        meternumber.setBounds(200, 80, 220, 25);
        leftPanel.add(meternumber);

        // Name
        JLabel lblname = new JLabel("Name");
        lblname.setFont(new Font("Arial", Font.PLAIN, 14));
        lblname.setForeground(new Color(100, 100, 100));
        lblname.setBounds(30, 120, 150, 25);
        leftPanel.add(lblname);

        JLabel labelname = new JLabel("");
        labelname.setFont(new Font("Arial", Font.BOLD, 15));
        labelname.setForeground(new Color(60, 60, 60));
        labelname.setBounds(200, 120, 220, 25);
        leftPanel.add(labelname);

        // Separator line
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(30, 160, 390, 1);
        separator1.setForeground(new Color(200, 200, 200));
        leftPanel.add(separator1);

        // Month
        JLabel lblmonth = new JLabel("Select Month");
        lblmonth.setFont(new Font("Arial", Font.PLAIN, 14));
        lblmonth.setForeground(new Color(100, 100, 100));
        lblmonth.setBounds(30, 180, 150, 25);
        leftPanel.add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(200, 180, 220, 25);
        cmonth.setFont(new Font("Arial", Font.PLAIN, 14));
        cmonth.setBackground(Color.WHITE);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        leftPanel.add(cmonth);

        // Separator line
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(30, 220, 390, 1);
        separator2.setForeground(new Color(200, 200, 200));
        leftPanel.add(separator2);

        // Units
        JLabel lblunits = new JLabel("Units Consumed");
        lblunits.setFont(new Font("Arial", Font.PLAIN, 14));
        lblunits.setForeground(new Color(100, 100, 100));
        lblunits.setBounds(30, 240, 150, 25);
        leftPanel.add(lblunits);

        labelunits = new JLabel("");
        labelunits.setFont(new Font("Arial", Font.BOLD, 15));
        labelunits.setForeground(new Color(60, 60, 60));
        labelunits.setBounds(200, 240, 220, 25);
        leftPanel.add(labelunits);

        // Total Bill - Highlighted
        JPanel billPanel = new JPanel();
        billPanel.setBackground(new Color(240, 248, 255));
        billPanel.setBounds(30, 280, 390, 80);
        billPanel.setLayout(null);
        billPanel.setBorder(BorderFactory.createLineBorder(new Color(102, 126, 234), 2));

        JLabel lbltotalbill = new JLabel("Total Bill Amount");
        lbltotalbill.setFont(new Font("Arial", Font.BOLD, 16));
        lbltotalbill.setForeground(new Color(102, 126, 234));
        lbltotalbill.setBounds(20, 10, 200, 25);
        billPanel.add(lbltotalbill);

        labelamount = new JLabel("₹ 0");
        labelamount.setFont(new Font("Arial", Font.BOLD, 24));
        labelamount.setForeground(new Color(102, 126, 234));
        labelamount.setBounds(20, 40, 350, 30);
        billPanel.add(labelamount);

        leftPanel.add(billPanel);

        // Status
        JLabel lblstatus = new JLabel("Payment Status");
        lblstatus.setFont(new Font("Arial", Font.PLAIN, 14));
        lblstatus.setForeground(new Color(100, 100, 100));
        lblstatus.setBounds(30, 380, 150, 25);
        leftPanel.add(lblstatus);

        labelstatus = new JLabel("");
        labelstatus.setFont(new Font("Arial", Font.BOLD, 15));
        labelstatus.setForeground(new Color(220, 53, 69));
        labelstatus.setBounds(200, 380, 220, 25);
        leftPanel.add(labelstatus);

        // Buttons
        pay = new JButton("💰 Pay Now");
        pay.setFont(new Font("Arial", Font.BOLD, 15));
        pay.setBackground(new Color(102, 126, 234));
        pay.setForeground(Color.WHITE);
        pay.setBounds(30, 450, 180, 45);
        pay.setFocusPainted(false);
        pay.setBorderPainted(false);
        pay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pay.addActionListener(this);

        // Hover effect for pay button
        pay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pay.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pay.setBackground(new Color(102, 126, 234));
            }
        });
        leftPanel.add(pay);

        back = new JButton("← Back");
        back.setFont(new Font("Arial", Font.BOLD, 15));
        back.setBackground(new Color(108, 117, 125));
        back.setForeground(Color.WHITE);
        back.setBounds(240, 450, 180, 45);
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(this);

        // Hover effect for back button
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                back.setBackground(new Color(90, 98, 104));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                back.setBackground(new Color(108, 117, 125));
            }
        });
        leftPanel.add(back);

        mainPanel.add(leftPanel);

        // Right Panel - Image
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(255, 255, 255, 200));
        rightPanel.setBounds(510, 80, 450, 450);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3, SwingConstants.CENTER);
        rightPanel.add(image, BorderLayout.CENTER);

        mainPanel.add(rightPanel);

        // Load initial data
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meter + "'");
            while (rs.next()) {
                meternumber.setText(meter);
                labelname.setText(rs.getString("name"));
            }

            rs = c.s.executeQuery("select * from bill where meter_number = '" + meter + "' AND billing_month = 'January'");
            while (rs.next()) {
                labelunits.setText(rs.getString("units_consumed") + " kWh");
                labelamount.setText("₹ " + rs.getString("amount"));
                String status = rs.getString("status");
                labelstatus.setText(status);
                if (status.equals("Paid")) {
                    labelstatus.setForeground(new Color(40, 167, 69));
                } else {
                    labelstatus.setForeground(new Color(220, 53, 69));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Month change listener
        cmonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ae) {
                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from bill where meter_number = '" + meter + "' AND billing_month = '" + cmonth.getSelectedItem() + "'");
                    while (rs.next()) {
                        labelunits.setText(rs.getString("units_consumed") + " kWh");
                        labelamount.setText("₹ " + rs.getString("amount"));
                        String status = rs.getString("status");
                        labelstatus.setText(status);
                        if (status.equals("Paid")) {
                            labelstatus.setForeground(new Color(40, 167, 69));
                        } else {
                            labelstatus.setForeground(new Color(220, 53, 69));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            try {
                Conn c = new Conn();
                String query = "SELECT amount, status FROM bill WHERE meter_number = '" + meter + "' AND billing_month = '" + cmonth.getSelectedItem() + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    String status = rs.getString("status");

                    if (status.equals("Paid")) {
                        JOptionPane.showMessageDialog(null,
                                "This bill has already been paid!",
                                "Already Paid",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    double amount = Double.parseDouble(labelamount.getText().replace("₹ ", ""));
                    PaymentGateway pg = new PaymentGateway(meter, cmonth.getSelectedItem(), amount);

// Wait for payment window to close, then refresh the current bill data
                    pg.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            // Refresh the displayed bill data
                            try {
                                Conn c = new Conn();
                                ResultSet rs = c.s.executeQuery("select * from bill where meter_number = '" + meter + "' AND billing_month = '" + cmonth.getSelectedItem() + "'");
                                while (rs.next()) {
                                    labelunits.setText(rs.getString("units_consumed") + " kWh");
                                    labelamount.setText("₹ " + rs.getString("amount"));
                                    String status = rs.getString("status");
                                    labelstatus.setText(status);
                                    if (status.equals("Paid")) {
                                        labelstatus.setForeground(new Color(40, 167, 69));
                                    } else {
                                        labelstatus.setForeground(new Color(220, 53, 69));
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    setVisible(true); // Keep the PayBill window open instead of closing it

                } else {
                    JOptionPane.showMessageDialog(null,
                            "No bill found for this meter number!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error loading bill details!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new PayBill("172232");
    }
}
