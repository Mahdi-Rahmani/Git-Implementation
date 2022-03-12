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

    public void userInfo(){

        //The user image
        try {
            image = ImageIO.read(new File("./images/user.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        picLabel = new JLabel(new ImageIcon(image));
        picLabel.setSize(150, 120);
        picLabel.setLocation(20,20);
        Image imageJLFit = image.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(imageJLFit));

        // The user name
        name = new JLabel("Name:"+client.getUsername());
        name.setLocation(20,120);
        name.setSize(150,70);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.PLAIN, 15));

        //the email
        email = new JLabel("Email:"+client.getEmail());
        email.setLocation(20,190);
        email.setSize(150,70);
        email.setForeground(Color.WHITE);
        email.setFont(new Font("Arial", Font.PLAIN, 15));

        //The bio
        bio = new JLabel("Bio:"+client.getBio());
        bio.setLocation(20,260);
        bio.setSize(150,70);
        bio.setForeground(Color.WHITE);
        bio.setFont(new Font("Arial", Font.PLAIN, 15));

        frame.add(picLabel);
        frame.add(name);
        frame.add(email);
        frame.add(bio);

    }
    public void commandLineSettings(){
        commandLine = new JTextArea();
        commandLine.setLocation(50,400);
        commandLine.setSize(900,180);
        commandLine.setFont(new Font("Arial", Font.PLAIN, 12));
        commandLine.setEnabled(true);
        commandLine.setBorder(BorderFactory.createMatteBorder(5,5 , 5, 5,Color.black ));
        frame.add(commandLine);

        commandLine.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pos += e.getLength();
                if(commandLine.getText().charAt(pos) == '\n') {
                    System.out.println(line);
                    String response = client.requestHandler(line) + "\n";
                    monitor.setText(response);
                    line = "";
                }else{
                    line += commandLine.getText().charAt(pos);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pos -= e.getLength();
                int i = 0;
                String newline = "";
                for (char ch : line.toCharArray()) {
                    if (i < line.toCharArray().length-1)
                        newline += ch;
                    i++;
                }
                line = newline;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void monitorSettings()
    {
        monitor = new JTextArea();
        monitor.setLocation(300,20);
        monitor.setSize(600,300);
        Color color=new Color(255,0,0);
        monitor.setForeground(color);
        monitor.setOpaque(true);
        monitor.setFont(new Font("Arial", Font.PLAIN, 16));
        monitor.setText("Welcome to git");
        monitor.setEditable(false);
        //monitor.setEnabled(false);
        monitor.setBorder(BorderFactory.createMatteBorder(5,5 , 5, 5,Color.black ));

        frame.add(monitor);

    }

}
