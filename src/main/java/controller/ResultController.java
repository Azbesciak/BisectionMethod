package controller;

import algorithm.Result;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

	@FXML
	private TextArea iterationsField;
	@FXML
	private TextArea scopeField;
	@FXML
	private TextArea resultField;
	@FXML
	private TextArea reasonField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setResult(Result result) {
		iterationsField.setText(String.valueOf(result.getIteration()));
		scopeField.setText(String.valueOf(result.getScope()));
		resultField.setText(String.valueOf(result.getResult()));
		reasonField.setText(result.getReason());
	}
}
