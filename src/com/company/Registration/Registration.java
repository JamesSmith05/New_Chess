package com.company.Registration;
import com.company.GUI;

import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration<userInfo> extends JDialog {
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField; //password validation input
    private JLabel text;
    private JLabel passwordConfirm; //password validation text
    private JPanel registerPanel;
    private JLabel registerText;
    private JButton registerButton;
    private JButton cancelButton;
    public String password;
    public String username;
    public String email;
    private boolean success;

    JFrame frame = new JFrame();


    public Registration() {
        frame.setTitle("Registration");
        frame.setMinimumSize(new Dimension(450, 470));
        frame.setContentPane(registerPanel);
        frame.setVisible(true);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();

            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelButton) {
                    frame.dispose();

                }
            }
        });

        frame.setVisible(true);
    }

    private void registerUser() {
        username = usernameField.getText();
        email = emailField.getText();
        password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(passwordConfirmField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showConfirmDialog(this,
                    "Please enter all fields",
                    "Try gain",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showConfirmDialog(this,
                    "Password doesn't match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        addUserToDatabase(username, email, password);

        if (success == true) {
            System.out.println("it works");
            frame.dispose();
            GUI.GUI();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public userInfo user;

    private void addUserToDatabase(String username, String email, String password)  {
//        userInfo user = null;
        success = true;

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://loginSystem.accdb");

            Statement st = con.createStatement();

            String template = "INSERT INTO Login(email,password,username) values (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, username);
            stmt.executeUpdate();


        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
    }
}