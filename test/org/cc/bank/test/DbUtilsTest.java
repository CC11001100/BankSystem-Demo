package org.cc.bank.test;

import static org.junit.Assert.assertNotNull;

import org.cc.bank.utils.DbUtils;
import org.junit.Test;

/**
 * 数据库工具类单元测试
 * @author CC11001100
 *
 */
public class DbUtilsTest {

	@Test
	public void testGetConnection() {
		assertNotNull("不能获取DbUtil实例",DbUtils.getInstance());
		assertNotNull("不能获取数据库连接",DbUtils.getInstance().getConnection());
	}

}
