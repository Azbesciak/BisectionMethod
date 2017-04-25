package controller;

import algorithm.*;
import algorithm.utils.Arithmetic;
import algorithm.utils.NumberType;
import algorithm.utils.Constants;
import algorithm.utils.Params;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static algorithm.utils.Constants.POLYNOMIAL_TEST_REGEX;


public class MainController implements Initializable {
	@FXML
	public Pane precisionPane;
	@FXML
	public Pane arithmeticPane;
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
	@FXML
	private ChoiceBox<NumberType> typeChoice;
	@FXML
	private ChoiceBox<Arithmetic> arithmeticChoice;

	private BooleanBinding matchesRegex(final TextField field, final Pattern pattern) {

		return Bindings.createBooleanBinding(
				() -> pattern.matcher(field.getText()).matches(), field.textProperty()
		);
	}

	/**
	 * Prepares main view, makes regex for inputs, maintains launch button disability and on click action.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Pattern polynomialPattern = Pattern.compile(POLYNOMIAL_TEST_REGEX);
		Arrays.asList(lowerBoundInput, upperBoundInput).forEach(
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
						.or(matchesRegex(polynomialInput, polynomialPattern).not())
						.or(upperBoundInput.textProperty().isEmpty())
						.or(lowerBoundInput.textProperty().isEmpty())
						.or(scopeEpsilonInput.textProperty().isEmpty())
						.or(resultEpsilonInput.textProperty().isEmpty())
						.or(iterationsInput.textProperty().isEmpty())
						.or(upperBoundInput.textProperty().lessThan(lowerBoundInput.textProperty()))
						.or(precisionInput.textProperty().isEmpty())
		);

		typeChoice.setValue(NumberType.FLOATING);
		arithmeticChoice.setValue(Arithmetic.INTERVAL);
		arithmeticPane.visibleProperty().bind(Bindings.createBooleanBinding(
				() -> NumberType.FLOATING.equals(typeChoice.getValue()), typeChoice.valueProperty())
		);

		precisionPane.visibleProperty().bind(Bindings.createBooleanBinding(
		        () -> NumberType.ARBITRARY.equals(typeChoice.getValue()), typeChoice.valueProperty())
        );


		startButton.setOnAction(event -> {
			Params params = Params.parse(this);
			try {
				final Result solution = Launcher.launch(params);
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



	/**
	 * Shows modal window containing result of algorithm
	 *
	 * @param result info which contains result scope, the narrowest interval of arguments, where root can be found,
	 *               and the reason of algorithm's computations end.
	 * @param event  Event needed to initialize window - gets rootStage
	 * @throws IOException if result-window.fxml contains errors or wasn't found in resources/controller folder
	 */
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
		stage.initOwner(((Node) event.getSource()).getScene().getWindow());
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * shows standard javaFX alert if custom will fail
	 *
	 * @param solution result of computations
	 */
	private void showAlertResultDialog(Result solution) {
		Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
		resultAlert.setHeaderText("Result");
		Text result = new Text(solution.toString());
		result.setWrappingWidth(500);

		resultAlert.getDialogPane().setContent(result);
		resultAlert.initModality(Modality.NONE);
		resultAlert.show();
	}

	/**
	 * Informs about exceptions in algorithm
	 *
	 * @param e An exception that occurred
	 */
	private void showExceptionAlert(Throwable e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Ooops...");
		final String message = StringUtils.isNotBlank(e.getMessage()) ?
				e.getMessage() : Arrays.toString(e.getStackTrace());
//		alert.setContentText(message);
		Text result = new Text(message);
		result.setWrappingWidth(500);
		alert.getDialogPane().setContent(result);
		alert.initModality(Modality.NONE);
		alert.showAndWait();
	}

	public Button getStartButton() {
		return startButton;
	}

	public TextField getPolynomialInput() {
		return polynomialInput;
	}

	public TextField getUpperBoundInput() {
		return upperBoundInput;
	}

	public TextField getLowerBoundInput() {
		return lowerBoundInput;
	}

	public TextField getScopeEpsilonInput() {
		return scopeEpsilonInput;
	}

	public TextField getResultEpsilonInput() {
		return resultEpsilonInput;
	}

	public TextField getIterationsInput() {
		return iterationsInput;
	}

	public TextField getPrecisionInput() {
		return precisionInput;
	}

	public ChoiceBox<NumberType> getTypeChoice() {
		return typeChoice;
	}

	public ChoiceBox<Arithmetic> getArithmeticChoice() {
		return arithmeticChoice;
	}
}
