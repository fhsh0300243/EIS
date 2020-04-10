package tw.eis.model;

import java.util.List;

public interface IUsersDAO {
	public List<Users> findUsers(String userName, String userPassword);
}
