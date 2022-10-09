package test.businessLogic;


import java.util.Date;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import domain.Sport;
import domain.Team;
import domain.Transaction;
import domain.User;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	protected static EntityManager  db;
	TestDataAccess dbManagerTest;
 	
    
	   public TestFacadeImplementation()  {
			
			System.out.println("Creating TestFacadeImplementation instance");
			ConfigXML c=ConfigXML.getInstance();
			dbManagerTest=new TestDataAccess(); 
			dbManagerTest.close();
		}
		
		 
		public boolean removeEvent(Event ev) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeEvent(ev);
			dbManagerTest.close();
			return b;

		}
		
		public void ApustuaIrabazi(ApustuAnitza apustua) {
			ApustuAnitza apustuAnitza = db.find(ApustuAnitza.class, apustua.getApustuAnitzaNumber());
			Registered reg = (Registered) apustuAnitza.getUser();
			Registered r = (Registered) db.find(User.class, reg.getUsername());
			db.getTransaction().begin();
			apustuAnitza.setEgoera("irabazita");
			Double d=apustuAnitza.getBalioa();
			for(Apustua ap: apustuAnitza.getApustuak()) {
				d = d*ap.getKuota().getQuote();
			}
			r.updateDiruKontua(d);
			r.setIrabazitakoa(r.getIrabazitakoa()+d);
			r.setZenbat(r.getZenbat()+1);
			Transaction t = new Transaction(r, d, new Date(), "ApustuaIrabazi"); 
			db.persist(t);
			db.getTransaction().commit();
		}
		
		public void EmaitzakIpini(Quote quote) throws EventNotFinished{
			
			Quote q = db.find(Quote.class, quote); 
			String result = q.getForecast();
			
			if(new Date().compareTo(q.getQuestion().getEvent().getEventDate())<0)
				throw new EventNotFinished();

			Vector<Apustua> listApustuak = q.getApustuak();
			db.getTransaction().begin();
			Question que = q.getQuestion(); 
			Question question = db.find(Question.class, que); 
			question.setResult(result);
			for(Quote quo: question.getQuotes()) {
				for(Apustua apu: quo.getApustuak()) {
					
					Boolean b=apu.galdutaMarkatu(quo);
					if(b) {
						apu.getApustuAnitza().setEgoera("galduta");
					}else {
						apu.setEgoera("irabazita");
					}
				}
			}
			db.getTransaction().commit();
			for(Apustua a : listApustuak) {
				db.getTransaction().begin();
				Boolean bool=a.getApustuAnitza().irabazitaMarkatu();
				db.getTransaction().commit();
				if(bool) {
					this.ApustuaIrabazi(a.getApustuAnitza());
				}
			}
		}
		public boolean gertaerakSortu(String description,Date eventDate, String sport) {
			boolean b = true;
			db.getTransaction().begin();
			Sport spo =db.find(Sport.class, sport);
			if(spo!=null) {
				TypedQuery<Event> Equery = db.createQuery("SELECT e FROM Event e WHERE e.getEventDate() =?1 ",Event.class);
				Equery.setParameter(1, eventDate);
				for(Event ev: Equery.getResultList()) {
					if(ev.getDescription().equals(description)) {
						b = false;
					}
				}
				if(b) {
					String[] taldeak = description.split("-");
					Team lokala = new Team(taldeak[0]);
					Team kanpokoa = new Team(taldeak[1]);
					Event e = new Event(description, eventDate, lokala, kanpokoa);
					e.setSport(spo);
					spo.addEvent(e);
					db.persist(e);
				}
			}else {
				return false;
			}
			db.getTransaction().commit();
			return b;
		}
		
		/*public Event addEventWithQuestion(String desc, Date d, String q, float qty) {
			dbManagerTest.open();
			Event o=dbManagerTest.addEventWithQuestion(desc,d,q, qty);
			dbManagerTest.close();
			return o;

		}*/

}
