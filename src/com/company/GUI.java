package com.company;

import com.company.Registration.Registration;
import com.company.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;


public class GUI implements ActionListener {
    private static JLabel userLabel, success, passwordLabel;
    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JButton loginButton, registerButton;
    private static JFrame GUIframe = new JFrame();


    public static void GUI(){
        GUIframe.setSize(350, 200);
        GUIframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        GUIframe.add(panel);

        panel.setLayout(null);
        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);


        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new GUI());
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 100, 25);
        registerButton.addActionListener(new GUI());
        panel.add(registerButton);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);


        GUIframe.setVisible(true);

    }

    public boolean authenticateUser(String username, String password){
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://loginSystem.accdb");

            String query = "SELECT Username, Password from Login";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                if (Objects.equals(username, rs.getString("Username")) && Objects.equals(password, rs.getString("Password"))){
                    return true;
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();

        if (e.getSource() == registerButton) {
            System.out.println("Button registering");
            //frame.dispose();
            Registration myRegister = new Registration();

        }
        if (e.getSource() == loginButton) {

            if (authenticateUser(user,password)){
                success.setForeground(Color.green);
                success.setText("login successful");
                GUIframe.dispose();
                Board.successfulLogin = true;

            }else {
                success.setForeground(Color.red);
                success.setText("login unsuccessful");
            }

        }
        else if (e.getSource() == registerButton) {
            success.setText(null);
            System.out.println("button 2 registering");

        } else {
            System.out.println("failing");
            success.setForeground(Color.red);
            success.setText("login unsuccessful");
        }


    }
}

