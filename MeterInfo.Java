import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; // Assuming Conn class uses java.sql

class MeterInfo extends JFrame implements ActionListener {
    JButton submit;
    Choice meterlocation, metertype, phasecode, billtype;
    String meternumber;

    MeterInfo(String meternumber) {
        this.meternumber = meternumber;
        setTitle("Electricity Billing System - Meter Information");
        setSize(850, 550);
        setLocation(350, 180);
        setLayout(null);

        // --- Color Definitions (Consistent with previous screens) ---
        Color primaryBlue = new Color(102, 126, 234);
        Color darkerPurple = new Color(118, 75, 162);
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
        JLabel heading = new JLabel("🔌 Configure Meter Info");
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
        int fieldX = 240;
        int yStart = 100;
        int ySpacing = 40;
        int fieldWidth = 280;
        int labelWidth = 150;
        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        Font dataFont = new Font("Arial", Font.BOLD, 15);

        // --- Form Elements ---

        // Meter Number
        JLabel lblname = new JLabel("Meter Number");
        lblname.setBounds(labelX, yStart, labelWidth, 25);
        lblname.setFont(labelFont);
        lblname.setForeground(standardText);
        formPanel.add(lblname);

        JLabel lblmeternumber = new JLabel(meternumber);
        lblmeternumber.setBounds(fieldX, yStart, fieldWidth, 25);
        lblmeternumber.setFont(dataFont);
        lblmeternumber.setForeground(dataText);
        formPanel.add(lblmeternumber);

        // Meter Location
        JLabel lblmeterno = new JLabel("Meter Location");
        lblmeterno.setBounds(labelX, yStart + ySpacing, labelWidth, 25);
        lblmeterno.setFont(labelFont);
        lblmeterno.setForeground(standardText);
        formPanel.add(lblmeterno);

        meterlocation = new Choice();
        meterlocation.add("Outside");
        meterlocation.add("Inside");
        meterlocation.setBounds(fieldX, yStart + ySpacing, fieldWidth, 25);
        meterlocation.setFont(labelFont);
        meterlocation.setBackground(Color.WHITE);
        formPanel.add(meterlocation);

        // Meter Type
        JLabel lbladdress = new JLabel("Meter Type");
        lbladdress.setBounds(labelX, yStart + 2 * ySpacing, labelWidth, 25);
        lbladdress.setFont(labelFont);
        lbladdress.setForeground(standardText);
        formPanel.add(lbladdress);

        metertype = new Choice();
        metertype.add("Electric Meter");
        metertype.add("Solar Meter");
        metertype.add("Smart Meter");
        metertype.setBounds(fieldX, yStart + 2 * ySpacing, fieldWidth, 25);
        metertype.setFont(labelFont);
        metertype.setBackground(Color.WHITE);
        formPanel.add(metertype);

        // Phase Code
        JLabel lblcity = new JLabel("Phase Code");
        lblcity.setBounds(labelX, yStart + 3 * ySpacing, labelWidth, 25);
        lblcity.setFont(labelFont);
        lblcity.setForeground(standardText);
        formPanel.add(lblcity);

        phasecode = new Choice();
        phasecode.add("011 (Single Phase)");
        phasecode.add("022");
        phasecode.add("033 (Three Phase)");
        phasecode.add("044");
        phasecode.add("055");
        phasecode.add("066");
        phasecode.add("077");
        phasecode.add("088");
        phasecode.add("099");
        phasecode.setBounds(fieldX, yStart + 3 * ySpacing, fieldWidth, 25);
        phasecode.setFont(labelFont);
        phasecode.setBackground(Color.WHITE);
        formPanel.add(phasecode);


        // Bill Type
        JLabel lblstate = new JLabel("Bill Type");
        lblstate.setBounds(labelX, yStart + 4 * ySpacing, labelWidth, 25);
        lblstate.setFont(labelFont);
        lblstate.setForeground(standardText);
        formPanel.add(lblstate);

        billtype = new Choice();
        billtype.add("Normal");
        billtype.add("Industrial");
        billtype.setBounds(fieldX, yStart + 4 * ySpacing, fieldWidth, 25);
        billtype.setFont(labelFont);
        billtype.setBackground(Color.WHITE);
        formPanel.add(billtype);

        // Days
        JLabel lblemail = new JLabel("Billing Cycle");
        lblemail.setBounds(labelX, yStart + 5 * ySpacing, labelWidth, 25);
        lblemail.setFont(labelFont);
        lblemail.setForeground(standardText);
        formPanel.add(lblemail);

        JLabel lblemails = new JLabel("30 Days (Fixed)");
        lblemails.setBounds(fieldX, yStart + 5 * ySpacing, fieldWidth, 25);
        lblemails.setFont(dataFont);
        lblemails.setForeground(new Color(255, 100, 100)); // Highlight important info
        formPanel.add(lblemails);

        // Note
        JLabel lblphone = new JLabel("Note");
        lblphone.setBounds(labelX, yStart + 6 * ySpacing, labelWidth, 25);
        lblphone.setFont(labelFont);
        lblphone.setForeground(standardText);
        formPanel.add(lblphone);

        JLabel lblphones = new JLabel("Bill is calculated for 30 days only.");
        lblphones.setBounds(fieldX, yStart + 6 * ySpacing, fieldWidth + 100, 25);
        lblphones.setFont(new Font("Arial", Font.ITALIC, 14));
        lblphones.setForeground(new Color(150, 150, 150));
        formPanel.add(lblphones);

        // --- Button (Styled) ---
        int buttonY = yStart + 7 * ySpacing + 20;

        submit = new JButton("✔ Submit Meter Info");
        submit.setBounds(170, buttonY, 230, 40);
        submit.setBackground(primaryBlue);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 15));
        submit.setFocusPainted(false);
        submit.setBorderPainted(false);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submit.addActionListener(this);

        // Submit Hover effect
        submit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submit.setBackground(new Color(85, 104, 211));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submit.setBackground(primaryBlue);
            }
        });
        formPanel.add(submit);

        mainPanel.add(formPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String meter = meternumber;
            String location = meterlocation.getSelectedItem();
            String type = metertype.getSelectedItem();
            // Clean up the phase code string (e.g., "011 (Single Phase)" -> "011")
            String code = phasecode.getSelectedItem().split(" ")[0];
            String typebill = billtype.getSelectedItem();
            String days = "30";

            String query = "INSERT INTO meter_info VALUES('" + meter + "', '" + location + "','" + type + "','" + code + "','" + typebill + "','" + days + "')";

            try {
                // Assuming Conn class is available and works correctly
                Conn c = new Conn();
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(this, "Meter Information Added Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving meter information: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Note: The original code only handles 'next' (now 'submit') and defaults to setVisible(false) for all other actions.
    }

    public static void main(String args[]) {
        new MeterInfo("123456"); // Test with a dummy meter number
    }
    // Dummy Conn Class/Interface reference is needed for compilation.
    // Ensure your project has the correct Conn class.
}
