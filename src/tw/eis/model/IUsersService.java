package tw.eis.model;

import java.util.List;

public interface IUsersService {
	public List<Users> findUsers(String userName, String userPassword);
}
