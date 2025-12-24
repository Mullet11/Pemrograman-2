package model;

public class LeafPlant extends Plant {
	public LeafPlant() {
		super();
	}
	
	@Override
	public double calculateGrowthScore(int age, double temperature, double ph) {
		double score = 0;
		
		if(age >= minHarvestAge) score += 40;
		if(temperature >= minTemperature && temperature <= maxTemperature) score += 30;
		if(ph >= minPH && ph <= maxPH) score += 30;
		
		return score;
	}
	
	@Override
    public String getPlantType() {
        return "LEAF";
    }
}
