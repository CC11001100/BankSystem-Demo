package org.cc.bank.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 批量运行所有的测试，如果 the bar is green then the code is clean. 
 * @author CC11001100
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	BeanFactoryTest.class,
	DbUtilsTest.class
})
public class SuiteHolder {

}
