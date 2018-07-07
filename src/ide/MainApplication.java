/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Shoka
 */
public class MainApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        MainIDEController root = MainIDEController.getInstance();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("VPL");
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
