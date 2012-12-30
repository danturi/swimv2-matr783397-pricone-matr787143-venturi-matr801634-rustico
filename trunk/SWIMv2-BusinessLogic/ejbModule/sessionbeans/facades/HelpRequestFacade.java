/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.HelpRequest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author MARCO
 */

public class HelpRequestFacade extends AbstractFacade<HelpRequest> implements HelpRequestFacadeLocal {
    @PersistenceContext(unitName = "SWIMv2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HelpRequestFacade() {
        super(HelpRequest.class);
    }
    
}
