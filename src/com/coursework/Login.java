package com.coursework;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.coursework.User;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    public boolean loginDone = false;
    private List<User> userList;

    public Login() {
        setTitle("Login App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the user list
        userList = new ArrayList<>();

        JPanel mainPanel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        add(mainPanel);
    }

    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // Check if user exists in the userList
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(new String(password))) {
                JOptionPane.showMessageDialog(this, "Login Successful for user: " + username);
                // Close the current Login window
                boolean loginDone = true;
                this.dispose();

                return;
            }
        }

        // User not found
        JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
    }

    private void register() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // Check if the username already exists
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(new String(password))) {
                JOptionPane.showMessageDialog(this, "Login Successful for user: " + username);

                // Close the current Login window
                this.dispose();

                return;
            }
        }

        // Create a new User and add to the userList
        User newUser = new User(username, new String(password));
        userList.add(newUser);

        JOptionPane.showMessageDialog(this, "Registration Successful for user: " + username);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
