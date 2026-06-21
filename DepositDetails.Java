import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Depositdetails extends JFrame implements ActionListener {

    Choice meternumber, cmonth;
    JTable table;
    JButton search, print, reset;

    Depositdetails() {
        super("Electricity Billing System - Deposit Details");

        setSize(1000, 750);
        setLocation(250, 80);
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

        // Top Panel - Search Controls
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setBounds(30, 30, 940, 120);
        topPanel.setLayout(null);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Heading
        JLabel heading = new JLabel("💰 Bill Deposit Details");
        heading.setBounds(320, 10, 350, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(102, 126, 234));
        topPanel.add(heading);

        // Meter Number Search
        JLabel lblmeternumber = new JLabel("Search By Meter:");
        lblmeternumber.setBounds(30, 60, 140, 25);
        lblmeternumber.setFont(new Font("Arial", Font.PLAIN, 14));
        lblmeternumber.setForeground(new Color(100, 100, 100));
        topPanel.add(lblmeternumber);

        meternumber = new Choice();
        meternumber.setBounds(170, 60, 180, 25);
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
        topPanel.add(meternumber);

        // Month Search
        JLabel lblmonth = new JLabel("Search By Month:");
        lblmonth.setBounds(380, 60, 140, 25);
        lblmonth.setFont(new Font("Arial", Font.PLAIN, 14));
        lblmonth.setForeground(new Color(100, 100, 100));
        topPanel.add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(520, 60, 180, 25);
        cmonth.setFont(new Font("Arial", Font.PLAIN, 14));
        cmonth.setBackground(Color.WHITE);
        cmonth.add("January"); cmonth.add("February"); cmonth.add("March");
        cmonth.add("April"); cmonth.add("May"); cmonth.add("June");
        cmonth.add("July"); cmonth.add("August"); cmonth.add("September");
        cmonth.add("October"); cmonth.add("November"); cmonth.add("December");
        topPanel.add(cmonth);

        // Search Button
        search = new JButton("🔍 Search");
        search.setBounds(730, 60, 120, 35);
        search.setFont(new Font("Arial", Font.BOLD, 14));
        search.setBackground(new Color(102, 126, 234));
        search.setForeground(Color.WHITE);
        search.setFocusPainted(false);
        search.setBorderPainted(false);
        search.setCursor(new Cursor(Cursor.HAND_CURSOR));
        search.addActionListener(this);

        // Hover effect
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                search.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                search.setBackground(new Color(102, 126, 234));
            }
        });
        topPanel.add(search);

        mainPanel.add(topPanel);

        // Table Panel - REDUCED HEIGHT
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBounds(30, 170, 940, 420);  // Changed from 450 to 420
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Table
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(102, 126, 234, 50));
        table.setSelectionForeground(Color.BLACK);

        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(102, 126, 234));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bill");
            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Apply center alignment to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        sp.getViewport().setBackground(Color.WHITE);
        tablePanel.add(sp, BorderLayout.CENTER);

        mainPanel.add(tablePanel);

        // Bottom Panel - Action Buttons - MOVED UP
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 255, 0));
        bottomPanel.setBounds(30, 610, 940, 60);  // Changed from 630 to 610 and height from 20 to 60
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Print Button
        print = new JButton("🖨️ Print");
        print.setFont(new Font("Arial", Font.BOLD, 14));
        print.setBackground(new Color(40, 167, 69));
        print.setForeground(Color.WHITE);
        print.setPreferredSize(new Dimension(140, 40));
        print.setFocusPainted(false);
        print.setBorderPainted(false);
        print.setCursor(new Cursor(Cursor.HAND_CURSOR));
        print.addActionListener(this);

        // Hover effect
        print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                print.setBackground(new Color(33, 136, 56));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                print.setBackground(new Color(40, 167, 69));
            }
        });
        bottomPanel.add(print);

        // Reset Button
        reset = new JButton("🔄 Reset");
        reset.setFont(new Font("Arial", Font.BOLD, 14));
        reset.setBackground(new Color(108, 117, 125));
        reset.setForeground(Color.WHITE);
        reset.setPreferredSize(new Dimension(140, 40));
        reset.setFocusPainted(false);
        reset.setBorderPainted(false);
        reset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reset.addActionListener(this);

        // Hover effect
        reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reset.setBackground(new Color(90, 98, 104));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reset.setBackground(new Color(108, 117, 125));
            }
        });
        bottomPanel.add(reset);

        mainPanel.add(bottomPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from bill where meter_number = '" + meternumber.getSelectedItem() + "' and month = '" + cmonth.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));

                // Re-apply center alignment after updating table
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                // Re-style header
                JTableHeader header = table.getTableHeader();
                header.setFont(new Font("Arial", Font.BOLD, 14));
                header.setBackground(new Color(102, 126, 234));
                header.setForeground(Color.WHITE);

                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this,
                            "No records found for the selected criteria!",
                            "No Results",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error searching records: " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == print) {
            try {
                boolean complete = table.print();
                if (complete) {
                    JOptionPane.showMessageDialog(this,
                            "Printing completed successfully!",
                            "Print Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error printing table: " + e.getMessage(),
                        "Print Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == reset) {
            // Reset to show all bills
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from bill");
                table.setModel(DbUtils.resultSetToTableModel(rs));

                // Re-apply center alignment
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                // Re-style header
                JTableHeader header = table.getTableHeader();
                header.setFont(new Font("Arial", Font.BOLD, 14));
                header.setBackground(new Color(102, 126, 234));
                header.setForeground(Color.WHITE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Depositdetails();
    }
}
