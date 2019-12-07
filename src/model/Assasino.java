package model;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import static model.XlProject.players;

public class Assasino extends Personagem{
    public Assasino(Player player){
       this.descricaoPersonagem();
       this.habilitPadrao();
       this.adagaMagica();
       this.corteMagico();
       this.setPlayer(player);
       this.setPosicoesMovimentacao(new ArrayList<>());
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
        
        //1ª clique [Selecionar uma peça]
        if(atuLinha != -1 && gm.isClickMouse() && gm.getPersonagemSelected() instanceof Assasino && gm.getPersonagemSelected().getPlayer() == players.get(rodada) && !gm.isMoveu()){
           
            //Esvaziar o array de posições
            if(gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().size() > 0) gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().clear();
            //Acao clicar ocorreu
            gm.setClickMouse(false);
            boolean lockCaminho1 = false, lockCaminho2 = false;
            
            for(int i = 0; i < 2; i++){
                //Subindo diagonalmente para a esquerda 
                if(!lockCaminho1 && (atuColuna - (i + 1)) >= 0){
                    if(rodada == 1){
                        if((atuLinha - (i + 1)) >= 0){
                            if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna - (i + 1)] == null){
                                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna - (i + 1))) + (atuLinha - (i + 1))).getGraphicsContext2D();
                                gc.setFill(Color.RED);
                                gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                                
                                String posicao = (atuLinha - (i + 1)) + "-" + (atuColuna - (i + 1)); 
                                gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                            }else  lockCaminho1 = true;
                        }
                    }else{
                        if((atuLinha + (i + 1)) < tamGrid){
                            if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna - (i + 1)] == null){
                                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna - (i + 1))) + (atuLinha + (i + 1))).getGraphicsContext2D();
                                gc.setFill(Color.RED);
                                gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);

                                String posicao = ((atuLinha + (i + 1)) + "-" + (atuColuna - (i + 1))); 
                                gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                            }else  lockCaminho1 = true;
                        }                            
                    }
                }
                //Subindo diagonalmente para a direita
                if(!lockCaminho2 && (atuColuna + (i + 1)) < tamGrid){
                    if(rodada == 1){
                        if((atuLinha - (i + 1)) >= 0){
                            if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna + (i + 1)] == null){
                                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna + (i + 1))) + (atuLinha - (i + 1))).getGraphicsContext2D();
                                gc.setFill(Color.RED);
                                gc.fillRect(0, 0, gm.getCanvasDisplay().get(dimensaoCanva).getWidth(), gm.getCanvasDisplay().get(dimensaoCanva).getHeight());                                
                                
                                //System.out.print("[->]DIAGONA PARA A DIREITA:  ");
                                //System.out.println((atuLinha - (i + 1)) + "-" + (atuColuna + (i + 1)));
                                
                                String posicao = ((atuLinha - (i + 1)) + "-" + (atuColuna + (i + 1))); 
                                gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                            }else lockCaminho2 = true;
                        }   
                    }else{
                        if((atuLinha + (i + 1)) < tamGrid){
                            if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna + (i + 1)] == null){
                                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * (atuColuna + (i + 1))) + (atuLinha + (i + 1))).getGraphicsContext2D();
                                gc.setFill(Color.RED);
                                gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);                                
                                
                                String posicao = ((atuLinha + (i + 1)) + "-" + (atuColuna + (i + 1))); 
                                gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                            }else lockCaminho2 = true;
                        }
                    }
                }
            }
        }else if(gm.getPersonagemAnterior() != null && gm.getPersonagemAnterior().getPlayer() == players.get(rodada) && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] instanceof Assasino)
        {
            //verifica se a posicao seleciona é disponivel para movimentação
            if(gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()].searchPosicaoMovimentacao(gm.getLineGridSelected(), gm.getColumGridSelected()) && !gm.isClickMouse()){
                gm.getMatriz()[gm.getLineGridSelected()][gm.getColumGridSelected()] = gm.getPersonagemAnterior();
                gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] = null;
                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * gm.getColumGridSelected()) + gm.getLineGridSelected()).getGraphicsContext2D();
                gm.setMoveu(true);
                if(rodada == 0)
                {
                Image assasino = new Image(getClass().getClassLoader().getResourceAsStream("img/Assassin.png"));
                gc.drawImage(assasino, 0, 0,dimensaoCanva, dimensaoCanva);
                }
                else
                {
                Image assasino = new Image(getClass().getClassLoader().getResourceAsStream("img/Assassin2.png"));
                gc.drawImage(assasino, 0, 0,dimensaoCanva, dimensaoCanva); 
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
        this.setNome("Assassino");
        this.setLife(150);
        this.setMana(100);
    }
    
    @Override
    public void habilitPadrao() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Ataque Normal");
        habilidade.setDescricao("O assasino troca soco com o inimigo");
        habilidade.setCustoMana(0);
        habilidade.setDano(15);
        this.setHabilidades(habilidade);
    }
    
    public void adagaMagica() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Adaga Mágica");
        habilidade.setDescricao("O assasino lança uma adaga imbuida de magia no inimigo");
        habilidade.setCustoMana(20);
        habilidade.setDano(20);
        this.setHabilidades(habilidade);  
    }

    public void corteMagico() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Corte Rápido");
        habilidade.setDescricao("O assasino utiliza magia de fortalecimento do corpo, e utiliza a espada para atacar");
        habilidade.setCustoMana(50);
        habilidade.setDano(30);
        this.setHabilidades(habilidade);
    }
    
    public void utilizarHabilitAdagaMagica(GameMecanic gm, int tamGrid)
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
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 20);
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
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 20);
                                gm.getMatriz()[atuLinha - (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha - (i + 1)][atuColuna].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }else if(gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha - (i + 1)][atuColuna] != gm.getPersonagemAtacante()){
                                blockAtaqueCima = true;
                            }
                        }
                        //PARA DIREITA ->
                        if(atuColuna + (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna + (i + 1)] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueDireita)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 20);
                                gm.getMatriz()[atuLinha][atuColuna + (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna + (i + 1)].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }else if(gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna + (i + 1)] != gm.getPersonagemAtacante()){
                                blockAtaqueDireita = true;
                            }
                        }
                        //PARA ESQUERDA ->
                        if(atuColuna - (i + 1) >= 0)
                        {
                            if(gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna - (i + 1)] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueEsquerda)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 20);
                                gm.getMatriz()[atuLinha][atuColuna - (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna - (i + 1)].getMana() - 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
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
    
    public void utilizarHabilitCorteMagico(GameMecanic gm, int tamGrid)
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
