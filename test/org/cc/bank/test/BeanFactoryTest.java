package org.cc.bank.test;

import static org.junit.Assert.assertNotNull;

import org.cc.bank.dao.BusinessDao;
import org.cc.bank.utils.BeanFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 测试BeanFactory的实现 
 * @author CC11001100
 *
 */
public class BeanFactoryTest {

	@Test
	public void testGetBean() {
		assertNotNull("不能获取BeanFactory实例",BeanFactory.getInstance());
		assertNotNull("不能获取Bean实例",BeanFactory.getInstance().getBean(BusinessDao.class));
	}

}
