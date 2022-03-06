package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * this is our data base
 * server work with this class and handle the requests that related to files
 *
 * @author Mahdi Rahmani
 * @since 2021/5/28
 */
public class DataBase {
    /**
     * this is the constructor of this class
     */
    public DataBase(){ }

    /**
     * we give a path to this method and give us the list of files in that path
     * @param path the path of directory
     * @return list of files
     */
    public String[] foldersList(String path)
    {
        File directoryPath = new File(path);
        //List of all files and directories
        String[] directories = directoryPath.list();
        return directories;
    }

    /**
     * this method checks does the entry username exit or not
     * @param username the username
     * @return boolean
     */
    public boolean isUserExist(String username){
        String[] usersList = foldersList("./Data");
        for (String user : usersList) {
            if (user.equals(username))
                return true;

        }
        return false;
    }

    /**
     * this method checks does the entry rep name exist for entry username
     * @param username the username
     * @param repName the rep name
     * @return boolean
     */
    public boolean isRepositoryExist(String username , String repName){
        String path = "./Data/"+ username +"/repository";
        String[] repList = foldersList(path);
        for (String name : repList)
        {
            if (repName.equals(name)) {
                System.out.println("this repository exists");
                return true;
            }
        }
        return false;
    }

	/**
     * this method checks is the the entry repository private or not
     * @param repOwner the name of owner of rep
     * @param repName the name of rep
     * @return boolean
     */
    public boolean isRepositoryPrivate(String repOwner,String repName)
    {
        try {
            FileReader fileReader = new FileReader("./Data/"+repOwner+"/repository/"+repName+"/info.txt");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                String privacy = scanner.nextLine();
                if( privacy.equals("private"))
                    return true;
                else
                    return false;
            }
            fileReader.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method is used for login purpose
     * @param username the username
     * @param password the password
     * @return the list of objects in personality.txt if the user exists
     */
    public ArrayList<String> checkUser2(String username, String password)
    {
        ArrayList<String> personality = new ArrayList<>();

        if (isUserExist(username)){
            try {
                FileReader fileReader = new FileReader("./Data/"+username+"/personality.txt");
                Scanner scanner = new Scanner(fileReader);
                int i = 0;
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    if (data.equals(password) && i == 0) {
                        personality.add("true");
                        personality.add(data);
                    }else{
                        personality.add(data);
                    }
                    i++;
                }
                fileReader.close();
                scanner.close();
                personality.add(1,""+i);
                return personality;
            } catch (Exception e) {
                System.out.println("Cant access Data ");
                personality.add("false");
                return personality;
            }
        }
        personality.add("false");
        return personality;
    }

    /**
     * this method is used for register
     * @param username username
     * @param password password
     * @return status
     */
    public String register(String username , String password)
    {

        if (isUserExist(username)) {
            System.out.println("Tis username is used before");
            return "false";
        }

        try {
            File newUser = new File("./Data/" + username);
            boolean status = newUser.mkdir();
            if (!status) {
                System.out.println("Cant create Folder for user");
            }

            FileWriter fw = new FileWriter("./Data/"+username+"/personality.txt");
            fw.write(password + "\n");
            fw.close();

            File hisRepository = new File("./Data/" + username+ "/repository");
            status = hisRepository.mkdir();
            if (!status) {
                System.out.println("Cant create repository Folder for user");
            }

            return "true";

        } catch (Exception e) {
            System.out.println("Cant read users Data !");
            return "false";
        }
    }
}
