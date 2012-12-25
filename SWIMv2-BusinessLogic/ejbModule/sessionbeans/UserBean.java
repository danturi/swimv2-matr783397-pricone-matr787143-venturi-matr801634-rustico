package sessionbeans;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class UserBean
 */
@Stateless

public class UserBean implements UserBeanRemote {

	@PersistenceContext
	private EntityManager em;

	public UserBean() {
		// TODO Auto-generated constructor stub
	}

}
