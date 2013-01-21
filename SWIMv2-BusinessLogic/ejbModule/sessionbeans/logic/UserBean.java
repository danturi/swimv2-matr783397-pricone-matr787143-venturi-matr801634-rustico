/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.logic;

import entities.Ability;
import entities.AbilityRequest;
import entities.FriendshipRequest;
import entities.HelpRequest;
import entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;




/**
 *
 * @author MARCO
 */
@Stateless(name = "UserBean")
public class UserBean implements UserBeanRemote {
	/*@EJB
    private UserFacadeLocal userFacade;*/

	@PersistenceContext(unitName = "SWIMv2-ejbPU")
	private EntityManager em;

	public void checkInitialConditions(){
		/**
		 * 
		 * CREAZIONE UTENTE AMMINISTRATORE SE NON ANCORA PRESENTE
		 */
		User admin = em.find(User.class, "ad");
		if(admin==null){
			admin = new User();
			admin.setEmail("ad");
			admin.setFirstname("admin");
			admin.setLastname("admin");
			admin.setPassword(MySHA512DigestClass.sha512HexDigest("ad"));
			admin.setRegisteredOn(new Date());

			List<Group> groups = new ArrayList<Group>();
			groups.add(Group.ADMINISTRATOR);
			groups.add(Group.USER);
			groups.add(Group.DEFAULT);
			admin.setGroups(groups);

			em.persist(admin);
		}

		/**
		 * 
		 * POPOLAZIONE INSIEME DELLE ABILITA' SE NON ANCORA POPOLATO
		 */

		TypedQuery<Ability> query_abl = em.createQuery("SELECT abl FROM Ability abl", Ability.class);
		List<Ability> listAbilities = query_abl.getResultList();
		if(listAbilities.isEmpty()){
			Ability idraulico = new Ability();
			idraulico.setDescription("Idraulico");
			//listAbilities.add(idraulico);
			em.persist(idraulico);
			Ability elettricista = new Ability();
			elettricista.setDescription("Elettricista");
			//listAbilities.add(elettricista);
			em.persist(elettricista);
		}


	}

	@Override
	public SwimResponse findAll() {

		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr ORDER BY usr.registeredOn ASC", User.class);
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Lista utenti ricevuta", query.getResultList());
		return swimResponse;
	}

	@Override
	public void createUser(User user) {
		checkInitialConditions();
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
	public SwimResponse getAbilityList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			user.getAbilityList().size();
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista abilità effettuato", user.getAbilityList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
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
			user.getFriendshipRequestList().size();
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

	@Override
	public SwimResponse sendAbilityReq(String emailUserFrom, String abilityDecription, String description) {
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		Ability ability = null;
		try{
			TypedQuery<Ability> query = em.createNamedQuery("Ability.findByDescription", Ability.class).setParameter("description", abilityDecription);
			ability = query.getSingleResult();
		} catch (Exception e){
		}
		if(ability!=null) {
			if(userFrom!=null){
				List<Ability> abilities_holded = userFrom.getAbilityList();

				for(Ability ability_holded : abilities_holded){
					if(ability_holded.getAbilityId().equals(ability.getAbilityId())){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta aggiunta abilità non inviata perchè già l'utente già possiede tale abilità.\n");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilità NON inviata perchè già l'utente già possiede tale abilità.\n");
						return swimResponse;
					}
				}

				List<AbilityRequest> listAbilityReq = userFrom.getAbilityRequestList();

				for(AbilityRequest abilityReqOld : listAbilityReq){
					if(abilityReqOld.getAbilityId().getAbilityId().equals(ability.getAbilityId())){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta aggiunta abilità non inviata perchè esiste già una richiesta pendente per tale abilità.\n");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilità NON inviata perchè esiste già una richiesta pendente per tale abilità.\n");
						return swimResponse;
					}
				}

				AbilityRequest abilityReq = new AbilityRequest();
				abilityReq.setAbilityId(ability);
				abilityReq.setDescription(description);
				abilityReq.setDatetime(new Date());
				abilityReq.setAcceptanceStatus(false);
				abilityReq.setUser(userFrom);
				userFrom.getAbilityRequestList().add(abilityReq);
				em.persist(userFrom);


				swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilità inviata con SUCCESSO!.");
				System.out.println("\nUSERBEAN: Richiesta aggiunta abilità inviata con SUCCESSO!.");

			}else{
				swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
				System.out.println("USERBEAN (sendAbilityReq): Utente non valido.\n");
			}
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Abilità non valida.");
			System.out.println("USERBEAN (sendAbilityReq): Abilità non valida.\n");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse replyToFriendshipReq(String emailUserFrom, String emailUserTo, boolean replyValue) {
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		User userTo = find(emailUserTo);
		if(userFrom!=null && userTo!=null && userFrom!=userTo){
			if(!(userFrom.getUserList().contains(userTo)) && !(userTo.getUserList().contains(userFrom))){

				List<FriendshipRequest> listUserTo = userTo.getFriendshipRequestList();

				FriendshipRequest currentFriendReq = null;

				for(FriendshipRequest friendReq: listUserTo){
					if(friendReq.getFromUser().equals(userFrom) && friendReq.getToUser().equals(userTo)){			
						currentFriendReq = friendReq;
					}
				}
				if(currentFriendReq!=null){

					currentFriendReq.setAcceptanceStatus(replyValue);

					if(replyValue==true){
						
						userFrom.getUserList().add(userTo);
						userTo.getUserList1().add(userFrom);
						em.persist(userTo);
						em.persist(userFrom);
						
						swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta d'amicizia ACCETTATA e amicizia stabilita con SUCCESSO.");
						System.out.println("\nUSERBEAN: Richiesta d'amicizia ACCETTATA e amicizia stabilita con SUCCESSO!");
					} else {
						userFrom.getSentFriendshipRequestList().remove(currentFriendReq);
						userTo.getFriendshipRequestList().remove(currentFriendReq);
						em.remove(currentFriendReq);
						em.persist(userTo);
						em.persist(userFrom);
						swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta d'amicizia RIFIUTATA e amicizia NON stabilita.");
						System.out.println("\nUSERBEAN: Richiesta d'amicizia RIFIUTATA. Amicizia NON stabilita.");
					}
					



				} else {
					swimResponse = new SwimResponse(SwimResponse.FAILED,"Non è presenta nessuna richiesta d'amicizia dall'utente indicato.");
					System.out.println("USERBEAN (replyToFriendshipReq): Non è presenta nessuna richiesta d'amicizia dall'utente indicato.");	
				}




			} else{
				swimResponse = new SwimResponse(SwimResponse.FAILED,"L'utente risulta già nella lista degli amici.");
				System.out.println("USERBEAN (replyToFriendshipReq): L'utente risulta già nella lista degli amici.");
			}

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
			System.out.println("USERBEAN (replyToFriendshipReq): Utente non valido.\n");
		}
		return swimResponse;
	}






}
