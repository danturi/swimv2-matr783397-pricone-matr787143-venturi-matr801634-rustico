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
import javax.ejb.Local;



/**
 *
 * @author MARCO
 */
@Local
public interface UserBeanLocal {
    
    /*


    public boolean sendFriendshipReq(String ToUserEmail);

    public boolean sendHelpReq(String ToUserEmail);

    public boolean sendAbilityReq(AbilityRequest abilityRequest);
    
    public boolean replyToFriendReq(FriendshipRequest friendshipReq);
    
    repleTo Help Req*/

  
    
    public SwimResponse findAll();
    
    public void createUser(User user);
    
    public void updateUser(User user);
    
    public void removeUser(User user);
   
    public void detachUser(User user);
    
    public User find(String email);
    
    public void removeUser(String email);
    
    public SwimResponse getFriendsList(String email);
    
    public SwimResponse getFriendshipReqList(String email);
    
    public SwimResponse getFriendshipReqList1(String email);
    
    public SwimResponse getHelpReqList(String email);
    
    
    
    public SwimResponse sendFriendshipReq(String emailUserFrom, String emailUserTo);
}
