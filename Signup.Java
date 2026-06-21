import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener {

    JButton create, back;
    Choice Account_Type;
    JTextField meter, username, name;
    JPasswordField password;

    SignUp() {
        setTitle("Electricity Billing System - Sign Up");
        setBounds(400, 150, 750, 450); // Increased size for better layout
        setLayout(null);

        // --- Color Definitions (Consistent with previous screens) ---
        Color primaryBlue = new Color(102, 126, 234);
        Color darkerPurple = new Color(118, 75, 162);
        Color cancelGray = new Color(108, 117, 125);
        Color lightBorder = new Color(200, 200, 200);
        Color standardText = new Color(100, 100, 100);
        Color dataText = new Color(60, 60, 60);

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

        // --- Content Panel (White) ---
        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 680, 360);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(lightBorder, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(panel);

        // Heading
        JLabel mainHeading = new JLabel("🔑 Create New Account");
        mainHeading.setBounds(50, 20, 350, 35);
        mainHeading.setFont(new Font("Arial", Font.BOLD, 26));
        mainHeading.setForeground(primaryBlue);
        panel.add(mainHeading);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(30, 65, 380, 1);
        separator.setForeground(lightBorder);
        panel.add(separator);

        int labelX = 50;
        int fieldX = 220;
        int yStart = 80;
        int ySpacing = 40;
        int fieldWidth = 200;
        int labelWidth = 150;
        Font labelFont = new Font("Arial", Font.PLAIN, 15);

        // --- Form Fields ---

        // Account Type
        JLabel heading = new JLabel("Account Type");
        heading.setBounds(labelX, yStart, labelWidth, 25);
        heading.setForeground(standardText);
        heading.setFont(labelFont);
        panel.add(heading);

        Account_Type = new Choice();
        Account_Type.add("Admin");
        Account_Type.add("Customer");
        Account_Type.setBounds(fieldX, yStart, fieldWidth, 25);
        Account_Type.setFont(labelFont);
        Account_Type.setBackground(Color.WHITE);
        panel.add(Account_Type);

        // Meter Number
        JLabel lblmeter = new JLabel("Meter Number");
        lblmeter.setBounds(labelX, yStart + ySpacing, labelWidth, 25);
        lblmeter.setForeground(standardText);
        lblmeter.setFont(labelFont);
        lblmeter.setVisible(false);
        panel.add(lblmeter);

        meter = new JTextField();
        meter.setBounds(fieldX, yStart + ySpacing, fieldWidth, 30);
        meter.setFont(labelFont);
        meter.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        meter.setVisible(false);
        panel.add(meter);

        // Username
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(labelX, yStart + 2 * ySpacing, labelWidth, 25);
        lblusername.setForeground(standardText);
        lblusername.setFont(labelFont);
        panel.add(lblusername);

        username = new JTextField();
        username.setBounds(fieldX, yStart + 2 * ySpacing, fieldWidth, 30);
        username.setFont(labelFont);
        username.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(username);

        // Name field
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(labelX, yStart + 3 * ySpacing, labelWidth, 25);
        lblname.setForeground(standardText);
        lblname.setFont(labelFont);
        panel.add(lblname);

        name = new JTextField();
        name.setBounds(fieldX, yStart + 3 * ySpacing, fieldWidth, 30);
        name.setFont(labelFont);
        name.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(name);

        // Password field
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(labelX, yStart + 4 * ySpacing, labelWidth, 25);
        lblpassword.setForeground(standardText);
        lblpassword.setFont(labelFont);
        panel.add(lblpassword);

        password = new JPasswordField();
        password.setBounds(fieldX, yStart + 4 * ySpacing, fieldWidth, 30);
        password.setFont(labelFont);
        password.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(password);

        // --- Action Listeners ---
        Account_Type.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ae) {
                String user = Account_Type.getSelectedItem();
                if (user.equals("Customer")) {
                    lblmeter.setVisible(true);
                    meter.setVisible(true);
                    name.setEditable(false);
                    name.setText(""); // Clear name field when switching to Customer
                } else {
                    lblmeter.setVisible(false);
                    meter.setVisible(false);
                    name.setEditable(true);
                }
            }
        });

        meter.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    // Assuming Conn class is correct
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meter.getText() + "'");
                    if (rs.next()) {
                        name.setText(rs.getString("name"));
                    } else {
                        name.setText("Meter not found");
                        name.setForeground(Color.RED);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    name.setText("DB Error");
                    name.setForeground(Color.RED);
                }
                name.setForeground(dataText); // Reset color on success
            }
        });

        // --- Buttons (Styled) ---
        int buttonY = yStart + 5 * ySpacing + 20;

        create = new JButton("➕ Create Account");
        create.setBounds(50, buttonY, 190, 40);
        create.setBackground(primaryBlue);
        create.setForeground(Color.WHITE);
        create.setFont(new Font("Arial", Font.BOLD, 15));
        create.setFocusPainted(false);
        create.setBorderPainted(false);
        create.setCursor(new Cursor(Cursor.HAND_CURSOR));
        create.addActionListener(this);

        // Create Hover effect
        create.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                create.setBackground(new Color(85, 104, 211));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                create.setBackground(primaryBlue);
            }
        });
        panel.add(create);

        back = new JButton("← Back to Login");
        back.setBounds(260, buttonY, 190, 40);
        back.setBackground(cancelGray);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Arial", Font.BOLD, 15));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(this);

        // Back Hover effect
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                back.setBackground(new Color(90, 98, 104));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                back.setBackground(cancelGray);
            }
        });
        panel.add(back);


        // --- Image Panel (Right Side) ---
        JPanel imageContainer = new JPanel();
        imageContainer.setBounds(440, 0, 240, 360);
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setLayout(new BorderLayout());

        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/signupImage.png"));
            Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel image = new JLabel(i3, SwingConstants.CENTER);
            imageContainer.add(image, BorderLayout.CENTER);

            // Add a friendly caption
            JLabel caption = new JLabel("Secure Registration", SwingConstants.CENTER);
            caption.setFont(new Font("Arial", Font.ITALIC, 14));
            caption.setForeground(primaryBlue);
            imageContainer.add(caption, BorderLayout.SOUTH);

        } catch (Exception e) {
            JLabel placeholder = new JLabel("IMAGE NOT FOUND", SwingConstants.CENTER);
            placeholder.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
            placeholder.setFont(new Font("Arial", Font.BOLD, 14));
            imageContainer.add(placeholder, BorderLayout.CENTER);
        }

        panel.add(imageContainer);
        // Note: The original logic for adding the image was moved into the imageContainer panel.


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            String accountType_value = Account_Type.getSelectedItem();
            String susername = username.getText();
            String sname = name.getText();
            String spassword = new String(password.getPassword());
            String smeter = meter.getText();

            // Basic validation
            if (susername.trim().isEmpty() || sname.trim().isEmpty() || spassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (accountType_value.equals("Customer") && smeter.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter meter number for customer account.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Ensure Conn class is defined in your project
                Conn c = new Conn();

                String query = "INSERT INTO login (meter_number, username, name, password, user) " +
                        "VALUES('" + smeter + "','" + susername + "','" + sname + "','" +
                        spassword + "','" + accountType_value + "')";

                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Account Created Successfully! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                // Assuming Login class exists and should be shown next
                // new Login();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creating account: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            // new Login();
        }
    }

    public static void main(String[] args) {
        new SignUp();
        // Assuming Login class exists for compilation completeness, if needed:
        /*
        class Login extends JFrame {
            Login() {
                System.out.println("Returning to Login screen.");
            }
        }
        */
        // Assuming Conn class exists for compilation completeness, if needed:
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
    }
}
