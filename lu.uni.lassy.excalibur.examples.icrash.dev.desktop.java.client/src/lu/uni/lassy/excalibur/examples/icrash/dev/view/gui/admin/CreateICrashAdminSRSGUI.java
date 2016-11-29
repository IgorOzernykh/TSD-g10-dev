package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

public class CreateICrashAdminSRSGUI implements CreatedWindows {
	
	private Stage stage;
	
	public CreateICrashAdminSRSGUI(JIntIsActor actor) {
		start((ActAdministrator)actor);
	}
	
	private void start(ActAdministrator aActAdministrator) {
		try {
			URL url = this.getClass().getResource("ICrashAdminSRSGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
			
			stage = new Stage();
			stage.setTitle("Surveys results screen");
			stage.setScene(new Scene(root));
			stage.show();
			
			
			((ICrashSRSGUIController)loader.getController()).setActor(aActAdministrator);
			((ICrashSRSGUIController)loader.getController()).setWindow(stage);
			((ICrashSRSGUIController)loader.getController()).setStage(stage);
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					((ICrashSRSGUIController)loader.getController()).closeForm();
				}
			});
			
		} catch (Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
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
