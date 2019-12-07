/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.Tabuleiro;

/**
 * FXML Controller class
 *
 * @author patri
 */
public class ConfigGameController implements Initializable {

    @FXML RadioButton radioP;
    @FXML RadioButton radioM;
    @FXML RadioButton radioG;
    @FXML Button btnJogar;
    
    @FXML private void playGame(ActionEvent event) {
        if(radioP.isSelected()){
            Tabuleiro.getTabuleiro().setPequeno(true);
        }else if(radioM.isSelected()){
            Tabuleiro.getTabuleiro().setMedio(true);            
        }else if(radioG.isSelected()){
            Tabuleiro.getTabuleiro().setGrande(true);
        }
        redirecionar();
    }
    private void redirecionar() {
        //fecha a tela atual
        Stage stageClose = (Stage) btnJogar.getScene().getWindow();
        stageClose.close();
        
        //abre a nova tela
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/MesaGame.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Xedrez Leveling");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Um erro inesperado ocorreu, tente novamente");
            alert.setTitle("Erro");
            alert.showAndWait();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }     
}
