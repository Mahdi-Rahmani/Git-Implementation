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
}
