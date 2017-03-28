package com.arthur;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Configuration {
	
	Map<String,ActionConfig> actions = new HashMap<String,ActionConfig>();

	public Configuration (String fileName) {
		String packageName = this.getClass().getPackage().getName();
		packageName = packageName.replace(".", "/");
		System.out.println("包名："+packageName);
		InputStream is = this.getClass().getResourceAsStream("/" + packageName + "/" + fileName);
		parseXML(is);
		
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void parseXML(InputStream is) {
		SAXBuilder builder = new SAXBuilder();
		
		try {
			
			Document doc = builder.build(is);
		
			Element root = doc.getRootElement();
			
			List<Element> children = root.getChildren("action");
			for(Element actionElement : children) {
				String actionName = actionElement.getAttributeValue("name");
				String clzName = actionElement.getAttributeValue("class");
				
				ActionConfig ac = new ActionConfig(actionName,clzName);
				
				List<Element> resultChildren = actionElement.getChildren("result");
				for(Element resultElement : resultChildren) {
					
					String resultName = resultElement.getAttributeValue("name");
					String viewName = resultElement.getText().trim();
					
					ac.addViewResult(resultName,viewName);
				}
				this.actions.put(actionName,ac);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getClassName(String action) {
		ActionConfig ac = this.actions.get(action);
		if(ac == null) {
			return null;
		}
		return ac.getClassName();
	}
	
	public String getResultView(String actionName, String resultName) {
		ActionConfig ac = this.actions.get(actionName);
		if(ac == null) {
			return null;
		}
		return ac.getViewName(resultName);
	}
	
	private static class ActionConfig{
		String name;
		String clzName;
		Map<String,String> viewResult = new HashMap<String,String>();
		
		public ActionConfig(String name, String clzName) {
			this.name = name;
			this.clzName = clzName;
		}
		
		public String getClassName() {
			return this.clzName;
		}
		
		public void addViewResult(String name,String viewName) {
			this.viewResult.put(name,viewName);
		}
		
		public String getViewName(String resultName) {
			return this.viewResult.get(resultName);
		}

	}

	
}
