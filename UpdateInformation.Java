import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class UpdateInformation extends JFrame implements ActionListener {

    JTextField tfaddress, tfstate, tfcity, tfemail, tfphone;
    JButton update, cancel;
    String meter;
    JLabel nameLabel; // Renamed 'name' to 'nameLabel' to avoid conflict with method parameters

    UpdateInformation(String meter) {
        this.meter = meter;
        setTitle("Electricity Billing System - Update Customer Information");
        setSize(850, 480);
        setLocation(350, 200);
        setLayout(null);

        // --- Color Definitions (Consistent with previous screens) ---
        Color primaryBlue = new Color(102, 126, 234);
        Color darkerPurple = new Color(118, 75, 162);
        Color cancelGray = new Color(108, 117, 125);
        Color lightBorder = new Color(200, 200, 200);
        Color standardText = new Color(100, 100, 100);
        Color dataText = new Color(60, 60, 60);
        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        Font dataFont = new Font("Arial", Font.BOLD, 15);

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
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(30, 30, 480, 400); // Adjusted size to fit content
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(lightBorder, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(formPanel);

        // Heading
        JLabel heading = new JLabel("✏️ Update Customer Information");
        heading.setBounds(50, 20, 400, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(primaryBlue);
        formPanel.add(heading);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(30, 60, 420, 1);
        separator.setForeground(lightBorder);
        formPanel.add(separator);

        int labelX = 30;
        int fieldX = 180;
        int yStart = 80;
        int ySpacing = 35;
        int fieldWidth = 250;
        int labelWidth = 120;

        // --- Form Fields ---

        // Name
        JLabel lblname = new JLabel("Customer Name");
        lblname.setBounds(labelX, yStart, labelWidth, 20);
        lblname.setFont(labelFont);
        lblname.setForeground(standardText);
        formPanel.add(lblname);

        nameLabel = new JLabel("");
        nameLabel.setBounds(fieldX, yStart, fieldWidth, 20);
        nameLabel.setFont(dataFont);
        nameLabel.setForeground(dataText);
        formPanel.add(nameLabel);

        // Meter Number
        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(labelX, yStart + ySpacing, labelWidth, 20);
        lblmeternumber.setFont(labelFont);
        lblmeternumber.setForeground(standardText);
        formPanel.add(lblmeternumber);

        JLabel meternumberLabel = new JLabel(this.meter); // Use the passed meter number
        meternumberLabel.setBounds(fieldX, yStart + ySpacing, fieldWidth, 20);
        meternumberLabel.setFont(dataFont);
        meternumberLabel.setForeground(dataText);
        formPanel.add(meternumberLabel);

        // Address
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(labelX, yStart + 2 * ySpacing, labelWidth, 20);
        lbladdress.setFont(labelFont);
        lbladdress.setForeground(standardText);
        formPanel.add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(fieldX, yStart + 2 * ySpacing, fieldWidth, 30);
        tfaddress.setFont(labelFont);
        tfaddress.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfaddress);

        // City
        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(labelX, yStart + 3 * ySpacing, labelWidth, 20);
        lblcity.setFont(labelFont);
        lblcity.setForeground(standardText);
        formPanel.add(lblcity);

        tfcity = new JTextField();
        tfcity.setBounds(fieldX, yStart + 3 * ySpacing, fieldWidth, 30);
        tfcity.setFont(labelFont);
        tfcity.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfcity);

        // State
        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(labelX, yStart + 4 * ySpacing, labelWidth, 20);
        lblstate.setFont(labelFont);
        lblstate.setForeground(standardText);
        formPanel.add(lblstate);

        tfstate = new JTextField();
        tfstate.setBounds(fieldX, yStart + 4 * ySpacing, fieldWidth, 30);
        tfstate.setFont(labelFont);
        tfstate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfstate);

        // Email
        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(labelX, yStart + 5 * ySpacing, labelWidth, 20);
        lblemail.setFont(labelFont);
        lblemail.setForeground(standardText);
        formPanel.add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(fieldX, yStart + 5 * ySpacing, fieldWidth, 30);
        tfemail.setFont(labelFont);
        tfemail.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfemail);

        // Phone
        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(labelX, yStart + 6 * ySpacing, labelWidth, 20);
        lblphone.setFont(labelFont);
        lblphone.setForeground(standardText);
        formPanel.add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(fieldX, yStart + 6 * ySpacing, fieldWidth, 30);
        tfphone.setFont(labelFont);
        tfphone.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(lightBorder, 1), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(tfphone);


        // --- Database Fetch ---
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_number = '" + meter + "'");
            while (rs.next()) {
                nameLabel.setText(rs.getString("name"));
                tfaddress.setText(rs.getString("address"));
                tfcity.setText(rs.getString("city"));
                tfstate.setText(rs.getString("state"));
                tfemail.setText(rs.getString("email"));
                tfphone.setText(rs.getString("phone"));
                // meternumberLabel is already set with 'this.meter'
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customer data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // --- Buttons (Styled) ---
        int buttonY = yStart + 7 * ySpacing + 30;

        update = new JButton("💾 Update Details");
        update.setBounds(50, buttonY, 190, 40);
        update.setBackground(primaryBlue);
        update.setForeground(Color.WHITE);
        update.setFont(new Font("Arial", Font.BOLD, 15));
        update.setFocusPainted(false);
        update.setBorderPainted(false);
        update.setCursor(new Cursor(Cursor.HAND_CURSOR));
        update.addActionListener(this);

        // Update Hover effect
        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                update.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                update.setBackground(primaryBlue);
            }
        });
        formPanel.add(update);

        cancel = new JButton("✕ Cancel");
        cancel.setBounds(260, buttonY, 190, 40);
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

        // --- Image Panel (Right Side) ---
        JPanel imageContainer = new JPanel();
        imageContainer.setBounds(530, 30, 290, 400); // Placed next to the form panel
        imageContainer.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        imageContainer.setLayout(new BorderLayout());
        imageContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(lightBorder, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/update.jpg"));
        Image i2 = i1.getImage().getScaledInstance(270, 380, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3, SwingConstants.CENTER);
        imageContainer.add(image, BorderLayout.CENTER);
        mainPanel.add(imageContainer);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String address = tfaddress.getText();
            String city = tfcity.getText();
            String state = tfstate.getText();
            String email = tfemail.getText();
            String phone = tfphone.getText();

            if (address.trim().isEmpty() || city.trim().isEmpty() || state.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Conn c = new Conn();
                String query = "UPDATE customer SET address = '" + address + "', city = '" + city + "', state = '" + state + "', email = '" + email + "', phone = '" + phone + "' WHERE meter_number = '" + meter + "'";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(this, "User Information Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating information: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateInformation(" "); // Test with a dummy meter number
    }
}
