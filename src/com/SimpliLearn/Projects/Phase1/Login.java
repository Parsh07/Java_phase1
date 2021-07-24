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
	private static Registration registration;
	File file;
	ArrayList<String> listOfFiles;
	
	public Login() {
		registration = new Registration();
	}
	
	public void userLogin() {
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println("*****************************************************\n"
				+ "	  --Welcome to Login Page--			\n"
						+ "******************************************************");
	
		while(!isCredentialsValid) {
			System.out.print("Enter Username : ");
			String Username = scan.nextLine();
			System.out.println();
			System.out.print("Enter Password : ");
			String Password = scan.nextLine();
			File file = new File("Credentials.txt");
			if(file.exists()) {
				if(validateCredentials(Username, Password)) {
					System.out.println("Login successful\n");
					registration.setUsername(Username);
					registration.setPassword(Password);
					options();
					isCredentialsValid=true;
				}
			}
			else {
				System.out.println("Do you want to register the user? (enter Y/N to answer) : ");
				String isRegister = scan.nextLine();
				if(isRegister.toUpperCase().equals("Y")) {
					registration.userRegistration();
				}
			}
		}
		scan.close();
	}
	
	public boolean validateUsername(String Username) {
		Scanner scan;
		try {
			file = new File("Credentials.txt");
			if(file.exists()) {
				FileReader fr = new FileReader("Credentials.txt");
				scan = new Scanner(fr);

				while(scan.hasNextLine()) {
					String data = scan.nextLine();
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
				scan.close();
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
		Scanner scan;
		try {
			file = new File("Credentials.txt");
			if(file.exists()) {
				FileReader fr = new FileReader("Credentials.txt");
				if(fr != null) {
					scan = new Scanner(fr);
				
					while(scan.hasNextLine()) {
						String data = scan.nextLine();
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
					scan.close();
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
		Scanner scan = new Scanner(System.in);
		boolean isOptionSelected=false;
		while(!isOptionSelected) {
			System.out.println("\n\nEnter your choice (numbers) : \n"
								+"1. FETCH ALL FILEs\n"
								+"2. STORE CREDENTIALS\n"
								+"3. FETCH ALL STORED CREDENTIALS\n"
								+"4. DELETE USER CREDENTIAL\n"
								+"5. SEARCH USER\n"
								+"6. Quit");
			
			int choice;
			choice = scan.nextInt();
			if(choice>=1 || choice<=5) {
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
		scan.close();
	}

	public void storeCredential() {
		Scanner scan = new Scanner(System.in);
		try {
			System.out.print("Enter credentials : ");
			String credentials = scan.nextLine();
			registration.setPassword(credentials);
			FileWriter userFile;
			
			System.out.print("\nDo you want to store this credentials for another user? (enter Y/N to answer) : ");
			String storeCreds = scan.nextLine();
			if(storeCreds.toUpperCase().equals("Y")) {
				System.out.print("\nEnter username for whom you want to store the credentials (enter username) : ");
				registration.setUsername(scan.nextLine());
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
					System.out.print("\nFile does not exist for - "+registration.getUsername()+","
									+"\nDo you want to create a file ? (enter Y/N to answer) : ");
					storeCreds = scan.nextLine();
					if(storeCreds.toUpperCase().equals("Y")) {
						FileWriter fr = new FileWriter("Files\\"+registration.getUsername()+".txt");
						fr.write("*****************Credentials*******************\n");
						fr.write(registration.getPassword());
						fr.write("\r\n");
						fr.close();
						System.out.println("Credentials are stored successfully in a respective userfile - "+registration.getUsername());
						isFileExist=true;
					}
					else if(storeCreds.toUpperCase().equals("N")) {
						isFileExist=true;
					}
				}
			}
		} catch (IOException e) {
			scan.close();
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
	
	public void fetchCredentials() {
		Scanner scan;
		try {
			File file = new File("Files\\"+registration.getUsername()+".txt");
			if(file.exists()) {
				FileReader fr = new FileReader("Files\\"+registration.getUsername()+".txt");
				scan = new Scanner(fr);
				while(scan.hasNextLine()) {
					System.out.println(scan.nextLine());
				}
				scan.close();
			}
			else {
				System.out.println("File not found");
			}
		} catch (Exception e) {
				System.out.println("File not found while fetching records.");
				e.printStackTrace();
		}
	}
	
	public void deleteFile() {
		try {
			File file = new File("Files\\"+registration.getUsername()+".txt");
			if(file.exists()) {
				if(file.delete()) {
//				if(Files.deleteIfExists(Paths.get(registration.getUsername()))) {
					System.out.println("File deletion successful");
				}
				else {
					System.out.println("File deletion failed");
				}
			}
			else {
				System.out.println("File not found for user");
			}
		} catch (Exception e) {
			System.out.println("No such file exists");
			e.printStackTrace();
		}
	}
	
	public void searchUser() {
		System.out.print("Enter username : ");
		Scanner scan = new Scanner(System.in);
		if(searchUser(scan.nextLine())){
			System.out.println("User exists");
			scan.close();
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
