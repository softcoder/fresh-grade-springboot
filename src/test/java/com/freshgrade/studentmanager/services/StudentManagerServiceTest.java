/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.services;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.any;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.verify;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.freshgrade.studentmanager.model.Student;
import com.freshgrade.studentmanager.services.dataaccess.StudentManagerDataAccessService;


public class StudentManagerServiceTest {

	StudentManagerService serviceTest;
	
	@Mock
	Student mockStudent1;

	@Mock
	StudentManagerDataAccessService dataAccess;
		
	@Before
	public void startUp() {
		MockitoAnnotations.initMocks(this);
		serviceTest = Mockito.spy(new StudentManagerService());	
		when(serviceTest.getDataAccess()).thenReturn(dataAccess);
		when(mockStudent1.getId()).thenReturn(101l);
	}
	
	@Test
	public void testGetAllStudents_empty() {
		Collection<Student> results = serviceTest.getAllStudents();
		
		assertEquals(Collections.emptyList(),results);
	}

	@Test
	public void testFindStudentById() {
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		Student result = serviceTest.findStudentById(101l);
		
		assertEquals(mockStudent1,result);
	}

	@Test
	public void testCreateStudentByFullName_ok() throws Throwable {
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		ResponseEntity<Student> result = serviceTest.createStudentByFullName("Richard Stallman",null);
		
		assertEquals(HttpStatus.OK,result.getStatusCode());
	}

	@Test
	public void testCreateStudentByFullNameAndPhoto_ok() throws Throwable {
		MultipartFile photoFileRef = mock(MultipartFile.class);
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		ResponseEntity<Student> result = serviceTest.createStudentByFullName("Richard Stallman",photoFileRef);
		
		assertEquals(HttpStatus.OK,result.getStatusCode());
	}

	@Test
	public void testCreateStudentByFullNameAndScriptInjection_ok() throws Throwable {
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		//doAnswer(returnsFirstArg()).when(dataAccess).addStudent(any(Student.class));
		when(dataAccess.addStudent(any(Student.class))).thenAnswer(new Answer<Student>() {
		   public Student answer(InvocationOnMock invocation) throws Throwable {
		      return (Student) invocation.getArguments()[0];
		   }
		});

		ResponseEntity<Student> result = serviceTest.createStudentByFullName(
				"Stallman, <script>alert('hello hack');</script>",null);
		
		assertEquals(HttpStatus.OK,result.getStatusCode());
		assertEquals("&lt;script&gt;alert(&#39;hello hack&#39;);&lt;/script&gt;",result.getBody().getFirstName());
	}
	
	@Test
	public void testCreateStudentByFullNameReversed_ok() throws Throwable {
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		ResponseEntity result = serviceTest.createStudentByFullName("Stallman, Richard",null);
		
		assertEquals(HttpStatus.OK,result.getStatusCode());
	}
	
	@Test
	public void testCreateStudentByFullName_null_bad_request() throws Throwable {
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		ResponseEntity result = serviceTest.createStudentByFullName(null,null);
		
		assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
	}

	@Test
	public void testCreateStudentByFullName_invalid_format_bad_request() throws Throwable {
		when(dataAccess.findStudentById(eq(101l))).thenReturn(mockStudent1);
		ResponseEntity result = serviceTest.createStudentByFullName("OnlyAFirstName",null);
		
		assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
	}
	
	@Test
	public void testCreateStudent() {
		Student student = new Student();
		when(dataAccess.addStudent(eq(student))).thenReturn(student);
		Student result = serviceTest.createStudent(student);
		
		assertEquals(student,result);
		verify(dataAccess).addStudent(eq(student));
	}

	@Test
	public void testUpdateStudent() {
		Student student = new Student();
		when(dataAccess.updateStudent(eq(student))).thenReturn(mockStudent1);
		Student result = serviceTest.updateStudent(101l,student);
		
		assertEquals(mockStudent1,result);
		verify(dataAccess).updateStudent(eq(student));
	}

	@Test
	public void testDeleteStudent_ok() {
		Student student = new Student();
		when(dataAccess.deleteStudent(eq(101l))).thenReturn(mockStudent1);
		ResponseEntity result = serviceTest.deleteStudent(101l);
		
		assertEquals(HttpStatus.OK,result.getStatusCode());
		verify(dataAccess).deleteStudent(eq(101l));
	}

	@Test
	public void testDeleteStudent_bad_request() {
		Student student = new Student();
		when(dataAccess.deleteStudent(eq(101l))).thenReturn(null);
		ResponseEntity result = serviceTest.deleteStudent(101l);
		
		assertEquals(HttpStatus.NOT_FOUND,result.getStatusCode());
		verify(dataAccess).deleteStudent(eq(101l));
	}
	
}
