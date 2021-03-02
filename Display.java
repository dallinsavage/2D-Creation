import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Shape[] selection = new Shape[1];
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 750, 750);
		primaryStage.setScene(scene);
		primaryStage.setTitle("2D Creation");
		primaryStage.show();
		
		// tool bar
		Label newColor = new Label("Color");
		TextField entry = new TextField();
		Button change = new Button("Change");
		ToggleGroup tools = new ToggleGroup();
		VBox vBox = new VBox();
		Button newShape = new Button("New Shape");
		RadioButton select = new RadioButton("Select");
		select.setToggleGroup(tools);
		RadioButton move = new RadioButton("Move");
		move.setToggleGroup(tools);
		RadioButton color = new RadioButton("Change Color");
		color.setToggleGroup(tools);
		RadioButton resize = new RadioButton("Resize");
		resize.setToggleGroup(tools);
		RadioButton addPoint = new RadioButton("Add Point");
		addPoint.setToggleGroup(tools);
		vBox.getChildren().addAll(newShape, select, move, color, resize, addPoint);
		pane.getChildren().add(vBox);
		
		//tool bar events
		pane.setOnMouseClicked(e -> {
			
			// select shape
			if (tools.getSelectedToggle() == select) {
				Shape closest = shapes.get(0);
				double clickX = e.getX();
				double clickY = e.getY();
				for (int i = 0; i < shapes.size(); i++) {
					if (Math.abs(shapes.get(i).getCenterX() - clickX) < Math.abs(closest.getCenterX() - clickX) &&
							Math.abs(shapes.get(i).getCenterY() - clickY) < Math.abs(closest.getCenterY() - clickY)) {
						closest = shapes.get(i);
					}
				}
				if (selection[0] == null) {
				}
				else {
					selection[0].deselect();
					pane.getChildren().removeAll(selection[0].getShownPoints());
				}
				selection[0] = closest;
				select(closest);
				pane.getChildren().addAll(selection[0].getShownPoints());
			}
			
			//move selected shape
			else if (tools.getSelectedToggle() == move) {
				pane.getChildren().removeAll(selection[0].getShownPoints());
				double xChange = selection[0].getCenterX() - e.getX();
				double yChange = selection[0].getCenterY() - e.getY();
				for (int i = 0; i < selection[0].getPoints().size(); i++) {
					Line pointLine = new Line(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), xChange, yChange);
					pointLine.setOpacity(0);
					pane.getChildren().add(pointLine);
					PathTransition pointPT = new PathTransition(Duration.millis(1), pointLine, selection[0].getShownPoints().get(i));
					pointPT.play();
				}
				Line line = new Line(selection[0].getCenterX(), selection[0].getCenterY(), e.getX(), e.getY());
				line.setOpacity(0);
				pane.getChildren().add(line);
				PathTransition pt = new PathTransition(Duration.millis(1), line, selection[0]);
				pt.play();
				selection[0].setCenterX(e.getX());
				selection[0].setCenterY(e.getY());
				pane.getChildren().remove(line);
				selection[0].setShownPoints(getSelectedPoints(selection[0]));
				pane.getChildren().addAll(selection[0].getShownPoints());
			}
			
			// resize selected shape
			else if (tools.getSelectedToggle() == resize) {
				selection[0].setScaleX(Math.abs(selection[0].getCenterX() - e.getX()) / 50);
				selection[0].setScaleY(Math.abs(selection[0].getCenterY() - e.getY()) / 50);
			}
		});
		
		//add new shape
		newShape.setOnAction(e -> {
			pane.getChildren().removeAll(shapes);
			shapes.add((new Shape(375, 375)));
			pane.getChildren().addAll(shapes);
		});
		
		//change selected shape color
		color.setOnAction(e -> {
			vBox.getChildren().addAll(newColor, entry, change);
			});
		change.setOnAction(e -> {
			String entryColor = entry.getText();
			Color c = Color.web(entryColor);
			selection[0].setFill(c);
			vBox.getChildren().removeAll(newColor, entry, change);
			color.setSelected(false);
			if ( c == Color.BLACK) {
				selection[0].setStroke(Color.RED);
			}
			else {
				selection[0].setStroke(Color.BLACK);
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public void select(Shape newSelection) {
		newSelection.setSelected(true);
		if (newSelection.getFill() == Color.BLACK) {
			newSelection.setStroke(Color.RED);
		}
		else {
			newSelection.setStroke(Color.BLACK);
		}
	}
	public double[] convertPoints(ObservableList<Double> observablePoints) {
		double[] list = new double[observablePoints.size()];
		for (int i = 0; i < observablePoints.size(); i++) {
			list[i] = observablePoints.get(i);
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
}
