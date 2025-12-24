package model;

import java.util.Date;

public class SensorLog {
	private int logId;
	private double temperature;
	private double ph;
	private Date recordedAt;
	
	public SensorLog() {
	}
	
	public SensorLog(double temperature, double ph) {
		this.temperature = temperature;
		this.ph = ph;
		this.recordedAt = new Date();
	}
	
	//Getter
	public int getLogId() {
		return logId;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public double getPh() {
		return ph;
	}
	
	public Date getRecordedAt() {
		return recordedAt;
	}
	
	//Setter
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public void setPh(double ph) {
		this.ph = ph;
	}
	
	public void setRecordedAt(Date recordedAt) {
		this.recordedAt = recordedAt;
	}
}
