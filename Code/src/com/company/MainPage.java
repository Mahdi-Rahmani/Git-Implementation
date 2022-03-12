package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This is the gui of main page
 * it contains the user info and command line and a monitor for showing the result
 *
 *  @author Mahdi Rahmani
 *  @since 2021/5/28
 */
public class MainPage {
    // the Frame
    private JFrame frame;
    // The panel that we use
    private JPanel mainPanel;
    // the menu bar
    private MenuBar menuBar;

    // the login button
    private JButton runCommand;
    // the client that use this page


    private Client client;
    // The user image
    private BufferedImage image;
    // The user image label
    private JLabel picLabel;
    // The name label
    private JLabel name;
    // The email label
    private JLabel email;
    // The bio
    private JLabel bio;

    // The command line that user can use it
    private JTextArea commandLine;
    private int pos ;
    private String line;

    // The monitor
    private JTextArea monitor;
    private String show;

    public MainPage(Client client)
    {
        this.client = client;
        pos = -1 ;
        line = "";
        frameSettings();

    }
    public void frameSettings()
    {
        frame = new JFrame("Git");
        frame.setSize(1000, 700);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(new Color(40, 41, 37));
        frame.setLayout(null);
        //Icon
        ImageIcon icon = new ImageIcon("./images/git2.png");
        frame.setIconImage(icon.getImage());
        menuBar = new MenuBar(frame);
        userInfo();
        commandLineSettings();
        monitorSettings();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);


    }

}
