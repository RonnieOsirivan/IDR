package th.ac.rbru.idr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class StudentEng {
	
	@Column(name="PREFIXNAME")
	private String prefixNameThai;
	@Column(name="STUDENTNAME")
	private String studentNameThai;
	@Column(name="STUDENTSURNAME")
	private String studentSurnameThai;
	@Column(name="PREFIXNAMEENG")
	private String prefixNameEng;
	@Column(name="STUDENTNAMEENG")
	private String studentNameEng;
	@Column(name="STUDENTSURNAMEENG")
	private String studentSurnameEng;
	@Column(name="STUDENTCODE")
	private String studentCode;
	@Column(name="BIRTHDATE")
	private Date birtDate;
	@Column(name="DEGREENAMEENG")
	private String degreeNameEng;
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
	@Column(name="ADMITACADYEAR")
	private int admitAcadYear;
	@Column(name="DEGREEID")
	private int degreeId;
	
	
	public String getPrefixNameThai() {
		return prefixNameThai;
	}
	public void setPrefixNameThai(String prefixNameThai) {
		this.prefixNameThai = prefixNameThai;
	}
	public String getStudentNameThai() {
		return studentNameThai;
	}
	public void setStudentNameThai(String studentNameThai) {
		this.studentNameThai = studentNameThai;
	}
	public String getStudentSurnameThai() {
		return studentSurnameThai;
	}
	public void setStudentSurnameThai(String studentSurnameThai) {
		this.studentSurnameThai = studentSurnameThai;
	}
	public String getPrefixNameEng() {
		return prefixNameEng;
	}
	public void setPrefixNameEng(String prefixNameEng) {
		this.prefixNameEng = prefixNameEng;
	}
	public String getStudentNameEng() {
		return studentNameEng;
	}
	public void setStudentNameEng(String studentNameEng) {
		this.studentNameEng = studentNameEng;
	}
	public String getStudentSurnameEng() {
		return studentSurnameEng;
	}
	public void setStudentSurnameEng(String studentSurnameEng) {
		this.studentSurnameEng = studentSurnameEng;
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
	public String getDegreeNameEng() {
		return degreeNameEng;
	}
	public void setDegreeNameEng(String degreeNameEng) {
		this.degreeNameEng = degreeNameEng;
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
	public int getAdmitAcadYear() {
		return admitAcadYear;
	}
	public void setAdmitAcadYear(int admitAcadYear) {
		this.admitAcadYear = admitAcadYear;
	}
	public int getDegreeId() {
		return degreeId;
	}
	public void setDegreeId(int degreeId) {
		this.degreeId = degreeId;
	}
}