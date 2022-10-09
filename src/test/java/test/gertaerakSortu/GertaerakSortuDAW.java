package test.gertaerakSortu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Team;

public class TestGertaerakSortu {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;
	//private DataAccessInterface d;
	//static DataAccess sut=new DataAccess();
	ConfigXML  c=ConfigXML.getInstance();

	
	public TestGertaerakSortu()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
			  
    	   }
		
	}
	public void close(){
		db.close();
		
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		/**
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }*/
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
	/*
		@Test
		public void test1() {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date;
			try {
				date = format.parse("23/12/2020");
				Boolean emaitza= sut.gertaerakSortu("Deskripzioa",date,"");
				Boolean esperotakoa=false;
				System.out.println(emaitza);
				assertTrue(esperotakoa.compareTo(emaitza)==0);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
		*/
		
		/*
		@Test
		public void test2() {
			//Event ev1=new Event(1, "Atletico-Athletic", UtilDate.newDate(year,month,17), team1, team2);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date;
			Team bat = new Team("Berria3");
			Team bi = new Team("Berria2");
			
			try {
				date = format.parse("17/11/2022");
				//System.out.println(db.isOpen());
			
				Boolean emaitza= sut.gertaerakSortu("Berria3-Berria2",date,"Tennis");
		 
				System.out.println(db.isOpen());
				Boolean esperotakoa=true;
				assertTrue(esperotakoa.compareTo(emaitza)==0);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}			
			
		}*/
		
		/*
		@After
		public void a () {
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date;
			Team bat = new Team("Berria2");
			Team bi = new Team("Berria2");
			try {
				date = format.parse("17/11/2022");
				Event event = new Event("Berria2-Berria2",date,bat,bi);
				sut.gertaeraEzabatu(event);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		
		
}

