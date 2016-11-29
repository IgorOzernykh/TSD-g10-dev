package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.AdminController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.CoordinatorController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController;

public class ICrashSRSGUIController extends AbstractGUIController {
	
	private ActAdministrator aActAdministrator;
	private AdminController userController;
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Root pane
	 */
	@FXML
	Pane pnRoot;
	
	/**
	 * DatePicker for viewing survey result at desired date
	 */
	@FXML
	DatePicker dpCoords;
	
	/**
	 * ListView to select coordinator to show their results
	 */
	@FXML
	ListView<String> lvCoords;
	
	/**
	 * Button for visualizing survey results
	 */
	@FXML
	Button bttnVisualize;
	
	/**
	 * Visualizes survey results
	 * @param event The event type fired, we do not need it's details
	 */
	@FXML
	void bttnVisualize_OnClick(ActionEvent event) {
		visualize();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resourceBundle) {
		lvCoords.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// TODO: fill the coords list with coordinators 
		
	}
	
	private void visualize() {
//		TODO: visualize
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try {
			if (!(actor instanceof ActAdministrator)) {
				throw new IncorrectActorException(actor, ActAdministrator.class);
			}
			aActAdministrator = (ActAdministrator)actor;
			try {
				userController = new AdminController(aActAdministrator);
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
