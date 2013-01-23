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
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;



/**
 *
 * @author MARCO
 */
@Remote
public interface UserBeanRemote {
    
    /*

    
    repleTo Help Req*/

  
    
    public SwimResponse findAll();
    
    public void createUser(User user);
    
    public void updateUser(User user);
    
    public void removeUser(User user);
   
    public void detachUser(User user);
    
    public User find(String email);
    
    public void removeUser(String email);
    
    public SwimResponse searchUserMatching(String userPrincipal, String lastname, String firstname, String city, String ability, String ability2, String onlyfriends);
    
    public SwimResponse changeUserInfo (String userPrincipal, String lastname,String firstname ,String city,String sex ,String age,String job ,String tel);
    
    public SwimResponse getAbilitySet();
    
    public SwimResponse getNewAbilityReqList();
    
    public SwimResponse getAbilityList(String email);
    
    public SwimResponse getFriendsList(String email);
    
    public SwimResponse getReversedFriendsList(String email);
    
    public SwimResponse getFriendshipReqList(String email);
    
    public SwimResponse getSentFriendshipReqList(String email);
    
    public SwimResponse getHelpReqList(String email);
    
    public SwimResponse getSentHelpReqList(String email);
    
    public SwimResponse sendFriendshipReq(String emailUserFrom, String emailUserTo);
    
    public SwimResponse sendHelpReq(String emailUserFrom, String emailUserTo, String description);
    
    public SwimResponse sendAbilityReq(String emailUserFrom, Long abilityId, String description);
    
    public SwimResponse replyToFriendshipReq(String emailUserFrom, String emailUserTo, boolean replyValue);
    
    public SwimResponse replyToAbilityReq(String emailUserFrom, Long abilityId, boolean replyValue);
    
}

