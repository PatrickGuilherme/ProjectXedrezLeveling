package model;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import static model.XlProject.players;

public class Tank extends Personagem{
    public Tank(Player player){
        this.descricaoPersonagem();
        this.habilitPadrao();
        this.ataqueComEscudo();
        this.arrancada();
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
        
        if(gm.isClickMouse() && gm.getPersonagemSelected() instanceof Tank && gm.getPersonagemSelected().getPlayer() == players.get(rodada) && !gm.isMoveu())
        {
            int atuLinha = gm.getLineGridSelected();
            int atuColuna = gm.getColumGridSelected();
            if(gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().size() > 0){
                gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().clear();
            }
            gm.setClickMouse(false);
            for(int i = 0; i < 1; i++){
                //printar para baixo
                if(atuLinha + (i + 1) < tamGrid){
                    if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] == null){
                        GraphicsContext gc = gm.getCanvasDisplay().get(((tamGrid * atuColuna) + (atuLinha + (i + 1)))).getGraphicsContext2D(); //CanvasDisplay.get((tamGrid * atuColuna) + (atuLinha + (i + 1))).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        
                        String posicao = (atuLinha + (i + 1)) + "-" + atuColuna; 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                    }
                }
                //para cima
                if(atuLinha - (i + 1) >= 0){
                    if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] == null){
                        GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * atuColuna) + (atuLinha - (i + 1))).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        
                        String posicao = (atuLinha - (i + 1)) + "-" + atuColuna; 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);                    
                    }
                }
                //para direita
                if(atuColuna + (i + 1) < tamGrid){
                    if(gm.getMatriz()[atuLinha][atuColuna + (i + 1)] == null){
                        GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna + (i + 1))) + atuLinha).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        
                        String posicao = atuLinha + "-" + (atuColuna + (i + 1)); 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);                    
                    }
                }
                //para esquerda
                if(atuColuna - (i + 1) >= 0){
                    if(gm.getMatriz()[atuLinha][atuColuna - (i + 1)] == null){
                        GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna - (i + 1))) + atuLinha).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        
                        String posicao = atuLinha  + "-" + (atuColuna - (i + 1)); 
                        
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);               
                    }
                }
            }
            
        }else if(gm.getPersonagemAnterior() != null && gm.getPersonagemAnterior().getPlayer() == players.get(rodada) && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] instanceof Tank)
        {
            //verifica se a posicao seleciona é disponivel para movimentação
            if(gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()].searchPosicaoMovimentacao(gm.getLineGridSelected(), gm.getColumGridSelected()) && !gm.isClickMouse()){
                gm.getMatriz()[gm.getLineGridSelected()][gm.getColumGridSelected()] = gm.getPersonagemAnterior();
                gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] = null;
                gm.setMoveu(true);
                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * gm.getColumGridSelected()) + gm.getLineGridSelected()).getGraphicsContext2D();
                if(rodada == 0)
                {
                Image imgTank = new Image(getClass().getClassLoader().getResourceAsStream("img/Tank.png"));
                gc.drawImage(imgTank, 0, 0,dimensaoCanva, dimensaoCanva);
                }
                else
                {
                Image imgTank = new Image(getClass().getClassLoader().getResourceAsStream("img/Tank2.png"));
                gc.drawImage(imgTank, 0, 0,dimensaoCanva, dimensaoCanva);
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
        this.setNome("Tank");
        this.setLife(300);
        this.setMana(100);
    }

    @Override
    public void habilitPadrao() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Ataque Normal");
        habilidade.setDescricao("O tank troca soco com o inimigo");
        habilidade.setCustoMana(0);
        habilidade.setDano(10);
        this.setHabilidades(habilidade);
    }
    
    public void ataqueComEscudo(){
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Ataque com Escudo");
        habilidade.setDescricao("O tank utiliza o escudo para atacar o inimigo ");
        habilidade.setCustoMana(20);
        habilidade.setDano(25);
        this.setHabilidades(habilidade);
    }
    
    public void arrancada(){
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Arrancada");
        habilidade.setDescricao("O tank corre em direção ao inimigo e o atropela");
        habilidade.setCustoMana(50);
        habilidade.setDano(30);
        this.setHabilidades(habilidade);
    }
    
    public void utilizarHabilidadeAtaqueComEscudo(GameMecanic gm, int tamGrid)
    {
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null)
        {
            if(gm.getPersonagemAtacante().getMana() >= 20)
            {
                if(!gm.isUsouHabilidade())
                {
                    int atuLinha = gm.getLineGridSelected();
                    int atuColuna = gm.getColumGridSelected();
                    boolean ataqueFeito = false;
                    boolean blockAtaqueBaixo  = false;
                    boolean blockAtaqueCima = false;
                    boolean blockAtaqueDireita = false;
                    boolean blockAtaqueEsquerda = false;
                    
                    for(int i = 0; i < 2; i++)
                    {
                        //PARA BAIXO 
                        if(atuLinha +  (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha + (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueBaixo)
                            {
                                if(i == 1) gm.getMatriz()[atuLinha + 1][atuColuna].setLife(gm.getMatriz()[atuLinha + 1][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha + (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha + (i + 1)][atuColuna].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                                if(i == 1) this.verifyMortePersonagem(gm, tamGrid, atuLinha + 1, atuColuna);
                            }
                        }
                        //PARA CIMA
                        if(atuLinha - (i + 1) >= 0)
                        {
                            if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha - (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueCima)
                            {
                                if(i == 1) gm.getMatriz()[atuLinha - 1][atuColuna].setLife(gm.getMatriz()[atuLinha - 1][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha - (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha - (i + 1)][atuColuna].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                                if(i == 1) this.verifyMortePersonagem(gm, tamGrid, atuLinha - 1, atuColuna);
                            }
                        }
                        //PARA DIREITA ->
                        if(atuColuna + (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna + (i + 1)] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueDireita)
                            {
                                if(i == 1) gm.getMatriz()[atuLinha][atuColuna + 1].setLife(gm.getMatriz()[atuLinha][atuColuna + 1].getLife() - 25);
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha][atuColuna + (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna + (i + 1)].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                                if(i == 1) this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna + 1);
                            }
                        }
                        //PARA ESQUERDA ->
                        if(atuColuna - (i + 1) >= 0)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna - (i + 1)] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueEsquerda)
                            {
                                if(i == 1) gm.getMatriz()[atuLinha][atuColuna - 1].setLife(gm.getMatriz()[atuLinha][atuColuna - 1].getLife() - 25);
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 25);
                                gm.getMatriz()[atuLinha][atuColuna - (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna - (i + 1)].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                                if(i == 1) this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna - 1);
                            }
                        }
                    }
                    if(ataqueFeito)
                    {
                        gm.setUsouHabilidade(true);
                    }
                    else
                    {
                        msgErroHeader = "Alcance da habilidade inválida";
                        msgErroContent = "Sua habilidade não alcança seus inimigos | Aproxime-se  para ataca-los";
                    }
                }
            }
            else
            {
                msgErroHeader = "Mana insuficiente";
                msgErroContent = "Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho";
            }
        }
        else
        {
            msgErroHeader = "Não há inimigos ai";
            msgErroContent = "O ar não é seu inimigo | Selecione uma peça inimiga para utilizar a habilidade";
        }
        if(msgErroHeader.trim().length() > 0 && msgErroContent.trim().length() > 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msgErroHeader);
            alert.setContentText(msgErroContent);
            alert.show();
        }
    }
    
    public void utilizarHabilidadeArrancada(GameMecanic gm, int tamGrid)
    {
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null)
        {
            if(gm.getPersonagemAtacante().getMana() >= 50)
            {
                if(!gm.isUsouHabilidade())
                {
                    int atuLinha = gm.getLineGridSelected();
                    int atuColuna = gm.getColumGridSelected();
                    boolean ataqueFeito = false;
                    boolean blockAtaqueBaixo  = false;
                    boolean blockAtaqueCima = false;
                    boolean blockAtaqueDireita = false;
                    boolean blockAtaqueEsquerda = false;
                    
                    for(int i = 0; i < 1; i++)
                    {
                        //PARA BAIXO 
                        if(atuLinha +  (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha + (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueBaixo)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 30);
                                if(atuColuna - 1 >= 0 && gm.getMatriz()[atuLinha][atuColuna - 1] != null && gm.getMatriz()[atuLinha][atuColuna - 1].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha][atuColuna - 1].setLife(gm.getMatriz()[atuLinha][atuColuna - 1].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna - 1);
                                }
                                if(atuColuna + 1 < tamGrid && gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha][atuColuna + 1].setLife(gm.getMatriz()[atuLinha][atuColuna + 1].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna + 1);
                                }
                                if(atuLinha - 1 >= 0 && gm.getMatriz()[atuLinha - 1][atuColuna] != null && gm.getMatriz()[atuLinha - 1][atuColuna].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha - 1][atuColuna].setLife(gm.getMatriz()[atuLinha - 1][atuColuna].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha -1, atuColuna);
                                    
                                }
                                gm.getMatriz()[atuLinha + (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha + (i + 1)][atuColuna].getMana() - 50);
                                ataqueFeito = true;
                                
                            }else if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != gm.getPersonagemAtacante()){
                                blockAtaqueBaixo = true;
                            }
                        }
                        //PARA CIMA
                        if(atuLinha - (i + 1) >= 0)
                        {
                            if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha - (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueCima)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 30);
                                if(atuColuna - 1 >= 0 && gm.getMatriz()[atuLinha][atuColuna - 1] != null && gm.getMatriz()[atuLinha][atuColuna - 1].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha][atuColuna - 1].setLife(gm.getMatriz()[atuLinha][atuColuna - 1].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna - 1);
                                }
                                if(atuColuna + 1 < tamGrid && gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha][atuColuna + 1].setLife(gm.getMatriz()[atuLinha][atuColuna + 1].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna + 1);
                                }
                                if(atuLinha + 1 < tamGrid && gm.getMatriz()[atuLinha + 1][atuColuna] != null && gm.getMatriz()[atuLinha + 1][atuColuna].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha + 1][atuColuna].setLife(gm.getMatriz()[atuLinha + 1][atuColuna].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha + 1, atuColuna);
                                }
                                gm.getMatriz()[atuLinha - (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha - (i + 1)][atuColuna].getMana() - 50);
                                ataqueFeito = true;
                            }else if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != gm.getPersonagemAtacante()){
                                blockAtaqueCima = true;
                            }
                        }
                        //PARA DIREITA ->
                        if(atuColuna + (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna + (i + 1)] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueDireita)
                            {
                                if(atuLinha - 1 >= 0 && gm.getMatriz()[atuLinha - 1][atuColuna] != null && gm.getMatriz()[atuLinha - 1][atuColuna].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha - 1][atuColuna].setLife(gm.getMatriz()[atuLinha - 1][atuColuna].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha - 1, atuColuna);
                                }
                                if(atuLinha + 1 < tamGrid && gm.getMatriz()[atuLinha + 1][atuColuna] != null && gm.getMatriz()[atuLinha + 1][atuColuna].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha + 1][atuColuna].setLife(gm.getMatriz()[atuLinha + 1][atuColuna].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha + 1, atuColuna);
                                }
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 30);
                                if(atuColuna - 1 >= 0 && gm.getMatriz()[atuLinha][atuColuna - 1] != null && gm.getMatriz()[atuLinha][atuColuna - 1].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha][atuColuna - 1].setLife(gm.getMatriz()[atuLinha][atuColuna - 1].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna - 1);
                                }
                                gm.getMatriz()[atuLinha][atuColuna + (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna + (i + 1)].getMana() - 50);
                                ataqueFeito = true;
                            }else if(gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != gm.getPersonagemAtacante()){
                                blockAtaqueDireita = true;
                            }
                        }
                        //PARA ESQUERDA ->
                        if(atuColuna - (i + 1) >= 0)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna - (i + 1)] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueEsquerda)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 30);
                                if(atuLinha - 1 >= 0 && gm.getMatriz()[atuLinha - 1][atuColuna] != null && gm.getMatriz()[atuLinha - 1][atuColuna].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha - 1][atuColuna].setLife(gm.getMatriz()[atuLinha - 1][atuColuna].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha - 1, atuColuna);
                                }
                                if(atuLinha + 1 < tamGrid && gm.getMatriz()[atuLinha + 1][atuColuna] != null && gm.getMatriz()[atuLinha + 1][atuColuna].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha + 1][atuColuna].setLife(gm.getMatriz()[atuLinha + 1][atuColuna].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha + 1, atuColuna);
                                }
                                if(atuColuna + 1 < tamGrid && gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1].getPlayer() != players.get(gm.getRodada()))
                                {
                                    gm.getMatriz()[atuLinha][atuColuna + 1].setLife(gm.getMatriz()[atuLinha][atuColuna + 1].getLife() - 30);
                                    this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna + 1);
                                }
                                gm.getMatriz()[atuLinha][atuColuna - (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna - (i + 1)].getMana() - 50);
                                ataqueFeito = true;
                            }else if(gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != gm.getPersonagemAtacante()){
                                blockAtaqueEsquerda = true;
                            }
                        }
                    }
                    if(ataqueFeito)
                    {
                        gm.setUsouHabilidade(true);
                    }
                    else
                    {
                        msgErroHeader = "Alcance da habilidade inválida";
                        msgErroContent = "Sua habilidade não alcança seus inimigos | Aproxime-se  para ataca-los";
                    }
                }
            }
            else
            {
                msgErroHeader = "Mana insuficiente";
                msgErroContent = "Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho";
            }
        }
        else
        {
            msgErroHeader = "Não há inimigos ai";
            msgErroContent = "O ar não é seu inimigo | Selecione uma peça inimiga para utilizar a habilidade";
        }
        if(msgErroHeader.trim().length() > 0 && msgErroContent.trim().length() > 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msgErroHeader);
            alert.setContentText(msgErroContent);
            alert.show();
        }
    }
}