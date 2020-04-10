package tw.eis.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO implements IUsersDAO{
	private SessionFactory sessionFacotry;

	@Autowired
	public UsersDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFacotry) {
		this.sessionFacotry = sessionFacotry;
	}
	
	public List<Users> findUsers(String userName, String userPassword) {
		Session session = sessionFacotry.getCurrentSession();
		Query<Users> query = session.createQuery("from Users where UserName = :userName and UserPassword = :userPassword", Users.class);
		query.setParameter("userName", userName);
		query.setParameter("userPassword", userPassword);
		List<Users> list = query.list();
		return list;
	}
}