/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager.services;

import java.io.IOException;
import java.util.Collection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.util.FileCopyUtils;
import org.springframework.http.MediaType;

import com.freshgrade.studentmanager.model.RandomStudentResultContainer;
import com.freshgrade.studentmanager.model.Student;
import com.freshgrade.studentmanager.services.dataaccess.StudentManagerDataAccessService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * This Service manages the Rest Entry Points for this application
 * @RestController
 * @author softcoder
 */
// The root base URL
@RestController
@RequestMapping("/v1/student-manager")
public class StudentManagerService {

    @Autowired
    StudentManagerDataAccessService dataAccess;

    /**
     * Rest API to get a list of all students in the data access layer
     * @return - list of student entities
     */
    @ApiOperation(value = "getAllStudents", nickname = "getAllStudents")
    @RequestMapping(path="",method=RequestMethod.GET)
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Success", response = Student.class, responseContainer="List")
    })
	public Collection<Student> getAllStudents() {
        return getDataAccess().getAllStudents();
	}

    /**
     * Rest API to find a student given their id in the data access layer
     * @param id
     * @return
     */
    @ApiOperation(value = "findStudentById", nickname = "findStudentById")
    @RequestMapping(path="find/{id}",method=RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Student id", required = true, dataType = "long", paramType = "path")
    })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Student.class),
        @ApiResponse(code = 404, message = "Not Found")
    })     
    public Student findStudentById(@PathVariable("id") long id) {
        return getDataAccess().findStudentById(id);
    }

    /**
     * Rest API to create a student given their fullName and optionally 
     * their Photo in the data access layer
     * @param fullName - full Student Name
     * @param photoFileRef - Optional Photo file reference
     * @return - the newly created Student entity
     * @throws Throwable
     */
    @ApiOperation(value = "createStudentByFullName", nickname = "createStudentByFullName")
    @RequestMapping(path="create_fullname",method=RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "full_name", value = "Student full name", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "photoFile", value = "Student photo", required = false, dataType = "MultipartFile", paramType = "query")
    })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Student.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Failure") 
    })
    public ResponseEntity createStudentByFullName(
    		@RequestParam(value="full_name") String fullName, 
            @RequestParam(value="photoFile", required=false) 
            MultipartFile photoFileRef) throws Throwable {
    	if(fullName != null) {
	        fullName = java.net.URLDecoder.decode(fullName, "UTF-8");
	        Student student = extractStudentFromFullName(fullName);
	        if(validStudent(student)) {
	            if(validPhoto(photoFileRef)) {
			        extractStudentPhoto(photoFileRef, student);
	            }
	            student = getDataAccess().addStudent(student);
	            return ResponseEntity.ok(student);
	        }
    	}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
        		body("Student name format was invalid: " + fullName);
    }

    /**
     * Rest API to create a student given their entity in the data access layer
     * @param student - the Student entity to crate
     * @return - the newly created Student entity
     * @throws IOException 
     */
    @ApiOperation(value = "createRandomStudent", nickname = "createRandomStudent")
    @RequestMapping(path="create_random",method=RequestMethod.POST)
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Student.class) 
    })
    public Student createRandomStudent() throws IOException {
    	RestTemplate restTemplate = new RestTemplate();
    	RandomStudentResultContainer randomStudent = restTemplate.getForObject(
    			"https://randomuser.me/api/?inc=name,picture&noinfo", 
    			RandomStudentResultContainer.class);
    	Student student = new Student();
    	student.setFirstName(randomStudent.getResults().get(0).getName().getFirst());
    	student.setLastName(randomStudent.getResults().get(0).getName().getLast());
    	
    	byte[] photo = restTemplate.getForObject(
    			randomStudent.getResults().get(0).getPicture().getLarge(), 
    			byte[].class);
    	
    	MediaType mediaType = MediaType.IMAGE_JPEG;
    	student.setPhoto(photo,mediaType);
    	return student;
    }
    
    /**
     * Rest API to create a student given their entity in the data access layer
     * @param student - the Student entity to crate
     * @return - the newly created Student entity
     */
    @ApiOperation(value = "createStudent", nickname = "createStudent")
    @RequestMapping(path="create",method=RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "student", value = "Student entity", required = true, dataType = "Student", paramType = "body")
    })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Student.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Failure") 
    })
    public Student createStudent(@RequestBody Student student) {
        return getDataAccess().addStudent(student);
    }

    /**
     * Rest API to update an existing student given their id in the data access layer
     * @param id - the Student id to update
     * @param student - the Student entity values to be updated
     * @return - the updated Student entity or an error if the id is not valid
     */
    @ApiOperation(value = "updateStudent", nickname = "updateStudent")
    @RequestMapping(path="update/{id}",method=RequestMethod.PUT)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Student id", required = true, dataType = "long", paramType = "path"),
        @ApiImplicitParam(name = "Student", value = "Student entity", required = true, dataType = "Student", paramType = "body")
    })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Student.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") 
    })
    public Student updateStudent(@PathVariable("id") long id, 
                                 @RequestBody Student student) {
        student.setId(id);
        return getDataAccess().updateStudent(student);
    }

    /**
     * Rest API to delete an existing student given their id in the data access layer
     * @param id - the Student id to update
     * @return - the deleted Student entity or an error if the id is not valid
     */
    @ApiOperation(value = "deleteStudent", nickname = "deleteStudent")
    @RequestMapping(path="delete/{id}",method=RequestMethod.DELETE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Student id", required = true, dataType = "long", paramType = "path")
    })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Student.class),
        @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity deleteStudent(@PathVariable("id") long id) {
        Student student = getDataAccess().deleteStudent(id);
        if(validStudent(student)) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
        		body("Student id was invalid, cannot delete student.");
    }

	boolean validStudent(Student student) {
		return student != null;
	}

	boolean validPhoto(MultipartFile photoFileRef) throws IOException {
		return photoFileRef != null && photoFileRef.getInputStream() != null && 
				photoFileRef.getContentType() != null;
	}

	void extractStudentPhoto(MultipartFile photoFileRef, Student student) throws IOException {
		byte bytesForProfilePhoto[] = FileCopyUtils.copyToByteArray(
				photoFileRef.getInputStream());
		MediaType mt = MediaType.parseMediaType(
				photoFileRef.getContentType());
		student.setPhoto(bytesForProfilePhoto,mt);
	}
    
    private Student extractStudentFromFullName(String fullname) {
        // Test fullName in the format: Lastname, Firstname
        Student student = extractStudentFromFullNameUsingFormat(
            "(.+),\\s+(.+)",fullname, true);
        if(student == null) {
            // Test fullName in the format: Firstname Lastname
            student = extractStudentFromFullNameUsingFormat(
                        "(.+)\\s+(.+)",fullname, false);
        }
        return student;
    }

    private Student extractStudentFromFullNameUsingFormat(String regexp,
                    String fullname, boolean lastNameIsFirst) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(fullname);
        boolean foundFormat = matcher.find();
        if(foundFormat) {
            if(lastNameIsFirst) {
                return new Student(matcher.group(2),matcher.group(1));
            }
            return new Student(matcher.group(1),matcher.group(2));
        }
        return null;
    }

    StudentManagerDataAccessService getDataAccess() {
        return dataAccess;
    }
}
