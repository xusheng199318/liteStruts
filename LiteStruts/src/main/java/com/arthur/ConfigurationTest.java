package com.arthur;

import org.junit.Assert;
import org.junit.Test;


public class ConfigurationTest {

	Configuration cfg = new Configuration("struts.xml");
	
	@Test
	public void testGetClassName() {
		String clzName = cfg.getClassName("login");
		Assert.assertEquals("com.arthur.LoginAction", clzName);
		System.out.println(clzName);
		
		clzName = cfg.getClassName("logout");
		Assert.assertEquals("com.arthur.LogoutAction", clzName);
		System.out.println(clzName);
	}
	
	@Test
	public void testGetResultView() {
		String jsp = cfg.getResultView("login","success");
		Assert.assertEquals("/jsp/homepage.jsp", jsp);
	}
	
}


