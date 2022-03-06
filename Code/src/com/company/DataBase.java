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

}
