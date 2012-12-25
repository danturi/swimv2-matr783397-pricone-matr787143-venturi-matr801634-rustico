package sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class AdminBean
 */
@Stateless
public class AdminBean implements AdminBeanRemote {

	@PersistenceContext
	private EntityManager em;

    public AdminBean() {
        // TODO Auto-generated constructor stub
    }

}
