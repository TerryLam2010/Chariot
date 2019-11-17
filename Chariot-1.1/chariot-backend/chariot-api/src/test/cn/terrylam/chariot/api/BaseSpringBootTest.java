package cn.terrylam.chariot.api;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseSpringBootTest {
	protected Logger logger = Logger.getLogger(BaseSpringBootTest.class);

	@Before(value = "")
	public void init() {
		logger.info("开始测试...");
	}

	@After(value = "")
	public void after() {
		logger.info("测试结束...");
	}
}