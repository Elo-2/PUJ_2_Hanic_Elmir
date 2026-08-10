package lifemanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterForm extends JFrame {

    private lifemanagement.UserManager userManager;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;
    private JTextField registerEmailField;
    private JTextField registerFirstNameField;
    private JTextField registerLastNameField;

    private lifemanagement.User currentUser;

    public LoginRegisterForm() {
        userManager = new lifemanagement.UserManager();
        currentUser = null;

        setTitle("Life Management System - Login/Registracija");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Login panel
        JPanel loginPanel = createLoginPanel();
        cardPanel.add(loginPanel, "login");

        // Register panel
        JPanel registerPanel = createRegisterPanel();
        cardPanel.add(registerPanel, "register");

        add(cardPanel);
        cardLayout.show(cardPanel, "login");

        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Naslov
        JLabel titleLabel = new JLabel("PRIJAVA", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(100, 200, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Username
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel usernameLabel = new JLabel("Korisničko ime:");
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        loginUsernameField = new JTextField(15);
        loginUsernameField.setBackground(new Color(60, 60, 60));
        loginUsernameField.setForeground(Color.WHITE);
        loginUsernameField.setCaretColor(Color.WHITE);
        panel.add(loginUsernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Lozinka:");
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(15);
        loginPasswordField.setBackground(new Color(60, 60, 60));
        loginPasswordField.setForeground(Color.WHITE);
        loginPasswordField.setCaretColor(Color.WHITE);
        panel.add(loginPasswordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Prijavi se");
        loginButton.setBackground(new Color(0, 150, 136));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        panel.add(loginButton, gbc);

        // Register button
        gbc.gridy = 4;
        JButton registerButton = new JButton("Kreiraj novi nalog");
        registerButton.setBackground(new Color(76, 175, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "register");
                clearLoginFields();
            }
        });
        panel.add(registerButton, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Naslov
        JLabel titleLabel = new JLabel("REGISTRACIJA", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(100, 200, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Ime
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel firstNameLabel = new JLabel("Ime:");
        firstNameLabel.setForeground(Color.WHITE);
        panel.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        registerFirstNameField = new JTextField(15);
        registerFirstNameField.setBackground(new Color(60, 60, 60));
        registerFirstNameField.setForeground(Color.WHITE);
        registerFirstNameField.setCaretColor(Color.WHITE);
        panel.add(registerFirstNameField, gbc);

        // Prezime
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lastNameLabel = new JLabel("Prezime:");
        lastNameLabel.setForeground(Color.WHITE);
        panel.add(lastNameLabel, gbc);

        gbc.gridx = 1;
        registerLastNameField = new JTextField(15);
        registerLastNameField.setBackground(new Color(60, 60, 60));
        registerLastNameField.setForeground(Color.WHITE);
        registerLastNameField.setCaretColor(Color.WHITE);
        panel.add(registerLastNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        registerEmailField = new JTextField(15);
        registerEmailField.setBackground(new Color(60, 60, 60));
        registerEmailField.setForeground(Color.WHITE);
        registerEmailField.setCaretColor(Color.WHITE);
        panel.add(registerEmailField, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel usernameLabel = new JLabel("Korisničko ime:");
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        registerUsernameField = new JTextField(15);
        registerUsernameField.setBackground(new Color(60, 60, 60));
        registerUsernameField.setForeground(Color.WHITE);
        registerUsernameField.setCaretColor(Color.WHITE);
        panel.add(registerUsernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel passwordLabel = new JLabel("Lozinka:");
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        registerPasswordField = new JPasswordField(15);
        registerPasswordField.setBackground(new Color(60, 60, 60));
        registerPasswordField.setForeground(Color.WHITE);
        registerPasswordField.setCaretColor(Color.WHITE);
        panel.add(registerPasswordField, gbc);

        // Potvrdi lozinku
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel confirmPasswordLabel = new JLabel("Potvrdi lozinku:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        panel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        registerConfirmPasswordField = new JPasswordField(15);
        registerConfirmPasswordField.setBackground(new Color(60, 60, 60));
        registerConfirmPasswordField.setForeground(Color.WHITE);
        registerConfirmPasswordField.setCaretColor(Color.WHITE);
        panel.add(registerConfirmPasswordField, gbc);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JButton createAccountButton = new JButton("Kreiraj nalog");
        createAccountButton.setBackground(new Color(76, 175, 80));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 12));
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        panel.add(createAccountButton, gbc);

        // Back button
        gbc.gridy = 8;
        JButton backButton = new JButton("Nazad");
        backButton.setBackground(new Color(150, 150, 150));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "login");
                clearRegisterFields();
            }
        });
        panel.add(backButton, gbc);

        return panel;
    }

    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Popunite sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lifemanagement.User user = userManager.loginUser(username, password);
        if (user != null) {
            currentUser = user;
            JOptionPane.showMessageDialog(this, "Dobrodošli, " + user.getFirstName() + "!", "Uspješna prijava", JOptionPane.INFORMATION_MESSAGE);
            clearLoginFields();
            openMainMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Neispravni kredencijali!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        String firstName = registerFirstNameField.getText();
        String lastName = registerLastNameField.getText();
        String email = registerEmailField.getText();
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String confirmPassword = new String(registerConfirmPasswordField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Popunite sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Lozinke se ne poklapaju!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lifemanagement.User newUser = new lifemanagement.User(username, password, email, firstName, lastName);
        if (userManager.registerUser(newUser)) {
            JOptionPane.showMessageDialog(this, "Nalog je uspješno kreiran! Sada se prijavite.", "Uspješna registracija", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "login");
            clearRegisterFields();
        } else {
            JOptionPane.showMessageDialog(this, "Korisničko ime već postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearLoginFields() {
        loginUsernameField.setText("");
        loginPasswordField.setText("");
    }

    private void clearRegisterFields() {
        registerFirstNameField.setText("");
        registerLastNameField.setText("");
        registerEmailField.setText("");
        registerUsernameField.setText("");
        registerPasswordField.setText("");
        registerConfirmPasswordField.setText("");
    }

    private void openMainMenu() {
        new MainMenuForm(currentUser);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegisterForm();
            }
        });
    }
}