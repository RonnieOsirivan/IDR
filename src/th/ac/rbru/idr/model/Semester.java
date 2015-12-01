package th.ac.rbru.idr.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Semester {
	@Column(name="SEMESTER")
	private int semester;
	@Column(name="ACADYEAR")
	private int acadYear;
	
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
}