import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class CustomerDetails extends JFrame implements ActionListener {

    JTable table;
    JButton print;

    CustomerDetails() {
        super("Electricity Billing System - Customer Details");

        setSize(1200, 650);
        setLocation(200, 150);
        setLayout(new BorderLayout()); // Use BorderLayout for main frame

        // Create gradient background panel (same as CalculateBill)
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(102, 126, 234); // Primary Blue
                Color color2 = new Color(118, 75, 162);  // Darker Purple
                // Top to bottom gradient
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10)); // Layout for the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // --- Heading Panel ---
        JLabel heading = new JLabel("👥 All Customer Details");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE); // Use white for high contrast on the gradient
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(heading, BorderLayout.NORTH);

        // --- Table Panel (White Background) ---
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        table = new JTable();
        // Customize table appearance for better look
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(102, 126, 234)); // Header color
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(200, 200, 255)); // Selection highlight

        try {
            Conn c = new Conn();
            // Assuming your connection class 'Conn' and SQL statements are correct
            ResultSet rs = c.s.executeQuery("SELECT * FROM customer");

            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching customer details.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder()); // Remove JScrollPane border
        tableContainer.add(sp, BorderLayout.CENTER);

        mainPanel.add(tableContainer, BorderLayout.CENTER);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Center the button
        buttonPanel.setOpaque(false); // Make the panel transparent so the gradient shows

        print = new JButton("🖨️ Print Details");
        print.addActionListener(this);

        // Apply the same button style as 'CalculateBill's 'Next' button
        print.setBackground(new Color(102, 126, 234));
        print.setForeground(Color.WHITE);
        print.setFont(new Font("Arial", Font.BOLD, 15));
        print.setFocusPainted(false);
        print.setBorderPainted(false);
        print.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                print.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                print.setBackground(new Color(102, 126, 234));
            }
        });

        buttonPanel.add(print);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == print) {
            try {
                // Ensure print dialog shows appropriate title
                table.print(JTable.PrintMode.FIT_WIDTH, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Printing failed: " + e.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}
