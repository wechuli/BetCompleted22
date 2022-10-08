package test.EmaitzakIpini;

import static org.junit.Assert.*;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDABTest {

	//sut:system under test
	static DataAccess sut=new DataAccess();
	
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
		 
	@Test
	public void test() {
		
	}

}
