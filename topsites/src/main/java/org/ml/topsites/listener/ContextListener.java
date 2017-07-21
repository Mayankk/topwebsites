package org.ml.topsites.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;

/**
 * Context listener class to handle whenever application starts, 
 * i.e when the context is initialized 
 * @author mkurra
 *
 */
public class ContextListener 
		implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ScheduledExecutorService exec =
				Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(new ApplicationStartup(),
				1, 24*60, TimeUnit.MINUTES);
		
	}
}
