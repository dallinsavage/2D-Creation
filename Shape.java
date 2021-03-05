import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Shape extends Polygon{
	private double centerX;
	private double centerY;
	private ArrayList<Point> pointList = new ArrayList<Point>();
	Shape() {
		super();
	}
	Shape(double newCenterX, double newCenterY) {
		super(newCenterX - 50, newCenterY - 50, newCenterX + 50, newCenterY - 50, newCenterX + 50, newCenterY + 50, newCenterX - 50, newCenterY + 50);
		this.setStrokeWidth(2);
		this.setStroke(Color.BLACK);
		pointList.add(new Point(newCenterX - 50, newCenterY - 50));
		pointList.add(new Point(newCenterX + 50, newCenterY - 50));
		pointList.add(new Point(newCenterX + 50, newCenterY + 50));
		pointList.add(new Point(newCenterX - 50, newCenterY + 50));
		centerX = newCenterX;
		centerY = newCenterY;
	}
	Shape(double newCenterX, double newCenterY, double[] newPoints) {
		super(newPoints);
		this.setStrokeWidth(2);
		this.setStroke(Color.BLACK);
		centerX = newCenterX;
		centerY = newCenterY;
		for (int i = 0; i < newPoints.length; i++) {
			double x = newPoints[i];
			double y = newPoints[i + 1];
			pointList.add(new Point(x, y));
			i++;
		}
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
	public void deselect() {
		setStroke(this.getFill());
	}
	public ArrayList<Point> getPointList() {
		return pointList;
	}
	public void setPointList(ArrayList<Point> array) {
		pointList = array;
	}
	public void addPoint(int index, Point point) {
		ArrayList<Point> newPoints = getPointList();
		newPoints.add(index, point);
		setPointList(newPoints);
	}
}
