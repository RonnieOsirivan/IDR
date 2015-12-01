package th.ac.rbru.idr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class StudentEng {
	@Column(name="PREFIXNAMEENG")
	private String prefixName;
	@Column(name="STUDENTNAMEENG")
	private String studentName;
	@Column(name="STUDENTSURNAMEENG")
	private String studentSurname;
	@Column(name="STUDENTCODE")
	private String studentCode;
	@Column(name="BIRTHDATE")
	private Date birtDate;
	@Column(name="DEGREECERTIFICATEENG")
	private String degreeCerificate;
	@Column(name="PROGRAMNAME")
	private String programNameThai;
	@Column(name="PROGRAMNAMEENG")
	private String programName;
	@Column(name="FACULTYNAMETHAI")
	private String facultyNameThai;
	@Column(name="FACULTYNAMEENG")
	private String facultyName;
	@Column(name="GPA")
	private double gpa;
	
	public String getPrefixName() {
		return prefixName;
	}
	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentSurname() {
		return studentSurname;
	}
	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public Date getBirtDate() {
		return birtDate;
	}
	public void setBirtDate(Date birtDate) {
		this.birtDate = birtDate;
	}
	public String getDegreeCerificate() {
		return degreeCerificate;
	}
	public void setDegreeCerificate(String degreeCerificate) {
		this.degreeCerificate = degreeCerificate;
	}
	public String getProgramNameThai() {
		return programNameThai;
	}
	public void setProgramNameThai(String programNameThai) {
		this.programNameThai = programNameThai;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getFacultyNameThai() {
		return facultyNameThai;
	}
	public void setFacultyNameThai(String facultyNameThai) {
		this.facultyNameThai = facultyNameThai;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public double getGpa() {
		return gpa;
	}
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
}