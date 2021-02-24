import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.shape.Polygon;

public class Shape extends Polygon implements Observable{
	private int centerX;
	private int centerY;
	boolean selected;
	Shape() {
		super();
	}
	Shape(int newCenterX, int newCenterY) {
		super(newCenterX - 50, newCenterY - 50, newCenterX + 50, newCenterY - 50, newCenterX + 50, newCenterY + 50, newCenterX - 50, newCenterY + 50);
		centerX = newCenterX;
		centerY = newCenterY;
	}
	public int getCenterX() {
		return centerX;
	}
	public void setCenterX(int newCenterX) {
		centerX = newCenterX;
	}
	public int getCenterY() {
		return centerY;
	}
	private void setCenterY(int newCenterY) {
		centerY = newCenterY;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean select) {
		selected = select;
	}
	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}
}
