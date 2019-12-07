package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author patri
 */
public class ApresentationController implements Initializable {
    @FXML Button btnStart; 
    
    @FXML private void startGame(ActionEvent event) {
        //fecha a tela atual
        Stage stageClose = (Stage) btnStart.getScene().getWindow();
        stageClose.close();
        
        //abre a nova tela
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/MenuGame.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Xedrez Leveling");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERRO");
            alert.setTitle("Um erro inesperado ocorreu, tente novamente");
            alert.showAndWait();
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //emitir som
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\patri\\Documents\\NetBeansProjects\\xlProject\\src\\sound\\notification2.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            //clip.loop(Clip.LOOP_CONTINUOUSLY); //Para repetir o som.
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Erro ao executar SOM!");
        }
    }    
    
}
