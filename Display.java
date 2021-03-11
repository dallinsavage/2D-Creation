import java.util.ArrayList;
import javafx.application.Application;
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
import javafx.stage.Stage;

public class Display extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Shape[] selection = new Shape[1];
		Point[] pointSelection = new Point[1];
		int[] selectedPointIndex = new int[1];
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		ArrayList<Circle> shownPoints = new ArrayList<Circle>();
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
		Button deleteShape = new Button("Delete Shape");
		RadioButton select = new RadioButton("Select");
		select.setToggleGroup(tools);
		RadioButton move = new RadioButton("Move");
		move.setToggleGroup(tools);
		RadioButton color = new RadioButton("Change Color");
		color.setToggleGroup(tools);
		RadioButton resize = new RadioButton("Resize");
		resize.setToggleGroup(tools);
		RadioButton selectPoint = new RadioButton("Select Point");
		selectPoint.setToggleGroup(tools);
		RadioButton movePoint = new RadioButton("Move Point");
		movePoint.setToggleGroup(tools);
		Button deletePoint = new Button("Delete Point");
		RadioButton addPoint = new RadioButton("Add Point");
		addPoint.setToggleGroup(tools);
		vBox.getChildren().addAll(newShape, deleteShape, select, move, color, resize, selectPoint, movePoint, deletePoint, addPoint);
		pane.getChildren().add(vBox);
		
	//tool bar events
		pane.setOnMouseClicked(e -> {
			
		// select shape
			if (tools.getSelectedToggle() == select) {
				if (pointSelection[0] != null) {
					shownPoints.get(selectedPointIndex[0]).setFill(Color.BLACK);
					pointSelection[0] = null;
				}
				Shape closest = shapes.get(0);
				double clickX = e.getX();
				double clickY = e.getY();
				for (int i = 0; i < shapes.size(); i++) {
					if (Math.abs(shapes.get(i).getCenterX() - clickX) <= Math.abs(closest.getCenterX() - clickX) &&
							Math.abs(shapes.get(i).getCenterY() - clickY) <= Math.abs(closest.getCenterY() - clickY)) {
						closest = shapes.get(i);
					}
				}
				if (selection[0] == null) {
				}
				else {
					selection[0].deselect();
					pane.getChildren().removeAll(shownPoints);
					shownPoints.clear();
					for (int i = 0; i < selection[0].getPointList().size(); i++) {
						shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
					}
					pane.getChildren().removeAll(shownPoints);
				}
				selection[0] = closest;
				select(closest);
				shownPoints.clear();
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
				}
				pane.getChildren().addAll(shownPoints);
			}
			
		// select point
			if (tools.getSelectedToggle() == selectPoint) {
				if (pointSelection[0] != null) {
					shownPoints.get(selectedPointIndex[0]).setFill(Color.BLACK);;
				}
				Point closest = selection[0].getPointList().get(0);
				double clickX = e.getX();
				double clickY = e.getY();
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					if (Math.abs(selection[0].getPointList().get(i).getPointX() - clickX) <= Math.abs(closest.getPointX() - clickX) && 
							Math.abs(selection[0].getPointList().get(i).getPointY() - clickY) <= Math.abs(closest.getPointY() - clickY)) {
						closest = selection[0].getPointList().get(i);
					}
				}
				selectedPointIndex[0] = selection[0].getPointList().indexOf(closest);
				pointSelection[0] = closest;
				shownPoints.get(selectedPointIndex[0]).setFill(Color.RED);
			}
			
		// move point
			if (tools.getSelectedToggle() == movePoint) {
				pane.getChildren().clear();
				pane.getChildren().add(vBox);
				shownPoints.clear();
				pointSelection[0].setPointX(e.getX());
				pointSelection[0].setPointY(e.getY());
				shapes.remove(selection[0]);
				double[] doublePoints = convertPoints(selection[0].getPointList());
				selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), doublePoints);
				select(selection[0]);
				shapes.add(selection[0]);
				pane.getChildren().addAll(shapes);
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
				}
				pane.getChildren().addAll(shownPoints);
			}
			
		// add point
			if (tools.getSelectedToggle() == addPoint) {
				pane.getChildren().clear();
				shownPoints.clear();
				pane.getChildren().add(vBox);
				shapes.remove(selection[0]);
				Point closest = selection[0].getPointList().get(0);
				double clickX = e.getX();
				double clickY = e.getY();
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					if (Math.abs(selection[0].getPointList().get(i).getPointX() - clickX) <= Math.abs(closest.getPointX() - clickX) && 
							Math.abs(selection[0].getPointList().get(i).getPointY() - clickY) <= Math.abs(closest.getPointY() - clickY)) {
						closest = selection[0].getPointList().get(i);
					}
				}
				int closestIndex = selection[0].getPointList().indexOf(closest);
				selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
				selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), convertPoints(selection[0].getPointList()));
				shapes.add(selection[0]);
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
				}
				select(selection[0]);
				pane.getChildren().addAll(shapes);
				pane.getChildren().addAll(shownPoints);
			}
			
		//move selected shape
			else if (tools.getSelectedToggle() == move) {
				pane.getChildren().clear();
				pane.getChildren().addAll(vBox);
				shapes.remove(selection[0]);
				double xChange = selection[0].getCenterX() - e.getX();
				double yChange = selection[0].getCenterY() - e.getY();
				selection[0].setCenterX(e.getX());
				selection[0].setCenterY(e.getY());
				shownPoints.clear();
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					selection[0].getPointList().get(i).setPointX(selection[0].getPointList().get(i).getPointX() - xChange);
					selection[0].getPointList().get(i).setPointY(selection[0].getPointList().get(i).getPointY() - yChange);
					shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
				}
				selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), convertPoints(selection[0].getPointList()));
				select(selection[0]);
				shapes.add(selection[0]);
				pane.getChildren().addAll(shapes);
				pane.getChildren().addAll(shownPoints);
			}
			
			// resize selected shape
		/*	else if (tools.getSelectedToggle() == resize) {
				pane.getChildren().removeAll(shownPoints);
				shownPoints.clear();
				for (int i = 0; i < selection[0].getPointList().size(); i++) {
					if (selection[0].getPointList().get(i).getPointX() < selection[0].getCenterX()) {
						selection[0].getPointList().get(i).setPointX(selection[0].getPointList().get(i).getPointX() * (Math.abs(selection[0].getCenterX() - e.getX()) / 50));
					}
					else {
						selection[0].getPointList().get(i).setPointX(selection[0].getPointList().get(i).getPointX() * (Math.abs(selection[0].getCenterX() - e.getX()) / 50));
					}
					if (selection[0].getPointList().get(i).getPointY() < selection[0].getCenterY()) {
						selection[0].getPointList().get(i).setPointY(selection[0].getPointList().get(i).getPointY() * (Math.abs(selection[0].getCenterX() - e.getX()) / 50));
					}
					else {
						selection[0].getPointList().get(i).setPointY(selection[0].getPointList().get(i).getPointY() * (Math.abs(selection[0].getCenterX() - e.getX()) / 50));
					}
					shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
				}
				pane.getChildren().removeAll(shapes);
				double[] doublePoints = convertPoints(selection[0].getPointList());
				selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), doublePoints);
				select(selection[0]);
				shapes.add(selection[0]);
				pane.getChildren().addAll(shapes);
				pane.getChildren().addAll(shownPoints);
			} */
		});
		
	//add new shape
		newShape.setOnAction(e -> {
			pane.getChildren().removeAll(shapes);
			shapes.add((new Shape(375, 375)));
			pane.getChildren().addAll(shapes);
		});
		
	//delete selected shape
		deleteShape.setOnAction(e -> {
			pane.getChildren().removeAll(shapes);
			shapes.remove(selection[0]);
			pane.getChildren().addAll(shapes);
			pane.getChildren().removeAll(shownPoints);
		});
		
	//delete selected point
		deletePoint.setOnAction(e -> {
			pane.getChildren().clear();
			pane.getChildren().add(vBox);
			shownPoints.remove(selectedPointIndex[0]);
			shapes.remove(selection[0]);
			selection[0].getPointList().remove(pointSelection[0]);
			selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), convertPoints(selection[0].getPointList()));
			shapes.add(selection[0]);
			pane.getChildren().addAll(shapes);
			pane.getChildren().addAll(shownPoints);
			pointSelection[0] = null;
			select(selection[0]);
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
		if (newSelection.getFill() == Color.BLACK) {
			newSelection.setStroke(Color.RED);
		}
		else {
			newSelection.setStroke(Color.BLACK);
		}
	}
	public double[] convertPoints(ArrayList<Point> pointList) {
		double[] list = new double[pointList.size() * 2];
		for (int i = 0; i < pointList.size() * 2; i++) {
			list[i] = pointList.get(i / 2).getPointX();
			list[i + 1] = pointList.get(i / 2).getPointY();
			i++;
		}
		return list;
	}
}
