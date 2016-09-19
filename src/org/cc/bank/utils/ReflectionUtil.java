package org.cc.bank.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 反射工具类
 * @author CC11001100
 *
 */
public class ReflectionUtil {
	
	/**
	 * 设置属性可访问
	 * @param field
	 */
	public static void makeFieldAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 设置方法可访问
	 * @param method
	 */
	public static void makeMethodAccessible(Method method) {
		if (!Modifier.isPublic(method.getModifiers())) {
			method.setAccessible(true);
		}
	}

	/**
	 * 获取本类显示声明的属性
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		try {
			return object.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取属性值（自己的或继承来的）
	 * @param object
	 * @param fieldName
	 * @return
	 */
	@Deprecated
	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field=getField(object,fieldName);
			if(field==null) return null;
			
//			Field.setAccessible(new Field[]{field},true);
			field.setAccessible(true);
			return field.get(object);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置属性的值（自己的或继承来的）
	 * @param object
	 * @param fieldName
	 * @param fieldValue
	 * @throws NoSuchFieldException 
	 */
	@Deprecated
	public static void setFieldValue(Object object, String fieldName, Object fieldValue) throws NoSuchFieldException {
		Field field=getField(object,fieldName);
		if(field==null) throw new NoSuchFieldException() ;
		
		try {
//			Field.setAccessible(new Field[]{field},true);
			field.setAccessible(true);
			field.set(object,fieldValue);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提取冗余代码：根据名字获取属性，然后setField or getField 
	 * 一个尴尬的情况：子类 父类都是Bean，然后子类可以通过继承而来的getter setter访问父类的私有field，
	 * 但就是不能直接访问（Java规范子类不能直接操作父类的私有）...
	 * 所以正确的姿势应该是通过setter getter来访问才对，所以此方法不建议使用  
	 * @param object
	 * @param fieldName
	 * @return
	 */
	@Deprecated
	private static Field getField(Object object,String fieldName){
		try {
			//从自己开始往上顺藤摸瓜
			Class<?> clazz=object.getClass();
			while(clazz!=Object.class){
				Field[] fields=clazz.getDeclaredFields();
				for(int i=0;i<fields.length;i++){
					//子类不应该继承父类的private，所以无视父类们的private field
					if(Modifier.isPrivate(fields[i].getModifiers()) && clazz!=object.getClass()) continue;
					if(fieldName.equals(fields[i].getName())){
						return fields[i];
					}
				}
				clazz=clazz.getSuperclass();
			}
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取在本类显示声明的方法
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		try {
			return object.getClass().getDeclaredMethod(methodName,parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得方法，无论是本类声明还是继承来的
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	private static Method getMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Class clazz=object.getClass();
		while(clazz!=Object.class){
			Method methods[]=clazz.getDeclaredMethods();
			Method.setAccessible(methods,true); //太挫了..
			for(int i=0;i<methods.length;i++){
				//子类不应该操作父类的private method
				if(Modifier.isPrivate(methods[i].getModifiers()) && clazz!=object.getClass()) continue;
				//因为Class只有一份，所以可以借助内置的进行比较，如果**名称**和**参数（数量、位置、类型）**都相同，则认定为同一个方法，返回值修饰符等不予考虑
				//这么做不能区分包装类型和基本类型，难道它们底层实现不一致？妈的我怎么知道... int.class!=Integer.class  WTF???!!!
				if(methodName.equals(methods[i].getName()) && Arrays.equals(methods[i].getParameterTypes(),parameterTypes)) return methods[i]; 
			}
			clazz=clazz.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 调用方法（本类声明或继承而来）
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * @throws NoSuchMethodException 
	 */
	public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,Object[] parameters) throws NoSuchMethodException {
		Method method=getMethod(object,methodName,parameterTypes);
		if(method==null) throw new NoSuchMethodException();
		
		try {
			return method.invoke(object,parameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置属性的值（通过setter方法）
	 * @param o
	 * @param fieldName
	 * @param fieldValue
	 */
	public static <T> void setProperty(Object o,String propertyName,T fieldValue){
		
		//先尝试通过setter设置
		String methodName="set"+StringUtils.initialUpperCase(propertyName);
		Method setter=getMethod(o,methodName,new Class[]{fieldValue.getClass()});
		if(setter!=null){
			try {
				setter.invoke(o,fieldValue);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return ;
		}
		
		//如果找不到setter再尝试通过field设置
		try {
			setFieldValue(o,propertyName,fieldValue);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		//那就是真没有了
	}
	
	/**
	 * 获得属性的值
	 * @param o
	 * @param propertyName
	 * @return
	 */
	public static <T> T getProperty(Object o,String propertyName){
		
		//先尝试通过setter设置
		String methodName="get"+StringUtils.initialUpperCase(propertyName);
		Method setter=getMethod(o,methodName,new Class[]{});
		if(setter!=null){
			try {
				return (T) setter.invoke(o);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		//再尝试直接访问可以继承到的field
		return (T) getFieldValue(o,propertyName);
		
		//下面，下面没有了
	}
	
	
}
