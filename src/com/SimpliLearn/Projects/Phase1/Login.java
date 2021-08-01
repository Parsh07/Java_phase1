package com.SimpliLearn.Projects.Phase1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Login {
	private boolean isCredentialsValid=false;
	private static Registration registration = new Registration();;

	File file;
	ArrayList<String> listOfFiles;

	public void userLogin() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n*****************************************************\n"
				+ "	  --Welcome to Login Page--			\n"
						+ "******************************************************");
		
		while(!isCredentialsValid) {
			System.out.print("Enter Username : ");
			String Username = scanner.nextLine();
			System.out.println();
			System.out.print("Enter Password : ");
			String Password = scanner.nextLine();
			File file = new File("Credentials.txt");
			if(file.exists()) {
				if(validateCredentials(Username, Password)) {
					System.out.println("Login successful\n");
					registration.setUsername(Username);
					registration.setPassword(Password);
					this.options();
					isCredentialsValid=true;
				}
			}
			else {
				System.out.println("User is not registered, Kindly register the user.");
			}
		}
		scanner.close();
	}
	
	public boolean validateUsername(String Username) {
		Scanner usernameScan;
		try {
			file = new File("Credentials.txt");
			if(file.exists()) {
				FileReader fr = new FileReader("Credentials.txt");
				usernameScan = new Scanner(fr);

				while(usernameScan.hasNextLine()) {
					String data = usernameScan.nextLine();
					String[] creds;
					creds = data.split(" ");
					for(int i=0; i<creds.length; i++) {
						creds[i] = creds[i].trim();
					}
					if(creds[0].equals(Username)) {
						registration.setUsername(Username);
						return true;
					}
				}
			}
			else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("File not found while validating username");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validatePassword(String Password) {
		Scanner passwordScan;
		try {
			file = new File("Credentials.txt");
			if(file.exists()) {
				FileReader fr = new FileReader("Credentials.txt");
				if(fr != null) {
					passwordScan = new Scanner(fr);
				
					while(passwordScan.hasNextLine()) {
						String data = passwordScan.nextLine();
						String[] creds;
						creds = data.split(" ");
						for(int i=0; i<creds.length; i++) {
							creds[i] = creds[i].trim();
						}
						if(creds[0].equals(registration.getUsername())) {
							if(creds[1].equals(Password)) {
								return true;
							}
						}
					}
				}
				
			}
			else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("File not found while validating password");
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean validateCredentials(String Username, String Password) {
		
		if(validateUsername(Username)) {
			if(validatePassword(Password)) {
				return true;
			}
			else {
				System.out.println("Invalid Password! Please enter the correct password\n");
				return false;
			}
		}
		else {
			System.out.println("Invalid Username! Please enter the correct username\n");
			return false;
		}
	}
	
	public void options() {
		Scanner optionScan = new Scanner(System.in);
		boolean isOptionSelected=false;
		while(!isOptionSelected) {
			System.out.println("\n\nEnter your choice (numbers) : \n"
								+"1. FETCH ALL FILES\n"
								+"2. STORE CREDENTIALS\n"
								+"3. FETCH ALL STORED CREDENTIALS\n"
								+"4. DELETE USER CREDENTIAL\n"
								+"5. SEARCH USER\n"
								+"6. Quit");
			int choice;
			choice = optionScan.nextInt();
			if(choice>=1 || choice<=6) {
				switch(choice) {
					case 1:
						fetchFiles();
						break;
					
					case 2:
						storeCredential();
						break;
						
					case 3:
						fetchCredentials();
						break;
						
					case 4:
						deleteFile();
						break;
						
					case 5:
						searchUser();
						break;
						
					case 6:
						isOptionSelected=true;
						break;
				}
			}
			else {
				System.out.println("Invalid option is choosed, Please select valid option");
			}
		}
		optionScan.close();
	}

	public void storeCredential() {
		Scanner storeCredScan = new Scanner(System.in);
		try {
			System.out.print("\nEnter credentials : ");
			String credentials = storeCredScan.nextLine();
			registration.setPassword(credentials);
			FileWriter userFile;
			
			System.out.print("\nDo you want to store this credentials for another user? (enter Y/N to answer) : ");
			String storeCreds = storeCredScan.nextLine();
			if(storeCreds.toUpperCase().equals("Y") || storeCreds.toUpperCase().equals("N")) {
				if(storeCreds.toUpperCase().equals("N")) {
					System.out.println("Storing creds for current user...");
				}
				else {
					System.out.print("\nEnter username for whom you want to store the credentials (enter username) : ");
					registration.setUsername(storeCredScan.nextLine());
				}
				
				boolean isFileExist=false;
				while(!isFileExist) {
					File file = new File("Files\\"+registration.getUsername()+".txt");
					if(file.exists()) {
						userFile = new FileWriter("Files\\"+registration.getUsername()+".txt", true);
						userFile.write(registration.getPassword());
						userFile.write("\r\n");
						userFile.close();
						System.out.println("Credentials stored successfully!");
						isFileExist = true;
					}
					else {
						System.out.print("\nFile does not exist for user - "+registration.getUsername()+","
								+"\nDo you want to create a file ? (enter Y/N to answer) : ");
						storeCreds = storeCredScan.nextLine();
						if(storeCreds.toUpperCase().equals("Y") || storeCreds.toUpperCase().equals("N")) {
							if(storeCreds.toUpperCase().equals("Y")) {
								FileWriter fr = new FileWriter("Files\\"+registration.getUsername()+".txt");
								fr.write("*****************Credentials*******************\n");
								fr.write(registration.getPassword());
								fr.write("\r\n");
								fr.close();
								System.out.println("Credentials are stored successfully in a respective userfile - "+registration.getUsername());
							}
							isFileExist=true;
						}
						else {
							System.out.println("Invalid input given");
						}
					}
				}
			}
			else {
				System.out.println("Invalid input given");
			}
		} catch (IOException e) {
			storeCredScan.close();
			System.out.println("File not found while storing creds.");
			e.printStackTrace();
		}
		
	}
	
	public void fetchFiles() {
		try {
			File folder = new File("./Files");
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles.length == 0) {
				System.out.println("Folder is empty");
			}
			else {
				System.out.println("--------Lists of Files--------");
				Arrays.sort(listOfFiles);
				for (File file : listOfFiles) {					
					if(file.isFile()) {
						System.out.println(file.getName());
					} else if (file.isDirectory()) {
		                System.out.println("Directory :" + file.getName());
		            }
				} 
			}
		} catch (Exception e) {
				System.out.println("Folder is empty");
				e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void fetchCredentials() {
		Scanner fetchCredScan = new Scanner(System.in);
		System.out.print("\nDo you want to fetch the credentials for another user? (enter Y/N to answer) : ");
		String isAnotherUser = fetchCredScan.nextLine();
		if(isAnotherUser.toUpperCase().equals("Y") || isAnotherUser.toUpperCase().equals("N")) {
			if(isAnotherUser.toUpperCase().equals("N")) {
				System.out.println("Fetching creds for current user...");
			}
			else {
				System.out.print("\nEnter username for whom you want to fetch the credentials (enter username) : ");
				registration.setUsername(fetchCredScan.nextLine());
			}		
			try {
				File file = new File("Files\\"+registration.getUsername()+".txt");
				if(file.exists()) {
					FileReader fr = new FileReader("Files\\"+registration.getUsername()+".txt");
					fetchCredScan = new Scanner(fr);
					System.out.println();
					while(fetchCredScan.hasNextLine()) {
						System.out.println(fetchCredScan.nextLine());
					}
					fr.close();
				}
				else {
					System.out.println("File not found for user - "+registration.getUsername());
				}
			} catch (Exception e) {
				fetchCredScan.close();
				System.out.println("File not found while fetching records.");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Invalid input given");
		}
	}
	
	public void deleteFile() {
		Scanner deleteScan = new Scanner(System.in);
		System.out.print("\nDo you want to delete the file for another user? (enter Y/N to answer) : ");
		String isDeleteForAnotherUser = deleteScan.nextLine();
		if(isDeleteForAnotherUser.toUpperCase().equals("Y") || isDeleteForAnotherUser.toUpperCase().equals("N")) {
			if(isDeleteForAnotherUser.toUpperCase().equals("Y")) {
				System.out.print("\nEnter file name which you want to delete (enter username) : ");
				registration.setUsername(deleteScan.nextLine());
			}			
			try {
				File file = new File("Files\\"+registration.getUsername()+".txt");
				if(file.exists()) {
					if(file.delete()) {
						System.out.println("File deletion successful");
					}
					else {
						System.out.println("File deletion failed");
					}
				}
				else {
					System.out.println("File not found for user - "+registration.getUsername());
				}
			} catch (Exception e) {
				deleteScan.close();
				System.out.println("No such file exists");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Invalid input given");
		}
	}
	
	public void searchUser() {
		Scanner searchUserScan = new Scanner(System.in);
		System.out.print("\nEnter username : ");
		if(searchUser(searchUserScan.nextLine())){
			System.out.println("User exists");
		}
		else {
			System.out.println("User does not exists");
		}
	}
	
	private boolean searchUser(String Username) {
		File file = new File("Files\\"+Username+".txt");
		if(file.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

}
