/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.services.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.freshgrade.studentmanager.model.Student;
import com.freshgrade.studentmanager.services.cache.StudentManagerCacheService;

public class StudentManagerCacheServiceTest {

	StudentManagerCacheService serviceTest;
	
	@Before
	public void startUp() {
		serviceTest = Mockito.spy(new StudentManagerCacheService());
	}

	@Test(expected=NullPointerException.class)
	public void testSetValueForKeyForNull() {
		Long expectedValue = null;
		serviceTest.setValueForKey("key1",expectedValue);
		assertEquals(expectedValue,serviceTest.getValueForKey("key1"));
	}
	
	@Test
	public void testSetValueForKeyForLong() {
		Long expectedValue = 100l;
		serviceTest.setValueForKey("key1",expectedValue);
		assertEquals(expectedValue,serviceTest.getValueForKey("key1"));
	}

	@Test
	public void testSetValueForKeyForStudent() {
		Student expectedValue = new Student();
		serviceTest.setValueForKey("key1",expectedValue);
		assertEquals(expectedValue,serviceTest.getValueForKey("key1"));
	}
	
}
