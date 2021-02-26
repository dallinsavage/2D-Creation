import java.util.ArrayList;
import javafx.animation.PathTransition;
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
				}
				selection[0] = closest;
				select(closest);
			}
			
			//move selected shape
			else if (tools.getSelectedToggle() == move) {
				Line line = new Line(selection[0].getCenterX(), selection[0].getCenterY(), e.getX(), e.getY());
				line.setOpacity(0);
				pane.getChildren().add(line);
				PathTransition pt = new PathTransition(Duration.millis(1), line, selection[0]);
				pt.play();
				selection[0].setCenterX(e.getX());
				selection[0].setCenterY(e.getY());
				pane.getChildren().remove(line);
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
}
