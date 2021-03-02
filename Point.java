
import javafx.scene.shape.Circle;

public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		new Circle(x, y, 4);
	}
	public double getPointX() {
		return x;
	}
	public double getPointY() {
		return y;
	}
	public void setPointX(double newX) {
		x = newX;
	}
	public void setPointY(double newY) {
		y = newY;
	}
}
