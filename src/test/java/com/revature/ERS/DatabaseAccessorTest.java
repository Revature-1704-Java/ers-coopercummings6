package com.revature.ERS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import org.junit.Test;

public class DatabaseAccessorTest 
{
	//methods to test:
	//get password
	@Test
	public void getPasswordShouldReturnStringGivenValidID()
	{
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		assertTrue("1234".equals(databaseAccessor.getPassword(1)));
	}
	@Test
	public void getPasswordDoesntReturnPasswordForInvalidID()
	{
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		assertTrue(databaseAccessor.getPassword(0) == null);
	}
	//get Reimbursement requests
	@Test
	public void getReimbursementRequestsShouldReturnListOfFOrms()
	{
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		assertTrue(databaseAccessor.getReimbursementRequests(1).getClass() == (new ArrayList<RequestForm>()).getClass());
	}
	
	//submit request
	
	//update request
	
}
