package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * this is login class
 * it is the GUI of login page
 *
 * @author mahdi rahmani
 * @since 2021/5/28
 */
public class Login {
    // the Frame
    private JFrame frame;
    // The panel that we use
    private JPanel mainPanel;
    // A JTextField for getting the user name
    private JTextField userNameField;
    // A JPasswordField for getting the password of user
    private JPasswordField passwordField;
    // login image
    private BufferedImage image;
    // the login button
    private JButton loginButton;
    // the register button
    private JButton registerButton;

    public Login()
    {
        frameSettings();
    }

    public void frameSettings()
    {
        frame = new JFrame("Login");
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(new Color(40, 41, 37));
        //Icon
        ImageIcon icon = new ImageIcon("./images/git2.png");
        frame.setIconImage(icon.getImage());
        frame.add(frameObjects());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JPanel frameObjects()
    {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.GRAY);


        //The login image
        try {
            image = ImageIO.read(new File("./images/git4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(image));
        picLabel.setSize(250, 160);

        Image imageJLFit = image.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(imageJLFit));

        //user name
        JLabel userNameLabel = new JLabel(" Username : ");
        userNameField = new JTextField("Username");
        userNameLabel.setForeground(Color.BLACK);
        userNameLabel.setBackground(mainPanel.getBackground());
        userNameField.setBackground(mainPanel.getBackground());
        userNameField.setForeground(Color.BLACK);
        userNameLabel.setOpaque(true);
        userNameField.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, mainPanel.getBackground()));
        userNameField.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, mainPanel.getBackground()));
        userNameField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.black));

        //password
        JLabel passWordLabel = new JLabel(" Password : ");
        passWordLabel.setForeground(Color.BLACK);
        passwordField = new JPasswordField();
        passWordLabel.setBackground(mainPanel.getBackground());
        passwordField.setBackground(mainPanel.getBackground());
        passwordField.setForeground(Color.BLACK);
        passWordLabel.setOpaque(true);
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, mainPanel.getBackground()));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, mainPanel.getBackground()));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.black));

        //***THE LOGIN BUTTON***
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        //the dimension of warning label
        int buttonWidth = loginButton.getPreferredSize().width +20;
        int buttonHeight = loginButton.getPreferredSize().height + 10;
        loginButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        //***The Register Button***
        registerButton = new JButton("Register");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        // the button handler
        ButtonHandler handler = new ButtonHandler();
        loginButton.addActionListener(handler);
        registerButton.addActionListener(handler);
        // the panels
        // the constraints that used for grid bag lay out
        GridBagConstraints constraints = new  GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        //A panel with grid layout for place the labels and fields of the UserName and password in it.
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 0, 0));
        fieldsPanel.add(userNameLabel);
        fieldsPanel.add(userNameField);
        fieldsPanel.add(passWordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.setBackground(mainPanel.getBackground());

        //A panel for keeping the button in middle of that
        JPanel button = new JPanel(new GridLayout(1,3));
        JPanel loginButPan = new JPanel(new GridBagLayout());
        loginButPan.add(loginButton,constraints);
        loginButPan.setBackground(mainPanel.getBackground());
        button.setBackground(mainPanel.getBackground());

        JPanel registerButPan = new JPanel(new GridBagLayout());
        registerButPan.add(registerButton,constraints);
        registerButPan.setBackground(mainPanel.getBackground());

        //create empty label
        JLabel empty = new JLabel();
        empty.setBackground(mainPanel.getBackground());
        empty.setOpaque(true);

        button.add(loginButPan);
        button.add(empty);
        button.add(registerButPan);


        //A panel for keeping the pic and fieldsPanel and button inside it
        JPanel loginPanel = new JPanel(new GridLayout(3,1));
        loginPanel.add(picLabel);
        loginPanel.add(fieldsPanel);
        loginPanel.add(button);
        loginPanel.setBackground(mainPanel.getBackground());
        //A main panel for keep all of this in the middle of that
        mainPanel.add(loginPanel,constraints);

        return mainPanel;
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = new Client(userNameField.getText(),passwordField.getText());
            String status;
            if (e.getSource().equals(loginButton)) {
                System.out.println("login Button");
                status = client.login();
                System.out.println(status);

                if (status.equals("true"))
                {
                    JOptionPane.showMessageDialog(null, "login successfully", "Result", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    MainPage mainPage = new MainPage(client);

                }
                else
                    JOptionPane.showMessageDialog(null, "Invalid Username or password", "Result", JOptionPane.ERROR_MESSAGE);


            } else if (e.getSource().equals(registerButton)){
                System.out.println("Register Button");
                String[] email_bio = email_bio_getter();
                client.setEmail(email_bio[0]);
                client.setBio(email_bio[1]);
                System.out.println(email_bio[1]);

                status = client.register();
                System.out.println(status);
                if (status.equals("true"))
                {
                    JOptionPane.showMessageDialog(null, "register successfully", "Result", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    MainPage mainPage = new MainPage(client);
                }
                else
                    JOptionPane.showMessageDialog(null, "This username is registered before ", "Result", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String[] email_bio_getter()
    {
        String[] answer = new String[2];
        answer[0] = "";
        answer[1] = "";
        JTextField emailField = new JTextField(5);
        JTextField bioField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("email:"));
        myPanel.add(emailField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Bio:"));
        myPanel.add(bioField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter your email and bio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            answer[0] = emailField.getText();
            answer[1] = bioField.getText();
        }
        return answer;

    }
}
