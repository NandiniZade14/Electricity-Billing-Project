import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class ViewInformation extends JFrame implements ActionListener {

    JButton cancel;

    ViewInformation(String meter) {
        setTitle("Electricity Billing System - Customer Information");
        setBounds(300, 100, 950, 700);
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

        // Information Card Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBounds(40, 30, 870, 360);
        infoPanel.setLayout(null);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        // Heading
        JLabel heading = new JLabel("👤 CUSTOMER INFORMATION");
        heading.setBounds(240, 15, 400, 40);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(102, 126, 234));
        infoPanel.add(heading);

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setBounds(30, 65, 810, 1);
        separator.setForeground(new Color(200, 200, 200));
        infoPanel.add(separator);

        // Left Column Labels
        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(50, 90, 150, 25);
        lblname.setFont(new Font("Arial", Font.PLAIN, 15));
        lblname.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblname);

        JLabel name = new JLabel("");
        name.setBounds(220, 90, 250, 25);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        name.setForeground(new Color(60, 60, 60));
        infoPanel.add(name);

        JLabel lblmeternumber = new JLabel("Meter Number:");
        lblmeternumber.setBounds(50, 130, 150, 25);
        lblmeternumber.setFont(new Font("Arial", Font.PLAIN, 15));
        lblmeternumber.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblmeternumber);

        JLabel meternumber = new JLabel("");
        meternumber.setBounds(220, 130, 250, 25);
        meternumber.setFont(new Font("Arial", Font.BOLD, 15));
        meternumber.setForeground(new Color(102, 126, 234));
        infoPanel.add(meternumber);

        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setBounds(50, 170, 150, 25);
        lbladdress.setFont(new Font("Arial", Font.PLAIN, 15));
        lbladdress.setForeground(new Color(100, 100, 100));
        infoPanel.add(lbladdress);

        JLabel address = new JLabel("");
        address.setBounds(220, 170, 250, 25);
        address.setFont(new Font("Arial", Font.BOLD, 15));
        address.setForeground(new Color(60, 60, 60));
        infoPanel.add(address);

        JLabel lblcity = new JLabel("City:");
        lblcity.setBounds(50, 210, 150, 25);
        lblcity.setFont(new Font("Arial", Font.PLAIN, 15));
        lblcity.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblcity);

        JLabel city = new JLabel("");
        city.setBounds(220, 210, 250, 25);
        city.setFont(new Font("Arial", Font.BOLD, 15));
        city.setForeground(new Color(60, 60, 60));
        infoPanel.add(city);

        // Right Column Labels
        JLabel lblstate = new JLabel("State:");
        lblstate.setBounds(500, 90, 150, 25);
        lblstate.setFont(new Font("Arial", Font.PLAIN, 15));
        lblstate.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblstate);

        JLabel state = new JLabel("");
        state.setBounds(650, 90, 200, 25);
        state.setFont(new Font("Arial", Font.BOLD, 15));
        state.setForeground(new Color(60, 60, 60));
        infoPanel.add(state);

        JLabel lblemail = new JLabel("Email:");
        lblemail.setBounds(500, 130, 150, 25);
        lblemail.setFont(new Font("Arial", Font.PLAIN, 15));
        lblemail.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblemail);

        JLabel email = new JLabel("");
        email.setBounds(650, 130, 200, 25);
        email.setFont(new Font("Arial", Font.BOLD, 15));
        email.setForeground(new Color(60, 60, 60));
        infoPanel.add(email);

        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(500, 170, 150, 25);
        lblphone.setFont(new Font("Arial", Font.PLAIN, 15));
        lblphone.setForeground(new Color(100, 100, 100));
        infoPanel.add(lblphone);

        JLabel phone = new JLabel("");
        phone.setBounds(650, 170, 200, 25);
        phone.setFont(new Font("Arial", Font.BOLD, 15));
        phone.setForeground(new Color(60, 60, 60));
        infoPanel.add(phone);

        // Load data from database
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meter + "'");
            if (rs.next()) {
                name.setText(rs.getString("name"));
                address.setText(rs.getString("address"));
                city.setText(rs.getString("city"));
                state.setText(rs.getString("state"));
                email.setText(rs.getString("email"));
                phone.setText(rs.getString("phone"));
                meternumber.setText(rs.getString("meter_number"));
            } else {
                JOptionPane.showMessageDialog(this,
                        "No customer found with meter number: " + meter,
                        "Not Found",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading customer information: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Close Button
        cancel = new JButton("✕ Close");
        cancel.setFont(new Font("Arial", Font.BOLD, 15));
        cancel.setBackground(new Color(108, 117, 125));
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(355, 280, 150, 45);
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
        infoPanel.add(cancel);

        mainPanel.add(infoPanel);

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(255, 255, 255, 200));
        imagePanel.setBounds(40, 410, 870, 220);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/viewcustomer.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 200, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3, SwingConstants.CENTER);
        imagePanel.add(image, BorderLayout.CENTER);

        mainPanel.add(imagePanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new ViewInformation("172232");
    }
}
