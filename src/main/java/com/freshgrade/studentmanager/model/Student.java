package com.freshgrade.studentmanager.model;

import org.springframework.http.MediaType;

/**
 * This class represents a Student model in this application
 * @author softcoder
 *
 */
public class Student {

    private long id;
    private String firstName;
    private String lastName;
    private byte[] photo;
	private MediaType mediaType;

    public Student() {
    }
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Copies the input students values into the current students values.
     * @param student
     */
    public void copyMutable(Student student) {
        this.firstName = student.firstName;
        this.lastName = student.lastName;
        this.photo = student.photo;
        this.mediaType = student.mediaType;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String name) {
        this.lastName = name;
    }

    public MediaType getMediaType() {
		return this.mediaType;
	}
	public byte[] getPhoto() {
		return this.photo;
	}

    public void setPhoto(byte[] data, MediaType mediaType) {
		this.mediaType = mediaType;
		this.photo = data;
	}
}
