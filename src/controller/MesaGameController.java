package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Assasino;
import model.GameMecanic;
import model.Guerreiro;
import model.Habilidade;
import model.Healer;
import model.Tabuleiro;
import model.Tank;
import static model.XlProject.players;

public class MesaGameController implements Initializable {
    @FXML private Label pNome;
    @FXML private Label qtdMana;
    @FXML private Label qtdLife;
    @FXML private Label txtHabilitDescricao;
    @FXML private Label txtHabilitCustoMana;
    @FXML private Label txtHabilitDano;
    @FXML private Label turnoNotificador;
    @FXML private ImageView icLife;
    @FXML private ImageView icMana;
    @FXML private ImageView icHabilit;
    @FXML private ProgressBar pLife;
    @FXML private ProgressBar pMana;
    @FXML private Button btnUsar;
    @FXML private Button passarTurno;
    @FXML private Rectangle displayHabilit;
    @FXML private GridPane gpTabuleiro;
    @FXML private ComboBox cbHabilidade;
   
    private GameMecanic gm;
    private boolean habilidadeFuncional = false;
    String habilidadeSelecionada;
    
    public void initializePecasMatriz(int tamMatriz){
        //clonar array de personagens para poder remover
        if(tamMatriz == 8){
            int qtdAssasino = 0;
            int qtdTank = 4;
            int qtdGuerreiro = 8;
            int qtdHealer = 14;
            boolean auxReset = true;
            
            for(int l = 0; l < tamMatriz; l++){
                for(int c = 0; c < tamMatriz; c++){
                    
                    //Player 1
                    if(l == 0){
                        //Assasino
                        if(c == 0 || c == 3 || c == 4 || c == 7){
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        //Healer
                        else if(c == 1 || c == 6){
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdHealer);
                            qtdHealer++;                            
                        }
                        //tank
                        else if(c == 2 || c == 5){
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdTank);
                            qtdTank++;
                        }
                    }else if(l == 1){
                        //guerreiro
                        if(c == 0 || c == 1 || c == 2 || c == 5 || c == 6 || c == 7){
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdGuerreiro);
                            qtdGuerreiro++;
                        }
                        //tank(extra)
                        if(c == 3 || c == 4){
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdTank);
                            qtdTank++;
                        }
                    }
                    //Player 2
                    else if(l == 7){
                        //Assasino
                        if(c == 0 || c == 3 || c == 4 || c == 7){
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        //Healer
                        else if(c == 1 || c == 6){
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdHealer);
                            qtdHealer++; 
                        }
                        //tank
                        else if(c == 2 || c == 5){
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdTank);
                            qtdTank++;
                        }
                    }else if(l == 6){
                        if(auxReset){
                            qtdAssasino = 0;
                            qtdTank = 4;
                            qtdGuerreiro = 8;
                            qtdHealer = 14;
                            auxReset = false;
                        }
                        //guerreiro
                        if(c == 0 || c == 1 || c == 2 || c == 5 || c == 6 || c == 7){
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdGuerreiro);
                            qtdGuerreiro++;
                        }
                        //tank(extra)
                        if(c == 3 || c == 4){
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdTank);
                            qtdTank++;
                        }   
                    }
                }
            }
        }else if(tamMatriz == 12){
            int qtdAssasino = 0;
            int qtdTank = 6;
            int qtdGuerreiro = 12;
            int qtdHealer = 20;
            boolean auxReset = true;
            
            //setar os personagens na matriz  
            for(int l = 0; l < tamMatriz; l++){
                for(int c = 0; c < tamMatriz; c++){
                    //player 1
                    if(l == 0){
                        if(c == 0 || c == 5 || c == 6 || c == 11)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        if(c == 1 || c == 3 || c == 8 || c == 10)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdHealer);
                            qtdHealer++;
                        }
                        if(c == 2 || c == 4 || c == 7 || c ==9)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                    }else if(l == 1)
                    {
                        if(c == 1 || c == 10)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        if(c == 3 || c == 8)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                        if(c == 0 || c == 2 || c == 4 || c == 5 || c == 6 || c == 7 || c == 9 || c == 11)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdGuerreiro);
                            qtdGuerreiro++;  
                        }
                    }
                    //player 2
                    else if(l == 10){
                        if(auxReset){
                            qtdAssasino = 0;
                            qtdTank = 6;
                            qtdGuerreiro = 12;
                            qtdHealer = 20;
                            auxReset = false;
                        }
                        if(c == 1 || c == 10)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        if(c == 3 || c == 8)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                        if(c == 0 || c == 2 || c == 4 || c == 5 || c == 6 || c == 7 || c == 9 || c == 11)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdGuerreiro);
                            qtdGuerreiro++;  
                        }
                    }else if(l == 11){
                        if(c == 0 || c == 5 || c == 6 || c == 11)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        if(c == 1 || c == 3 || c == 8 || c == 10)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdHealer);
                            qtdHealer++;
                        }
                        if(c == 2 || c == 4 || c == 7 || c ==9)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                    }
                }
            }
        }else if(tamMatriz == 20){
            int qtdAssasino = 0;
            int qtdTank = 10;
            int qtdGuerreiro = 20;
            int qtdHealer = 34;
            boolean auxReset = true;
            
            //setar os personagens na matriz  
            for(int l = 0; l < tamMatriz; l++){
                for(int c = 0; c < tamMatriz; c++){
                    //player 1
                    if(l == 0){
                        if(c == 3|| c == 4 || c == 6 || c == 7 || c == 9 || c == 10 || c == 12 || c == 13 || c == 15|| c == 16)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        if(c == 2 || c == 5 || c == 8 || c == 11 || c== 14 || c ==17)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdHealer);
                            qtdHealer++;
                        }
                        if(c == 0 || c == 1 || c == 18 || c == 19)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                    }else if(l == 1){
                        if(c == 2 || c == 5 || c == 8 || c == 11 || c == 14 || c == 17)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                        if(c == 0 || c == 1 || c == 3 || c == 4 || c == 6 || c == 7 || c == 9 || c == 10 || c == 12 || c == 13 || c == 15 || c == 16 || c == 18 || c == 19)
                        {
                            gm.getMatriz()[l][c] = players.get(0).getPersonagens().get(qtdGuerreiro);
                            qtdGuerreiro++;  
                        }
                    }
                    //player 2
                    else if(l == 18){
                        if(auxReset){
                            qtdAssasino = 0;
                            qtdTank = 10;
                            qtdGuerreiro = 20;
                            qtdHealer = 34;
                            auxReset = false;
                        }
                        if(c == 2 || c == 5 || c == 8 || c == 11 || c == 14 || c == 17)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                        if(c == 0 || c == 1 || c == 3 || c == 4 || c == 6 || c == 7 || c == 9 || c == 10 || c == 12 || c == 13 || c == 15 || c == 16 || c == 18 || c == 19)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdGuerreiro);
                            qtdGuerreiro++;  
                        }
                    }else if(l == 19){
                        if(c == 3|| c == 4 || c == 6 || c == 7 || c == 9 || c == 10 || c == 12 || c == 13 || c == 15|| c == 16)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdAssasino);
                            qtdAssasino++;
                        }
                        if(c == 2 || c == 5 || c == 8 || c == 11 || c== 14 || c ==17)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdHealer);
                            qtdHealer++;
                        }
                        if(c == 0 || c == 1 || c == 18 || c == 19)
                        {
                            gm.getMatriz()[l][c] = players.get(1).getPersonagens().get(qtdTank);
                            qtdTank++;  
                        }
                    }
                }
            }
        }
    }
    
    public void initializePecasPlayers(int tamGrid){
        if(tamGrid == 1){
            //[16 peças]
            //- 6 guerreiros
            //- 4 tanks
            //- 2 healers
            //- 4 assasinos
            Assasino assasino;
            Guerreiro guerreiro;
            Tank tank;
            Healer healer;
            
            //tank do player 1 e 2
            for(int i = 0; i < 4; i++){
                tank = new Tank(players.get(0));
                players.get(0).setPersonagens(tank);
                tank = new Tank(players.get(1));
                players.get(1).setPersonagens(tank);   
            }
            
            //assasino do player 1 e 2
            for(int i = 0; i < 4; i++){
                assasino = new Assasino(players.get(0));
                players.get(0).setPersonagens(assasino);
                assasino = new Assasino(players.get(1));
                players.get(1).setPersonagens(assasino);
            }
            
            //guerreiro do player 1 e 2
            for(int i = 0; i < 6; i++){
                guerreiro = new Guerreiro(players.get(0));
                players.get(0).setPersonagens(guerreiro);
                guerreiro = new Guerreiro(players.get(1));
                players.get(1).setPersonagens(guerreiro);
            }
            
            //healers do player 1 e 2
            for(int i = 0; i < 2; i++){
                healer = new Healer(players.get(0));
                players.get(0).setPersonagens(healer);
                healer = new Healer(players.get(1));
                players.get(1).setPersonagens(healer);
            }
        }else if(tamGrid == 2){
            //[24 peças]
            //- 8 guerreiros
            //- 6 tanks
            //- 4 healers
            //- 6 assasinos
            Assasino assasino;
            Guerreiro guerreiro;
            Tank tank;
            Healer healer;
            
            //tank do player 1 e 2
            for(int i = 0; i < 6; i++){
                tank = new Tank(players.get(0));
                players.get(0).setPersonagens(tank);
                tank = new Tank(players.get(1));
                players.get(1).setPersonagens(tank); 
            }
            //assasinos do player 1 e 2
            for(int i = 0; i < 6; i++){
                assasino = new Assasino(players.get(0));
                players.get(0).setPersonagens(assasino);
                assasino = new Assasino(players.get(1));
                players.get(1).setPersonagens(assasino); 
            }
            //guerreiro do player 1 e 2
            for(int i = 0; i < 8; i++){
                guerreiro = new Guerreiro(players.get(0));
                players.get(0).setPersonagens(guerreiro);
                guerreiro = new Guerreiro(players.get(1));
                players.get(1).setPersonagens(guerreiro);
            }
            //healer do player 1 e 2
            for(int i = 0; i < 4; i++){
                healer = new Healer(players.get(0));
                players.get(0).setPersonagens(healer);
                healer = new Healer(players.get(1));
                players.get(1).setPersonagens(healer);
            }
        }else if(tamGrid == 3){
            //[40 peças]
            //- 14 guerreiros
            //- 10 tanks
            //- 6 healers
            //- 10 assasinos
            Assasino assasino;
            Guerreiro guerreiro;
            Tank tank;
            Healer healer;
            
            //tank do player 1 e 2
            for(int i = 0; i < 10; i++){
                tank = new Tank(players.get(0));
                players.get(0).setPersonagens(tank);
                tank = new Tank(players.get(1));
                players.get(1).setPersonagens(tank);
            }
            //assasino do player 1 e 2
            for(int i = 0; i < 10; i++){
                assasino = new Assasino(players.get(0));
                players.get(0).setPersonagens(assasino);
                assasino = new Assasino(players.get(1));
                players.get(1).setPersonagens(assasino);
            }
            //guerreiro do player 1 e 2
            for(int i = 0; i < 14; i++){
                guerreiro = new Guerreiro(players.get(0));
                players.get(0).setPersonagens(guerreiro);
                guerreiro = new Guerreiro(players.get(1));
                players.get(1).setPersonagens(guerreiro);                
            }
            //healer do player 1 e 2
            for(int i = 0; i < 6; i++){
                healer = new Healer(players.get(0));
                players.get(0).setPersonagens(healer);
                healer = new Healer(players.get(1));
                players.get(1).setPersonagens(healer);
            }
        }
    }
    
    public void initializeTabuleiroGrid(int i, int canvasX, int canvasY, int tamGrid){
        RowConstraints con = new RowConstraints();
        ColumnConstraints con1 = new ColumnConstraints();
        con.setPrefHeight(1000);
        con1.setPrefWidth(1000);
        gpTabuleiro.getRowConstraints().add(con);
        gpTabuleiro.getColumnConstraints().add(con1);
         Canvas canvas;
        for(int j = 0; j < tamGrid; j++)
        {
            canvas = new Canvas(canvasX,canvasY);
            
            if(i % 2 == 0 && j % 2 == 0)
            {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.valueOf("#0A4167"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }else if(i % 2 == 0 && j % 2 != 0){
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.valueOf("#fefefe"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }else if(i % 2 != 0 && j % 2 != 0){
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.valueOf("#0A4167"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }else if(i % 2 != 0 && j % 2 == 0){
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.valueOf("#fefefe"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
            actionUser(canvas, canvasX,canvasY,tamGrid);
            gm.setCanvasDisplay(canvas);
            gpTabuleiro.add(canvas, i, j);
        }
    }
    
    private void setVisibleElements(boolean ativo){
        icLife.setVisible(ativo);
        icMana.setVisible(ativo);
        icHabilit.setVisible(ativo);
        pNome.setVisible(ativo);
        qtdMana.setVisible(ativo);
        qtdLife.setVisible(ativo);
        pMana.setVisible(ativo);
        pLife.setVisible(ativo);
        displayHabilit.setVisible(ativo);
        cbHabilidade.setVisible(ativo);
        txtHabilitDano.setVisible(ativo);
        txtHabilitDescricao.setVisible(ativo);
        txtHabilitCustoMana.setVisible(ativo);
        if(gm.getPersonagemSelected() != null && players.get(gm.getRodada()) == gm.getPersonagemSelected().getPlayer()){
            btnUsar.setVisible(true);    
        }else{
            btnUsar.setVisible(false);
        }
    }
   
    @FXML public void usarHabilidade(ActionEvent event){
        habilidadeFuncional = true;
        habilidadeSelecionada = cbHabilidade.getSelectionModel().getSelectedItem().toString();
        gm.setPersonagemAtacante(gm.getPersonagemSelected());
        gm.setColumGridAnterior(gm.getColumGridSelected());
        gm.setLineGridAnterior(gm.getLineGridSelected());
        
        int dimensaoCanva;
        int tamGrid;
        dimensaoCanva = (int) gm.getCanvasDisplay().get(1).getWidth();
        boolean mover = false;
        if(dimensaoCanva == 55){
            tamGrid = 8;
        }else if(dimensaoCanva == 37){
            tamGrid = 12;
        }else{
            tamGrid = 20;
        }
        gm.setClickMouse(true);
        for(int l = 0; l < tamGrid; l++){
            for(int c = 0; c < tamGrid; c++){
                if(gm.getLineGridAnterior() != -1 || gm.getColumGridAnterior() != -1){
                    if(!mover && gm.getMatriz()[l][c] == null && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()].searchPosicaoMovimentacao(l, c)){
                       GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * c) + l).getGraphicsContext2D();
                       if(gm.getMatrizGridPadrao()[l][c] == 1){
                           gc.setFill(Color.valueOf("#0A4167"));
                       }else{
                           gc.setFill(Color.valueOf("#fefefe"));
                       }
                       gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                    }else if(mover && gm.getMatriz()[l][c] == null && gm.getMatriz()[gm.getLineGridSelected()][gm.getColumGridSelected()].searchPosicaoMovimentacao(l, c)){
                       GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * c) + l).getGraphicsContext2D();
                       if(gm.getMatrizGridPadrao()[l][c] == 1){
                           gc.setFill(Color.valueOf("#0A4167"));
                       }else{
                           gc.setFill(Color.valueOf("#fefefe"));
                       }
                       gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);                        
                    }   
                }
            }
        }
        gm.setColumGridSelected(-1);
        gm.setLineGridSelected(-1);
    }
    
    @FXML public void updateDisplayHabilit(ActionEvent event){
        if(cbHabilidade.getSelectionModel().getSelectedItem() != null && cbHabilidade.isVisible()){
            Habilidade h = null;
            for(Habilidade habilidade : gm.getPersonagemSelected().getHabilidades()) {
                if(habilidade.getNome().equalsIgnoreCase(cbHabilidade.getSelectionModel().getSelectedItem().toString())){
                    h = habilidade;
                }
            }
            if(h != null){
                if(h.getDano() < 0){
                    txtHabilitDano.setText("Cura: "+ (h.getDano()*-1));
                }else{
                   txtHabilitDano.setText("Dano: "+ h.getDano());
                }
                txtHabilitDescricao.setText("Descrição: "+h.getDescricao());
                txtHabilitCustoMana.setText("Custo Mana: "+h.getCustoMana());

                txtHabilitDano.setVisible(true);
                txtHabilitDescricao.setVisible(true);
                txtHabilitCustoMana.setVisible(true);
            }    
        }
    }
    
    @FXML public void passarTurno(ActionEvent event){
        gm.setPersonagemAnterior(gm.getPersonagemSelected());
        gm.setColumGridAnterior(gm.getColumGridSelected());
        gm.setLineGridAnterior(gm.getLineGridSelected());
        gm.setMoveu(false);
        gm.setUsouHabilidade(false);
        int dimensaoCanva;
        int tamGrid;
        dimensaoCanva = (int) gm.getCanvasDisplay().get(1).getWidth();
        boolean mover = false;
        if(dimensaoCanva == 55){
            tamGrid = 8;
        }else if(dimensaoCanva == 37){
            tamGrid = 12;
        }else{
            tamGrid = 20;
        }
        gm.setClickMouse(true);
        for(int l = 0; l < tamGrid; l++){
            for(int c = 0; c < tamGrid; c++){
                if(gm.getLineGridAnterior() != -1 && gm.getColumGridAnterior() != -1 && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] != null){
                    if(!mover && gm.getMatriz()[l][c] == null && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()].searchPosicaoMovimentacao(l, c)){
                       GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * c) + l).getGraphicsContext2D();
                       if(gm.getMatrizGridPadrao()[l][c] == 1){
                           gc.setFill(Color.valueOf("#0A4167"));
                       }else{
                           gc.setFill(Color.valueOf("#fefefe"));
                       }
                       gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                    }else if(mover && gm.getMatriz()[l][c] == null && gm.getMatriz()[gm.getLineGridSelected()][gm.getColumGridSelected()].searchPosicaoMovimentacao(l, c)){
                       GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * c) + l).getGraphicsContext2D();
                       if(gm.getMatrizGridPadrao()[l][c] == 1){
                           gc.setFill(Color.valueOf("#0A4167"));
                       }else{
                           gc.setFill(Color.valueOf("#fefefe"));
                       }
                       gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);                        
                    }   
                }
            }
        }
        gm.setColumGridSelected(-1);
        gm.setLineGridSelected(-1);
        gm.alterarRodada();
        if(gm.getRodada() == 0)
        {
            turnoNotificador.setText("Sua vez " + players.get(0).getNick());
            turnoNotificador.setTextFill(Color.WHITE);
        }
        else
        {
            turnoNotificador.setText("Sua vez " + players.get(1).getNick());
            turnoNotificador.setTextFill(Color.WHITE);
        }
        setVisibleElements(false);
    }
    
    public void actionUser(Canvas canvas, int canvasX, int canvasY, int tamGrid){
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, EVENT ->
        {
            initializeMatrizGridPadrao(tamGrid);
            int coluna = (int) canvas.getLayoutX()/canvasX;
            int linha = (int) canvas.getLayoutY()/canvasY;

            //armazena a linha e coluna anterior
            gm.setLineGridAnterior(gm.getLineGridSelected());
            gm.setColumGridAnterior(gm.getColumGridSelected());
            //verifica se o personagem anterior é a posicao -1 -1 (Posicao inicial nao valida)
            if(gm.getLineGridAnterior() == -1 || gm.getColumGridAnterior() == -1){
                gm.setPersonagemAnterior(null);
            }else{
                gm.setPersonagemAnterior(gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()]);
            }
            
            //armazena o personagem selecionado e a linha
            gm.setPersonagemSelected(gm.getMatriz()[linha][coluna]);
            gm.setLineGridSelected(linha);
            gm.setColumGridSelected(coluna);
            
            if(gm.getMatriz()[linha][coluna] != null){
                txtHabilitDano.setVisible(false);
                txtHabilitDescricao.setVisible(false);
                txtHabilitCustoMana.setVisible(false);
                displayAcoes();    
            }else{
                txtHabilitDano.setVisible(false);
                txtHabilitDescricao.setVisible(false);
                txtHabilitCustoMana.setVisible(false);
                setVisibleElements(false);
            }
            Tank tank = (Tank) players.get(0).getPersonagens().get(0);
            Guerreiro guerreiro;
            Healer healer;
            Assasino assassino;
            
            if(tamGrid == 8)
            {
                guerreiro = (Guerreiro) players.get(0).getPersonagens().get(9);
                healer = (Healer) players.get(0).getPersonagens().get(15);
                assassino = (Assasino) players.get(0).getPersonagens().get(4);
            }
            else if(tamGrid == 12)
            {
                guerreiro = (Guerreiro) players.get(0).getPersonagens().get(12);
                healer = (Healer) players.get(0).getPersonagens().get(23);
                assassino = (Assasino) players.get(0).getPersonagens().get(6);
            }
            else
            {
                guerreiro = (Guerreiro) players.get(0).getPersonagens().get(21);
                healer = (Healer) players.get(0).getPersonagens().get(39);
                assassino = (Assasino) players.get(0).getPersonagens().get(11);
            }
            
            if(habilidadeFuncional == true)
            {
                //habilidade padrão para todos os personagens
                if(habilidadeSelecionada.equals("Ataque Normal"))
                {
                    healer.utilizarHabilitPadrao(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                 
                //Habilidades espacificas do healer
                if(habilidadeSelecionada.equals("Curar"))
                {
                    healer.utilizarHabilitCurar(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                if(habilidadeSelecionada.equals("Curar Todos"))
                {
                    healer.utilizarHabilitCurarTodos(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                if(habilidadeSelecionada.equalsIgnoreCase("Drenar Vida")){
                    healer.utilizarHabilitDrenarVida(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                
                //habilidades especificas do guerreiro
                if(habilidadeSelecionada.equalsIgnoreCase("Espada Mágica")){
                    guerreiro.utilizarHabilitEspadaMagica(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                if(habilidadeSelecionada.equalsIgnoreCase("Utimate Dual Sword")){
                    guerreiro.utilizarHabilitUltimateDualSword(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                
                //habilidades especificas do tank 
                if(habilidadeSelecionada.equalsIgnoreCase("Ataque com Escudo")){
                    tank.utilizarHabilidadeAtaqueComEscudo(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                if(habilidadeSelecionada.equalsIgnoreCase("Arrancada")){
                    tank.utilizarHabilidadeArrancada(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                
                //habilidades espeficicas do asassino
                if(habilidadeSelecionada.equalsIgnoreCase("Adaga Mágica")){
                    assassino.utilizarHabilitAdagaMagica(gm, tamGrid);
                    habilidadeFuncional = false;
                }
                if(habilidadeSelecionada.equalsIgnoreCase("Corte Rápido")){
                    assassino.utilizarHabilitCorteMagico(gm, tamGrid);
                    habilidadeFuncional = false;
                }
            }
            healer.movimentacao(gm);
            tank.movimentacao(gm);
            guerreiro.movimentacao(gm);
            assassino.movimentacao(gm);
            
            if(gm.isMoveu() && gm.isUsouHabilidade()){
                gm.alterarRodada();
                gm.setMoveu(false);
                gm.setUsouHabilidade(false);
                setVisibleElements(false);
                btnUsar.setVisible(false);
            }
            System.out.println("SELECIONADA: [" + gm.getLineGridSelected() + "][" + gm.getColumGridSelected()+"]");
            System.out.println("ANTERIOR: ["+gm.getLineGridAnterior() + "][" + gm.getColumGridAnterior()+ "|");
            
            if(gm.getRodada() == 0)
            {
                turnoNotificador.setText("Sua vez " + players.get(0).getNick());
                turnoNotificador.setTextFill(Color.WHITE);
            }
            else
            {
                turnoNotificador.setText("Sua vez " + players.get(1).getNick());
                turnoNotificador.setTextFill(Color.WHITE);
            }
            this.vitoriaJogador(tamGrid);
        });
    }
    
    private void initializeMatrizGridPadrao(int tamGrid) {
        
        for(int m = 0; m < tamGrid; m++)
        {
            for(int n = 0; n < tamGrid; n++)
            {
                if(m % 2 == 0 && n % 2 == 0)
                {
                    gm.setMatrizGridPadrao(m, n, 1);//matrizGridPadrao[m][n] = 1;
                }else if(m % 2 == 0 && n % 2 != 0){
                    gm.setMatrizGridPadrao(m, n, 0);//matrizGridPadrao[m][n] = 0;
                }else if(m % 2 != 0 && n % 2 != 0){
                    gm.setMatrizGridPadrao(m, n, 1);
                }else if(m % 2 != 0 && n % 2 == 0){
                    gm.setMatrizGridPadrao(m, n, 0);
                }
            }
        }
    }
    
    public void displayAcoes(){
        int x = gm.getLineGridSelected();
        int y = gm.getColumGridSelected();
        int manaInit = 0, lifeInit = 0;        
        pNome.setText(gm.getMatriz()[x][y].getNome());
        pLife.progressProperty().setValue(gm.getMatriz()[x][y].getLife());
        if(gm.getMatriz()[x][y] instanceof Healer){
            lifeInit = 100;
            manaInit = 200;
        }else if(gm.getMatriz()[x][y] instanceof Assasino){
            lifeInit = 150;
            manaInit = 100;
        }else if(gm.getMatriz()[x][y] instanceof Guerreiro){
            lifeInit = 200;
            manaInit = 100;
        }else if(gm.getMatriz()[x][y] instanceof Tank){
            lifeInit = 300;
            manaInit = 100;
        }
        qtdMana.setText(gm.getMatriz()[x][y].getMana()+"/"+manaInit);
        qtdLife.setText(gm.getMatriz()[x][y].getLife()+"/"+lifeInit);
        pMana.progressProperty().setValue(gm.getMatriz()[x][y].getMana());
        //LIMPAR COMBOBOX
        if(cbHabilidade.getItems().size() > 0){
            cbHabilidade.getItems().clear();
        }
        //PREENCHER COMBOBOX
        for(Habilidade p: gm.getMatriz()[x][y].getHabilidades()){
            cbHabilidade.getItems().add(p.getNome());
            
        }
        cbHabilidade.getSelectionModel().select("Ataque normal");
        
        pLife.setProgress((double) gm.getMatriz()[x][y].getLife()/lifeInit);
        pMana.setProgress((double) gm.getMatriz()[x][y].getMana()/manaInit);
       
        setVisibleElements(true);
    }
    
    public void initializeTabuleiroDisplay(int tamGrid, int tam){
        Image imgAssassino = new Image(getClass().getClassLoader().getResourceAsStream("img/Assassin.png"));
        Image imgHealer = new Image(getClass().getClassLoader().getResourceAsStream("img/Healer.png"));
        Image imgTank = new Image(getClass().getClassLoader().getResourceAsStream("img/Tank.png"));
        Image imgWarrior = new Image(getClass().getClassLoader().getResourceAsStream("img/Warrior.png"));
        Image imgAssassino2 = new Image(getClass().getClassLoader().getResourceAsStream("img/Assassin2.png"));
        Image imgHealer2 = new Image(getClass().getClassLoader().getResourceAsStream("img/Healer2.png"));
        Image imgTank2 = new Image(getClass().getClassLoader().getResourceAsStream("img/Tank2.png"));
        Image imgWarrior2 = new Image(getClass().getClassLoader().getResourceAsStream("img/Warrior2.png"));
            for(int i=0; i < tamGrid; i++){
                for(int l = 0; l < tamGrid; l++){
                    if(gm.getMatriz()[l][i] instanceof Healer)
                    {
                        if(players.get(0) == gm.getMatriz()[l][i].getPlayer())
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgHealer, 0, 0, tam, tam);
                        }
                        else
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgHealer2, 0, 0, tam, tam);
                        }
                    }
                    else if(gm.getMatriz()[l][i] instanceof Tank)
                    {
                        if(players.get(0) == gm.getMatriz()[l][i].getPlayer())
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgTank, 0, 0, tam, tam);
                        }
                        else
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgTank2, 0, 0, tam, tam);
                        }
                    }
                    else if(gm.getMatriz()[l][i] instanceof Assasino)
                    {
                        if(players.get(0) == gm.getMatriz()[l][i].getPlayer())
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgAssassino, 0, 0, tam, tam);
                        }
                        else
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgAssassino2, 0, 0, tam, tam);
                        }
                    }
                    else if(gm.getMatriz()[l][i] instanceof Guerreiro)
                    {
                        if(players.get(0) == gm.getMatriz()[l][i].getPlayer())
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgWarrior, 0, 0, tam, tam);
                        }
                        else
                        {
                        GraphicsContext gc = gm.getCanvasDisplay().get(l + (tamGrid*i)).getGraphicsContext2D();
                        gc.drawImage(imgWarrior2, 0, 0, tam, tam);
                        }
                    }
                }
            }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Tabuleiro.getTabuleiro().isPequeno())
        {
            int tamGrid = 8;
            gm = new GameMecanic(tamGrid);
            initializePecasPlayers(1);
            initializePecasMatriz(8);
            for (int i = 0; i < 8; i++) 
            {
                initializeTabuleiroGrid(i, 55, 55, 8);
            }
            initializeTabuleiroDisplay(8, 55);
            turnoNotificador.setText("Sua vez " + players.get(0).getNick());
            turnoNotificador.setTextFill(Color.WHITE);
        }
        else if(Tabuleiro.getTabuleiro().isMedio() == true)
        {
            int tamGrid = 12;
            gm = new GameMecanic(tamGrid);
            initializePecasPlayers(2);    
            initializePecasMatriz(12);
            for (int i = 0; i < 12; i++) 
            {
                initializeTabuleiroGrid(i, 37, 37, 12);
            }
            initializeTabuleiroDisplay(12, 37);
            turnoNotificador.setText("Sua vez " + players.get(0).getNick());
            turnoNotificador.setTextFill(Color.WHITE);
        }
        else if(Tabuleiro.getTabuleiro().isGrande())
        {
            int tamGrid = 20;
            gm = new GameMecanic(tamGrid);
            initializePecasPlayers(3);
            initializePecasMatriz(20);
            for (int i = 0; i < 20; i++) 
            {
                initializeTabuleiroGrid(i, 22, 22, 20);
            }
            initializeTabuleiroDisplay(20, 22);
            turnoNotificador.setText("Sua vez " + players.get(0).getNick());
            turnoNotificador.setTextFill(Color.WHITE);
        }
    }    

    //condição de vitória
    public void vitoriaJogador(int tamGrid) {
    int derrota = 0;
    for(int i = 0; i < tamGrid*2; i++)
    {
        //Pra testar a janela nova ative a linha de baixo
        //players.get(0).getPersonagens().get(i).setLife(0);
        if(players.get(0).getPersonagens().get(i).getLife() == 0)
        {
            derrota++;
        }
    }
    if(derrota == tamGrid*2)
    { 
        Tabuleiro.getTabuleiro().setVencedor(players.get(1).getNick());
        Stage stageClose = (Stage) turnoNotificador.getScene().getWindow();
        stageClose.close();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaFinal.fxml"));
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
    
    derrota = 0;
    
    for(int i = 0; i < tamGrid*2; i++)
    {
        if(players.get(1).getPersonagens().get(i).getLife() == 0)
        {
            derrota++;
        }
    }
    
    if(derrota == tamGrid*2)
    {
        Tabuleiro.getTabuleiro().setVencedor(players.get(0).getNick());
        Stage stageClose = (Stage) turnoNotificador.getScene().getWindow();
        stageClose.close();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaFinal.fxml"));
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
    }
}