package sessionbeans.test;

import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.Date;

import sessionbeans.logic.SwimResponse;
import sessionbeans.logic.UserBean;
import sessionbeans.logic.UserBeanRemote;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import entities.Ability;
import entities.User;

public class TestUserBean {

	private static EJBContainer ejbContainer;
    private static Context ctx;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		 ejbContainer = EJBContainer.createEJBContainer();
	     System.out.println("Starting the container");
	     ctx = ejbContainer.getContext();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
		 ejbContainer.close();
         System.out.println("Closing the container");
	}

	@Test
	public void test() throws NamingException {
		
		UserBeanRemote userBeanRemote = (UserBeanRemote) ctx.lookup("java:global/classes/UserBean");
		SwimResponse swimResponse = new SwimResponse();
		User user = new User();
		user.setEmail("prova");
		user.setPassword("password");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		Date date= new Date();
		user.setRegisteredOn(date);
		
		//test creazione utente
		
		User utente=userBeanRemote.createUser(user);
		 
		//controllo che i dati inseriti corrispondano
		 
		 assertEquals("prova",utente.getEmail());
		 assertEquals("password",utente.getPassword());
		 assertEquals("firstname",utente.getFirstname());
		 assertEquals("lastname",utente.getLastname());
		 
		 //test cambio informazioni utente
		 
		 swimResponse = userBeanRemote.changeUserInfo("prova", "ciao", "", "", "", "", "", "");
		 utente = userBeanRemote.find("prova");
		 assertEquals("ciao", utente.getLastname());
		 assertEquals("ok", swimResponse.getStatusMsg());
		 
		 try {
			 swimResponse = userBeanRemote.changeUserInfo(null, "ciao", "", "", "", "", "", "");
		 } catch (Exception e){
			 //mi aspetto che scatti eccezione perchè l'utente cercato è NULL
			 swimResponse.setStatusMsg("fail");
			 assertEquals("fail", swimResponse.getStatusMsg());
		 }
		 
		 //test richiesta abilità
		 Ability abilità = new Ability();
		 abilità.setAbilityId(Long.valueOf(1));
		 
		 swimResponse= userBeanRemote.sendAbilityReq("prova", abilità.getAbilityId(), "");
		 userBeanRemote.replyToAbilityReq("prova", abilità.getAbilityId(), true);
		 
		 //verifico che l'utente possieda l'abilità richiesta
		 utente = userBeanRemote.find("prova");
		 swimResponse=userBeanRemote.getAbilityList("prova");
		 List<Ability> listaAbilità = (List<Ability>) swimResponse.getData();
		 assertEquals(true,listaAbilità.contains(abilità)); 
		 
		 //creazione altro utente 
		 User user2 = new User();
			user2.setEmail("prova2");
			user2.setPassword("password2");
			user2.setFirstname("firstname2");
			user2.setLastname("lastname2");
			Date date2= new Date();
			user2.setRegisteredOn(date2);
		 User utente2 =userBeanRemote.createUser(user2);
			
		 // test richiesta di amicizia
			userBeanRemote.sendFriendshipReq("prova", "prova2");
			userBeanRemote.replyToFriendshipReq("prova", "prova2", true);
			swimResponse = userBeanRemote.getFullFriendsList("prova");
			List<User> amici= (List<User>) swimResponse.getData();
			assertEquals(true , amici.contains(utente2));
			swimResponse = userBeanRemote.getFullFriendsList("prova2");
			List<User> amici2= (List<User>) swimResponse.getData();
			assertEquals(true , amici2.contains(utente));
	}

}
