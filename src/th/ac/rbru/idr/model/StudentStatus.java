package th.ac.rbru.idr.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class StudentStatus {
	@Column(name="STUDENTSTATUS")
	private int studentStatus;
	@Column(name="BYTEDES")
	private String statusNameThai;
	@Column(name="BYTEDESENG")
	private String StatusNameEng;
	@Column(name="SEMESTER")
	private int semester;
	@Column(name="ACADYEAR")
	private int acadYear;
	
	private String canReq;
	private String msgThai;
	private String msgEng;
	
	public int getStudentStatus() {
		return studentStatus;
	}
	public void setStudentStatus(int studentStatus) {
		this.studentStatus = studentStatus;
	}
	public String getStatusNameThai() {
		return statusNameThai;
	}
	public void setStatusNameThai(String statusNameThai) {
		this.statusNameThai = statusNameThai;
	}
	public String getStatusNameEng() {
		return StatusNameEng;
	}
	public void setStatusNameEng(String statusNameEng) {
		StatusNameEng = statusNameEng;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(int acadYear) {
		this.acadYear = acadYear;
	}
	public String getCanReq() {
		return canReq;
	}
	public void setCanReq(String canReq) {
		this.canReq = canReq;
	}
	public String getMsgThai() {
		return msgThai;
	}
	public void setMsgThai(String msgThai) {
		this.msgThai = msgThai;
	}
	public String getMsgEng() {
		return msgEng;
	}
	public void setMsgEng(String msgEng) {
		this.msgEng = msgEng;
	}
}
