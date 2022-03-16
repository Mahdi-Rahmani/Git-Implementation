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
    // The DataBase that server uses
    private DataBase dataBase;

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
        dataBase = new DataBase();
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
            requestHandler(socket);
        }
    }

    /**
     * the request handler of server
     * in run method we have a true while and always listen on a specific port
     * if client creat a socket and transmit a request we should give that request
     * to this method to handle it
     * @param socket the socket
     * @throws IOException exceptions
     */
    public void requestHandler(Socket socket) throws IOException {
        String username;
        String password;
        String status = "";
        String request = in.readLine();

        System.out.println(request);

        switch (request) {
            case "login":
                ArrayList<String> personality ;
                System.out.println("Client wants to login:");
                username = in.readLine();
                password = in.readLine();
                personality = dataBase.checkUser2(username , password);
                for (int j = 0; j<personality.size() ; j++)
                {
                    out.println(personality.get(j));
                }
                out.flush();

                break;
            case "register":
                System.out.println("Client wants to register:");
                username = in.readLine();
                password = in.readLine();
                status = dataBase.register(username , password);
                out.println(status);
                out.flush();
                System.out.println(status);
                if (status.equals("true")){
                    String email = in.readLine();
                    String bio = in.readLine();
                    System.out.println(bio);
                    dataBase.email_bio_setter(username, email, bio);
                }
                out.flush();
                System.out.println("fininsh register");

                break;
            case "makerep":
                System.out.println("Client wants to make repository:");
                username = in.readLine();
                String rep_name = in.readLine();
                String rep_privacy = in.readLine();
                status = dataBase.makeRepository(username,rep_name,rep_privacy);
                out.println(status);
                out.flush();
                break;
            case "addcont":
                System.out.println("Client wants to add contributor:");
                username = in.readLine();
                String cont_name = in.readLine();
                rep_name = in.readLine();
                status = dataBase.addContributor(username,cont_name,rep_name);
                out.println(status);
                out.flush();
                break;
            case "makedir":
                System.out.println("Client wants to make new directory:");
                username = in.readLine();
                String rep_owner = in.readLine();
                rep_name = in.readLine();
                String dir_name = in.readLine();

                status = dataBase.makeDirectory(username,rep_owner,rep_name,dir_name);
                out.println(status);
                out.flush();
                break;

            case "commit&push":
                System.out.println("Client wants to commit and push:");
                username = in.readLine();
                String message = in.readLine();
                String destAdd = in.readLine();
                String fileName = in.readLine();
                status = dataBase.commit_push(username,message,destAdd);
                String[] statusPart = status.split("/");
                if (!statusPart[0].equals("false")) {
                    out.println("true");
                    out.println(statusPart[1]);
                    out.flush();
                    BufferedInputStream bufIn = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream bufOut = new BufferedOutputStream(new FileOutputStream("./Data/" +destAdd+"/"+fileName));
                    byte[] buff = new byte[1024 * 8];
                    int len;
                    while ((len = bufIn.read(buff)) != -1) {
                        bufOut.write(buff, 0, len);
                    }
                    bufOut.close();
                    bufIn.close();
                    System.out.println("commit and push successfully ");
                }else{
                    out.println("false");
                    out.flush();
                }

                break;
            case "getcommits":
                System.out.println("Client wants to get commits:");
                username = in.readLine();
                rep_owner = in.readLine();
                rep_name = in.readLine();
                status = dataBase.getCommits(username,rep_owner,rep_name);
                System.out.println(status);
                // if status isnt false the status contains the commits
                if (!status.equals("false")){
                    String[] parts = status.split("\n");
                    System.out.println(parts.length);
                    out.println(parts.length);
                    for (String part:parts){
                        out.println(part);
                    }
                }else
                    out.println(status);
                out.flush();
                break;
            case "download":// for downloading a file in a repository
                System.out.println("Client wants to download:");
                username = in.readLine();
                String destinationAddress =in.readLine();//   <owner>/repository/<repName>/<fileAddress>
                String[] addParts = destinationAddress.split("/");
                if(dataBase.isUserExist(addParts[0]) && dataBase.isRepositoryExist(addParts[0],addParts[2])
                        &&((dataBase.isRepositoryPrivate(addParts[0],addParts[2]) && dataBase.isContributor(username,addParts[0],addParts[2]))||!dataBase.isRepositoryPrivate(addParts[0],addParts[2]))) {
                    out.println("true");
                    out.flush();
                    BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream("Data/" + destinationAddress));
                    BufferedOutputStream buffOut = new BufferedOutputStream(socket.getOutputStream());
                    byte[] buff = new byte[1024 * 8];
                    int length;
                    while ((length = buffIn.read(buff)) != -1) {
                        buffOut.write(buff, 0, length);
                        buffOut.flush();
                    }
                    buffOut.close();
                    buffIn.close();
                    System.out.println("the file is transmitted to client completely");
                }
                else{
                    out.println("false");
                    out.flush();
                }
                break;

            case "pull"://for downloading a complete repository
                System.out.println("Client wants to pull:");
                username = in.readLine();
                String repAddress = in.readLine();//  <owner>/repository/<repName> in recursive call extended
                addParts = repAddress.split("/");
                if(dataBase.isUserExist(addParts[0]) && dataBase.isRepositoryExist(addParts[0],addParts[2])
                        &&((dataBase.isRepositoryPrivate(addParts[0],addParts[2]) && dataBase.isContributor(username,addParts[0],addParts[2]))||!dataBase.isRepositoryPrivate(addParts[0],addParts[2]))) {
                    out.println("true");
                    System.out.println(repAddress);
                    String[] list = dataBase.foldersList("./Data/"+repAddress);
                    System.out.println(list.length);
                    out.println(list.length);
                    for (String name  : list){
                        System.out.println(name);
                        out.println(name);
                    }
                    out.flush();
                }else{
                    out.println("false");
                    out.flush();
                }
                out.println(status);
                out.flush();
                break;

            case "synchronize":
                System.out.println("Client wants to synchronize his files with server:");
                username = in.readLine();
                rep_owner = in.readLine();
                rep_name = in.readLine();
                status = dataBase.synchronize(username, rep_owner, rep_name);
                out.println(status);
                out.flush();
                break;

            case "view-users":
                System.out.println("Client wants to see users:");
                String list;
                list = dataBase.usersList();
                String[] parts = list.split("\n");
                out.println(parts.length);
                for (String name : parts){
                    out.println(name);
                }
                out.flush();
                break;

            case "view-rep":
                System.out.println("Client wants to see repositories:");
                username = in.readLine();
                String ownerName = in.readLine();
                status = dataBase.repositoryList(username , ownerName);
                parts = status.split("\n");
                if (parts[0].equals("the user doesn't exist"))
                    out.println(parts[0]);
                else {
                    out.println(parts[0]);
                    out.println(parts.length);
                    for (String name : parts) {
                        out.println(name);
                    }
                }
                out.flush();
                break;

            case "changeprivacy":
                System.out.println("Client wants to change the privacy of one of his repository:");
                username = in.readLine();
                rep_name = in.readLine();
                rep_privacy = in.readLine();
                status = dataBase.changeRepoPrivacy(username, rep_name, rep_privacy);
                out.println(status);
                out.flush();
                break;

            case "removecontributor":
                System.out.println("Client wants to remove one contributor:");
                username = in.readLine();
                cont_name = in.readLine();
                rep_name = in.readLine();
                status = dataBase.removeContributor(username, cont_name, rep_name);
                out.println(status);
                out.flush();
                break;

        }
        out.close();
    }

}
