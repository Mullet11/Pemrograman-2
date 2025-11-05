package Modul5;

public abstract class Shape {
	protected String shapeName;
	
	Shape(String name) {
		this.shapeName = name;
	}
	
	protected abstract double area();
	
	public String toString() {
		return shapeName;
	}
}
