package model;

public abstract class Plant {
	protected int plantId;
	protected String plantName;
	protected int minHarvestAge;
	protected double minTemperature;
	protected double maxTemperature;
	protected double minPH;
	protected double maxPH;
	
	public Plant() {
	}
	
	public Plant(String plantName) {
		this.plantName = plantName;
	}
	
	public abstract double calculateGrowthScore(int age, double temperature, double ph);
	public abstract String getPlantType();
	
	//getter
	public int getPlantId() {
		return plantId;
	}
	
	public String getPlantName() {
		return plantName;
	}
	
	public int getMinHarvestAge() {
		return minHarvestAge;
	}
	
	public double getMinTemperature() {
		return minTemperature;
	}
	
	public double getMaxTemperature() {
		return maxTemperature;
	}
	
	public double getMaxPH() {
		return maxPH;
	}
	
	public double getMinPH() {
		return minPH;
	}
	
	//setter
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
	public void setMinHarvestAge(int minHarvestAge) {
		this.minHarvestAge = minHarvestAge;
	}
	
	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}
	
	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	
	public void setMinPH(double minPH) {
		this.minPH = minPH;
	}
	
	public void setMaxPH(double maxPH) {
		this.maxPH = maxPH;
	}
}
