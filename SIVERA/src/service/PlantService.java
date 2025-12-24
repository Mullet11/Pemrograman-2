package service;

import dao.PlantDAO;
import model.Plant;

import java.util.List;

public class PlantService {
	private final PlantDAO plantDAO = new PlantDAO();
	
	public List<Plant> getAllPlants(){
		return plantDAO.findAll();
	}
	
	public Plant getPlantById(int id) {
		return plantDAO.findById(id);
	}
	
	public void addPlant(Plant plant) {
		plantDAO.insert(plant);
	}
	
	public void updatePlant(Plant plant) {
        plantDAO.update(plant);
    }

    public void deletePlant(int plantId) {
        plantDAO.delete(plantId);
    }

}
