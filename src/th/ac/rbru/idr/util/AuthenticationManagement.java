package th.ac.rbru.idr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import th.ac.rbru.idr.model.UserAuthen;

public class AuthenticationManagement implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		
		//if username is numberic must to check user from regDB
		//if username isn't numberic must to check user from mySQL DB
		if(StringUtils.isNumeric(username)){
			user = getUserFromREG(username);
		}else{
			user = getUserFromReduis(username);
		}
		return user;
	}
	
	@SuppressWarnings("deprecation")
	private User getUserFromREG(String userName){
		ResultSet result = null;
		ConnectionDB.getInstance();
		Connection con = ConnectionDB.getRegConnection();
		String sql = "	SELECT STDM.STUDENTCODE AS USERNAME, S.PASSWORD AS PASSWORD	"+
				"	FROM SYSPASSWORD S, STUDENTMASTER STDM	"+
				"	WHERE STDM.STUDENTID = S.USERID	"+
				"	AND S.USERTYPE = 0	"+
				"	AND STDM.STUDENTCODE LIKE "+userName;
		try {
			Statement st = con.createStatement();
			result = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSetMapper<UserAuthen> userMapper = new ResultSetMapper<UserAuthen>();
		UserAuthen userAuthen = userMapper.mapRersultSetToObject(result, UserAuthen.class).get(0);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(userAuthen.getPassword(), null);
		User user = new User(String.valueOf(userAuthen.getUsername()), pass, authorities);
		return user;
	}
	
	@SuppressWarnings("deprecation")
	private User getUserFromReduis(String userName){
		ResultSet result = null;
		ConnectionDB.getInstance();
		Connection con = ConnectionDB.getRBRUMySQL();
		String sql = "	SELECT  rad.username as 'USERNAME', 	"+
				"	rad.value as 'VALUE', 	"+
				"	rad.ATTRIBUTE as 'ATTRIBUTE'	"+
				"	FROM IDR.radcheck rad	"+
				"	WHERE rad.username = '"+userName+"'";
		System.out.println(sql);
		
		try {
			Statement st = con.createStatement();
			result = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSetMapper<UserAuthen> userMapper = new ResultSetMapper<UserAuthen>();
		UserAuthen userAuthen = userMapper.mapRersultSetToObject(result, UserAuthen.class).get(0);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = "";
		if(userAuthen.getAttribute().equalsIgnoreCase("MD5-Password")){
			pass = userAuthen.getValue();
		}else{
			pass = encoder.encodePassword(userAuthen.getValue(), null);
		}
		User user = new User(String.valueOf(userAuthen.getUsername()), pass, authorities);
		return user;
	}
}
