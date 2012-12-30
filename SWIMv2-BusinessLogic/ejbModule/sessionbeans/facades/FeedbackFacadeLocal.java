/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans.facades;

import entities.Feedback;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author MARCO
 */
@Local

public interface FeedbackFacadeLocal {

    void create(Feedback feedback);

    void edit(Feedback feedback);

    void remove(Feedback feedback);

    Feedback find(Object id);

    List<Feedback> findAll();

    List<Feedback> findRange(int[] range);

    int count();
    
}
