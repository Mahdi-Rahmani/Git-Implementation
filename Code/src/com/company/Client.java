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
	/**
     * this method open a socket and create a connection for us
     * also creat BufferedReader in and PrintWriter out objects for transfer and receive data
     */
    public void makeConnection() {
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is the main method of this class.
     * after client login and creating a client object in login class
     * we go to main page and the commands that user write in command line 
     * should handle with this method.
     * @param request the request of user in command line
     * @return a string that we want show on monitor (text area)
     */
    public String requestHandler(String request)
    {
        String response;
        String[] parts = request.split(" ");

        switch (parts[0])
        {
            case "makerep":
                if (!(parts[2].equals("public") || parts[2].equals("private")))
                    return "invalid status";
                makeConnection();
                response = handle3(parts[0],parts[1],parts[2]);
                if (response.equals("true"))
                    return "The repository " + parts[1] + " is Created";
                else
                    return "Error in creating Repository";
            case "addcont":
                makeConnection();
                response = handle3(parts[0],parts[1],parts[2]);
                if (response.equals("true"))
                    return "The contributor " + parts[1] + " is added";
                else
                    return "Error in adding contributor";
            case "makedir":
                makeConnection();
                response = makeDir(parts[1],parts[2],parts[3]);
                if (response.equals("true"))
                    return "the directory is created successfully";
                else
                    return "Error in creating directory";
            case "commit&push":
                //commit&push < test > hasan/repository/test3 readme.txt ./somefiles/test1/readme.txt
                String commitMessage = "";
                boolean start = false;
                int j = 0;
                for (String st : parts){
                    j++;
                    if (st.equals(">"))
                        break;
                    if (start)
                        commitMessage += st + " ";
                    if (st.equals("<"))
                        start = true;
                }
                System.out.println(commitMessage);
                System.out.println(parts[j]);
                makeConnection();
                response = commit_push(commitMessage,parts[j],parts[j+1],parts[j+2]);
                return response;
            case "getcommits":
                makeConnection();
                response = getCommits(parts[1],parts[2]);
                if (!response.equals("false"))
                    return response;
                else
                    return "Error occurs in reading commits";
            case "download":
                // download hasan/repository/test3/a5.png
                String[] partparts = parts[1].split("/");
                makeConnection();
                response = download(parts[1],"./clientsData/" + username + "/" + "downloads/"+partparts[partparts.length-1]);
                return response;
            case "pull":
                //  pull hasan/repository/test3
                return pull(parts[1],"");

            case "synchronize":
                makeConnection();
                response = synchronizer(parts[1],parts[2]);
                return response;
            case "view-users":
                makeConnection();
                out.println("view-users");
                out.flush();
                int userNum = 0;
                String userList = "";
                try {
                    userNum = Integer.parseInt(in.readLine());
                    for (int i = 0 ; i < userNum ; i++)
                    {
                        userList += in.readLine() + "\n";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return userList;

            case "view-rep":
                makeConnection();
                out.println("view-rep");
                out.println(username);
                out.println(parts[1]);
                out.flush();
                int repNum = 0;
                String repList = "";
                try {
                    response = in.readLine();
                    if (!(response.equals("the user doesn't exist"))) {
                        repNum = Integer.parseInt(in.readLine());
                        for (int i = 0; i < repNum; i++) {
                            repList += in.readLine() + "\n";
                        }
                        return repList;
                    }else
                        return response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "something were wrong. please try again.";

            case "changeprivacy":
                makeConnection();
                response = handle3(parts[0],parts[1],parts[2]);
                if (response.equals("true"))
                    return "The privacy of repository changed successfully";
                else
                    return "something were wrong. please try again.(maybe your repName is false.)";

            case "removecontributor":
                makeConnection();
                response = handle3(parts[0],parts[1],parts[2]);
                if (response.equals("true"))
                    return "The contributor is removed successfully";
                else
                    return "something were wrong. please try again.(maybe your repName or contributor name is false.)";

            case "ischanged":
                return isChanged();

            default:
                return "please enter a valid command";
        }

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
