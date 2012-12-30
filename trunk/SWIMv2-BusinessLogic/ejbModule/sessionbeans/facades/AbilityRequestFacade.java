/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.AbilityRequest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author MARCO
 */

public class AbilityRequestFacade extends AbstractFacade<AbilityRequest> implements AbilityRequestFacadeLocal {
    @PersistenceContext(unitName = "SWIMv2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbilityRequestFacade() {
        super(AbilityRequest.class);
    }
    
}
