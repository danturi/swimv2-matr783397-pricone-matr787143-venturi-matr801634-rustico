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
			em.persist(idraulico);
			Ability elettricista = new Ability();
			elettricista.setDescription("Elettricista");
			em.persist(elettricista);
			Ability meccanico = new Ability();
			meccanico.setDescription("Meccanico");
			em.persist(meccanico);
			Ability falegname = new Ability();
			falegname.setDescription("Falegname");
			em.persist(falegname);
			Ability informatico = new Ability();
			informatico.setDescription("Informatico");
			em.persist(informatico);
			Ability architetto = new Ability();
			architetto.setDescription("Architetto");
			em.persist(architetto);
			Ability fabbro = new Ability();
			fabbro.setDescription("Fabbro");
			em.persist(fabbro);
			Ability webDesigner = new Ability();
			webDesigner.setDescription("Web Designer");
			em.persist(webDesigner);
			Ability sarto = new Ability();
			sarto.setDescription("Sarto");
			em.persist(sarto);
			Ability elettrauto = new Ability();
			elettrauto.setDescription("Elettrauto");
			em.persist(elettrauto);
			Ability carrozziere = new Ability();
			carrozziere.setDescription("Carrozziere");
			em.persist(carrozziere);

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
	public SwimResponse searchUserMatching(String userPrincipal, String lastname, String firstname, String city, String ability, String ability2, String friends){
		SwimResponse swimResponse;
		boolean onlyFriends = false;
		boolean filteredByCity = false;
		boolean filteredByAb1 = false;
		boolean filteredByAb2 = false;
		boolean filteredByLastname = false;
		boolean filteredByFirstname = false;
		if(friends!=null){
			if(friends.equals("on")) onlyFriends = true;
		}

		User usr = find(userPrincipal);
		if(usr!=null){
			List<User> globalList = new ArrayList<User>();
			List<User> resultList = new ArrayList<User>();
			List<User> resultList2 = new ArrayList<User>();
			List<User> resultList3 = new ArrayList<User>();
			List<User> resultList4 = new ArrayList<User>();

			if(onlyFriends){

				List<User> globalListOf1 = (List<User>) getFriendsList(userPrincipal).getData();
				List<User> globalListOf2 = (List<User>) getReversedFriendsList(userPrincipal).getData();
				for(User usr1: globalListOf1){
					globalList.add(usr1);
				}
				for(User usr2: globalListOf2){
					if(!globalList.contains(usr2)) globalList.add(usr2);
				}

				System.out.println("\n**** USERBEAN: globalList: "+globalList);
			} else {
				TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr ORDER BY usr.registeredOn ASC", User.class);
				globalList = query.getResultList();
			}

			for(User tryUser: globalList){ // FILTRO PER CITTA'
				if(!city.equals("") && tryUser.getCity()!=null){
					if(tryUser.getCity().equals(city)) {
						filteredByCity = true;
						resultList.add(tryUser);
					}
				} else {
					resultList.add(tryUser);
				}
			}
			//System.out.println("\n**** USERBEAN: MATCH LIST1: "+resultList);
			for(User tryUser2: resultList){// FILTRO PER ABILITA'
				tryUser2.getAbilityList().size();

				if(!ability.equals("0")){
					filteredByAb1=true;
					for(Ability ab: tryUser2.getAbilityList()){

						if(ab.getAbilityId().equals(Long.valueOf(ability)) && !resultList2.contains(tryUser2)){
							resultList2.add(tryUser2);
						}
					}
				}

				if(!ability2.equals("0")){
					filteredByAb2=true;
					for(Ability ab: tryUser2.getAbilityList()){

						if(ab.getAbilityId().equals(Long.valueOf(ability2)) && !resultList2.contains(tryUser2)){
							resultList2.add(tryUser2);
						}
					}
				}

			}
			if(filteredByAb1 || filteredByAb2) resultList=resultList2;
			System.out.println("\n**** USERBEAN: "+filteredByAb1+"  "+filteredByAb2);

			if(!lastname.equals("")){// FILTRO PER COGNOME
				filteredByLastname= true;
				for(User tryUser3: resultList){
					if(tryUser3.getLastname().equals(lastname)){
						filteredByLastname= true;
						resultList3.add(tryUser3);
					}
				}
				if(filteredByLastname) resultList=resultList3;
			}
			//System.out.println("\n**** USERBEAN: MATCH LIST2: "+resultList2);
			if(!firstname.equals("")){// FILTRO PER NOME
				for(User tryUser4: resultList){
					if(tryUser4.getFirstname().equals(firstname)){
						filteredByFirstname= true;
						resultList4.add(tryUser4);
					}
				}
				if(filteredByFirstname) resultList=resultList4;
			}

			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"",resultList);
			System.out.println("\n**** USERBEAN: MATCH LIST1: "+resultList);
			return swimResponse;

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"");
			return swimResponse;
		}

	}

	public SwimResponse changeUserInfo (String userPrincipal, String lastname,String firstname ,String city,String sex ,String age,String job ,String tel){
		SwimResponse swimResponse;
		User usr = find(userPrincipal);
		if (usr!=null){
			if(!lastname.equals("")) {
				usr.setLastname(lastname);
			}
			if(!firstname.equals("")) {
				usr.setFirstname(firstname);
			}
			if(!city.equals("")) {
				usr.setCity(city);
			}
			if(sex!=null){
				if(!sex.equals("")) {
					usr.setSex(sex);
				}
			}
			if(!age.equals("")) {
				usr.setAge(age);
			}
			if(!job.equals("")) {
				usr.setOccupation(job);
			}
			if(!tel.equals("")) {
				usr.setTel(tel);
			}
			em.persist(usr);
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"fail");
		}
		return swimResponse;	
	}

	@Override
	public SwimResponse getAbilitySet() {
		TypedQuery<Ability> query = em.createQuery("SELECT ab FROM Ability ab", Ability.class);
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Lista abilità ricevuta", query.getResultList());
		return swimResponse;

	}

	@Override
	public SwimResponse getNewAbilityReqList(){
		TypedQuery<AbilityRequest> query = em.createQuery("SELECT abr FROM AbilityRequest abr", AbilityRequest.class);
		List<AbilityRequest> allAbilityReqList = query.getResultList();
		List<AbilityRequest> newAbilityReqList = new ArrayList<AbilityRequest>();

		for(AbilityRequest abr: allAbilityReqList){
			if(!abr.getIsEvaluated()){
				newAbilityReqList.add(abr);
			}
		}
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"newAbilityReqList", newAbilityReqList);
		return swimResponse;
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
			user.getUserList().size();
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista degli amici effettuato", user.getUserList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse getReversedFriendsList(String email) {
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			user.getUserList1().size();
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista reversa degli amici effettuato", user.getUserList1());
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
			user.getSentFriendshipRequestList().size();
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
	public SwimResponse sendAbilityReq(String emailUserFrom, Long abilityId, String description) {
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		/*Ability ability = null;
		try{
			TypedQuery<Ability> query = em.createNamedQuery("Ability.findByDescription", Ability.class).setParameter("description", abilityDecription);
			ability = query.getSingleResult();
		} catch (Exception e){
		}*/
		Ability ability = em.find(Ability.class, abilityId);
		if(ability!=null) {
			if(userFrom!=null){
				List<Ability> abilities_holded = userFrom.getAbilityList();

				for(Ability ability_holded : abilities_holded){
					if(ability_holded.getAbilityId().equals(ability.getAbilityId())){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"abilityAlreadyOwned");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilità NON inviata perchè già l'utente già possiede tale abilità.\n");
						return swimResponse;
					}
				}

				List<AbilityRequest> listAbilityReq = userFrom.getAbilityRequestList();

				for(AbilityRequest abilityReqOld : listAbilityReq){
					if(abilityReqOld.getAbilityId().getAbilityId().equals(ability.getAbilityId())){

						if(abilityReqOld.getIsEvaluated()){
							if(abilityReqOld.getAcceptanceStatus()==true){
								swimResponse = new SwimResponse(SwimResponse.FAILED,"abilityAlreadyOwned");
								System.out.println("\nUSERBEAN: Richiesta aggiunta abilità NON inviata perchè già l'utente già possiede tale abilità.\n");
								return swimResponse;
							} 
						} else {

							swimResponse = new SwimResponse(SwimResponse.FAILED,"reqAlreadySent");
							System.out.println("\nUSERBEAN: Richiesta aggiunta abilità NON inviata perchè esiste già una richiesta pendente per tale abilità.\n");
							return swimResponse;
						}


					}
				}

				AbilityRequest abilityReq = new AbilityRequest();
				abilityReq.setAbilityId(ability);
				abilityReq.setDescription(description);
				abilityReq.setDatetime(new Date());
				abilityReq.setAcceptanceStatus(false);
				abilityReq.setIsEvaluated(false);
				abilityReq.setUser(userFrom);
				userFrom.getAbilityRequestList().add(abilityReq);
				em.persist(userFrom);


				swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilità inviata con SUCCESSO!.");
				System.out.println("\nUSERBEAN: Richiesta aggiunta abilità inviata con SUCCESSO!.");

			}else{
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
				System.out.println("USERBEAN (sendAbilityReq): Utente non valido.\n");
			}
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidAbility");
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

	@Override
	public SwimResponse replyToAbilityReq(String emailUserFrom, Long abilityId, boolean replyValue){
		SwimResponse swimResponse = null;
		Ability chosenAbility = em.find(Ability.class, abilityId);
		User userFrom = find(emailUserFrom);

		if(userFrom!=null){
			if(chosenAbility!=null){
				userFrom.getAbilityRequestList().size();
				List<AbilityRequest> abilityReqList = userFrom.getAbilityRequestList();

				AbilityRequest currentRequest = null;

				for(AbilityRequest req: abilityReqList){
					if(req.getAbilityId().getAbilityId().equals(abilityId) && !req.getIsEvaluated()){
						req.setIsEvaluated(true);
						currentRequest = req;
					}
				}
				if(currentRequest!=null){

					currentRequest.setAcceptanceStatus(replyValue);

					if(replyValue==true){
						userFrom.getAbilityList().size();
						userFrom.getAbilityList().add(chosenAbility);
						em.persist(userFrom);

						swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilità accettata.");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilità accettata.");
					} else {

						swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilità rifiutata.");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilità rifiutata.");
					}

				} else {
					swimResponse = new SwimResponse(SwimResponse.FAILED,"noSuchRequestFound");
					System.out.println("USERBEAN (replyToAbilityReq): Non è presenta nessuna richiesta per questa abilità dall'utente indicato.");	
				}
			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidAbility");
				System.out.println("USERBEAN (replyToAbilityReq): Abilità non valida.\n");
			}

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
			System.out.println("USERBEAN (replyToAbilityReq): Utente non valido.\n");
		}


		return swimResponse;

	}
}
