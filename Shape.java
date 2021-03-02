import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Shape extends Polygon{
	private double centerX;
	private double centerY;
	private boolean selected;
	private double[] points;
	private ArrayList<Circle> shownPoints = new ArrayList<Circle>();
	private ArrayList<Point> pointList = new ArrayList();
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
		points = this.convertPoints();
		shownPoints = getSelectedPoints(this);
		centerX = newCenterX;
		centerY = newCenterY;
	}
	Shape(double newCenterX, double newCenterY, double[] newPoints) {
		super(newPoints);
		this.setStrokeWidth(2);
		this.setStroke(Color.BLACK);
		points = this.convertPoints();
		shownPoints = getSelectedPoints(this);
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
	public double[] getPointsDouble() {
		return points;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean select) {
		selected = select;
	}
	public void deselect() {
		setSelected(false);
		setStroke(this.getFill());
	}
	public double[] convertPoints() {
		double[] list = new double[this.getPoints().size()];
		for (int i = 0; i < this.getPoints().size(); i++) {
			list[i] = this.getPoints().get(i);
		}
		return list;
	}
	public ArrayList<Circle> getSelectedPoints(Shape shape) {
		double[] points = shape.getPointsDouble();
		ArrayList<Circle> pointList = new ArrayList<Circle>();
		for (int i = 0; i < points.length; i++) {
			double x = points[i];
			double y = points[i + 1];
			pointList.add(new Circle(x, y, 4));
			i++;
		}
		return pointList;
	}
	public void setShownPoints(ArrayList list) {
		shownPoints = list;
	}
	public ArrayList<Circle> getShownPoints() {
		return shownPoints;
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
