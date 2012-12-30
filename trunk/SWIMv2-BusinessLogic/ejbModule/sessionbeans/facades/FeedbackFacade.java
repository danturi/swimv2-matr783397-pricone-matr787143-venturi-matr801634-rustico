/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.Feedback;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author MARCO
 */

public class FeedbackFacade extends AbstractFacade<Feedback> implements FeedbackFacadeLocal {
    @PersistenceContext(unitName = "SWIMv2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackFacade() {
        super(Feedback.class);
    }
    
}
