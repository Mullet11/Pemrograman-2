package service;

import dao.RackDAO;
import model.Rack;

import java.util.List;

public class RackService {
	private final RackDAO rackDAO = new RackDAO();
	
	public void addRack(String name, String location, int capacity) {
	    Rack rack = new Rack();
	    rack.setRackName(name);
	    rack.setLocation(location);
	    rack.setCapacity(capacity);

	    rackDAO.insert(rack);
	}
	
	public List<Rack> getAllRacks(){
		return rackDAO.findAll();
	}
	
	public Rack getRackById(int id) {
		return rackDAO.findById(id);
	}
	
	public void updateRack(Rack rack) {
		rackDAO.update(rack);
	}
	
	public void deleteRack(int id) {
		rackDAO.delete(id);
	}
}
