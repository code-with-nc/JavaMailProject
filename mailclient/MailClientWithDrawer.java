package mailclient;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class MailClientWithDrawer extends JFrame {

    private JTextField tfFrom, tfTo, tfSubject, tfAppPass;
    private JTextArea taBody;
    private JLabel lblStatus;
    private JPanel mainPanel;

    public MailClientWithDrawer() {
        super("JavaMail - Modern UI with Drawer");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);
        splitPane.setDividerSize(2);

        JPanel drawer = new JPanel();
        drawer.setBackground(new Color(30, 40, 60));
        drawer.setLayout(new BoxLayout(drawer, BoxLayout.Y_AXIS));
        drawer.setBorder(new EmptyBorder(20, 10, 20, 10));

        String[] menuItems = {"Compose Email", "Inbox", "Sent Emails", "Drafts", "Settings"};

        for (String item : menuItems) {
            JLabel lbl = new JLabel(item);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            lbl.setBorder(new EmptyBorder(10, 5, 10, 5));

            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { lbl.setForeground(Color.YELLOW); }
                @Override
                public void mouseExited(MouseEvent e) { lbl.setForeground(Color.WHITE); }
                @Override
                public void mouseClicked(MouseEvent e) { switchMenu(item); }
            });

            drawer.add(lbl);
            drawer.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(58, 123, 213),
                        0, getHeight(), new Color(58, 213, 178));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(buildComposePanel());

        splitPane.setLeftComponent(drawer);
        splitPane.setRightComponent(mainPanel);
        splitPane.setResizeWeight(0);

        add(splitPane);
        setVisible(true);
    }

    private void switchMenu(String menu) {
        mainPanel.removeAll();
        switch (menu) {
            case "Compose Email" -> mainPanel.add(buildComposePanel());
            case "Inbox" -> mainPanel.add(buildInboxPanel());
            case "Sent Emails" -> mainPanel.add(buildSentEmailsPanel());
            case "Drafts" -> mainPanel.add(buildDraftsPanel());
            case "Settings" -> mainPanel.add(buildSettingsPanel());
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // --- Compose Panel ---
    private JPanel buildComposePanel() {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 550));
        card.setBackground(new Color(255, 255, 255, 230));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Compose Email", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setOpaque(false);

        form.add(simpleLabel("Your Gmail:")); tfFrom = roundedField(); form.add(tfFrom);
        form.add(simpleLabel("App Password:")); tfAppPass = roundedField(); form.add(tfAppPass);
        form.add(simpleLabel("Recipient:")); tfTo = roundedField(); form.add(tfTo);
        form.add(simpleLabel("Subject:")); tfSubject = roundedField(); form.add(tfSubject);

        card.add(form);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        taBody = new JTextArea(8, 40);
        taBody.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taBody.setLineWrap(true);
        taBody.setWrapStyleWord(true);
        taBody.setBorder(BorderFactory.createTitledBorder("Mail Body"));
        card.add(new JScrollPane(taBody));
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        JButton btnSend = roundedButton("Send Email", new Color(40, 167, 69));
        JButton btnClear = roundedButton("Clear", new Color(220, 53, 69));
        btnSend.addActionListener(e -> sendMailThread());
        btnClear.addActionListener(e -> clearForm());
        btnPanel.add(btnSend);
        btnPanel.add(btnClear);
        card.add(btnPanel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        lblStatus = new JLabel("Ready", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatus.setForeground(Color.BLUE);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblStatus);

        return card;
    }

    // --- Inbox Panel ---
    private JPanel buildInboxPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Inbox", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"From", "Subject", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Refresh Inbox");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.addActionListener(e -> loadInboxEmails(model));
        panel.add(btnRefresh, BorderLayout.SOUTH);

        return panel;
    }

    // --- Sent Emails Panel ---
    private JPanel buildSentEmailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Sent Emails", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"To", "Subject", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Refresh Sent");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.addActionListener(e -> loadSentEmails(model));
        panel.add(btnRefresh, BorderLayout.SOUTH);

        return panel;
    }

    // --- Drafts & Settings Panels ---
    private JPanel buildDraftsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Drafts", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"To", "Subject", "Last Edited"};
        String[][] data = {{"boss@company.com", "Quarterly Report", "2025-11-28"}};

        JTable table = new JTable(data, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Settings", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(simpleLabel("Default Sender Email:"));
        JTextField defaultEmail = roundedField();
        panel.add(defaultEmail);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(simpleLabel("Email Signature:"));
        JTextArea signature = new JTextArea(4, 30);
        signature.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        signature.setLineWrap(true);
        signature.setWrapStyleWord(true);
        panel.add(new JScrollPane(signature));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton saveBtn = roundedButton("Save Settings", new Color(40, 167, 69));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(panel, "Settings saved!"));
        panel.add(saveBtn);

        return panel;
    }

    // --- Utilities ---
    private JTextField roundedField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120,120,120),1,true),
                new EmptyBorder(8,10,8,10)
        ));
        return tf;
    }

    private JLabel simpleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return lbl;
    }

    private JButton roundedButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setForeground(Color.YELLOW); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setForeground(Color.WHITE); }
            @Override
            public void mousePressed(MouseEvent e) { btn.setForeground(Color.BLACK); }
        });
        return btn;
    }

    private void clearForm() {
        tfFrom.setText(""); tfAppPass.setText(""); tfTo.setText(""); tfSubject.setText(""); taBody.setText("");
        lblStatus.setText("Cleared"); lblStatus.setForeground(Color.BLUE);
    }

    // --- Send Email ---
    private void sendMailThread() {
        String from = tfFrom.getText().trim();
        String appPass = tfAppPass.getText().trim();
        String to = tfTo.getText().trim();
        String subject = tfSubject.getText().trim();
        String body = taBody.getText();

        if(from.isEmpty() || to.isEmpty() || appPass.isEmpty()) {
            lblStatus.setText("Fill all required fields!");
            lblStatus.setForeground(Color.RED);
            return;
        }

        lblStatus.setText("Sending...");
        lblStatus.setForeground(Color.ORANGE);

        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, appPass);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO sent (recipient, subject, body) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, to);
                        ps.setString(2, subject);
                        ps.setString(3, body);
                        ps.executeUpdate();
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Email Sent and Saved to DB!");
                    lblStatus.setForeground(new Color(0, 150, 0));
                    clearForm();
                });

            } catch (MessagingException | SQLException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Failed to send email!");
                    lblStatus.setForeground(Color.RED);
                });
            }
        }).start();
    }

    // --- Load Sent Emails ---
    private void loadSentEmails(DefaultTableModel model) {
        model.setRowCount(0);
        new Thread(() -> {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT recipient, subject, sent_at FROM sent ORDER BY id DESC";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    var rs = ps.executeQuery();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    while (rs.next()) {
                        String to = rs.getString("recipient");
                        String subject = rs.getString("subject");
                        String date = sdf.format(rs.getTimestamp("sent_at"));
                        SwingUtilities.invokeLater(() -> model.addRow(new Object[]{to, subject, date}));
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> lblStatus.setText("Failed to load sent emails!"));
            }
        }).start();
    }

    // --- Load Inbox Emails ---
    private void loadInboxEmails(DefaultTableModel model) {
        String username = tfFrom.getText().trim();
        String appPassword = tfAppPass.getText().trim();

        if (appPassword.isEmpty() || username.isEmpty()) {
            lblStatus.setText("Enter Gmail & App Password to fetch inbox!");
            lblStatus.setForeground(Color.RED);
            return;
        }

        model.setRowCount(0);

        new Thread(() -> {
            try {
                Properties props = new Properties();
              props.put("mail.store.protocol", "imap");
props.put("mail.imap.host", "imap.gmail.com");
props.put("mail.imap.port", "993");
props.put("mail.imap.ssl.enable", "true");
props.put("mail.imap.ssl.trust", "imap.gmail.com"); // <-- trust Gmail

                Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, appPassword);
                    }
                });

                Store store = session.getStore("imap");
                store.connect();

                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);

                int messageCount = inbox.getMessageCount();
                int start = Math.max(1, messageCount - 19); // last 20 messages
                Message[] messages = inbox.getMessages(start, messageCount);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                for (Message msg : messages) {
                    String from = msg.getFrom()[0].toString();
                    String subject = msg.getSubject();
                    String date = sdf.format(msg.getReceivedDate());
                    SwingUtilities.invokeLater(() -> model.addRow(new Object[]{from, subject, date}));
                }

                inbox.close(false);
                store.close();

                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Inbox Loaded Successfully!");
                    lblStatus.setForeground(new Color(0, 150, 0));
                });

            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Failed to load inbox! Check App Password & IMAP settings.");
                    lblStatus.setForeground(Color.RED);
                });
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MailClientWithDrawer::new);
    }
}
