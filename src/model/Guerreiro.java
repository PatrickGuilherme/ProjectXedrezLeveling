package model;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import static model.XlProject.players;

public class Guerreiro extends Personagem{
    public Guerreiro(Player player){
        this.descricaoPersonagem();
        this.habilitPadrao();
        this.espadaMagica();
        this.ultimateDualSword();
        this.setPlayer(player);
        this.setPosicoesMovimentacao(new ArrayList<String>());
    }
    
    @Override
    public void movimentacao(GameMecanic gm){
        int rodada = gm.getRodada();
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
        int atuLinha = gm.getLineGridSelected();
        int atuColuna = gm.getColumGridSelected();
        
        if(atuLinha != -1 && gm.getMatriz()[atuLinha][atuColuna]!= null && gm.isClickMouse() && gm.getPersonagemSelected() instanceof Guerreiro && gm.getPersonagemSelected().getPlayer() == players.get(rodada) && gm.getLineGridSelected() != -1 && gm.getColumGridSelected() != -1 && !gm.isMoveu())
        {
            if(gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().size() > 0){
                gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().clear();
            }
            
            gm.setClickMouse(false);
            for(int m = 1; m < 3; m++)
            {
                if(atuLinha - m >= 0)
                {
                    if(gm.getMatriz()[atuLinha - m][atuColuna] == null)
                    {
                        GraphicsContext gc = gm.getCanvasDisplay().get(atuLinha - m + (tamGrid*atuColuna)).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        
                        String posicao = (atuLinha - m) + "-" + atuColuna; 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                    } else break;
                }
            }
            for(int m = 1; m < 3; m++)
            {
                if(atuLinha + m < tamGrid)
                {
                    if(gm.getMatriz()[atuLinha + m][atuColuna] == null)
                    {
                        GraphicsContext gc = gm.getCanvasDisplay().get(atuLinha + m + (tamGrid*atuColuna)).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        
                        String posicao = (atuLinha + m) + "-" + atuColuna; 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                    } else break;
                }
            }
            if(atuColuna + (1) < tamGrid)
            {
                if(gm.getMatriz()[atuLinha][atuColuna + (1)] == null)
                {
                    GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna + (1))) + atuLinha).getGraphicsContext2D();
                    gc.setFill(Color.RED);
                    gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                    
                    String posicao = atuLinha + "-" + (atuColuna + (1)); 
                    gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);                    
                }
            }
            if(atuColuna - (1) >= 0)
            {
                if(gm.getMatriz()[atuLinha][atuColuna - (1)] == null)
                {
                    GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna - (1))) + atuLinha).getGraphicsContext2D();
                    gc.setFill(Color.RED);
                    gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                    
                    String posicao = atuLinha  + "-" + (atuColuna - (1)); 
                    gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);               
                }
            }
        }
        else if(gm.getPersonagemAnterior() != null && gm.getPersonagemAnterior().getPlayer() == players.get(rodada) && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] instanceof Guerreiro)
        {   
            if(gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()].searchPosicaoMovimentacao(gm.getLineGridSelected(), gm.getColumGridSelected()) && !gm.isClickMouse()){
                gm.getMatriz()[gm.getLineGridSelected()][gm.getColumGridSelected()] = gm.getPersonagemAnterior();
                gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] = null;
                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * gm.getColumGridSelected()) + gm.getLineGridSelected()).getGraphicsContext2D();
                if(rodada == 0)
                {
                    Image imgWarrior = new Image(getClass().getClassLoader().getResourceAsStream("img/Warrior.png"));
                    gc.drawImage(imgWarrior, 0, 0,dimensaoCanva, dimensaoCanva);
                }
                else
                {
                    Image imgWarrior = new Image(getClass().getClassLoader().getResourceAsStream("img/Warrior2.png"));
                    gc.drawImage(imgWarrior, 0, 0,dimensaoCanva, dimensaoCanva);    
                }
                //limpar a posicao anterior da peça
                gc = gm.getCanvasDisplay().get((tamGrid * gm.getColumGridAnterior()) + gm.getLineGridAnterior()).getGraphicsContext2D();
                if(gm.getMatrizGridPadrao()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] == 1){
                    gc.setFill(Color.valueOf("#0A4167"));
                }else{
                    gc.setFill(Color.valueOf("#fefefe"));
                }
                gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                mover = true;
                gm.setMoveu(mover);
            }
            gm.setClickMouse(true);
            for(int l = 0; l < tamGrid; l++){
                for(int c = 0; c < tamGrid; c++){
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
            gm.setColumGridSelected(-1);
            gm.setLineGridSelected(-1);
        }
    }
    
    @Override
    public void descricaoPersonagem() {
        this.setNome("Guerreiro");
        this.setLife(200);
        this.setMana(100);
    }

    @Override
    public void habilitPadrao() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Ataque Normal");
        habilidade.setDescricao("O guerreiro troca soco com o inimigo");
        habilidade.setCustoMana(0);
        habilidade.setDano(20);
        this.setHabilidades(habilidade);
    }
    
    public void espadaMagica() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Espada Mágica");
        habilidade.setDescricao("O guerrreiro utiliza a espada embuida de magia para atacar o inimigo");
        habilidade.setCustoMana(20);
        habilidade.setDano(25);
        this.setHabilidades(habilidade);  
    }
    
    public void ultimateDualSword() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Utimate Dual Sword");
        habilidade.setDescricao("O guerreiro utiliza duas espada embuidas de magia para atacar o inimigo");
        habilidade.setCustoMana(90);
        habilidade.setDano(40);
        this.setHabilidades(habilidade);  
    }
    
    public void utilizarHabilitEspadaMagica(GameMecanic gm, int tamGrid){
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null){
            if(gm.getPersonagemAtacante().getMana() >= 20){
                if(!gm.isUsouHabilidade()){
                    int atuLinha = gm.getLineGridSelected();
                    int atuColuna = gm.getColumGridSelected();
                    boolean ataqueFeito = false;
                    boolean blockAtaqueBaixo  = false;
                    boolean blockAtaqueCima = false;

                    for(int i = 0; i < 2; i++){
                        //PARA BAIXO 
                        if(atuLinha +  (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha + (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueBaixo)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha + (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha + (i + 1)][atuColuna].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }else if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != gm.getPersonagemAtacante()){
                                blockAtaqueBaixo = true;
                            }
                        }
                        //PARA CIMA
                        if(atuLinha - (i + 1) >= 0)
                        {
                            if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha - (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueCima)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha - (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha - (i + 1)][atuColuna].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }else if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != gm.getPersonagemAtacante()){
                                blockAtaqueCima = true;
                            }
                        }    
                    }
                    //PARA DIREITA ->
                    if(atuColuna + 1 < tamGrid)
                    {
                        if(gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1] == gm.getPersonagemAtacante() && !ataqueFeito)
                        {
                            gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                            gm.getMatriz()[atuLinha][atuColuna + 1].setMana(gm.getMatriz()[atuLinha][atuColuna + 1].getMana() - 20);
                            ataqueFeito = true;
                            this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                        }
                    }
                    //PARA ESQUERDA ->
                    if(atuColuna - 1 >= 0)
                    {
                        if(gm.getMatriz()[atuLinha][atuColuna -  1] != null && gm.getMatriz()[atuLinha][atuColuna - 1] == gm.getPersonagemAtacante() && !ataqueFeito)
                        {
                            gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                            gm.getMatriz()[atuLinha][atuColuna - 1].setMana(gm.getMatriz()[atuLinha][atuColuna - 1].getMana() - 20);
                            ataqueFeito = true;
                            this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                        }
                    }
                    if(ataqueFeito ){
                        gm.setUsouHabilidade(true);
                    }else{
                        msgErroHeader = "Alcance da habilidade inválida";
                        msgErroContent = "Sua habilidade não alcança o inimigo | Aproxime-se  para ataca-lo";
                    }
                }
            }else{
                msgErroHeader = "Mana insuficiente";
                msgErroContent = "Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho";
            }
        }else{
            msgErroHeader = "Não há inimigos ai";
            msgErroContent = "Seus aliados precisam de você | Selecione uma peça inimiga para utilizar a habilidade";
        }
        if(msgErroHeader.trim().length() > 0 && msgErroContent.trim().length() > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msgErroHeader);
            alert.setContentText(msgErroContent);
            alert.show();
        }
    }
    
    public void utilizarHabilitUltimateDualSword(GameMecanic gm, int tamGrid){
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null){
            if(gm.getPersonagemAtacante().getMana() >= 90){
                if(!gm.isUsouHabilidade()){
                    int atuLinha = gm.getLineGridSelected();
                    int atuColuna = gm.getColumGridSelected();
                    boolean ataqueFeito = false;
                    boolean searchPersonagemAtaque = true;

                    //PARA BAIXO 
                    if((atuLinha + 1) < tamGrid)
                    {
                        if(gm.getMatriz()[atuLinha + 1][atuColuna] != null && gm.getMatriz()[atuLinha + 1][atuColuna] == gm.getPersonagemAtacante())
                        {
                            searchPersonagemAtaque = true;
                            gm.getMatriz()[atuLinha + 1][atuColuna].setMana(gm.getMatriz()[atuLinha + 1][atuColuna].getMana() - 90);
                        }
                    }
                    //PARA CIMA
                    if(atuLinha - 1 >= 0)
                    {
                        if(gm.getMatriz()[atuLinha - 1][atuColuna] != null && gm.getMatriz()[atuLinha - 1][atuColuna] == gm.getPersonagemAtacante())
                        {
                            searchPersonagemAtaque = true;
                            gm.getMatriz()[atuLinha - 1][atuColuna].setMana(gm.getMatriz()[atuLinha - 1][atuColuna].getMana() - 90);
                        }
                    }
                    //verifica se encontrou o personagem atacante
                    if(searchPersonagemAtaque){
                        //tira life na posição selecionada
                        gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 40);
                        
                        //tira life na diagonal direita ->
                        if(atuColuna + 1 < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1] != gm.getPersonagemAtacante())
                            {
                                gm.getMatriz()[atuLinha][atuColuna + 1].setLife(gm.getMatriz()[atuLinha][atuColuna + 1].getLife() - 40);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }
                        }
                        //tira life da diagonal esquerda <-
                        if(atuColuna - 1 >= 0)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna - 1] != null && gm.getMatriz()[atuLinha][atuColuna - 1] != gm.getPersonagemAtacante())
                            {
                                gm.getMatriz()[atuLinha][atuColuna - 1].setLife(gm.getMatriz()[atuLinha][atuColuna - 1].getLife() - 40);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }
                        }
                    }
                                        
                    if(ataqueFeito){
                        gm.setUsouHabilidade(true);
                    }else{
                        msgErroHeader = "Alcance da habilidade inválida";
                        msgErroContent = "Sua habilidade não alcança o inimigo | Aproxime-se  para ataca-lo";
                    }
                }
            }else{
                msgErroHeader = "Mana insuficiente";
                msgErroContent = "Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho";
            }
        }else{
            msgErroHeader = "Não há inimigos ai";
            msgErroContent = "Seus aliados precisam de você | Selecione uma peça inimiga para utilizar a habilidade";
        }
        if(msgErroHeader.trim().length() > 0 && msgErroContent.trim().length() > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msgErroHeader);
            alert.setContentText(msgErroContent);
            alert.show();
        }
    }
}