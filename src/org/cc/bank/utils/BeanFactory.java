package org.cc.bank.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Bean工厂，用来管理各种乱七八糟的接口和实现
 * @author CC11001100
 *
 */
public class BeanFactory {
	
	//配置文件
	private Document doc;
	//默认的bean配置文件路径
	private String CONF_PATH="beans.xml";
	
	private BeanFactory() {
		//加载bean配置文件
		try {
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(CONF_PATH);
			doc=new SAXReader().read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 终极模式...
	 * @author CC11001100
	 */
	private static class BeanFactoryHolder {
		private static final BeanFactory INSTANCE=new BeanFactory();
	}
	
	/**
	 * 单例，获取对象
	 * @return
	 */
	public static BeanFactory getInstance(){
		return BeanFactoryHolder.INSTANCE;
	}
	
	//缓冲池
	private Map<String,Object> buffer=new HashMap<>();
	
	/**
	 * 得到接口的实现类的对象
	 * @param klazz
	 * @return
	 */
	public <T> T getBean(Class<T> klazz){
		//先检查池子里面有没有
		if(buffer.get(klazz.getName())!=null) return (T) buffer.get(klazz.getName());
		
		Element e=(Element) doc.selectSingleNode("//bean[@name=\""+klazz.getName()+"\"]");

		if(e==null) return null;
		
		T t = null;
		try {
			t=(T) Class.forName(e.attributeValue("class")).newInstance();
			//放入缓冲池，下次就可以直接取来用了
			buffer.put(klazz.getName(),t);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return t;
	}
	
}
