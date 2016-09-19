package org.cc.bank.test;

import static org.junit.Assert.*;

import org.cc.bank.po.Admin;
import org.cc.bank.utils.ReflectionUtil;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 反射工具类 单元测试
 * @author CC11001100
 *
 */
public class ReflectionUtilTest {

	@Test
	public void testSetProperty() {
		Admin admin=new Admin();
		ReflectionUtil.setProperty(admin,"name","令狐冲");;
		System.out.println(admin.getName());
	}

	@Test
	public void testGetProperty() {
		
		Admin admin=new Admin();
		ReflectionUtil.setProperty(admin,"name","令狐冲");;
		System.out.println(ReflectionUtil.getProperty(admin,"name"));
	}

}
