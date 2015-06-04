package th.ac.rbru.idr.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Student {
	
	@Column(name="PREFIXNAME")
	private String prefix;
	@Column(name="STUDENTNAME")
	private String firstName;
	@Column(name="STUDENTSURNAME")
	private String lastName;
	@Column(name="FACULTYNAME")
	private String facultyName;
	@Column(name="STUDENTCODE")
	private String studentCode;
	
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
}