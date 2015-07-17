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
	@Column(name="CREATEDATE")
	private Timestamp createDate;
	
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
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}