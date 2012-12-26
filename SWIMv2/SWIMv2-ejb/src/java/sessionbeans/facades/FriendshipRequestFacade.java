/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.FriendshipRequest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author MARCO
 */
@Stateless
public class FriendshipRequestFacade extends AbstractFacade<FriendshipRequest> implements FriendshipRequestFacadeLocal {
    @PersistenceContext(unitName = "SWIMv2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FriendshipRequestFacade() {
        super(FriendshipRequest.class);
    }
    
}
