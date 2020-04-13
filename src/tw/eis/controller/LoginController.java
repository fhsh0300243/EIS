package tw.eis.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import tw.eis.model.Users;
import tw.eis.model.UsersService;



@Controller
@SessionAttributes(names = {"usersResultMap", "errorMsgMap", "LoginOK"})
@RequestMapping(path ="/login")
public class LoginController {
	private UsersService uService;
	
	@Autowired
	public LoginController(UsersService uService){
		this.uService = uService;
		
	}
	
	@RequestMapping(path = "/userLogin", method = RequestMethod.GET)
	public String goToLoginPage() {
		return "UserLogin";
	}
	
	@RequestMapping(path = "/userLoginCheck", method = RequestMethod.POST)
	public String processLoginAction(@RequestParam("userName") String userAccount,
			@RequestParam("userPassword") String userPwd, Model model) throws IOException, Exception {
		Map<String, String> errorMsgMap = new HashMap<String, String>();
		model.addAttribute("errorMsgMap", errorMsgMap);

		if (userAccount == null || userAccount.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "User account should not be empty");
		}
		
		if (userPwd == null || userPwd.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "User password should not be empty");
		}

		if (!errorMsgMap.isEmpty()) {
			return "UserLogin";
		}
		
		List<Users> loginResult=uService.findUsers(userAccount, userPwd);
		
		if(loginResult.size()>0) {
			Iterator<Users> loginResultIT = loginResult.iterator();
			Users uBean = loginResultIT.next();
			model.addAttribute("LoginOK", uBean);
			Map<String, String> usersResultMap = new HashMap<String, String>();
			usersResultMap.put("EmployeeID", String.valueOf(uBean.getEmployeeID()));
			usersResultMap.put("UserName", uBean.getUserName());
			usersResultMap.put("UserPassword", uBean.getUserPassword());
			usersResultMap.put("Title", uBean.getTitle());
			usersResultMap.put("Department", uBean.getDepartment());
			model.addAttribute("usersResultMap", usersResultMap);
			return "LoginSucess";
			
		}
		else {
			errorMsgMap.put("LoginError", "Account doesn't exit or password wrong");
			return "UserLogin";
		}
	} 
	
}