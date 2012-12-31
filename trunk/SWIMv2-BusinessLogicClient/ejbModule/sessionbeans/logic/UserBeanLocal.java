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
    
    public boolean createUser(User user);
    
    public boolean removeUser(User user);
    
    public boolean modifyUser(User user);
    
    public int countUsers();
    
    public User findUserByEmail(String email);
    
    public List<User> findAllUsers();

    public List<User> getOtherUserFriendList(String friendUserEmail);

    public List<User> getFriendsList();

    public boolean sendFriendshipReq(String ToUserEmail);

    public boolean sendHelpReq(String ToUserEmail);

    public boolean sendAbilityReq(AbilityRequest abilityRequest);
    
    public boolean replyToFriendReq(FriendshipRequest friendshipReq);

    public List<FriendshipRequest> getFriendshipReqList();

    public List<HelpRequest> getHelpReqList();
    
    //metodi vecchi da auth demo
    
    public List<User> findAll();
    
    public void save(User user);
    
    public void update(User user);
    
    public void remove(String email);
    
    public void remove(User user);
    
    public User find(String email);
    
    public void detach(User user);
    
    
}
