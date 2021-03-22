# 2D-Creation
2D Creation was designed to allow users to create shapes and edit them. There are many ways to edit you shapes such as adding more points to a shape, moving existing points, changing the color of your shape and moving the position of the shape.
## <h2> Motivation
What motivated me to make 2D Creation was that I wanted to make somthing that wouldn't be limited by my own imagination. I wanted to create a tool for people to use to create whatever they can imagine.
## <h2> Program being run
![chess pieces](https://github.com/dallinsavage/2D-Creation/blob/main/Screen%20Shot%202021-03-22%20at%203.49.37%20PM.png)
## <h2> Code example
  ```
  if (tools.getSelectedToggle() == selectPoint) {
		try {
			if (pointSelection[0] != null) {
				shownPoints.get(selectedPointIndex[0]).setFill(Color.BLACK);;
			}
			Point closest = selection[0].getPointList().get(0);
			double clickX = e.getX();
			double clickY = e.getY();
			for (int i = 0; i < selection[0].getPointList().size(); i++) {
				if (Math.abs(selection[0].getPointList().get(i).getPointX() - clickX) + Math.abs(selection[0].getPointList().get(i).getPointY() - clickY) <
						Math.abs(closest.getPointX() - clickX) + Math.abs(closest.getPointY() - clickY)) {
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
  ```
