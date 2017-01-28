/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StudentManagerApplication.class)
@WebAppConfiguration
public class StudentManagerApplicationTest {

	StudentManagerApplication appTest;
	
	@Before
	public void startUp() {
		appTest = spy(new StudentManagerApplication());	
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testHealthy() {
		String result = appTest.healthy();
		assertEquals("Still surviving.",result);
	}
	
}
