import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, cancel, signUp;
    JTextField username;
    JPasswordField password;
    Choice logginin;

    Login() {
        super("Electricity Billing System - Login");

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

        // Left side - Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(255, 255, 255, 200));
        imagePanel.setBounds(30, 30, 280, 340);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        imagePanel.setLayout(new BorderLayout());

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image i8 = i7.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i8), SwingConstants.CENTER);
        imagePanel.add(image, BorderLayout.CENTER);

        JLabel welcomeText = new JLabel("Welcome Back!", SwingConstants.CENTER);
        welcomeText.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeText.setForeground(new Color(102, 126, 234));
        imagePanel.add(welcomeText, BorderLayout.SOUTH);

        mainPanel.add(imagePanel);

        // Right side - Login form panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(340, 30, 350, 340);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel("⚡ Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(102, 126, 234));
        titleLabel.setBounds(0, 20, 350, 40);
        loginPanel.add(titleLabel);

        // Username
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(30, 80, 100, 25);
        lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
        loginPanel.add(lblusername);

        username = new JTextField();
        username.setBounds(30, 105, 290, 35);
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginPanel.add(username);

        // Password
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(30, 150, 100, 25);
        lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
        loginPanel.add(lblpassword);

        password = new JPasswordField();
        password.setBounds(30, 175, 290, 35);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginPanel.add(password);

        // Login as
        JLabel loggininas = new JLabel("Login as");
        loggininas.setBounds(30, 220, 100, 25);
        loggininas.setFont(new Font("Arial", Font.PLAIN, 14));
        loginPanel.add(loggininas);

        logginin = new Choice();
        logginin.add("Admin");
        logginin.add("Customer");
        logginin.setBounds(30, 245, 290, 30);
        logginin.setFont(new Font("Arial", Font.PLAIN, 14));
        logginin.setBackground(Color.WHITE);
        logginin.setForeground(Color.BLACK);
        loginPanel.add(logginin);

        // Login button
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i2 = i1.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        login = new JButton("Login", new ImageIcon(i2));
        login.setBounds(30, 290, 135, 40);
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.setBackground(new Color(102, 126, 234));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setBorderPainted(false);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.addActionListener(this);

        // Hover effect
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                login.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                login.setBackground(new Color(102, 126, 234));
            }
        });
        loginPanel.add(login);

        // Cancel button
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/cancel.jpg"));
        Image i4 = i3.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        cancel = new JButton("Cancel", new ImageIcon(i4));
        cancel.setBounds(185, 290, 135, 40);
        cancel.setFont(new Font("Arial", Font.BOLD, 14));
        cancel.setBackground(new Color(220, 53, 69));
        cancel.setForeground(Color.WHITE);
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(this);

        // Hover effect
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancel.setBackground(new Color(200, 35, 51));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancel.setBackground(new Color(220, 53, 69));
            }
        });
        loginPanel.add(cancel);

        // SignUp button (link style)
        ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icon/signup.png"));
        Image i6 = i5.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        signUp = new JButton("Don't have an account? Sign Up", new ImageIcon(i6));
        signUp.setBounds(50, 340, 250, 30);
        signUp.setFont(new Font("Arial", Font.PLAIN, 12));
        signUp.setForeground(new Color(102, 126, 234));
        signUp.setBackground(Color.WHITE);
        signUp.setFocusPainted(false);
        signUp.setBorderPainted(false);
        signUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUp.setHorizontalAlignment(SwingConstants.CENTER);
        signUp.addActionListener(this);

        // Hover effect
        signUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUp.setForeground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUp.setForeground(new Color(102, 126, 234));
            }
        });
        loginPanel.add(signUp);

        mainPanel.add(loginPanel);

        setSize(750, 450);
        setLocation(350, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String susername = username.getText();
            String spassword = new String(password.getPassword());
            String user = logginin.getSelectedItem();

            try {
                Conn c = new Conn();
                String query = "select * from login where username = '" + susername + "' and password = '" + spassword + "' and user = '" + user + "'";

                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    String meter = rs.getString("meter_number");
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new Project(user, meter);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        } else if (ae.getSource() == signUp) {
            setVisible(false);
            new SignUp();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
