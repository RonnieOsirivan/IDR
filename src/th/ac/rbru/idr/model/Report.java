package th.ac.rbru.idr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Report {
	
	@Column(name="REPORTID")
	private int reportId;
	@Column(name="STUDENTCODE")
	private String studentCode;
	@Column(name="STUDENTNAME")
	private String studentName;
	@Column(name="REPORTNAMETHAI")
	private String reportName;
	@Column(name="LANGUAGE")
	private String language;
	@Column(name="CREATEDATE")
	private Timestamp createDate;
	@Column(name="REPORTPRINTSTATUS")
	private int reportPrintStatus;
	@Column(name="TELEPHONENUMBER")
	private String telNum;
	@Column(name="USEFOR")
	private String useFor;
	private String reportLink;
	private String createDateString;
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public int getReportPrintStatus() {
		return reportPrintStatus;
	}
	public void setReportPrintStatus(int reportPrintStatus) {
		this.reportPrintStatus = reportPrintStatus;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public String getUseFor() {
		return useFor;
	}
	public void setUseFor(String useFor) {
		this.useFor = useFor;
	}
	public String getReportLink() {
		return reportLink;
	}
	public void setReportLink(String reportLink) {
		this.reportLink = reportLink;
	}
	public String getCreateDateString() {
		return createDateString;
	}
	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}
}