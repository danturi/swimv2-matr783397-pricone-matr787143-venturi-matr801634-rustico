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
	public SwimResponse getSentFriendshipReqList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista richieste di amicizia effettuato", user.getSentFriendshipRequestList());
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
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista richieste di aiuto effettuato", user.getHelpReqList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse getSentHelpReqList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista richieste di aiuto effettuato", user.getSentHelpReqList());
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
		if(userFrom!=null && userTo!=null && userFrom!=userTo){// se i due utenti esistono nel sistema (e non sono lo stesso utente)...

			if(!(userFrom.getUserList().contains(userTo)) && !(userTo.getUserList().contains(userFrom))){ // se gli utenti non sono già amici...

				List<FriendshipRequest> listUserTo = userTo.getFriendshipRequestList();
				//List<FriendshipRequest> listSentByUserTo = userTo.getSentFriendshipRequestList();
				List<FriendshipRequest> listUserFrom = userFrom.getFriendshipRequestList();
				
				for(FriendshipRequest friendReq : listUserTo){
					if(friendReq.getFromUser().equals(userFrom)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perchè già presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta d'amicizia <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> NON inviata perchè già presente una UGUALE pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}
				
				for(FriendshipRequest friendReq : listUserFrom){
					if(friendReq.getFromUser().equals(userTo)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perchè già presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta d'amicizia <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> NON inviata perchè già presente una OPPOSTA pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}

				/*for(FriendshipRequest friendReq : listSentByUserTo){
					if(friendReq.getToUser().equals(userFrom)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perchè già presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta d'amicizia <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> NON inviata perchè già presente una OPPOSTA pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}*/

				//METODO ALTERNATIVO AL CICLO FOR SOPRA
				/*TypedQuery<FriendshipRequest> query = em.createQuery("SELECT frq FROM FriendshipRequest frq", FriendshipRequest.class);
				List<FriendshipRequest> list = query.getResultList();

				for(FriendshipRequest friendReq : list){
					if(friendReq.getFromUser().equals(userFrom) && friendReq.getToUser().equals(userTo)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perchè già presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta da <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> Richiesta d'amicizia NON inviata perchè già presente una pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}*/

				FriendshipRequest friendshipReq = new FriendshipRequest();
				friendshipReq.setFromUser(userFrom);
				friendshipReq.setToUser(userTo);
				friendshipReq.setAcceptanceStatus(false);
				friendshipReq.setDatetime(new Date());
				userFrom.getSentFriendshipRequestList().add(friendshipReq);
				userTo.getFriendshipRequestList().add(friendshipReq);		
				em.persist(userTo);
				em.persist(userFrom);
				
				swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta d'amicizia inviata con SUCCESSO!.");
				System.out.println("\nUSERBEAN: Richiesta d'amicizia inviata con SUCCESSO!\n");
			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"L'utente risulta già nella lista degli amici.");
				System.out.println("USERBEAN: L'utente risulta già nella lista degli amici.");
			}

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
			System.out.println("USERBEAN (sendFriendshipReq): Utente non valido.\n");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse sendHelpReq(String emailUserFrom, String emailUserTo, String description) {
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		User userTo = find(emailUserTo);
		if(userFrom!=null && userTo!=null && userFrom!=userTo){// se i due utenti esistono nel sistema (e non sono lo stesso utente)...

			HelpRequest helpReq = new HelpRequest();
			helpReq.setFromUser(userFrom);
			helpReq.setToUser(userTo);
			helpReq.setAcceptanceStatus(false);
			helpReq.setDatetime(new Date());
			helpReq.setDescription(description);
			userFrom.getSentHelpReqList().add(helpReq);
			userTo.getHelpReqList().add(helpReq);
			em.persist(userTo);
			em.persist(userFrom);

			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta d'aiuto inviata con SUCCESSO!.");
			System.out.println("\nUSERBEAN: Richiesta d'aiuto inviata con SUCCESSO!\n");


		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
			System.out.println("USERBEAN (sendHelpReq): Utente non valido.\n");
		}
		return swimResponse;
	}






}
