import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new BigFloat("1", BinaryMathContext.BINARY64);
		Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
		primaryStage.getIcons().add(new Image("./stack-overflow.png"));
		primaryStage.setTitle("Bisection Method");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
