package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

public class CreateQualitySurveyGUI implements CreatedWindows {
	
	private Stage stage;
	
	public CreateQualitySurveyGUI(JIntIsActor actor) {
		start((ActCoordinator)actor);
	}
	
	private void start(ActCoordinator aActCoordinator) {
		try {
			URL url = this.getClass().getResource("QualitySurveyGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
			
			stage = new Stage();
			stage.setTitle("Survey on the quality of the iCrash syste"); 
			stage.setTitle("Survey on the quality of the iCrash system for " + aActCoordinator.getLogin().value.getValue());
			
			stage.setScene(new Scene(root));
			stage.show();
			
			((QualitySurveyGUIController)loader.getController()).setActor(aActCoordinator);
			
			((QualitySurveyGUIController)loader.getController()).setWindow(stage);
			((QualitySurveyGUIController)loader.getController()).setStage(stage);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					((QualitySurveyGUIController)loader.getController()).closeForm();
					
				}
			});
		} catch (Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);;
		}
	}
	
	@Override
	public void closeWindow() {
		if (stage != null) {
			stage.close();
			stage = null;
		}
		
	}
	
}
