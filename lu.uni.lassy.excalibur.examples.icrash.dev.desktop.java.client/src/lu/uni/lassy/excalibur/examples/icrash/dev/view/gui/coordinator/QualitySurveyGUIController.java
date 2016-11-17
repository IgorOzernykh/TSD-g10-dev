package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.CoordinatorController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController;

public class QualitySurveyGUIController extends AbstractGUIController {
	
	private ActCoordinator aActCoordinator;
	private CoordinatorController userController;
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML 
	private Pane pnRoot;
	
	@FXML
	private Button bttnQSSubmit;
	
	@FXML
	private Button bttnQSCancel;
	
	@FXML
	private Pane pnQuestionList;
	
	@FXML
	private VBox vbQuestionList;
	
	private HBox[] answerList;
	
	/**
     * Button event that submits the survey.
     *
     * @param event The event type fired, we do not need it's details
     */
	@FXML
	void bttnQSSubmit_OnClick(ActionEvent event) {
		submitQualitySurvey();
		closeForm();
	}
	
	/**
	 * Interprets the results of the quality survey and send an oeSubmitQualitySurvey to the system.
	 */
	private void submitQualitySurvey() {
		StringBuilder sb = new StringBuilder();
		for (HBox hBox : answerList) {
			int index = 0;
			for (Node node : hBox.getChildren()) {
				if (!(node instanceof RadioButton)) continue;
				if (!((RadioButton)node).isSelected()) {
					index++;
				} else {
					sb.append("" + (index + 1) + "/" + hBox.getChildren().size() + " ");
					continue;
				}
			}
		}
		try {
			if (!userController.submitQualitySurvey("1", sb.toString()).getValue()) { //TODO ID!
				showWarningMessage("Unable to submit the survey", "Unable to submit the survey, please try again");
			}
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showServerOffLineMessage(e);
		} catch (IncorrectFormatException e) {
			showWarningIncorrectInformationEntered(e);
		}
	}
	
	
	@FXML
	void bttnQSCancel_OnClick(ActionEvent event) {
		closeForm();
	}
	
	private void setupQuestionList() { // temporal
		String[] questions = {
				"1. Is it take a lot of time for system to validate or invalidate an alert?",
				"2. Were there any situations when the system was inaccessible?",
				"3. Was there a situation when you were unable to react quickly because of system lags?",
				"4. Were there any situations when many messages were received simultaneously after long delay?",
				"5. Were there any situations when you were unable to submit a report on crisis handling?",
				"6. Were there any situations when you were unable to reopen closed alert?",
				"7. Were there any situations when you were unable to contact to the users of icrash?",
				"8. Were there any situations when you were unable to contact to the police, ambulance etc?",
				"9.  Were there any false reports on crises from the system?",
				"10. Were there any messages that have been showed incorrectly?"};
		
		String[][] answers = {
				{"No, it's immediately", "Sometimes", "Often"}, 
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"},
				{"No", "Once", "Several Times"}};
		final int n = questions.length;
		answerList = new HBox[n];
		for (int i = 0; i < answerList.length; i++) {
			Text question = new Text(questions[i]);
			question.setLayoutX(question.getLayoutX() + 5);
			answerList[i] = new HBox(10);
			answerList[i].setPadding(new Insets(2, 0, 10, 5));
			ToggleGroup tg = new ToggleGroup();
			for (int j = 0; j < answers[i].length; j++) {
				RadioButton rb = new RadioButton(answers[i][j]);
				if (j == 0) rb.setSelected(true);
				rb.setToggleGroup(tg);
				answerList[i].getChildren().add(rb);
			}
			vbQuestionList.getChildren().add(question);
			vbQuestionList.getChildren().add(answerList[i]);
			vbQuestionList.getChildren().add(new Separator());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		setupQuestionList();
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try {
			if (!(actor instanceof ActCoordinator)) {
				throw new IncorrectActorException(actor, ActCoordinator.class);
			}
			aActCoordinator = (ActCoordinator)actor;
			try {
				userController = new CoordinatorController(aActCoordinator);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		} catch (IncorrectActorException | ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
			return new PtBoolean(false);
		}
		return new PtBoolean(true);
	}

	@Override
	public void closeForm() {
		if (stage != null) {
			stage.close();
			stage = null;
		}
		
	}
	
}
