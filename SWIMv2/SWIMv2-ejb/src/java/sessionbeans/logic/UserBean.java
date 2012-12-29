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
    @EJB
    private UserFacadeLocal userFacade;
    
    @PersistenceContext(unitName = "SWIMv2-ejbPU")
    private EntityManager em;
    
    
    
 
    //List<User> findRange(int[] range);

    //int count();
    

    @Override
    public boolean createUser(User user) {
        //throw new UnsupportedOperationException("Not supported yet.");
        userFacade.create(user);
        return true;
    }
    
    @Override
    public boolean removeUser(User user){
        userFacade.remove(user);
        return true;
    }
    
    @Override
    public boolean modifyUser(User user){
        userFacade.edit(user);
        return true;
    }
    
    @Override
    public int countUsers(){
        return userFacade.count();
    }
    

    @Override
    public User findUserByEmail(String email) {
        //throw new UnsupportedOperationException("Not supported yet.");
        /*List<User> list = userFacade.findAll();
        while(list.iterator().hasNext()){
            User currentUser = list.iterator().next();
            if(currentUser.getEmail().equals(email)) return currentUser;
            list.remove((User)currentUser);
        }
        return null;*/
       User usr = (User)em.createNamedQuery("User.findByEmail").setParameter("email",email).getSingleResult();
        return usr;
        
    }
    
    @Override
    public List<User> findAllUsers(){
        return userFacade.findAll();
    }

    @Override
    public List<User> getOtherUserFriendList(String friendUserEmail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> getFriendsList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
    }

    
    /*DA QUI FUNZIONI DI USERBEAN AUTHDEMO
     * 
     * 
     */
    
    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("SELECT usr FROM User ORDER BY usr.registeredOn ASC", User.class);
        return query.getResultList();
    }
  
    @Override
    public void save(User user) {
        em.persist(user);
    }
  
    @Override
    public void update(User user) {
        em.merge(user);
    }
  
    @Override
    public void remove(String email) {
        User user = find(email);
        if (user != null) {
            em.remove(user);
        }
    }
      
    @Override
    public void remove(User user) {
        if (user != null && user.getEmail()!=null && em.contains(user)) {
            em.remove(user);
        }
    }
  
    @Override
    public User find(String email) {
        return em.find(User.class, email);
    }
     
    public void detach(User user) {
        em.detach(user);
    }



}
