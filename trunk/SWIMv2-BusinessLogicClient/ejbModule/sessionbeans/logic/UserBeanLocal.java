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
    
    repleTo Help Req

    public List<FriendshipRequest> getFriendshipReqList();

    public List<HelpRequest> getHelpReqList();*/
    
    //metodi vecchi da auth demo
    
    public SwimResponse findAll();
    
    public void createUser(User user);
    
    public void updateUser(User user);
    
    public void removeUser(String email);
    
    public void removeUser(User user);
    
    public SwimResponse find(String email);
    
    public void detachUser(User user);
    
    public SwimResponse getFriendsList(User user);
    
    public SwimResponse getFriendshipReqList(User user);
    
}
