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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Player;
import static model.XlProject.players;

public class MenuGameController implements Initializable {
    @FXML TextField txtNome;
    @FXML TextField txtNick;
    @FXML Circle readyS1;
    @FXML Circle readyS2;
    @FXML Circle readyNot1;
    @FXML Circle readyNot2;
    @FXML Button btnCadastrar;
    @FXML ImageView imgReadyP1;
    @FXML ImageView imgReadyP2;
    @FXML Label labelNick;
    @FXML Label labelNome;
    
    @FXML private void cadastrarDadosJogador (ActionEvent event) {
        String msgErro = "";
        
        //redirecionar
        if(readyS1.isVisible() && readyS2.isVisible()){
            //fecha a tela atual
            Stage stageClose = (Stage) btnCadastrar.getScene().getWindow();
            stageClose.close();

            //abre a nova tela
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/view/ConfigGame.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Xedrez Leveling");
                stage.show();
                
            }catch(IOException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERRO");
                alert.setTitle("Um erro inesperado ocorreu, tente novamente");
                alert.showAndWait();
            }
            return;
        //Tratamento de erro
        }else if(txtNome.getText().trim().length() <= 0 || txtNick.getText().trim().length() <= 0){
            msgErro = "Preencha todos os campos para continuar";
        }
        
        if(msgErro.length() > 0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Mensagem");
            alert.setTitle("Algo deu errado");
            alert.setContentText(msgErro);
            alert.showAndWait();
        }else{
            //criação de player
            Player player = new Player();
            player.setNome(txtNome.getText());
            player.setNick(txtNick.getText());
            players.add(player);
            
            //verificação de player pronto
            if(!readyS1.isVisible()){
                readyS1.setVisible(true);
                imgReadyP1.setVisible(true);
            }else if(!readyS2.isVisible()){
                readyS2.setVisible(true);
                txtNome.setDisable(true);
                txtNick.setDisable(true);
                txtNome.setVisible(false);
                txtNick.setVisible(false);
                labelNick.setVisible(false);
                labelNome.setVisible(false);
                imgReadyP2.setVisible(true);
                btnCadastrar.setText("Continuar");
            }
            //limpar campos
            txtNome.clear();
            txtNick.clear();  
        }
    }
    
    @FXML private void verifyNome(KeyEvent evt) {
        String m  = txtNome.getText();
        if(!Character.isAlphabetic(evt.getCharacter().charAt(0)) && !Character.isWhitespace(evt.getCharacter().charAt(0))) evt.consume();      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }        
}
