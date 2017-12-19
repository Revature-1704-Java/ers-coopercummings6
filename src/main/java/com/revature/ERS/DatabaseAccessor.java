package com.revature.ERS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.ERS.RequestForm;

public class DatabaseAccessor 
{
	/*
	 * method to add a new request to database. Will not add the request if it is incomplete.
	 * @param form a request form to be submitted to the database. The requesterId, amount,
	 * description, completed, and approved fields must be set before calling, but requestID
	 * can and should be left alone
	 */
	public void submitRequest(RequestForm form)
	{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		if(form.getAmount() != 0 && form.getDescription() != null && form.getRequesterID() != 0)
		{
			try(Connection conn = ConnectionUtil.getConnection())
			{
				String sql = "INSERT INTO Reimbursement (ReimbursementID, Description, Amount, Approved, Completed) VALUES(Reimbursement_keys.NEXTVAL, ?, ?, '0', '0')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, form.getDescription());
				ps.setDouble(2, form.getAmount());
				ps.executeUpdate();
				sql = "INSERT INTO EmployeeReimbursements (EmployeeID, ReimbursementID) VALUES (?, (SELECT MAX(ReimbursementID) FROM Reimbursement))";
				ps2  = conn.prepareStatement(sql);
				ps2.setInt(1, form.getRequesterID());
				ps2.executeUpdate();
				
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
			finally 
			{
				if (ps != null) 
				{
					try 
					{
						ps.close();
					} catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (ps2 != null) 
				{
					try 
					{
						ps.close();
					} catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (rs != null) 
				{
					try 
					{
						rs.close();
					} catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/*
	 *method to update a request from a database 
	 *@Param ReqID			ID number of the request
	 *@Param aprovalStatus	0 or 1 to represent false or true, for whether the request has been approved or not
	 *@Param completed		0 or 1 to represent false or true, for whether the request has been completed or not
	 */
	public void updateRequest(int reqID, char approvalStatus, char completed)
	{
		PreparedStatement preparedStatement = null;
		try(Connection connection = ConnectionUtil.getConnection())
		{
			String sql = "UPDATE Reimbursement SET Approved = ?, Completed = ? WHERE ReimbursementID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, String.valueOf(approvalStatus));
			preparedStatement.setString(2, String.valueOf(completed));
			preparedStatement.setInt(3, reqID);
			preparedStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (preparedStatement != null) 
			{
				try 
				{
					preparedStatement.close();
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * method to retrieve requests associated with a manager,
	 * @param IDno manager's employee Id number
	 * @return returns an arraylist containing the ID numbers, names of requesters, amounts of requests, and descriptions.
	 * will be empty if there are no requests.
	 */
	public ArrayList<RequestForm> getReimbursementRequests(int IDno)
	{
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		ArrayList<RequestForm> forms = new ArrayList();
		try(Connection conn = ConnectionUtil.getConnection() )
		{
			String sql = "SELECT Employee.EmployeeID, Employee.EmployeeName, Reimbursement.Amount,\r\n" + 
					"Reimbursement.Description, Reimbursement.ReimbursementID\r\n" + 
					"FROM\r\n" + 
					"Employee INNER JOIN EmployeeReimbursements\r\n" + 
					"ON Employee.EmployeeID = EmployeeReimbursements.EmployeeID\r\n" + 
					"INNER JOIN Reimbursement\r\n" + 
					"ON Reimbursement.ReimbursementID = EmployeeReimbursements.ReimbursementID\r\n" + 
					"WHERE Employee.ManagerID = ? AND Approved = '0' AND Completed = '0'\r\n";
			pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, IDno);
			rs = pStatement.executeQuery();	
			while(rs.next())//loop through result set and add rows to arraylist of request forms
			{
				forms.add(new RequestForm());
				forms.get(forms.size()-1).setRequesterID(rs.getInt(1));
				forms.get(forms.size()-1).setRequesterName(rs.getString(2));
				forms.get(forms.size()-1).setAmount(rs.getDouble(3));
				forms.get(forms.size()-1).setDescription(rs.getString(4));
				forms.get(forms.size()-1).setRequestID(rs.getInt(5));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pStatement != null) 
			{
				try 
				{
					pStatement.close();
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return forms;
	}
	
	/*
	 * This method retrieves a password in order to verify employees before
	 * allowing request forms or approvals
	 * @param ID 	The employee ID number of the employee logging in
	 * @return 		The password of the employee, so that it can be checked against their input
	 */
	public String getPassword(int ID)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String pw = null;
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT Password FROM EMPLOYEE WHERE EmployeeID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);
			rs = ps.executeQuery();
			if(rs.next())
			{
				pw = rs.getString(1);
			}
			else
			{
				pw = null;
			}
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		finally 
		{
			if (ps != null) 
			{
				try 
				{
					ps.close();
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) 
			{
				try 
				{
					rs.close();
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return pw;
	}

}

