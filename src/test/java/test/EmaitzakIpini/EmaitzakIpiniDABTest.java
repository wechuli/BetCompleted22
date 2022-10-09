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
	
	//null balioa pasatzen dugu parametro bezala
	@Test
	public void test1() {
			
		try {
			sut.EmaitzakIpini(null);
			fail("Parametro bezala sartu den Quote-a ez dago datubasean");
		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
	//Quote-a ez dago datu basean
	@Test 
	public void test2() {
		Team team1= new Team("Almeria");
		Team team2= new Team("Athletic");
		Event ev111=new Event(1, "Almeria-Athletic", UtilDate.newDate(2022,10,17), team1, team2);
		Sport sp1=new Sport("Futbol");
		sp1.addEvent(ev111);
		Question q1=ev111.addQuestion("Zeinek irabaziko du partidua?",1);
		Quote quote111 = q1.addQuote(1.3, "1", q1);
			
		try {
			sut.EmaitzakIpini(quote111);
			fail("Parametro bezala sartu den Quote-a ez dago datubasean");
		} catch (Exception e) {
			
		}
	}
	
	//Gertaera ez da amaitu
	@Test
	public void test3() {
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
				fail("Gertaera ez da amaitu oraindik");
			} catch (EventNotFinished e) {
				
			}
			
		}finally {
			testDA.open();
			testDA.removeEvent(ev111);
			testDA.close();
		}
	}

	//Apustu galdua
	@Test
	public void test4() {
		Registered reg333 = new Registered("Gotzon", "123", 1111);
		Team team1= new Team("Almeria");
		Team team2= new Team("Athletic");
		Event ev111=new Event(1, "Almeria-Athletic", UtilDate.newDate(2022,10,8), team1, team2);
		Sport sp1=new Sport("Futbol");
		sp1.addEvent(ev111);
		Question q1=ev111.addQuestion("Zeinek irabaziko du partidua?",1);
		Quote quote111 = q1.addQuote(1.3, "1", q1);
		ApustuAnitza apA1 = new ApustuAnitza(reg333, 5.0);
		Apustua ap1 = new Apustua(apA1, quote111);
		apA1.addApustua(ap1);
		
		try {
			testDA.open();
			testDA.createEvent(ev111);
			testDA.close();
			
			try {
				sut.EmaitzakIpini(quote111);
				assertEquals("galduta", ap1.getEgoera());
			} catch (EventNotFinished e) {
				fail("Gertaera ez da amaitu oraindik");
			}
			
		}finally {
			testDA.open();
			testDA.removeEvent(ev111);
			testDA.close();
		}
	}
	
	//Apustu irabazia
	@Test
	public void test5() {
		Registered reg334 = new Registered("Gotzon", "123", 1111);
		Team team1= new Team("Almeria");
		Team team2= new Team("Athletic");
		Event ev111=new Event(1, "Almeria-Athletic", UtilDate.newDate(2022,10,8), team1, team2);
		Sport sp1=new Sport("Futbol");
		sp1.addEvent(ev111);
		Question q1=ev111.addQuestion("Zeinek irabaziko du partidua?",1);
		Quote quote111 = q1.addQuote(1.3, "1", q1);
		ApustuAnitza apA1 = new ApustuAnitza(reg334, 5.0);
		Apustua ap1 = new Apustua(apA1, quote111);
		apA1.addApustua(ap1);
		
		try {
			testDA.open();
			testDA.createEvent(ev111);
			testDA.close();
			
			try {
				sut.EmaitzakIpini(quote111);
				assertEquals("irabazita", ap1.getEgoera());
			} catch (EventNotFinished e) {
				fail("Gertaera ez da amaitu oraindik");
			}
			
		}finally {
			testDA.open();
			testDA.removeEvent(ev111);
			testDA.close();
		}
	}
}
