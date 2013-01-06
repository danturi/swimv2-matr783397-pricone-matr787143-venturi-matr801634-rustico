/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.logic;

import entities.AbilityRequest;
import entities.FriendshipRequest;
import entities.HelpRequest;
import entities.User;
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




	/*



    @Override
    public boolean sendFriendshipReq(String ToUserEmail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean sendHelpReq(String ToUserEmail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean sendAbilityReq(AbilityRequest abilityRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean replyToFriendReq(FriendshipRequest friendshipReq){

        return true;

    }

    @Override
    public List<FriendshipRequest> getFriendshipReqList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<HelpRequest> getHelpReqList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/


	/*DA QUI FUNZIONI DI USERBEAN AUTHDEMO
	 * 
	 * 
	 */

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
		User user = (User) find(email).getData();
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
	public SwimResponse find(String email) {
		User usr = em.find(User.class, email);
		SwimResponse swimResponse;
		if(usr!=null){
			swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Ricerca utente per email effettuata.", usr);
		}
		else{
			swimResponse = new SwimResponse(SwimResponse.FAILED,"Utente non trovato.");
		}
		return swimResponse;

	}
	@Override
	public void detachUser(User user) {
		em.detach(user);
	}

	@Override
	public SwimResponse getFriendsList(User user) {
		SwimResponse swimResponse = new SwimResponse(SwimResponse.SUCCESS,"Recupero lista degli amici effettuato", user.getUserList());
		return swimResponse;
	}

	@Override
	public SwimResponse getFriendshipReqList(User user) {
		// TODO Auto-generated method stub
		return null;
	}






}
