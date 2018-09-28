package give.util;

import javafx.beans.value.ChangeListener;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BoundUtils {

    // reference: https://stackoverflow.com/a/26204372/5137352
    public static void forceStageWithinBounds(Stage stage) {

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Screen screen : Screen.getScreens()) {
            Rectangle2D screenBounds = screen.getBounds();
            if (screenBounds.getMinX() < minX) {
                minX = screenBounds.getMinX();
            }
            if (screenBounds.getMinY() < minY) {
                minY = screenBounds.getMinY();
            }
            if (screenBounds.getMaxX() > maxX) {
                maxX = screenBounds.getMaxX();
            }
            if (screenBounds.getMaxY() > maxY) {
                maxY = screenBounds.getMaxY();
            }
        }

        Bounds allScreenBounds = new BoundingBox(minX, minY, maxX - minX, maxY - minY);
        ChangeListener<Number> boundsListener = (obs, oldValue, newValue) -> {
            try {
                double x = stage.getX();
                double y = stage.getY();
                double w = stage.getWidth();
                double h = stage.getHeight();
                if (x < allScreenBounds.getMinX()) {
                    stage.setX(allScreenBounds.getMinX());
                }
                if (x + w > allScreenBounds.getMaxX()) {
                    stage.setX(allScreenBounds.getMaxX() - w);
                }
                if (y < allScreenBounds.getMinY()) {
                    stage.setY(allScreenBounds.getMinY());
                }
                if (y + h > allScreenBounds.getMaxY()) {
                    stage.setY(allScreenBounds.getMaxY() - h);
                }
            } catch (StackOverflowError ignored) {
            }
        };

        stage.xProperty().addListener(boundsListener);
        stage.yProperty().addListener(boundsListener);
        stage.widthProperty().addListener(boundsListener);
        stage.heightProperty().addListener(boundsListener);
    }

}
