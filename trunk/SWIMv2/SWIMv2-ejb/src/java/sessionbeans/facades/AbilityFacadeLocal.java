/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.Ability;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MARCO
 */
@Local
public interface AbilityFacadeLocal {

    void create(Ability ability);

    void edit(Ability ability);

    void remove(Ability ability);

    Ability find(Object id);

    List<Ability> findAll();

    List<Ability> findRange(int[] range);

    int count();
    
}
