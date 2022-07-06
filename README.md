# Implement a simple Git
This projetct is the second project of Computer Networks course at AUT. It has two parts. First we should answer to some questions related to the project and then implement Git according to the [project explanation.](https://github.com/Mahdi-Rahmani/Git-Implementation/blob/main/Project%20Explanation/CN-P2-A.pdf)  
  
## Questions  
In this part we have some questions to answer. They are written below:
 1. Research about RUDP protocol and how to implement it.  
 2. Give some examples of the difference between RUDP and TCP.  
The answer file is added in [Question answer](https://github.com/Mahdi-Rahmani/Git-Implementation/blob/main/Question%20answer/question%20answer.pdf)  
  
## Implementation  
### step1) Creating a user account and logging in  
Each person must first create a user account including a unique username and password to be able to use the following services. After registering, each user must enter his username and password to enter, and the program will allow entry by checking the registered user's letter and matching the corresponding password, in case of authentication. Otherwise, the program should display the appropriate error to the user.  
### step3) creating repository  
The Git server includes repositories created by users. Each user must first create a repository in order to be able to upload his file to the git, and each user can have several repositories. This repository is created on the server side by the user's request. Also, if the user wishes, he can create other directories on that repository. The user must commit and push the desired file in that repository with a message, and other users can pull it from the server side. (in fact, they should rewrite all the files and directories on the repository again on their side)  
If you wish, you can check whether you have changed your existing files or not, and if you have changed, commit them first so that the changes are saved on your side and then pull.  
On the repository, note that any user can access any repository and pull files from there, but commit and new push are only available to contributors. Note that contributors must be able to add new directories and files to the repository.  
The user can create his own repository in private mode, and in this way, other users do not have access to it. In this case, only people who have been given access can see the files in that repository.  
### step4) Add contributor  
First, the only creator of the repository is its Contributor, but the creator can add any number of contributors to the repository, whether in public or private mode.  
### step5) Commits  
The program must save the commits of each user on the client and server side and must have the ability to show the list of the user's commits along with their message.  
### step6) Synchronization  
The program must check that the user's files are in sync with the committed files on the server. In this section, for convenience, you can check the time of file change, and if it is not synchronized, the user must get the new files from the server and update his files.  
### step7) Download files  
Every user should be able to download the files in the repositories.  
  
Note: Note that the appropriate user interface should be displayed on the console, and if possible, you can create a graphical display for the program. Also, instead of using TCP, you can use the Reliable UDP protocol (RUDP) to establish a secure connection. The implementation of other functions of Gate such as View, Branch Review, etc. is also a bonus.  
