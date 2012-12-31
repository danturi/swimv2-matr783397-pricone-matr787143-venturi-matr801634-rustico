/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.Ability;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author MARCO
 */
@Stateless
public class AbilityFacade extends AbstractFacade<Ability> implements AbilityFacadeLocal {
    @PersistenceContext(unitName = "SWIMv2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbilityFacade() {
        super(Ability.class);
    }
    
}
