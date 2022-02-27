package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is server class
 * the requests of user is sent to this class and server handle them
 * our server has a data base . so we create object from DataBase class
 * if we want to handle the files of users and store some info we should work with our data base
 *
 * @author Mahdi Rahmani
 * @since 2021/5/28
 */
public class Server {

    // The socket of server client
    private ServerSocket server;
    // The port of server
    private int port;
    // The input message that received from client
    private BufferedReader in;
    // The output message that server transmit
    private PrintWriter out;

    /**
     * this is the main method of this class
     * we can run the server with this
     * @param args the input args
     * @throws IOException the exceptions
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }

    /**
     * this is the constructor of this class
     * we can create a ne object from this class by calling this
     * @throws IOException the exceptions
     */
    public Server() throws IOException {
        port = 1600;
        server = new ServerSocket(port);
        run();

    }

    /**
     * when we create an object from this class by using the constructor
     * then we should call run method to run the server.
     * so we call this in constructor of class
     * @throws IOException exceptions
     */
    public void run() throws IOException {
        System.out.println("Server started");
        System.out.println("The connection is created on port:" + port);
        while (true) {
            Socket socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
    }

}
