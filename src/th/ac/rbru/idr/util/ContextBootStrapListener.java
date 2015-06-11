package th.ac.rbru.idr.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextBootStrapListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ConnectionDB.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ConnectionDB.getInstance();
	}

}
