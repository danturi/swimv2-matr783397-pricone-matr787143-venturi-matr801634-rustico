/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.logic;

import entities.Ability;
import entities.AbilityRequest;
import entities.Feedback;
import entities.FileStorageEntity;
import entities.FriendshipRequest;
import entities.HelpRequest;
import entities.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

	public void updateUserRating(String email){
		User usr = em.find(User.class, email);
		float newRating = 0.0f;
		float sum = 0.0f;
		if(usr!=null){
			usr.getHelpReqList().size();
			List<HelpRequest> receivedHelpReqList = usr.getHelpReqList();
			List<Feedback> feedList = new ArrayList<Feedback>();
			for(HelpRequest helpReq: receivedHelpReqList){

				if(helpReq.getFeedbackId()!=null) feedList.add(helpReq.getFeedbackId());
			}

			if(!feedList.isEmpty()){
				for(Feedback feed: feedList){

					if(feed.getRating()!=null){
						sum+=feed.getRating().floatValue();
					}
				}
				newRating = sum/Float.valueOf(feedList.size()+".0f");
				usr.setRating(Float.valueOf(newRating));
			}
		}
	}

	@Override
	public SwimResponse bindPictureToUser(String email, Long pictureId){

		SwimResponse swimResponse = null;

		User usr = find(email);
		FileStorageEntity picture = em.find(FileStorageEntity.class, pictureId);

		if(usr!=null && picture!=null){
			usr.setProfilePicture(picture);
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");
			System.out.println("USERBEAN: qui inserita foto utente\n");
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"fail");
			System.out.println("USERBEAN: foto non inserita\n");
		}


		return swimResponse;
	}

	@Override
	public SwimResponse retrievePicture(String email){

		SwimResponse swimResponse = null;
		if(email!=null){
			User usr = find(email);
			if(usr!=null){
				if(usr.getPictureId()!=null){

					File parentDir = new File("C:/SwimPictures/pictures");
					parentDir.mkdir();
					File f = new File(parentDir,"picture_id_"+usr.getPictureId().getPictureId()+".jpg");

					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						swimResponse = new SwimResponse(SwimResponse.FAILED,"fileError");
						return swimResponse;

					}

					String strFilePath = "C:/SwimPictures/pictures/"+"picture_id_"+usr.getPictureId().getPictureId()+".jpg";

					try{

						FileOutputStream fos = new FileOutputStream(strFilePath);
						//String strContent = "Write File using Java FileOutputStream example !";
						//fos.write(strContent.getBytes());
						fos.write(usr.getPictureId().getContent());
						fos.close();
					} catch(FileNotFoundException ex) {
						ex.printStackTrace();
						swimResponse = new SwimResponse(SwimResponse.FAILED,"fileError");
						return swimResponse;
					} catch(IOException ioe){
						ioe.printStackTrace();
						swimResponse = new SwimResponse(SwimResponse.FAILED,"fileError");
						return swimResponse;
					}

					String urlPath = "pictures/"+"picture_id_"+usr.getPictureId().getPictureId()+".jpg";
					swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok", urlPath);

				} else {
					swimResponse = new SwimResponse(SwimResponse.FAILED,"noPictureFound");
				}

			} else{
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
			}
		} else {

			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
		}


		return swimResponse;
	}

	@Override
	public SwimResponse findAll() {

		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr ORDER BY usr.registeredOn ASC", User.class);
		List<User> listWithAdmin = query.getResultList();
		List<User> finalList = new ArrayList<User>();
		for(User usr: listWithAdmin){
			if(!usr.getGroups().contains(Group.ADMINISTRATOR)){
				finalList.add(usr);
			}	
		}
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Lista utenti ricevuta", finalList);
		return swimResponse;
	}

	@Override
	public User createUser(User user) {
		checkInitialConditions();
		em.persist(user);
		em.flush();
		return user;
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public SwimResponse removeUser(String email) {
		SwimResponse swimResponse = null;
		User user =  find(email);
		if (user != null) {
			user.getUserList().size();
			user.getUserList1().size();



			List<User> userList = (List<User>) findAll().getData();

			for(User user1 : userList){
				user1.getUserList().size();
				user1.getUserList1().size();

				user1.getHelpReqList().size();
				user1.getSentHelpReqList().size();
				user1.getFriendshipRequestList().size();
				user1.getSentFriendshipRequestList().size();
				List<HelpRequest> helpReqList1 = user1.getHelpReqList();
				List<HelpRequest> helpReqList2 =user1.getSentHelpReqList();
				List<FriendshipRequest> friendReqList1 = user1.getFriendshipRequestList();
				List<FriendshipRequest> friendReqList2 = user1.getSentFriendshipRequestList();

				List<FriendshipRequest> removalList1 = new ArrayList<FriendshipRequest>();
				List<HelpRequest> removalList2 = new ArrayList<HelpRequest>();

				for(FriendshipRequest freq1 : friendReqList1){

					if(freq1.getFromUser().equals(user) || freq1.getToUser().equals(user)){
						removalList1.add(freq1);
					}
				}
				friendReqList1.removeAll(removalList1);
				removalList1.clear();

				for(FriendshipRequest freq2 : friendReqList2){
					if(freq2.getFromUser().equals(user) || freq2.getToUser().equals(user))
						removalList1.add(freq2);
				}
				friendReqList2.removeAll(removalList1);
				removalList1.clear();

				for(HelpRequest hreq1 : helpReqList1){
					if(hreq1.getFromUser().equals(user) || hreq1.getToUser().equals(user))
						removalList2.add(hreq1);

				}
				helpReqList1.removeAll(removalList2);
				removalList2.clear();

				for(HelpRequest hreq2 : helpReqList2){
					if(hreq2.getFromUser().equals(user) || hreq2.getToUser().equals(user))
						removalList2.add(hreq2);
				}
				helpReqList2.removeAll(removalList2);
				removalList2.clear();

				user1.getUserList().remove(user);
				user1.getUserList1().remove(user);
				em.persist(user1);
			}




			em.remove(user);


			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
		}
		return swimResponse;
	}

	@Override
	public void removeUser(User user) {
		if (user != null && user.getEmail()!=null && em.contains(user)) {
			em.remove(user);
		}
	}
	@Override
	public SwimResponse addAbility(String newAbility){

		SwimResponse swimResponse = null;

		if(newAbility.length()>0 && newAbility.length()<128){

			Ability newAb = new Ability();
			newAb.setDescription(newAbility);
			em.persist(newAb);
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidAbilityName");
		}
		return swimResponse;

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

				globalList = (List<User>) getFullFriendsList(userPrincipal).getData();

				System.out.println("\n**** USERBEAN: globalList: "+globalList);
			} else {
				globalList = (List<User>) findAll().getData();
			}

			for(User tryUser: globalList){ // FILTRO PER CITTA'
				if(!city.equals("")){
					if(tryUser.getCity()!=null){
						if(tryUser.getCity().equalsIgnoreCase(city)) {
							resultList.add(tryUser);
						}
					}

				} else {
					resultList = globalList;
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
					if(tryUser3.getLastname().equalsIgnoreCase(lastname)){
						filteredByLastname= true;
						resultList3.add(tryUser3);
					}
				}
				if(filteredByLastname) resultList=resultList3;
			}
			System.out.println("\n**** USERBEAN: MATCH LIST3: "+resultList3);
			if(!firstname.equals("")){// FILTRO PER NOME
				filteredByFirstname= true;
				for(User tryUser4: resultList){
					if(tryUser4.getFirstname().equalsIgnoreCase(firstname)){
						filteredByFirstname= true;
						resultList4.add(tryUser4);
					}
				}
				if(filteredByFirstname) resultList=resultList4;
			}
			System.out.println("\n**** USERBEAN: MATCH LIST4: "+resultList4);

			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"",resultList);
			System.out.println("\n**** USERBEAN: MATCH LIST: "+resultList);
			return swimResponse;

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"");
			return swimResponse;
		}

	}

	@Override
	public SwimResponse searchUserMatchingGuest(String lastname, String firstname, String city, String ability, String ability2){
		SwimResponse swimResponse;
		boolean filteredByAb1 = false;
		boolean filteredByAb2 = false;
		boolean filteredByLastname = false;
		boolean filteredByFirstname = false;


		if(lastname!=null && firstname!=null && city!=null && ability!=null && ability2!=null){
			List<User> globalList = new ArrayList<User>();
			List<User> resultList = new ArrayList<User>();
			List<User> resultList2 = new ArrayList<User>();
			List<User> resultList3 = new ArrayList<User>();
			List<User> resultList4 = new ArrayList<User>();


			globalList = (List<User>) findAll().getData();


			for(User tryUser: globalList){ // FILTRO PER CITTA'
				if(!city.equals("")){
					if(tryUser.getCity()!=null){
						if(tryUser.getCity().equalsIgnoreCase(city)) {
							resultList.add(tryUser);
						}
					}

				} else {
					resultList = globalList;
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
					if(tryUser3.getLastname().equalsIgnoreCase(lastname)){
						filteredByLastname= true;
						resultList3.add(tryUser3);
					}
				}
				if(filteredByLastname) resultList=resultList3;
			}
			System.out.println("\n**** USERBEAN: MATCH LIST3: "+resultList3);
			if(!firstname.equals("")){// FILTRO PER NOME
				filteredByFirstname= true;
				for(User tryUser4: resultList){
					if(tryUser4.getFirstname().equalsIgnoreCase(firstname)){
						filteredByFirstname= true;
						resultList4.add(tryUser4);
					}
				}
				if(filteredByFirstname) resultList=resultList4;
			}
			System.out.println("\n**** USERBEAN: MATCH LIST4: "+resultList4);

			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"",resultList);
			System.out.println("\n**** USERBEAN: MATCH LIST: "+resultList);
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"fail");
		}
		return swimResponse;



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
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Lista abilit� ricevuta", query.getResultList());
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
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista abilit� effettuato", user.getAbilityList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse getAbilityReqList(String email){
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			user.getAbilityRequestList().size();
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok", user.getAbilityRequestList());
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"fail");
		}
		return swimResponse;
		
	}

	@Override
	public SwimResponse getFullFriendsList(String email){
		SwimResponse swimResponse;
		User user = find(email);
		if(user!=null){
			user.getUserList().size();
			user.getUserList1().size();
			List<User> fullList = new ArrayList<User>();
			List<User> globalListOf1 = user.getUserList();
			List<User> globalListOf2 = user.getUserList1();
			for(User usr1: globalListOf1){
				fullList.add(usr1);
			}
			for(User usr2: globalListOf2){
				if(!fullList.contains(usr2)) fullList.add(usr2);
			}

			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista degli amici effettuato", fullList);
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
			user.getHelpReqList().size();
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
			user.getSentHelpReqList().size();
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

			if(!(userFrom.getUserList().contains(userTo)) && !(userTo.getUserList().contains(userFrom))){ // se gli utenti non sono gi� amici...

				List<FriendshipRequest> listUserTo = userTo.getFriendshipRequestList();
				//List<FriendshipRequest> listSentByUserTo = userTo.getSentFriendshipRequestList();
				List<FriendshipRequest> listUserFrom = userFrom.getFriendshipRequestList();

				for(FriendshipRequest friendReq : listUserTo){
					if(friendReq.getFromUser().equals(userFrom)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perch� gi� presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta d'amicizia <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> NON inviata perch� gi� presente una UGUALE pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}

				for(FriendshipRequest friendReq : listUserFrom){
					if(friendReq.getFromUser().equals(userTo)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perch� gi� presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta d'amicizia <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> NON inviata perch� gi� presente una OPPOSTA pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}

				/*for(FriendshipRequest friendReq : listSentByUserTo){
					if(friendReq.getToUser().equals(userFrom)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perch� gi� presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta d'amicizia <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> NON inviata perch� gi� presente una OPPOSTA pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
						return swimResponse;
					}
				}*/

				//METODO ALTERNATIVO AL CICLO FOR SOPRA
				/*TypedQuery<FriendshipRequest> query = em.createQuery("SELECT frq FROM FriendshipRequest frq", FriendshipRequest.class);
				List<FriendshipRequest> list = query.getResultList();

				for(FriendshipRequest friendReq : list){
					if(friendReq.getFromUser().equals(userFrom) && friendReq.getToUser().equals(userTo)){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"Richiesta d'amicizia NON inviata perch� gi� presente una pendente.\n");
						System.out.println("\nUSERBEAN: richiesta da <"+userFrom.getEmail()+"> a <"+userTo.getEmail()+"> Richiesta d'amicizia NON inviata perch� gi� presente una pendente: MITTENTE = "+friendReq.getFromUser()+"	DESTINATARIO = "+friendReq.getToUser()+"\n");
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
				swimResponse = new SwimResponse(SwimResponse.FAILED,"L'utente risulta gi� nella lista degli amici.");
				System.out.println("USERBEAN: L'utente risulta gi� nella lista degli amici.");
			}

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non valido.");
			System.out.println("USERBEAN (sendFriendshipReq): Utente non valido.\n");
		}
		return swimResponse;
	}

	@Override
	public SwimResponse sendHelpReq(String emailUserFrom, String emailUserTo, Long abilityId, String description) {
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		User userTo = find(emailUserTo);
		if(userFrom!=null && userTo!=null && userFrom!=userTo){// se i due utenti esistono nel sistema (e non sono lo stesso utente)...
			Ability chosenAbility = em.find(Ability.class, abilityId);
			if(chosenAbility!=null){
				userTo.getHelpReqList().size();
				List<HelpRequest> helpReqListUserTo = userTo.getHelpReqList();
				for(HelpRequest helpReq: helpReqListUserTo){ //FILTRO RICHIESTE AIUTO A STESSO UTENTE CON STESSA ABILITA'
					if(helpReq.getFromUser().equals(userFrom) && helpReq.getAbilityId().getAbilityId().equals(abilityId) && !helpReq.getIsEvaluated().booleanValue()){
						swimResponse = new SwimResponse(SwimResponse.FAILED,"reqAlreadySent");
						System.out.println("\nUSERBEAN: Richiesta d'aiuto NON inviata perch� gi� presente una simile.\n");
						return swimResponse;
					}
				}
				HelpRequest helpReq = new HelpRequest();
				helpReq.setFromUser(userFrom);
				helpReq.setToUser(userTo);
				helpReq.setAbilityId(chosenAbility);
				helpReq.setIsEvaluated(false);
				helpReq.setAcceptanceStatus(false);
				helpReq.setDatetime(new Date());
				helpReq.setDescription(description);
				helpReq.setFeedbackId(null);
				userFrom.getSentHelpReqList().add(helpReq);
				userTo.getHelpReqList().add(helpReq);
				em.persist(userTo);
				em.persist(userFrom);

				swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");
				System.out.println("\nUSERBEAN: Richiesta d'aiuto inviata con SUCCESSO!\n");

			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidAbility");
				System.out.println("USERBEAN (sendHelpReq): Abilit� non valido.\n");
			}
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
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
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilit� NON inviata perch� gi� l'utente gi� possiede tale abilit�.\n");
						return swimResponse;
					}
				}

				List<AbilityRequest> listAbilityReq = userFrom.getAbilityRequestList();

				for(AbilityRequest abilityReqOld : listAbilityReq){
					if(abilityReqOld.getAbilityId().getAbilityId().equals(ability.getAbilityId())){

						if(abilityReqOld.getIsEvaluated()){
							if(abilityReqOld.getAcceptanceStatus()==true){
								swimResponse = new SwimResponse(SwimResponse.FAILED,"abilityAlreadyOwned");
								System.out.println("\nUSERBEAN: Richiesta aggiunta abilit� NON inviata perch� gi� l'utente gi� possiede tale abilit�.\n");
								return swimResponse;
							} 
						} else {

							swimResponse = new SwimResponse(SwimResponse.FAILED,"reqAlreadySent");
							System.out.println("\nUSERBEAN: Richiesta aggiunta abilit� NON inviata perch� esiste gi� una richiesta pendente per tale abilit�.\n");
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
				em.flush();

				swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilit� inviata con SUCCESSO!.");
				System.out.println("\nUSERBEAN: Richiesta aggiunta abilit� inviata con SUCCESSO!.");

			}else{
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
				System.out.println("USERBEAN (sendAbilityReq): Utente non valido.\n");
			}
		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidAbility");
			System.out.println("USERBEAN (sendAbilityReq): Abilit� non valida.\n");
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
					swimResponse = new SwimResponse(SwimResponse.FAILED,"Non � presenta nessuna richiesta d'amicizia dall'utente indicato.");
					System.out.println("USERBEAN (replyToFriendshipReq): Non � presenta nessuna richiesta d'amicizia dall'utente indicato.");	
				}




			} else{
				swimResponse = new SwimResponse(SwimResponse.FAILED,"L'utente risulta gi� nella lista degli amici.");
				System.out.println("USERBEAN (replyToFriendshipReq): L'utente risulta gi� nella lista degli amici.");
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

						swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilit� accettata.");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilit� accettata.");
					} else {

						swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Richiesta aggiunta abilit� rifiutata.");
						System.out.println("\nUSERBEAN: Richiesta aggiunta abilit� rifiutata.");
					}

				} else {
					swimResponse = new SwimResponse(SwimResponse.FAILED,"noSuchRequestFound");
					System.out.println("USERBEAN (replyToAbilityReq): Non � presenta nessuna richiesta per questa abilit� dall'utente indicato.");	
				}
			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidAbility");
				System.out.println("USERBEAN (replyToAbilityReq): Abilit� non valida.\n");
			}

		} else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
			System.out.println("USERBEAN (replyToAbilityReq): Utente non valido.\n");
		}
		return swimResponse;
	}

	public SwimResponse replyToHelpReq(String emailUserFrom, String emailUserTo, Long reqId, boolean replyValue){

		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		User userTo = find(emailUserTo);
		HelpRequest helpReq = em.find(HelpRequest.class, reqId);

		if(userFrom!=null && userTo!=null && userFrom!=userTo){
			if(helpReq!=null){
				userTo.getHelpReqList().size();
				if(userTo.getHelpReqList().contains(helpReq)){
					if(helpReq.getFromUser().equals(userFrom)){
						if(!helpReq.getIsEvaluated()){
							helpReq.setAcceptanceStatus(replyValue);
							helpReq.setIsEvaluated(true);
							em.persist(helpReq);
							swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");
							System.out.println("\nUSERBEAN: Richiesta di aiuto accettata.");
						} else {
							swimResponse = new SwimResponse(SwimResponse.FAILED,"reqAlreadyEvaluated");
							System.out.println("USERBEAN (replyToHelpReq): Richiesta di aiuto gi� valutata");
						}
					} else {
						swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
						System.out.println("USERBEAN (replyToHelpReq): Utente non valido.\n");
					}

				} else {
					swimResponse = new SwimResponse(SwimResponse.FAILED,"noSuchRequestFound");
					System.out.println("USERBEAN (replyToHelpReq): Non � presente nessuna richiesta di aiuto con l'Id indicato dall'utente indicato.");
				}
			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidReq");
				System.out.println("USERBEAN (replyToHelpReq): HelpReq non valida.\n");
			}
		}  else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
			System.out.println("USERBEAN (replyToHelpReq): Utente non valido.\n");
		}

		return swimResponse;
	}

	public SwimResponse sendFeedback(String emailUserFrom, String emailUserTo, String reqIdString, String vote, String description){
		System.out.println("USERBEAN (sendFeedback): *********INIZIO \n");
		SwimResponse swimResponse = null;
		User userFrom = find(emailUserFrom);
		User userTo = find(emailUserTo);
		Long reqId;
		float rating = 0.0f;
		try{
			float minRating = Float.valueOf("0.0f").floatValue();
			float maxRating = Float.valueOf("5.5f").floatValue();
			rating = Float.valueOf(vote).floatValue();
			if(rating<minRating || rating>maxRating){
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidVote");
				System.out.println("USERBEAN (sendFeedback): Voto non valido.\n");
				return swimResponse;
			}
		} catch (Exception e){
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidVote");
			System.out.println("USERBEAN (sendFeedback): Voto non valido.\n");
			e.printStackTrace();
			return swimResponse;
		}
		try{
			reqId = Long.valueOf(reqIdString);
		} catch (Exception e){
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidReq");
			System.out.println("USERBEAN (sendFeedback): HelpReq associata non valida\n");
			return swimResponse;
		}

		HelpRequest helpReq = em.find(HelpRequest.class, reqId);
		if(userFrom!=null && userTo!=null && userFrom!=userTo){
			if(helpReq!=null){
				userTo.getHelpReqList().size();
				if(userTo.getHelpReqList().contains(helpReq)){
					if(helpReq.getFromUser().equals(userFrom)){


						if(helpReq.getIsEvaluated() && helpReq.getAcceptanceStatus()==true){
							if(helpReq.getFeedbackId()==null){
								Feedback feedback = new Feedback();
								feedback.setAuthorUser(userFrom);
								feedback.setRating(Float.valueOf(rating));
								feedback.setComment(description);
								feedback.setDatetime(new Date());
								helpReq.setFeedbackId(feedback);

								em.persist(feedback);
								em.flush();
								updateUserRating(emailUserTo);
								swimResponse = new SwimResponse(SwimResponse.SUCCESS,"ok");
								System.out.println("\nUSERBEAN: (sendFeedback) Feedback inviato con successo.");
							}  else {
								swimResponse = new SwimResponse(SwimResponse.FAILED,"feedAlreadySent");
								System.out.println("USERBEAN (sendFeedback): Feedback gi� presente");
							}
						} else {
							swimResponse = new SwimResponse(SwimResponse.FAILED,"reqNotSuitable");
							System.out.println("USERBEAN (sendFeedback): HelpReq non adatta per un feedback.");
						}
					} else {
						swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
						System.out.println("USERBEAN (sendFeedback): Utente non valido.\n");
					}

				} else {
					swimResponse = new SwimResponse(SwimResponse.FAILED,"noSuchRequestFound");
					System.out.println("USERBEAN (sendFeedback): Non � presente nessuna richiesta di aiuto con l'Id indicato dall'utente indicato.");
				}
			} else {
				swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidReq");
				System.out.println("USERBEAN (sendFeedback): HelpReq non valida.\n");
			}
		}  else {
			swimResponse = new SwimResponse(SwimResponse.FAILED,"noValidUser");
			System.out.println("USERBEAN (sendFeedback): Utente non valido.\n");
		}

		return swimResponse;
	}
}
