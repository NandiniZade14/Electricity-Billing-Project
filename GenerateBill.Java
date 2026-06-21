import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateBill extends JFrame implements ActionListener {

    String meter;
    JButton bill;
    Choice cmonth;
    JTextArea area;

    GenerateBill(String meter) {
        this.meter = meter;

        setTitle("Electricity Billing System - Generate Bill");
        setSize(650, 850);
        setLocation(450, 30);
        setLayout(new BorderLayout());

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
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Panel - Header
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        topPanel.setPreferredSize(new Dimension(600, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("⚡ Generate Bill");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(102, 126, 234));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        topPanel.add(heading, gbc);

        // Meter Number Label
        JLabel meterLabel = new JLabel("Meter Number:");
        meterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        meterLabel.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        topPanel.add(meterLabel, gbc);

        JLabel meternumber = new JLabel(meter);
        meternumber.setFont(new Font("Arial", Font.BOLD, 16));
        meternumber.setForeground(new Color(102, 126, 234));
        gbc.gridx = 1;
        gbc.gridy = 1;
        topPanel.add(meternumber, gbc);

        // Month Selection
        JLabel monthLabel = new JLabel("Select Month:");
        monthLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        monthLabel.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 2;
        topPanel.add(monthLabel, gbc);

        cmonth = new Choice();
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
        cmonth.setFont(new Font("Arial", Font.PLAIN, 14));
        cmonth.setBackground(Color.WHITE);
        cmonth.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 2;
        topPanel.add(cmonth, gbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Bill Display Area
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(255, 255, 255, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        area = new JTextArea(50, 15);
        area.setText("\n\n\n\t  📄 Click on the Generate Bill\n\n\t  button to view the bill for\n\n\t  the selected month");
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setBackground(Color.WHITE);
        area.setForeground(new Color(60, 60, 60));
        area.setEditable(false);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        pane.setBackground(Color.WHITE);
        centerPanel.add(pane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 255, 0));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        bill = new JButton("📊 Generate Bill");
        bill.setFont(new Font("Arial", Font.BOLD, 16));
        bill.setBackground(new Color(102, 126, 234));
        bill.setForeground(Color.WHITE);
        bill.setFocusPainted(false);
        bill.setBorderPainted(false);
        bill.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bill.setPreferredSize(new Dimension(200, 45));
        bill.addActionListener(this);

        // Hover effect
        bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bill.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bill.setBackground(new Color(102, 126, 234));
            }
        });

        bottomPanel.add(bill);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn c = new Conn();
            String month = cmonth.getSelectedItem();

            area.setText("");
            area.setFont(new Font("Monospaced", Font.PLAIN, 13));

            // Header
            area.append("\n");
            area.append("    ═══════════════════════════════════════════════════════\n");
            area.append("                   RELIANCE POWER LIMITED\n");
            area.append("              ELECTRICITY BILL FOR " + month.toUpperCase() + ", 2022\n");
            area.append("    ═══════════════════════════════════════════════════════\n\n");

            // Customer Details
            ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meter + "'");

            if (rs.next()) {
                area.append("    CUSTOMER DETAILS\n");
                area.append("    ───────────────────────────────────────────────────────\n");
                area.append("    Customer Name    : " + rs.getString("name") + "\n");
                area.append("    Meter Number     : " + rs.getString("meter_number") + "\n");
                area.append("    Address          : " + rs.getString("address") + "\n");
                area.append("    City             : " + rs.getString("city") + "\n");
                area.append("    State            : " + rs.getString("state") + "\n");
                area.append("    Email            : " + rs.getString("email") + "\n");
                area.append("    Phone            : " + rs.getString("phone") + "\n");
                area.append("    ───────────────────────────────────────────────────────\n\n");
            }

            // Meter Information
            rs = c.s.executeQuery("select * from meter_info where meter_number = '" + meter + "'");

            if (rs.next()) {
                area.append("    METER INFORMATION\n");
                area.append("    ───────────────────────────────────────────────────────\n");
                area.append("    Meter Location   : " + rs.getString("meter_location") + "\n");
                area.append("    Meter Type       : " + rs.getString("meter_type") + "\n");
                area.append("    Phase Code       : " + rs.getString("phase_code") + "\n");
                area.append("    Bill Type        : " + rs.getString("bill_type") + "\n");
                area.append("    Days             : " + rs.getString("days") + "\n");
                area.append("    ───────────────────────────────────────────────────────\n\n");
            }

            // Tax Details
            rs = c.s.executeQuery("select * from tax");

            if (rs.next()) {
                area.append("    TARIFF DETAILS\n");
                area.append("    ───────────────────────────────────────────────────────\n");
                area.append("    Cost Per Unit           : ₹" + rs.getString("cost_per_unit") + "\n");
                area.append("    Meter Rent              : ₹" + rs.getString("meter_rent") + "\n");
                area.append("    Service Charge          : ₹" + rs.getString("service_charge") + "\n");
                area.append("    Service Tax             : ₹" + rs.getString("service_tax") + "\n");
                area.append("    Swacch Bharat Cess      : ₹" + rs.getString("swacch_bharat_cess") + "\n");
                area.append("    Fixed Tax               : ₹" + rs.getString("fixed_tax") + "\n");
                area.append("    ───────────────────────────────────────────────────────\n\n");
            }

            // Bill Details
            rs = c.s.executeQuery("select * from bill where meter_number = '" + meter + "' and billing_month='" + month + "'");

            if (rs.next()) {
                area.append("    BILLING SUMMARY\n");
                area.append("    ═══════════════════════════════════════════════════════\n");
                area.append("    Current Month           : " + rs.getString("billing_month") + "\n");
                area.append("    Units Consumed          : " + rs.getString("units_consumed") + " kWh\n");
                area.append("    Total Charges           : ₹" + rs.getString("amount") + "\n");
                area.append("    ───────────────────────────────────────────────────────\n");
                area.append("    TOTAL PAYABLE           : ₹" + rs.getString("amount") + "\n");
                area.append("    ═══════════════════════════════════════════════════════\n\n");
                area.append("    Thank you for using Reliance Power Services!\n");
                area.append("    For queries, contact: support@reliancepower.com\n\n");
            } else {
                area.append("\n    ⚠️  No bill found for the selected month!\n");
                area.append("    Please select a different month or contact support.\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            area.setText("\n\n    ❌ Error generating bill!\n\n    " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new GenerateBill("172232");
    }
}
