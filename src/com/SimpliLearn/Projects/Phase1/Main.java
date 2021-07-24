package com.SimpliLearn.Projects.Phase1;
import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		Registration registration = new Registration();
		Login login = new Login();
		
		boolean isValid=false;
		while(!isValid) {
			new Welcome();
			try {
				int action = scanner.nextInt();
				scanner.nextLine();
				if(action!=1 || action !=2) {
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
					System.out.println("Enter valid action number.");
				}
			} catch (Exception e) {
				scanner.close();
				System.out.println("Enter options number");
				e.printStackTrace();
				isValid=true;
			}
		}
		scanner.close();
	}
}