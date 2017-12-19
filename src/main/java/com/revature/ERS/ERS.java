package com.revature.ERS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ERS 
{

	public static void main(String[] args) 
	{
		//Ask user if manager or if submitting a request
		System.out.println("Are you submitting a reimbursement request or a manager approving requests?");
		System.out.println("1: Submitting a request");
		System.out.println("2: Approving requests");
		System.out.println("3: Exit");
		//get value
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int input = in.nextInt();
		//call method that validates user with id and password
		System.out.println("Please enter your employee ID number to login: ");
		int idNo = in.nextInt();
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		String pw = databaseAccessor.getPassword(idNo);
		System.out.println("please enter your password");
		String offeredPassword = in.next();
		//call either reimbursement request form method or form approval method depending on
		//previous answer
		if(offeredPassword.equals(pw))
		{
			System.out.println("logged in successfully");
			switch (input) 
			{
			case 1:
				//submit form stuff
				RequestForm formToSubmit = new RequestForm();
				formToSubmit.setRequesterID(idNo);
				System.out.println("Please enter the amount of the reimbursement: ");
				formToSubmit.setAmount(in.nextDouble());
				System.out.println("Please enter a brief description of the reimbursement request");
				in.nextLine();
				formToSubmit.setDescription(in.nextLine());
				
				//pass formToSubmit to databaseAccessor
				databaseAccessor.submitRequest(formToSubmit);
				System.out.println("Form submitted");
				break;
			case 2:
				//approve form stuff
				//get forms to approve
				ArrayList<RequestForm> formsForApproval = databaseAccessor.getReimbursementRequests(idNo);
					
				for(RequestForm form : formsForApproval)
				{
					//output form
					System.out.println("Reimbursement Request Form");
					System.out.println("Requester ID: " + form.getRequesterID());
					System.out.println("Requester Name: " + form.getRequesterName());
					System.out.println("Amount of Request: $" + form.getAmount());
					System.out.println("Description: " + form.getDescription());
					//ask for approval
					System.out.println("Do you approve this reques Y/N?");
					String response = in.next();
					if(response.equals("Y") || response.equals("y"))
					{
						//update as approved
						//approved requests are not completed because accounting still needs to deal with them
						databaseAccessor.updateRequest(form.getRequestID(), '1', '0');
					}
					else
					{
						//update as disapproved
						//disapproved requests are completed because a new request is needed to appeal the decision
						databaseAccessor.updateRequest(form.getRequestID(), '0', '1');
					}
				}
				System.out.println("No more forms for approval.");
				break;
			default:
				break;
			}//end switch
		}//end if
		else
		{
			System.out.println("Incorrect Password");
		}
	}//end main
}//end class

