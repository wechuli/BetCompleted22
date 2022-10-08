package test.EmaitzakIpini;

import static org.junit.Assert.*;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Sport;
import domain.Team;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAWTest {

	//sut:system under test
	static DataAccess sut=new DataAccess();
		
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
		
	@Test
	public void test() {
		Team team1= new Team("Almeria");
		Team team2= new Team("Athletic");
		Event ev111=new Event(1, "Almeria-Athletic", UtilDate.newDate(2022,10,17), team1, team2);
		Sport sp1=new Sport("Futbol");
		sp1.addEvent(ev111);
		Question q1=ev111.addQuestion("Zeinek irabaziko du partidua?",1);
		Quote quote111 = q1.addQuote(1.3, "1", q1);
		
		try {
			testDA.open();
			testDA.createEvent(ev111);
			testDA.close();
			
			try {
				sut.EmaitzakIpini(quote111);
			} catch (EventNotFinished e) {
				fail();
			}
			
		}finally {
			testDA.open();
			testDA.removeEvent(ev111);
			testDA.close();
		}
	}

}
