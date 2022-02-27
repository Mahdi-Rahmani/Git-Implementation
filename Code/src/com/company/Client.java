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

	/**
     * if the user wants to login we should use this method
     * it checks the username and password of the user with server data base
     * @return the status of server response
     */
    public String login()  {
        out.println("login");
        out.println(username);
        out.println(password);
        out.flush();
        String status = "false";
        try {
            status = in.readLine();
            if (status.equals("true"))
            {
                int max = Integer.parseInt(in.readLine());
                for (int i = 0 ; i< max ;i++)
                {
                    String data = in.readLine();
                    switch (i){
                        case 1:
                            email = data;
                            break;
                        case 2:
                            bio = data;
                            break;
                    }
                }
                return status;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * if the user wants to register we should use this method
     * also when user register we should creat a folder for that user in clientsData folder
     * @return status
     */
    public String register()  {
        //makeConnection();
        out.println("register");
        out.println(username);
        out.println(password);
        out.flush();
        System.out.println(password);
        String status = "false";
        try {
            status = in.readLine();
            if (status.equals("true")){
                out.println(email);
                out.println(bio);
                out.flush();
                File directory1 = new File("./clientsData/" + username );
                File directory2 = new File("./clientsData/" + username + "/repository");
                File directory3 = new File("./clientsData/" + username + "/downloads");
                boolean bool1 = directory1.mkdir();
                boolean bool2 = directory2.mkdir();
                boolean bool3 = directory3.mkdir();

                if (!(bool1&&bool2&&bool3))
                    System.out.println("The client directory isn't created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * for handling the requests with 3 parts(opcode , op1 , op2)
     * @param part0 the opcode
     * @param part1 the op1
     * @param part2 the op2
     * @return response
     */
    public String handle3(String part0 , String part1 , String part2){
        String response = "";
        out.println(part0);
        out.println(username);
        out.println(part1);
        out.println(part2);
        out.flush();
        try {
            response = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * handle the request of making directory
     * @param repOwner the name of repository owner
     * @param repName the name of ogf
     * @param dirName the name of directory
     * @return response
     */
    public String makeDir( String repOwner , String repName, String dirName){
        String response = "";
        out.println("makedir");
        out.println(username);
        out.println(repOwner);
        out.println(repName);
        out.println(dirName);
        out.flush();
        try {
            response = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * handle the commit_push request
     * @param message the message of commit
     * @param destinationAddress the destination address
     * @param fileName the name of file
     * @param sourceAddress the source address of file
     * @return response
     */
    public String commit_push(String message ,String destinationAddress , String fileName ,String sourceAddress)  {
        try {
            String[] parts = destinationAddress.split("/");
            String firstStatus = synchronizer(parts[0],parts[2]);
            if (firstStatus.equals("access denied"))
                return "your access is denied. you should be a contributor of this repo";
            makeConnection();
            out.println("commit&push");
            out.println(username);
            out.println(message);
            out.println(destinationAddress);
            out.println(fileName);
            out.flush();
            String status = in.readLine();
            if (status.equals("true")) {
                String lastModif = in.readLine();
                System.out.println(sourceAddress);
                BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(sourceAddress));
                BufferedOutputStream bufOut = new BufferedOutputStream(socket.getOutputStream());
                byte[] buff = new byte[1024 * 8];
                int len;
                while ((len = bufIn.read(buff)) != -1) {
                    bufOut.write(buff, 0, len);
                    bufOut.flush();
                }
                bufOut.close();
                bufIn.close();
                commitInClient(message, destinationAddress,fileName,sourceAddress,lastModif);
                updateLastModif();
                return "commit and push successfully";
            }else
                return "please try again. something is wrong!!";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
        return "please try again. something is wrong!!";
    }
	public void updateLastModif(){

        long latestModified = 0;
        File directory = new File("./somefiles/files" );
        File[] files = directory.listFiles();
        for(File file : files) {
            if (latestModified < file.lastModified()) {
                latestModified = file.lastModified();
            }
        }
        System.out.println(latestModified);
        try {
            FileWriter fw = new FileWriter("./somefiles/lastmodified.txt");
            fw.write(latestModified+"");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * we check if the committed files are changed or not
     * @return the status
     */
    public String isChanged(){
        File file = new File("./somefiles/lastmodified.txt");
        if (!file.exists())
            return "these files never committed";
        else {
            long latestModified = 0;
            File directory = new File("./somefiles/files" );
            File[] files = directory.listFiles();
            for(File file1 : files) {
                if (latestModified < file1.lastModified()) {
                    latestModified = file1.lastModified();
                }
            }
            String nowLastModif = latestModified + "";

            String commitLastModif = "";
            try {
                FileReader fileReader = new FileReader("./somefiles/lastmodified.txt");
                Scanner scanner = new Scanner(fileReader);
                while (scanner.hasNext()) {
                    commitLastModif = scanner.nextLine();
                }
                fileReader.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(nowLastModif);
            System.out.println(commitLastModif);
            if (nowLastModif.equals(commitLastModif))
                return "your files arent changed";
            else
                return "your files are changed. please first commit them.";
        }

    }
    /**
     * we should hold the commits of client inside his system too.
     * this method handle this problem and holds the commit of user in clientData/<user>/repository path
     * @param message the message of commit
     * @param destinationAddress the address of destination
     * @param fileName the file name that we want to commit
     * @param sourceAddress the source address of file
     * @param lastmodified the last modified (we get it from server for that repository)
     */
    public void commitInClient(String message ,String destinationAddress , String fileName ,String sourceAddress,String lastmodified){
        String path = "./clientsData/"+username+"/repository";
        String[] parts = destinationAddress.split("/");
        for (String dirName : parts){
            path += "/"+dirName;
            File file = new File(path);
            if (!file.exists()){
                boolean bool = file.mkdir();
                if (!bool)
                    System.out.println("The directory cant be created");
            }
        }
        // now we should transfer file in our repository inside clientsData directory
        try {
            BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(sourceAddress));
            BufferedOutputStream bufOut = new BufferedOutputStream(new FileOutputStream(path+"/"+fileName));
            byte[] buff = new byte[1024 * 8];
            int len;
            while ((len = bufIn.read(buff)) != -1) {
                bufOut.write(buff, 0, len);
            }
            bufOut.close();
            bufIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // now we should save commits
        String path2 ="./clientsData/"+username+"/repository/"+parts[0]+"/"+parts[1]+"/"+parts[2]+"/"+username+"commits.txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(path2,true);
            fw.write(message + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // now we should save last modified
        String path3 ="./clientsData/"+username+"/repository/"+parts[0]+"/"+parts[1]+"/"+parts[2]+"/lastmodified.txt";
        FileWriter fw2 = null;
        try {
            fw2 = new FileWriter(path3);
            fw2.write(lastmodified );
            fw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method synchronize one repository commits and files of user in clientsData and server database
     * @param repOwner the name of repository owner
     * @param repName the name of repository
     * @return response
     */
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
