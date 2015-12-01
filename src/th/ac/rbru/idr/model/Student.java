package th.ac.rbru.idr.model;


import java.util.Date;
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
	@Column(name="STUDENTNAMEENG")
	private String firstNameEng;
	@Column(name="STUDENTSURNAMEENG")
	private String surNameEng;
	@Column(name="BIRTHDATE")
	private Date birthDate;
	@Column(name="FACULTYNAME")
	private String facultyName;
	@Column(name="STUDENTCODE")
	private String studentCode;
	@Column(name="PERIOD")
	private String period;
	@Column(name="LEVELCODENAME")
	private String levelCodeName;
	@Column(name="DEGREECERTIFICATE")
	private String degreeName;
	@Column(name="DEGREEABB")
	private String degreeAbb;
	@Column(name="STUDYYEAR")
	private int studyYear;
	@Column(name="PROGRAMNAME")
	private String programName;
	@Column(name="STUDENTYEAR")
	private int studentYear;
	@Column(name="ADMITACADYEAR")
	private int admitAcadYear;
	@Column(name="DEGREEID")
	private int degreeID;
	@Column(name="GPA")
	private double gpa;
	
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
	public String getFirstNameEng() {
		return firstNameEng;
	}
	public void setFirstNameEng(String firstNameEng) {
		this.firstNameEng = firstNameEng;
	}
	public String getSurNameEng() {
		return surNameEng;
	}
	public void setSurNameEng(String surNameEng) {
		this.surNameEng = surNameEng;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getLevelCodeName() {
		return levelCodeName;
	}
	public void setLevelCodeName(String levelCodeName) {
		this.levelCodeName = levelCodeName;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	public String getDegreeAbb() {
		return degreeAbb;
	}
	public void setDegreeAbb(String degreeAbb) {
		this.degreeAbb = degreeAbb;
	}
	public int getStudyYear() {
		return studyYear;
	}
	public void setStudyYear(int studyYear) {
		this.studyYear = studyYear;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public int getStudentYear() {
		return studentYear;
	}
	public void setStudentYear(int studentYear) {
		this.studentYear = studentYear;
	}
	public int getAdmitAcadYear() {
		return admitAcadYear;
	}
	public void setAdmitAcadYear(int admitAcadYear) {
		this.admitAcadYear = admitAcadYear;
	}
	public int getDegreeID() {
		return degreeID;
	}
	public void setDegreeID(int degreeID) {
		this.degreeID = degreeID;
	}
	public double getGpa() {
		return gpa;
	}
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
}