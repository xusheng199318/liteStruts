package com.arthur;

import java.lang.reflect.Method;
import java.util.Map;

public class Struts {

	private final static Configuration cfg = new Configuration("struts.xml");
	
	public static View runAction(String actionName, Map<String, String> parameters) {
		/*
        
		0. ��ȡ�����ļ�struts.xml
 		
 		1. ����actionName�ҵ����Ӧ��class �� ����LoginAction,   ͨ������ʵ��������������
		��parameters�е����ݣ����ö����setter������ ����parameters�е������� 
		("name"="test" ,  "password"="1234") ,     	
		�Ǿ�Ӧ�õ��� setName��setPassword����
		
		2. ͨ��������ö����exectue ������ ����÷���ֵ������"success"
		
		3. ͨ�������ҵ����������getter���������� getMessage��,  
		ͨ�����������ã� ��ֵ�������γ�һ��HashMap , ���� {"message":  "��¼�ɹ�"} ,  
		�ŵ�View�����parameters
		
		4. ����struts.xml�е� <result> ����,�Լ�execute�ķ���ֵ��  ȷ����һ��jsp��  
		�ŵ�View�����jsp�ֶ��С�
        
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
