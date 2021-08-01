package com.SimpliLearn.Projects.Phase1;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Registration registration = new Registration();
		Login login = new Login();
		Scanner mainScanner = new Scanner(System.in);
		boolean isValid=false;
		
		while(!isValid) {
			new Welcome();
			String actionInput = mainScanner.nextLine();
			try {
				int action = Integer.parseInt(actionInput);
				if(action==1 || action ==2) {
					switch (action) {
						case 1:
							registration.userRegistration();
							isValid=true;
							break;
			
						case 2:
							login.userLogin();
							isValid=true;
							break;
							
						default:
							isValid=true;
							break;
					}
				}
				else {
					System.out.println("Enter valid action number.\n");
				}
			} catch (Exception e) {
				System.out.println("Enter numbers only\n");
			}
		}
		mainScanner.close();
	}
}