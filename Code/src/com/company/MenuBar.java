package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * This is menu bar class
 * I design the menuBar of his class here
 *
 * @author Mahdi Rahmani
 * @since 2021/5/28
 */
public class MenuBar extends JMenuBar {
    // The frame of programme
    private JFrame frame;
    //frame icon image
    private final ImageIcon icon = new ImageIcon("./images/portal.png");
    //editMenu
    private JMenu optionsMenu;
    //viewMenu
    private JMenu viewMenu;
    //windowMenu
    private JMenu exitMenu;
    //helpMenu
    private JMenu helpMenu;
    /**
     * Create a new MenuBar.
     *
     * @param frame frame.
     */
    public MenuBar(JFrame frame) {
        this.frame = frame;
        Font font = new Font("Times New Roman (Headings CS)", Font.PLAIN, 12);
        exitMenu = new JMenu("Exit");
        exitMenu.setFont(font);
        viewMenu = new JMenu("View");
        viewMenu.setFont(font);
        optionsMenu = new JMenu("Window");
        optionsMenu.setFont(font);
        helpMenu = new JMenu("Help");
        helpMenu.setFont(font);

        makeExitMenu();
        makeOptionsMenu();
        makeViewMenu();
        makeHelpMenu();

        add(exitMenu);
        add(viewMenu);
        //add(optionsMenu);
        add(helpMenu);
    }

    /**
     * Make the exitMenu for programme
     */
    private void makeExitMenu()
    {
        JMenuItem quit = new JMenuItem("Sign out");
        quit.setFont(new Font("Arial", Font.PLAIN, 12));
        quit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Do you want to Exit?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                //System.exit(0);
                frame.dispose();
                Login login = new Login();
            }
        });


        exitMenu.add(quit);
    }

    /**
     * the option menu
     */
    private void makeOptionsMenu()
    {
        JMenuItem pssWordChange = new JMenuItem("Changing password");
        JMenuItem userChange = new JMenuItem("Changing userName");

        pssWordChange.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK));
        userChange.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
    }

    /**
     * The view menu operation
     * you can toggle full screen or normal screen
     */
    private void makeViewMenu()
    {
        JMenuItem toggleFull = new JMenuItem("Toggle Full Screen");
        toggleFull.setFont(new Font("Arial", Font.PLAIN, 12));
        toggleFull.addActionListener(e -> {
            if (toggleFull.getText().equals("Toggle Full Screen")) {
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                toggleFull.setText("Toggle Normal Screen");
            }
            else {
                frame.setExtendedState(Frame.NORMAL);
                toggleFull.setText("Toggle Full Screen");
            }
        });
        toggleFull.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));


        JMenuItem toggleSidebar = new JMenuItem("Toggle Sidebar");
        toggleSidebar.setFont(new Font("Arial", Font.PLAIN, 12));
        toggleSidebar.addActionListener(e -> {
            if (toggleSidebar.getText().equals("Toggle Sidebar")) {
                toggleSidebar.setText("Toggle Normal");
                frame.setLocation(0,0);
                frame.setSize(700,1024);
            }
            else {
                toggleSidebar.setText("Toggle Sidebar");
                frame.setSize(1100, 600);
                frame.setLocation(100, 200);
            }
        });
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        viewMenu.add(toggleSidebar);
        viewMenu.add(toggleFull);
    }

    /**
     * set the operation of help menu
     * we set (About) menu item that contains the name of programmer
     */
    private void makeHelpMenu() {
        Color aboutColor = new Color(40,41,37);
        JMenuItem about = new JMenuItem("About");
        about.setFont(new Font("Arial", Font.PLAIN, 12));
        about.addActionListener(e -> {
            JFrame aboutFrame = new JFrame();
            aboutFrame.setIconImage(icon.getImage());
            JPanel frame = new JPanel(new BorderLayout());
            frame.setBackground(aboutColor);
            aboutFrame.setBackground(aboutColor);
            aboutFrame.setTitle("About ");
            aboutFrame.setSize(400, 200);
            aboutFrame.setLocation(100, 200);
            aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel aboutField = new JLabel("programmer: Mahdi Rahmani©");
            aboutField.setForeground(Color.white);
            aboutField.setHorizontalAlignment(JTextField.CENTER);
            frame.add(aboutField, BorderLayout.CENTER);
            aboutFrame.add(frame, BorderLayout.CENTER);
            aboutFrame.setVisible(true);
        });
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));

        JMenuItem help2 = new JMenuItem("Help");
        help2.setFont(new Font("Arial", Font.PLAIN, 12));
        help2.addActionListener(e -> {
            JFrame help2Frame = new JFrame();
            help2Frame.setIconImage(icon.getImage());

            help2Frame.setBackground(Color.BLACK);
            help2Frame.setTitle("About ");
            help2Frame.setSize(500, 400);
            help2Frame.setLocation(100, 200);
            help2Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTextArea commands = new JTextArea();
            commands.setSize(450, 350);
            commands.setLocation(100, 200);
            commands.setForeground(Color.BLACK);
            commands.setBackground(Color.GRAY);
            StringBuilder myText = new StringBuilder();
            myText.append("            ((description))              : ((opcode)) ((Op1)) ((Op2)) ((Op3)) ((Op4))  \n\n");
            myText.append("'making a repository'                    : makerep (rep_name) (rep_privacy)\n");
            myText.append("'adding a contributor'                   : addcont (cont_Name) (rep_name)\n");
            myText.append("'making a directory'                     : makedir (rep_owner) (rep_name) (dir_name)\n");
            myText.append("'commit and push                         : commit&push <(message)> (destination_add) (fileName) (source_add)\n");
            myText.append("'get all commits of a rep'               : getcommits (rep_owner) (rep_name)\n");
            myText.append("'download a file in a rep'               : download (destination_add)\n");
            myText.append("'pull'                                   : pull (destination_add)\n");
            myText.append("'synchronizing source commits and server': synchronize (rep_owner) (rep_name)\n");
            myText.append("'view the users list'                    : view_users\n");
            myText.append("'view the rep_list of a user             : view_rep (owner_name)\n");
            myText.append("'change the privacy of my rep'           : changeprivacy (rep_name) (rep_privacy)\n ");
            myText.append("'removing a contributor from a rep'      : removecontributor (cont_name) (rep_name)\n");
            myText.append("'check our files are changed or not'     : ischanged\n");
            commands.setText(String.valueOf(myText));
            commands.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(commands);
            help2Frame.add(scrollPane);
            help2Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            help2Frame.setVisible(true);


        });

        help2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));
        helpMenu.add(about);
        helpMenu.add(help2);
    }
}
