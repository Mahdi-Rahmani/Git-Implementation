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
	
	public void email_bio_setter(String username , String email , String bio){

        FileWriter fw = null;
        try {
            fw = new FileWriter("./Data/"+username+"/personality.txt",true);
            fw.write(email + "\n");
            fw.write(bio + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String makeRepository(String username,String repName , String status)
    {
        String path = "./Data/"+ username +"/repository";

        if (isRepositoryExist(username, repName)) {
            System.out.println("this name is used before");
            return "false";
        }

        File rep = new File(path + "/" + repName);
        boolean isCreated = rep.mkdir();
        if (!isCreated) {
            System.out.println("this repository isn`t created");
            return "false";
        }

        try {
            FileWriter fw = new FileWriter(path + "/" + repName + "/info.txt",true);
            fw.write(status + "\n");
            fw.write(username + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "true";
    }

    public String addContributor(String username , String contName , String repName){

        //first we should check if the repository or contributor exist or not
        String path = "./Data/"+ username +"/repository";


        if (!(isUserExist(contName) && isRepositoryExist(username, repName)))
        {
            System.out.println("the contributor or repository isn`t exist ");
            return "false";
        }
        //then we should check if the contributor is added later or not
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path+ "/" + repName + "/info.txt");
            Scanner scanner = new Scanner(fileReader);
            int lineNumber = 0;
            while (scanner.hasNext()) {
                if(lineNumber == 0)
                    scanner.nextLine();
                else {
                    String contributors = scanner.nextLine();
                    if (contributors.equals(contName)) {
                        System.out.println("this contributor added later");
                        return "false";
                    }
                }
                lineNumber ++;
            }
            fileReader.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // now we can add contributor
        FileWriter fw = null;
        try {
            fw = new FileWriter(path+ "/" + repName + "/info.txt",true);
            fw.write(contName + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "true";
    }

    public String makeDirectory(String username,String repOwner , String repName , String dirName ){
        if (isUserExist(repOwner)&&isRepositoryExist(repOwner, repName)&&isContributor(username, repOwner, repName)){
            File directory = new File("./Data/" + repOwner + "/repository/" + repName + "/"+ dirName);
            //System.out.println("./Data/" + repOwner + "/repository/" + repName + "/"+ dirName);
            boolean bool = directory.mkdir();
            if (!bool)
                return "false";
            //System.out.println("miiiad");
            return "true";
        }
        return "false";
    }

    public String usersList(){
        String[] usersList = foldersList("./Data");
        StringBuilder list = new StringBuilder();
        for (String user : usersList) {
            list.append(user).append("\n");
        }
        return String.valueOf(list);
    }

    public String repositoryList(String username , String ownerName){
        // first we should check if the user exist
        if(!isUserExist(ownerName))
            return "the user doesn't exist";
        String[] repList = foldersList("./Data/"+ownerName+"/repository");
        StringBuilder list = new StringBuilder();
        for (String rep : repList) {
            // now we should check if the rep is private or not
            FileReader fileReader = null;
            try {
                fileReader = new FileReader("./Data/"+ownerName+"/repository/"+rep+"/info.txt");
                Scanner scanner = new Scanner(fileReader);
                int lineNumber = 0;
                while (scanner.hasNext()) {
                    // if the rep is private we should check if the user is one of its contributor or not
                    if(lineNumber == 0 && scanner.nextLine().equals("public")) {
                        list.append(rep).append("\n");
                        fileReader.close();
                        scanner.close();
                        break;
                    }
                    else {
                        String contributors = scanner.nextLine();
                        if (contributors.equals(username)) {
                            list.append(rep).append("\n");
                        }
                    }
                    lineNumber ++;
                }
                fileReader.close();
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list.toString();
    }
	
	public String removeContributor(String username ,String contName , String repName){
        if(isUserExist(contName) && isRepositoryExist(username, repName)){
            FileReader fileReader = null;
            FileWriter fileWriter = null;
            try {
                fileReader = new FileReader("./Data/"+username+"/repository/"+repName+"/info.txt");
                fileWriter = new FileWriter("./Data/" + username + "/repository/" + repName + "/temp.txt");
                Scanner scanner = new Scanner(fileReader);
                int lineNumber = 0;
                while (scanner.hasNext()) {
                    if(lineNumber == 0 ) {
                        String privacy = scanner.nextLine();
                        fileWriter.write(privacy+"\n");
                    }
                    else {
                        String contributors = scanner.nextLine();
                        if (!contributors.equals(contName))
                            fileWriter.write(contributors+"\n");
                    }
                    lineNumber ++;
                }
                fileWriter.close();
                fileReader.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            return "false";
        copyFile(username, repName);
        return "true";
    }

    public boolean isContributor(String username, String owner, String repName ){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("./Data/"+owner+"/repository/"+repName+"/info.txt");
            Scanner scanner = new Scanner(fileReader);
            int lineNumber = 0;
            while (scanner.hasNext()) {
                // if the rep is private we should check if the user is one of its contributor or not
                if(lineNumber == 0 ) {
                    String privacy = scanner.nextLine();
                }
                else {
                    String contributors = scanner.nextLine();
                    if (contributors.equals(username)) {
                        System.out.println("this user is one of the contributor of this repo");
                        return true;
                    }
                }
                lineNumber++;
            }
            fileReader.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("this user isn`t one of the contributor of this repo");
        return false;
    }
}
