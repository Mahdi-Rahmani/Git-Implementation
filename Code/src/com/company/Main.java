package com.company;


import javax.swing.*;

/**
 * this is main class
 * we creat an object of client here
 * also we set the look and feel of our GUI
 *
 * @author Mahdi Rahmani
 * @since 2021/5/28
 */
public class Main {

    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        Login login = new Login();
    }
}
