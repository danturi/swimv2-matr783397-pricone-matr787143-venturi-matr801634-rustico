/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.HelpRequest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MARCO
 */
@Local
public interface HelpRequestFacadeLocal {

    void create(HelpRequest helpRequest);

    void edit(HelpRequest helpRequest);

    void remove(HelpRequest helpRequest);

    HelpRequest find(Object id);

    List<HelpRequest> findAll();

    List<HelpRequest> findRange(int[] range);

    int count();
    
}
