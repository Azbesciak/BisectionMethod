package controller;

import algorithm.Result;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController {

	@FXML
	private TextArea iterationsField;
	@FXML
	private TextArea scopeField;
	@FXML
	private TextArea resultField;
	@FXML
	private TextArea reasonField;
	@FXML
	private TextArea polynomialField;

	/**
	 * Prepares data for view filling out text fields.
	 * @param result received result
	 */
	public void setResult(Result result) {
		iterationsField.setText(String.valueOf(result.getIteration()));
		scopeField.setText(result.getScope().getIntervalWithDelta());
		resultField.setText(result.getResult().getIntervalWithDelta());
		reasonField.setText(result.getReason());
		polynomialField.setText(String.valueOf(result.getPolynomial()));
	}
}
