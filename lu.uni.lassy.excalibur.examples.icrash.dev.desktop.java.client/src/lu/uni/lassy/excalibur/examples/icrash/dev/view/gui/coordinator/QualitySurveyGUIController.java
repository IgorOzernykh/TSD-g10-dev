package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
	
	@FXML
	void bttnQSSubmit_OnClick(ActionEvent event) {
		// TODO: store results
	}
	
	
	
	@FXML
	void bttnQSCancel_OnClick(ActionEvent event) {
		closeForm();
	}
	
	private void setupQuestionList() { // temporal
		for (int i = 0; i < 25; i++) {
			Text question = new Text("Q" + i + ":");
			question.setLayoutX(question.getLayoutX() + 5);
			HBox answers = new HBox(10);
			answers.setPadding(new Insets(2, 0, 10, 5));
			ToggleGroup toggleGroup = new ToggleGroup();
			RadioButton r11 = new RadioButton("No delay");
			r11.setSelected(true);
			r11.setToggleGroup(toggleGroup);
			RadioButton r12 = new RadioButton("Small delay");
			r12.setToggleGroup(toggleGroup);
			RadioButton r13 = new RadioButton("Huge delay");
			r13.setToggleGroup(toggleGroup);
			answers.getChildren().add(r11);
			answers.getChildren().add(r12);
			answers.getChildren().add(r13);
			vbQuestionList.getChildren().add(question);
			vbQuestionList.getChildren().add(answers);
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
