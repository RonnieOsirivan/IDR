package th.ac.rbru.idr.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ReportType {
	@Column(name="REPORTTYPEID")
	private int reportId;
	@Column(name="REPORTNAMETHAI")
	private String reportNameThai;
	@Column(name="REPORTNAMEENG")
	private String reportNameEng;
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getReportNameThai() {
		return reportNameThai;
	}
	public void setReportNameThai(String reportNameThai) {
		this.reportNameThai = reportNameThai;
	}
	public String getReportNameEng() {
		return reportNameEng;
	}
	public void setReportNameEng(String reportNameEng) {
		this.reportNameEng = reportNameEng;
	}
}