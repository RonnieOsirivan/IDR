package th.ac.rbru.idr.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DocumentNumber {
	@Column(name="RUNNINGNUMBER")
	private int runningNumber;
	@Column(name="ACADYEAR")
	private int acadyear;
	
	public int getRunningNumber() {
		return runningNumber;
	}
	public void setRunningNumber(int runningNumber) {
		this.runningNumber = runningNumber;
	}
	public int getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(int acadyear) {
		this.acadyear = acadyear;
	}
}