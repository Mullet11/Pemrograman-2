package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Batch implements Harvestable {
	private int batchId;
	private LocalDate plantingDate;
	private String status;
	
	public Plant plant;
	public Rack rack;
	public SensorLog sensorLog;

	public Batch() {
	}
	
	public Batch(Plant plant, LocalDate plantingDate) {
		this.plant = plant;
		this.plantingDate = plantingDate;
		this.status = "ACTIVE";
	}
	
	public int getPlantAge() {
		return(int) ChronoUnit.DAYS.between(plantingDate, LocalDate.now());
	}
	
	@Override
	public boolean isReadyToHarvest() {
		if (sensorLog == null || plant == null) return false;
		
		double score = plant.calculateGrowthScore(getPlantAge(), sensorLog.getTemperature(), sensorLog.getPh());
		
		return score >= 80;
	}
	
	@Override
	public void harvest() {
		if (isReadyToHarvest()) {
			status = "HARVESTED";
		}
	}
	
	//Getter
	public int getBatchId() {
		return batchId;
	}
	
	public LocalDate getPlantingDate() {
		return plantingDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Plant getPlant() {
		return plant;
	}
	
	public Rack getRack() {
		return rack;
	}
	
	public SensorLog getSensorLog() {
		return sensorLog;
	}
	
	//Setter
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	
	public void setPlantingDate(LocalDate plantingDate) {
		this.plantingDate = plantingDate;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	
	public void setRack(Rack rack) {
		this.rack = rack;
	}
	
	public void setSensorLog(SensorLog sensorLog) {
		this.sensorLog = sensorLog;
	}
}
