import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		Button newShape = new Button("  New Shape  ");
		Button deleteShape = new Button("Delete Shape");
		RadioButton select = new RadioButton("Select");
		select.setToggleGroup(tools);
		RadioButton move = new RadioButton("Move");
		move.setToggleGroup(tools);
		RadioButton color = new RadioButton("Change Color");
		color.setToggleGroup(tools);
		RadioButton selectPoint = new RadioButton("Select Point");
		selectPoint.setToggleGroup(tools);
		RadioButton movePoint = new RadioButton("Move Point");
		movePoint.setToggleGroup(tools);
		Button deletePoint = new Button(" Delete Point  ");
		RadioButton addPoint = new RadioButton("Add Point");
		addPoint.setToggleGroup(tools);
		vBox.getChildren().addAll(select, move, color, selectPoint, movePoint, addPoint, deletePoint, newShape, deleteShape);
		pane.getChildren().add(vBox);
		
	//tool bar events
		pane.setOnMouseClicked(e -> {
			
		// select shape
			if (tools.getSelectedToggle() == select) {
				try {
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
				catch (Exception ex) {
					message(pane, "add a shape");
				}
			}
			
		// select point
			if (tools.getSelectedToggle() == selectPoint) {
				try {
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
				catch (Exception ex) {
					message(pane, "select a shape");
				}
				
			}
			
		// move point
			if (tools.getSelectedToggle() == movePoint) {
				try {
					pane.getChildren().clear();
					pane.getChildren().add(vBox);
					pointSelection[0].setPointX(e.getX());
					pointSelection[0].setPointY(e.getY());
					shapes.remove(selection[0]);
					double[] doublePoints = convertPoints(selection[0].getPointList());
					Color c = Color.BLACK;
					c = (Color) selection[0].getFill();
					selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), doublePoints);
					selection[0].setFill(c);
					select(selection[0]);
					shapes.add(selection[0]);
					pane.getChildren().addAll(shapes);
					shownPoints.clear();
					for (int i = 0; i < selection[0].getPointList().size(); i++) {
						shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
					}
					pointSelection[0] = selection[0].getPointList().get(selectedPointIndex[0]);
					shownPoints.get(selectedPointIndex[0]).setFill(Color.RED);
					pane.getChildren().addAll(shownPoints);
				}
				catch (Exception ex) {
					message(pane, "select a point");
					pane.getChildren().addAll(shapes);
					pane.getChildren().addAll(shownPoints);
				}
			}
			
		// add point
			if (tools.getSelectedToggle() == addPoint) {
				try {
					Color c = Color.BLACK;
					c = (Color) selection[0].getFill();
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
					if (closestIndex == 0) {
						Line line1 = new Line(clickX, clickY, selection[0].getPointList().get(selection[0].getPointList().size() - 1).getPointX(), selection[0].getPointList().get(selection[0].getPointList().size() - 1).getPointY());
						Line line2 = new Line(selection[0].getPointList().get(closestIndex).getPointX(), selection[0].getPointList().get(closestIndex).getPointY(), 
								selection[0].getPointList().get(closestIndex + 1).getPointX(), selection[0].getPointList().get(closestIndex + 1).getPointY());
						Line line3 = new Line(clickX, clickY, selection[0].getPointList().get(selection[0].getPointList().size() - 2).getPointX(), selection[0].getPointList().get(selection[0].getPointList().size() - 2).getPointY());
						if (line1.getBoundsInParent().intersects(line2.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex + 1, new Point(clickX, clickY));
						}
						else if (line1.getBoundsInParent().intersects(line3.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
						else {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
					}
					else if (closestIndex == selection[0].getPointList().size() - 1) {
						Line line1 = new Line(clickX, clickY, selection[0].getPointList().get(closestIndex - 1).getPointX(), selection[0].getPointList().get(closestIndex - 1).getPointY());
						Line line2 = new Line(selection[0].getPointList().get(closestIndex).getPointX(), selection[0].getPointList().get(closestIndex).getPointY(), 
								selection[0].getPointList().get(0).getPointX(), selection[0].getPointList().get(0).getPointY());
						Line line3 = new Line(clickX, clickY, selection[0].getPointList().get(closestIndex - 2).getPointX(), selection[0].getPointList().get(closestIndex - 2).getPointY());
						if (line1.getBoundsInParent().intersects(line2.getBoundsInParent())) {
							selection[0].getPointList().add(0, new Point(clickX, clickY));
						}
						else if (line1.getBoundsInParent().intersects(line3.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
						else {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
					}
					else if (closestIndex == 1) {
						Line line1 = new Line(clickX, clickY, selection[0].getPointList().get(closestIndex - 1).getPointX(), selection[0].getPointList().get(closestIndex - 1).getPointY());
						Line line2 = new Line(selection[0].getPointList().get(closestIndex).getPointX(), selection[0].getPointList().get(closestIndex).getPointY(), 
								selection[0].getPointList().get(closestIndex + 1).getPointX(), selection[0].getPointList().get(closestIndex + 1).getPointY());
						Line line3 = new Line(clickX, clickY, selection[0].getPointList().get(selection[0].getPointList().size() - 1).getPointX(), selection[0].getPointList().get(selection[0].getPointList().size() - 1).getPointY());
						if (line1.getBoundsInParent().intersects(line2.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex + 1, new Point(clickX, clickY));
						}
						else if (line1.getBoundsInParent().intersects(line3.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
						else {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
					}
					else {
						Line line1 = new Line(clickX, clickY, selection[0].getPointList().get(closestIndex - 1).getPointX(), selection[0].getPointList().get(closestIndex - 1).getPointY());
						Line line2 = new Line(selection[0].getPointList().get(closestIndex).getPointX(), selection[0].getPointList().get(closestIndex).getPointY(), 
								selection[0].getPointList().get(closestIndex + 1).getPointX(), selection[0].getPointList().get(closestIndex + 1).getPointY());
						Line line3 = new Line(clickX, clickY, selection[0].getPointList().get(closestIndex - 2).getPointX(), selection[0].getPointList().get(closestIndex - 2).getPointY());
						if (line1.getBoundsInParent().intersects(line2.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex + 1, new Point(clickX, clickY));
						}
						else if (line1.getBoundsInParent().intersects(line3.getBoundsInParent())) {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
						else {
							selection[0].getPointList().add(closestIndex, new Point(clickX, clickY));
						}
					}
					selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), convertPoints(selection[0].getPointList()));
					shapes.add(selection[0]);
					for (int i = 0; i < selection[0].getPointList().size(); i++) {
						shownPoints.add(new Circle(selection[0].getPointList().get(i).getPointX(), selection[0].getPointList().get(i).getPointY(), 4));
					}
					selection[0].setFill(c);
					select(selection[0]);
					double x = 0;
					double y = 0;
					for (int i = 0; i < selection[0].getPointList().size(); i++) {
						x += selection[0].getPointList().get(i).getPointX();
						y += selection[0].getPointList().get(i).getPointY();
					}
					x = x /selection[0].getPointList().size();
					y = y /selection[0].getPointList().size();
					selection[0].setCenterX(x);
					selection[0].setCenterY(y);
					pane.getChildren().addAll(shapes);
					pane.getChildren().addAll(shownPoints);
				}
				catch (Exception ex) {
					pane.getChildren().addAll(shapes);
					message(pane, "select a shape");
				}
			}
			
		//move selected shape
			else if (tools.getSelectedToggle() == move) {
				try {
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
					Color c = Color.BLACK;
					c = (Color) selection[0].getFill();
					selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), convertPoints(selection[0].getPointList()));
					selection[0].setFill(c);
					select(selection[0]);
					shapes.add(selection[0]);
					pane.getChildren().addAll(shapes);
					pane.getChildren().addAll(shownPoints);
				}
				catch (Exception ex) {
					message(pane, "select a shape");
					pane.getChildren().addAll(shapes);
				}
			}
		});
		
	//add new shape
		newShape.setOnAction(e -> {
			pane.getChildren().clear();
			shapes.add((new Shape(375, 375)));
			pane.getChildren().add(vBox);
			pane.getChildren().addAll(shapes);
			pane.getChildren().addAll(shownPoints);
		});
		
	//delete selected shape
		deleteShape.setOnAction(e -> {
			pane.getChildren().removeAll(shapes);
			shapes.remove(selection[0]);
			pane.getChildren().addAll(shapes);
			pane.getChildren().removeAll(shownPoints);
			shownPoints.clear();
			selection[0] = null;
		});
		
	//delete selected point
		deletePoint.setOnAction(e -> {
			try {
				pane.getChildren().clear();
				pane.getChildren().add(vBox);
				shownPoints.remove(selectedPointIndex[0]);
				shapes.remove(selection[0]);
				selection[0].getPointList().remove(pointSelection[0]);
				Color c = Color.BLACK;
				c = (Color) selection[0].getFill();
				selection[0] = new Shape(selection[0].getCenterX(), selection[0].getCenterY(), convertPoints(selection[0].getPointList()));
				selection[0].setFill(c);
				shapes.add(selection[0]);
				pane.getChildren().addAll(shapes);
				pane.getChildren().addAll(shownPoints);
				pointSelection[0] = null;
				select(selection[0]);
			}
			catch (Exception ex) {
				message(pane, "select a point");
				pane.getChildren().addAll(shapes);
			}
		});
		
	//change selected shape color
		color.setOnAction(e -> {
			if (!vBox.getChildren().contains(newColor)) {
				vBox.getChildren().addAll(newColor, entry, change);
			}
			});
		change.setOnAction(e -> {
			try {
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
			}
			catch (Exception ex) {
				message(pane, "select a shape");
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
	public void message(Pane pane, String message) {
		Label label = new Label();
		Pane labelPane = new Pane(label);
		label.setTextFill(Color.BLUE);
		pane.getChildren().add(labelPane);
		labelPane.setLayoutY(720);
		labelPane.setLayoutX(10);
		label.setText(message);
		FadeTransition ft = new FadeTransition(Duration.seconds(1.5), label);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pane.getChildren().remove(labelPane);
			}
		});
	}
}
