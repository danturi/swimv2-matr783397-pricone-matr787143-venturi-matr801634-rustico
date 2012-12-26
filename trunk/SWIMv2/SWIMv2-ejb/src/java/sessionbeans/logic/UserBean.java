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
import sessionbeans.facades.UserFacadeLocal;

/**
 *
 * @author MARCO
 */
@Stateless
public class UserBean implements UserBeanLocal {
    
    @EJB
    private UserFacadeLocal userFacade;
    
 
    //List<User> findRange(int[] range);

    //int count();
    

    @Override
    public boolean createNewUser(User user) {
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
    public User findUserByEmail(String email) {
        //throw new UnsupportedOperationException("Not supported yet.");
        List<User> list = userFacade.findAll();
        while(list.iterator().hasNext()){
            User currentUser = list.iterator().next();
            if(currentUser.getEmail().equals(email)) return currentUser;
        }
        return null;
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
    public List<FriendshipRequest> getFriendshipReqList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<HelpRequest> getHelpReqList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}
