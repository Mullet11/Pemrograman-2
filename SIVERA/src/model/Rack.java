package model;

public class Rack {
	private int rackId;
	private String rackName;
	private String location;
	private int capacity;
	
	public Rack() {
	}
	
	public Rack(String rackName, int capacity) {
		this.rackName = rackName;
		this.capacity = capacity;
	}
	
	@Override
    public String toString() {
        return rackName + " - " + location;
    }
	
	//Getter
	public int getRackId() {
		return rackId;
	}
	
	public String getRackName() {
		return rackName;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	//Setter
	public void setRackId(int rackId) {
		this.rackId = rackId;
	}
	
	public void setRackName(String rackName) {
		this.rackName = rackName;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
