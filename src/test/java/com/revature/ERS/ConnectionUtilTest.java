package com.revature.ERS;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionUtilTest {

	@Test
	public void ConnectionUtilReturnsConnection() {
		try {
			Connection connection = ConnectionUtil.getConnection();
			assertTrue(connection instanceof Connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
