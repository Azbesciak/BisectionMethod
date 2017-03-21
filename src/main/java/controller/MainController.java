package controller;

import algorithm.Constants;
import algorithm.Launcher;
import algorithm.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class MainController implements Initializable {

	Logger logger = Logger.getLogger(this.getClass().getName());
	@FXML
	private Button startButton;
	@FXML
	private TextField polynomialInput;
	@FXML
	private TextField upperBoundInput;
	@FXML
	private TextField lowerBoundInput;
	@FXML
	private TextField scopeEpsilonInput;
	@FXML
	private TextField resultEpsilonInput;
	@FXML
	private TextField iterationsInput;
	@FXML
	private TextField precisionInput;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Arrays.asList( lowerBoundInput, upperBoundInput).forEach(
				textField -> textField.textProperty().addListener(((observable, oldValue, newValue) -> textField
						.setText(newValue.matches(Constants.TOTAL_NUMBER_VARIABLE_REGEX) ? newValue : oldValue))));

		Arrays.asList(iterationsInput, precisionInput).forEach(
				textField -> textField.textProperty().addListener(((observable, oldValue, newValue) -> textField
						.setText(newValue.matches(Constants.INTEGER_REGEX) ? newValue : oldValue))));

		Arrays.asList(scopeEpsilonInput, resultEpsilonInput).forEach(
				textField -> textField.textProperty().addListener(((observable, oldValue, newValue) -> textField
						.setText(newValue.matches(Constants.NUMBER_VARIABLE_REGEX) ? newValue : oldValue))));

		startButton.disableProperty().bind(
				polynomialInput.textProperty().isEmpty()
						.or(upperBoundInput.textProperty().isEmpty())
						.or(lowerBoundInput.textProperty().isEmpty())
						.or(scopeEpsilonInput.textProperty().isEmpty())
						.or(resultEpsilonInput.textProperty().isEmpty())
						.or(iterationsInput.textProperty().isEmpty())
						.or(upperBoundInput.textProperty().lessThan(lowerBoundInput.textProperty()))
						.or(precisionInput.textProperty().isEmpty())
		);
		startButton.setOnAction(event -> {
			final String polynomial = polynomialInput.getText()
					.replace(",", ".").replaceAll("\\s+", "").replace("+"," +").replace("-", " -");
			final String lowerBound = lowerBoundInput.getText().replace(",", ".");
			final String upperBound = upperBoundInput.getText().replace(",", ".");
			final String scopeEpsilon = scopeEpsilonInput.getText().replace(",", ".");
			final String resultEpsilon = resultEpsilonInput.getText().replace(",", ".");
			final String iterations = iterationsInput.getText();
			final String precision = precisionInput.getText();
			try {
				final Result solution = Launcher.launch(polynomial, resultEpsilon, scopeEpsilon, lowerBound,
														upperBound, iterations, precision);
				try {
					showResultCustomWindow(solution, event);
				} catch (IOException e) {
					e.printStackTrace();
					showAlertResultDialog(solution);
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
				showExceptionAlert(e);
			}
		});
	}
	@FXML
	private void showResultCustomWindow(Result result, ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("result-window.fxml"));
		Parent root = fxmlLoader.load();
		final ResultController resultController = fxmlLoader.getController();
		resultController.setResult(result);
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("./stack-overflow.png"));
		stage.setTitle("Result");
		stage.setScene(scene);
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.show();
	}

	private void showAlertResultDialog(Result solution) {
		Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
		resultAlert.setHeaderText("Result");
		Text result = new Text(solution.toString());
		result.setWrappingWidth(500);

		resultAlert.getDialogPane().setContent(result);
		resultAlert.initModality(Modality.NONE);
		resultAlert.show();
	}

	private void showExceptionAlert(Throwable e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Ooops...");
		final String message = StringUtils.isNotBlank(e.getMessage()) ?
				e.getMessage() : Arrays.toString(e.getStackTrace());
		alert.setContentText(message);
		alert.showAndWait();
	}
}
