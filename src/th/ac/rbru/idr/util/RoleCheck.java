package th.ac.rbru.idr.util;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class RoleCheck {
	public boolean hasRole(String role) {
	  Collection<GrantedAuthority> authorities = ((UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication()).getAuthorities();
	  boolean hasRole = false;
	  for (GrantedAuthority authority : authorities) {
	     hasRole = authority.getAuthority().equals(role);
	     if (hasRole) {
		  break;
	     }
	  }
	  return hasRole;
	}
}
