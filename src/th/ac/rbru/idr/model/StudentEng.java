package th.ac.rbru.idr.model;

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
	private String studnetCode;
	@Column(name="DEGREECERTIFICATEENG")
	private String degreeCerificate;
	@Column(name="PROGRAMNAMEENG")
	private String programName;
	@Column(name="FACULTYNAMEENG")
	private String facultyName;
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
	public String getStudnetCode() {
		return studnetCode;
	}
	public void setStudnetCode(String studnetCode) {
		this.studnetCode = studnetCode;
	}
	public String getDegreeCerificate() {
		return degreeCerificate;
	}
	public void setDegreeCerificate(String degreeCerificate) {
		this.degreeCerificate = degreeCerificate;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
}
