package com.freshgrade.studentmanager.services.dataaccess;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.freshgrade.studentmanager.model.Student;
import com.freshgrade.studentmanager.services.cache.StudentManagerCacheService;

/**
 * This is the Data Access Service providing access to the Student Entities
 * @author softcoder
 *
 */
@Component
public class StudentManagerDataAccessService {

    @Autowired
    StudentManagerCacheService cache;

    public void StudentManagerDataAccess() {
    }

    /**
     * Find a student given their student id
     * @param id - the student id to find
     * @return - the matching student entity or null if not found
     */
    public Student findStudentById(long id) {
        return getCachedStudents().get(id);
    }

    /**
     * Return a list of all students.
     * @return - a list of all students.
     */
    public Collection<Student> getAllStudents() {
        return getCachedStudentsAsList();
    }

    /**
     * Add a new student to the list
     * @param student - the student entity to add
     * @return - the newly added student
     */
    public Student addStudent(Student student) {
        student.setId(getNextCachedStudentId());
        Map<Long,Student> students = getCachedStudents();
        students.put(student.getId(),student);
        return student;
    }

    /**
     * Update an existing Student in the list
     * @param updateStudent - the student entity to update
     * @return - the updated student or null if the id is invalid
     */
    public Student updateStudent(Student updateStudent) {
        Student student = findStudentById(updateStudent.getId());
        if(student != null) {
            student.copyMutable(updateStudent);
            return student;
        }
        return null;
    }

    /**
     * Delete an existing student from the list
     * @param id - the student id to delete
     * @return - the deleted student or null if the id is invalid
     */
    public Student deleteStudent(long id) {
        Student student = findStudentById(id);
        if(student != null) {
            getCachedStudents().remove(id);
            return student;
        }
        return null;

    }

    /**
     * Update the students photo
     * @param id - the student id to update
     * @return - he updated student
     */
    public boolean updateStudentPhoto(long id) {
        return true;
    }

    List<Student> getCachedStudentsAsList() {
        List<Student> list = new ArrayList<Student>(getCachedStudents().values());
        return list;
    }

    Map<Long,Student> getCachedStudents() {
        Map<Long,Student> students = getCache().getValueForKey("db_stub_students");
        if(students == null) {
            students = new ConcurrentHashMap<Long,Student>();
            getCache().setValueForKey("db_stub_students",students);
        }
        return students;
    }

   Long getNextCachedStudentId() {
        AtomicLong student_id = getCache().getValueForKey("db_stub_student_ids");
        if(student_id == null) {
            student_id = new AtomicLong();
            getCache().setValueForKey("db_stub_student_ids",student_id);
        }
        return student_id.incrementAndGet();
    }

    StudentManagerCacheService getCache() {
        return cache;
    }
}
