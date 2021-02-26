import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Shape extends Polygon{
	private double centerX;
	private double centerY;
	private boolean selected;
	private ObservableList<Double> points;
	Shape() {
		super();
	}
	Shape(double newCenterX, double newCenterY) {
		super(newCenterX - 50, newCenterY - 50, newCenterX + 50, newCenterY - 50, newCenterX + 50, newCenterY + 50, newCenterX - 50, newCenterY + 50);
		this.setStrokeWidth(2);
		this.setStroke(Color.BLACK);
		points = this.getPoints();
		centerX = newCenterX;
		centerY = newCenterY;
	}
	Shape(double newCenterX, double newCenterY, double[] newPoints) {
		super(newPoints);
		this.setStrokeWidth(2);
		this.setStroke(Color.BLACK);
		points = this.getPoints();
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
	public double[] convertPoints(ObservableList<Double> observablePoints) {
		double[] list = new double[observablePoints.size()];
		for (int i = 0; i < observablePoints.size(); i++) {
			list[i] = observablePoints.get(i);
		}
		return list;
	}
	public Shape draw() {
		double[] doubleList = convertPoints(getPoints());
		Shape shape = new Shape(this.getCenterX(), this.getCenterY(), doubleList);
		return shape;
	}
	public void deselect() {
		setSelected(false);
		setStroke(this.getFill());
	}
}
