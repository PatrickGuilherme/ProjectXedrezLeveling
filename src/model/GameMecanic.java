package model;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import static model.XlProject.players;

public class GameMecanic {
    private Personagem personagemSelected;
    private Personagem personagemAnterior;
    private Personagem personagemAtacante;
    private Personagem[][] matriz;
    private int[][] matrizGridPadrao;
    private int lineGridSelected;
    private int lineGridAnterior;
    private int columGridSelected;
    private int columGridAnterior;
    private int rodada;
    private boolean clickMouse;
    private boolean moveu;
    private boolean usouHabilidade;
    private ArrayList<Canvas> CanvasDisplay;
    
    public GameMecanic(int tamGrid){
        this.matriz = new Personagem[tamGrid][tamGrid];
        this.matrizGridPadrao = new int[tamGrid][tamGrid];
        this.lineGridSelected = -1;
        this.columGridSelected = -1;
        this.rodada = 0;
        this.clickMouse = true;
        this.moveu = false;
        this.usouHabilidade = false;
        this.CanvasDisplay = new ArrayList<>();
    }
    
    public void setPersonagemSelected(Personagem personagemSelected) {
        this.personagemSelected = personagemSelected;
    }
    public Personagem getPersonagemSelected() {
        return personagemSelected;
    }
    
    public void setPersonagemAnterior(Personagem personagemAnterior) {
        this.personagemAnterior = personagemAnterior;
    }
    public Personagem getPersonagemAnterior() {
        return personagemAnterior;
    }
    
    public void setPersonagemAtacante(Personagem personagemAtacante) {
        this.personagemAtacante = personagemAtacante;
    }
    public Personagem getPersonagemAtacante() {
        return personagemAtacante;
    }
    
    public void setMatriz(int linha, int coluna, Personagem personagem) {
        this.matriz[linha][coluna] = personagem;
    }
    public Personagem[][] getMatriz() {
        return matriz;
    }
    
    public void setMatrizGridPadrao(int linha, int coluna, int cor) {
        this.matrizGridPadrao[linha][coluna] = cor;
    }
    public int[][] getMatrizGridPadrao() {
        return matrizGridPadrao;
    }
    
    public void setLineGridSelected(int lineGrid) {
        this.lineGridSelected = lineGrid;
    }
    public int getLineGridSelected() {
        return lineGridSelected;
    } 
     
    public void setLineGridAnterior(int lineGridAnterior) {
        this.lineGridAnterior = lineGridAnterior;
    }
    public int getLineGridAnterior() {
        return lineGridAnterior;
    }
    
    public void setColumGridSelected(int columGrid) {
        this.columGridSelected = columGrid;
    }
    public int getColumGridSelected() {
        return columGridSelected;
    }
   
    public void setColumGridAnterior(int columGridAnterior) {
        this.columGridAnterior = columGridAnterior;
    }
    public int getColumGridAnterior() {
        return columGridAnterior;
    }
    
    public int getRodada() {
        return rodada;
    }
    public void alterarRodada(){
        if(rodada == 0) rodada = 1;
        else rodada = 0;
    }
  
    public void setClickMouse(boolean clickMouse) {
        this.clickMouse = clickMouse;
    }
    public boolean isClickMouse() {
        return clickMouse;
    }
    
    public void setMoveu(boolean moveu) {
        this.moveu = moveu;
    }
    public boolean isMoveu() {
        return moveu;
    }
    
    public void setUsouHabilidade(boolean usouHabilidade) {
        this.usouHabilidade = usouHabilidade;
    }
    public boolean isUsouHabilidade() {
        return usouHabilidade;
    }
    
    public void setCanvasDisplay(Canvas CanvasDisplay) {
        this.CanvasDisplay.add(CanvasDisplay);
    }
    public ArrayList<Canvas> getCanvasDisplay() {
        return CanvasDisplay;
    }
}
