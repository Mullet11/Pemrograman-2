package model;

public class FruitPlant extends Plant {
	public FruitPlant() {
		super();
	}
	
	@Override
	public double calculateGrowthScore(int age, double temperature, double ph) {
		double score = 0;
		
		if(age >= minHarvestAge) score += 30;
		if(temperature >= minTemperature && temperature <= maxTemperature) score += 35;
		if(ph >= minPH && ph <= maxPH) score += 35;
		
		return score;
	}
	
	@Override
    public String getPlantType() {
        return "FRUIT";
    }
}
