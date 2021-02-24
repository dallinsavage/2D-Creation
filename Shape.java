import javafx.scene.shape.Polygon;

public class Shape extends Polygon{
	private double centerX;
	private double centerY;
	boolean selected;
	Shape() {
		super();
	}
	Shape(double newCenterX, double newCenterY) {
		super(newCenterX - 50, newCenterY - 50, newCenterX + 50, newCenterY - 50, newCenterX + 50, newCenterY + 50, newCenterX - 50, newCenterY + 50);
		centerX = newCenterX;
		centerY = newCenterY;
	}
	public double getCenterX() {
		return centerX;
	}
	public void setCenterX(double newCenterX) {
		centerX = newCenterX;
	}
	public double getCenterY() {
		return centerY;
	}
	public void setCenterY(double newCenterY) {
		centerY = newCenterY;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean select) {
		selected = select;
	}
	public void draw() {
		
	}
}
