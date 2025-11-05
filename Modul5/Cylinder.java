package Modul5;

public class Cylinder extends Shape {
	private double radius;
	private double height;
	
	public Cylinder (double r, double h) {
		super("Cylinder");
		radius = r;
		height = h;
	}
	
	@Override
	public double area() {
		return 2 * Math.PI * radius * (radius + height);
	}
	
	public String toString () {
		return super.toString() + " of radius " + radius + " and height " + height;
	}
}
