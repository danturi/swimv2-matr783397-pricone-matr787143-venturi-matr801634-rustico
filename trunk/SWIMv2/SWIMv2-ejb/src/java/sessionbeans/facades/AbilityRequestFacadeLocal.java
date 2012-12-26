/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.AbilityRequest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MARCO
 */
@Local
public interface AbilityRequestFacadeLocal {

    void create(AbilityRequest abilityRequest);

    void edit(AbilityRequest abilityRequest);

    void remove(AbilityRequest abilityRequest);

    AbilityRequest find(Object id);

    List<AbilityRequest> findAll();

    List<AbilityRequest> findRange(int[] range);

    int count();
    
}
