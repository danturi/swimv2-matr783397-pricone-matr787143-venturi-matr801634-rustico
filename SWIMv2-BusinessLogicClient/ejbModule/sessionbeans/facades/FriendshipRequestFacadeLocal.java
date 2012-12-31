/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.FriendshipRequest;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author MARCO
 */
@Local
public interface FriendshipRequestFacadeLocal {

    void create(FriendshipRequest friendshipRequest);

    void edit(FriendshipRequest friendshipRequest);

    void remove(FriendshipRequest friendshipRequest);

    FriendshipRequest find(Object id);

    List<FriendshipRequest> findAll();

    List<FriendshipRequest> findRange(int[] range);

    int count();
    
}
