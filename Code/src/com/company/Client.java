package com.company;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is client class.
 * We handle the requests of user and send it to the server and also receive the server response.
 *
 * @author mahdi rahmani
 * @since 2021/5/28
 */
public class Client {
    // The username of this user
    private String username;
    // The password of this user
    private String password;
    // The User email
    private String email;
    // The user bio
    private String bio;

    // The socket of client
    private Socket socket;
    // The IP address of server
    private String address;
    // The port that server listen on that
    private int port;
    // The input string from server
    private BufferedReader in;
    // The output string from client
    private PrintWriter out;
    // The server Response
    private String serverResponse;

    /**
     * This is the constructor of this class
     * it creat a client object with a given password and username
     * @param username the username of client
     * @param password the password of client
     */
    public Client(String username,String password)
    {
        this.username = username;
        this.password = password;
        email = "-";
        bio = "-";
        address = "127.0.0.1";
        port = 1600;
        serverResponse = "false";
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
