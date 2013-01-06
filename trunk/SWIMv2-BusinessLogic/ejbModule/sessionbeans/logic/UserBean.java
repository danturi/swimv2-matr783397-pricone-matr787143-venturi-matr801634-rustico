/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.logic;

import entities.AbilityRequest;
import entities.FriendshipRequest;
import entities.HelpRequest;
import entities.User;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sessionbeans.facades.UserFacadeLocal;



/**
 *
 * @author MARCO
 */
@Stateless
public class UserBean implements UserBeanLocal {
	/*@EJB
    private UserFacadeLocal userFacade;*/

	@PersistenceContext(unitName = "SWIMv2-ejbPU")
	private EntityManager em;


	@Override
	public SwimResponse findAll() {

		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr ORDER BY usr.registeredOn ASC", User.class);
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Lista utenti ricevuta", query.getResultList());
		return swimResponse;
	}

	@Override
	public void createUser(User user) {
		em.persist(user);
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public void removeUser(String email) {
		User user =  find(email);
		if (user != null) {
			em.remove(user);
		}
	}

	@Override
	public void removeUser(User user) {
		if (user != null && user.getEmail()!=null && em.contains(user)) {
			em.remove(user);
		}
	}

	@Override
	public User find(String email) {
		return em.find(User.class, email);
	}
	@Override
	public void detachUser(User user) {
		em.detach(user);
	}

	@Override
	public SwimResponse getFriendsList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista degli amici effettuato", user.getUserList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse getFriendshipReqList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista richieste di amicizia effettuato", user.getFriendshipRequestList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse getHelpReqList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista richieste di aiuto effettuato", user.getHelpRequestList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse sendFriendshipReq(String emailUserFrom, String emailUserTo) {
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		User userTo = find(emailUserTo);
		if(userFrom!=null && userTo!=null){// se i due utenti esistono nel sistema...

			if(!(userFrom.getUserList().contains(userTo)) && !(userTo.getUserList().contains(userFrom))){ // se gli utenti non sono gi� amici...

				List<FriendshipRequest> listUserTo = userTo.getFriendshipRequestList();
				
				while(listUserTo.iterator().hasNext()){// e se non ci sono richieste d'amicizia gi� pendenti
					FriendshipRequest friendReq = listUserTo.iterator().next();
					if(friendReq.getFromUser().equals(userFrom)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perch� gi� presente una pendente.\n");
						System.out.println("\nUSERBEAN: Richiesta d'amicizia NON inviata perch� gi� presente una pendente.\n");
						return swimResponse;
					}
					listUserTo.remove(friendReq);
				}


				FriendshipRequest friendshipReq = new FriendshipRequest();
				friendshipReq.setFromUser(userFrom);
				friendshipReq.setToUser(userTo);
				friendshipReq.setAcceptanceStatus(false);
				friendshipReq.setDatetime(new Date());
				userTo.getFriendshipRequestList().add(friendshipReq);
				updateUser(userTo);
				updateUser(userFrom);
				
				swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta d'amicizia inviata con SUCCESSO!.");
				System.out.println("\nUSERBEAN: Richiesta d'amicizia inviata con SUCCESSO!\n");
			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"L'utente risulta gi� nella lista degli amici.");
				System.out.println("USERBEAN: L'utente risulta gi� nella lista degli amici.");
			}

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
			System.out.println("USERBEAN: Utente non valido.\n");
		}
		return swimResponse;
	}






}
