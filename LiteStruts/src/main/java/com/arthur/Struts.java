package com.arthur;

import java.lang.reflect.Method;
import java.util.Map;

public class Struts {

	private final static Configuration cfg = new Configuration("struts.xml");
	
	public static View runAction(String actionName, Map<String, String> parameters) {
		/*
        
		0. ¶ÁÈ¡ÅäÖÃÎÄ¼þstruts.xml
 		
 		1. ¸ù¾ÝactionNameÕÒµ½Ïà¶ÔÓ¦µÄclass £¬ ÀýÈçLoginAction,   Í¨¹ý·´ÉäÊµÀý»¯£¨´´½¨¶ÔÏó£©
		¾ÝparametersÖÐµÄÊý¾Ý£¬µ÷ÓÃ¶ÔÏóµÄsetter·½·¨£¬ ÀýÈçparametersÖÐµÄÊý¾ÝÊÇ 
		("name"="test" ,  "password"="1234") ,     	
		ÄÇ¾ÍÓ¦¸Ãµ÷ÓÃ setNameºÍsetPassword·½·¨
		
		2. Í¨¹ý·´Éäµ÷ÓÃ¶ÔÏóµÄexectue ·½·¨£¬ ²¢»ñµÃ·µ»ØÖµ£¬ÀýÈç"success"
		
		3. Í¨¹ý·´ÉäÕÒµ½¶ÔÏóµÄËùÓÐgetter·½·¨£¨ÀýÈç getMessage£©,  
		Í¨¹ý·´ÉäÀ´µ÷ÓÃ£¬ °ÑÖµºÍÊôÐÔÐÎ³ÉÒ»¸öHashMap , ÀýÈç {"message":  "µÇÂ¼³É¹¦"} ,  
		·Åµ½View¶ÔÏóµÄparameters
		
		4. ¸ù¾Ýstruts.xmlÖÐµÄ <result> ÅäÖÃ,ÒÔ¼°executeµÄ·µ»ØÖµ£¬  È·¶¨ÄÄÒ»¸öjsp£¬  
		·Åµ½View¶ÔÏóµÄjsp×Ö¶ÎÖÐ¡£
        
        */
    	
    	String clzName = cfg.getClassName(actionName);
    	
    	if(clzName == null) {
    		return null;
    	}
    	
    	try {
    		
    		Class<?> clz = Class.forName(clzName);
			Object action = clz.newInstance();
			
			ReflectionUtil.setParameters(action, parameters);
			
			Method m = clz.getDeclaredMethod("execute");
			
			String resultName = (String) m.invoke(action);
			
			String jsp = cfg.getResultView(actionName, resultName);
			
			Map<String, Object> params = ReflectionUtil.getParamterMap(action);
			
			View view = new View();
			view.setJsp(jsp);
			view.setParameters(params);
			
			return view;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
    	 
	}

        
    	
}
