import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.sql.*; // Assuming Conn class uses java.sql

public class NewCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    JButton next, cancel; // Declared 'next' locally in the original, declaring here for scope clarity
    JLabel lblmeter;

    NewCustomer() {
        setTitle("Electricity Billing System - Add New Customer");
        setSize(850, 550);
        setLocation(350, 180);
        setLayout(null);

        // --- Color Definitions (Consistent with CalculateBill) ---
        Color primaryBlue = new Color(102, 126, 234);
        Color darkerPurple = new Color(118, 75, 162);
        Color cancelGray = new Color(108, 117, 125);
        Color lightBorder = new Color(200, 200, 200);

        // --- Main Background Panel (Gradient) ---
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, primaryBlue, 0, getHeight(), darkerPurple);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // --- Left Panel - Image ---
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent White
        imagePanel.setBounds(30, 30, 200, 470);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(lightBorder, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(180, 450, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3, SwingConstants.CENTER);
        imagePanel.add(image, BorderLayout.CENTER);

        mainPanel.add(imagePanel);

        // --- Right Panel - Form (White) ---
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(250, 30, 570, 470);
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(lightBorder, 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        // Heading
        JLabel heading = new JLabel("📝 Register New Customer");
        heading.setBounds(120, 20, 350, 35);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(primaryBlue);
        formPanel.add(heading);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(30, 70, 510, 1);
        separator.setForeground(lightBorder);
        formPanel.add(separator);

        int labelX = 50;
        int fieldX = 220;
        int yStart = 100;
        int ySpacing = 40;
        int fieldWidth = 300;
        int labelWidth = 150;
        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        Font dataFont = new Font("Arial", Font.BOLD, 15);

        // --- Form Elements ---

        // Customer Name
        JLabel lblname = new JLabel("Customer Name");
        lblname.setBounds(labelX, yStart, labelWidth, 25);
        lblname.setFont(labelFont);
        formPanel.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(fieldX, yStart, fieldWidth, 30);
        tfname.setFont(labelFont);
        tfname.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfname);

        // Meter Number (Auto-generated)
        JLabel lblmeterno = new JLabel("Meter Number");
        lblmeterno.setBounds(labelX, yStart + ySpacing, labelWidth, 25);
        lblmeterno.setFont(labelFont);
        formPanel.add(lblmeterno);

        lblmeter = new JLabel();
        lblmeter.setBounds(fieldX, yStart + ySpacing, fieldWidth, 25);
        lblmeter.setFont(dataFont);
        lblmeter.setForeground(new Color(60, 60, 60));

        Random ran = new Random();
        long number = ran.nextLong() % 1000000;
        lblmeter.setText("" + Math.abs(number));
        formPanel.add(lblmeter);

        // Address
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(labelX, yStart + 2 * ySpacing, labelWidth, 25);
        lbladdress.setFont(labelFont);
        formPanel.add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(fieldX, yStart + 2 * ySpacing, fieldWidth, 30);
        tfaddress.setFont(labelFont);
        tfaddress.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfaddress);

        // City
        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(labelX, yStart + 3 * ySpacing, labelWidth, 25);
        lblcity.setFont(labelFont);
        formPanel.add(lblcity);

        tfcity = new JTextField();
        tfcity.setBounds(fieldX, yStart + 3 * ySpacing, fieldWidth, 30);
        tfcity.setFont(labelFont);
        tfcity.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfcity);

        // State
        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(labelX, yStart + 4 * ySpacing, labelWidth, 25);
        lblstate.setFont(labelFont);
        formPanel.add(lblstate);

        tfstate = new JTextField();
        tfstate.setBounds(fieldX, yStart + 4 * ySpacing, fieldWidth, 30);
        tfstate.setFont(labelFont);
        tfstate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfstate);

        // Email
        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(labelX, yStart + 5 * ySpacing, labelWidth, 25);
        lblemail.setFont(labelFont);
        formPanel.add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(fieldX, yStart + 5 * ySpacing, fieldWidth, 30);
        tfemail.setFont(labelFont);
        tfemail.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfemail);

        // Phone Number
        JLabel lblphone = new JLabel("Phone Number");
        lblphone.setBounds(labelX, yStart + 6 * ySpacing, labelWidth, 25);
        lblphone.setFont(labelFont);
        formPanel.add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(fieldX, yStart + 6 * ySpacing, fieldWidth, 30);
        tfphone.setFont(labelFont);
        tfphone.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfphone);

        // --- Buttons (Styled) ---
        int buttonY = yStart + 7 * ySpacing + 20;

        next = new JButton("➡️ Next: Meter Info");
        next.setBounds(50, buttonY, 230, 40);
        next.setBackground(primaryBlue);
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Arial", Font.BOLD, 15));
        next.setFocusPainted(false);
        next.setBorderPainted(false);
        next.setCursor(new Cursor(Cursor.HAND_CURSOR));
        next.addActionListener(this);

        // Next Hover effect
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                next.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                next.setBackground(primaryBlue);
            }
        });
        formPanel.add(next);

        cancel = new JButton("✕ Cancel");
        cancel.setBounds(300, buttonY, 230, 40);
        cancel.setBackground(cancelGray);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Arial", Font.BOLD, 15));
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(this);

        // Cancel Hover effect
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancel.setBackground(new Color(90, 98, 104));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancel.setBackground(cancelGray);
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
            String name = tfname.getText();
            String meter = lblmeter.getText();
            String address = tfaddress.getText();
            String city = tfcity.getText();
            String state = tfstate.getText();
            String email = tfemail.getText();
            String phone = tfphone.getText();

            // Simple validation
            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query1 = "INSERT INTO customer (name, meter_number, address, city, state, email, phone) VALUES ('" + name + "', '" + meter + "','" + address + "','" + city + "','" + state + "','" + email + "','" + phone + "')";
            // NOTE: Assuming login table structure is (meter_number, username, name, password, user_type)
            String query2 = "INSERT INTO login (meter_number, name) VALUES ('" + meter + "', '" + name + "')";

            try {
                // Ensure Conn class is available in your project and works correctly
                Conn c = new Conn();
                c.s.executeUpdate(query1);
                c.s.executeUpdate(query2);

                JOptionPane.showMessageDialog(this, "Customer Details Added Successfully. Proceeding to Meter Information.", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);

                // Open MeterInfo frame (assuming you have a class named MeterInfo)
                // new MeterInfo(meter);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String args[]) {
        new NewCustomer();
    }
    // Dummy Conn Class for compilation reference (Replace with your actual one)
    /*
    class Conn {
        Connection c;
        Statement s;
        public Conn() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "root");
                s = c.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */
    // Dummy MeterInfo Class for compilation reference (Replace with your actual one)
    /*
    class MeterInfo extends JFrame {
        MeterInfo(String meter) {
            System.out.println("Opening MeterInfo for meter: " + meter);
        }
    }
    */
}
