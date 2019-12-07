package model;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import static model.XlProject.players;

/*Dano negativo refere-se a recuperação de life*/        

public class Healer extends Personagem{
    public Healer(Player player){
        this.descricaoPersonagem();
        this.habilitPadrao();
        this.curar();
        this.curaTodos();
        this.drenarVida();
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
        
        if(atuLinha != -1 && gm.getMatriz()[atuLinha][atuColuna]!= null && gm.isClickMouse() && gm.getPersonagemSelected() instanceof Healer && gm.getPersonagemSelected().getPlayer() == players.get(rodada) && gm.getLineGridSelected() != -1 && gm.getColumGridSelected() != -1 && !gm.isMoveu())
        {
            if(gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().size() > 0){
                gm.getMatriz()[atuLinha][atuColuna].getPosicoesMovimentacao().clear();
            }
            gm.setClickMouse(false);
            if(players.get(0) == gm.getMatriz()[atuLinha][atuColuna].getPlayer())
            {
                if(atuLinha + 1 < tamGrid)
                {
                    if(gm.getMatriz()[atuLinha+1][atuColuna] == null)
                    {
                        GraphicsContext gc = gm.getCanvasDisplay().get((atuLinha+1) + (tamGrid*atuColuna)).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        String posicao = (atuLinha + 1) + "-" + atuColuna; 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                    }
                }
            }
            else
            {
                if(atuLinha - 1 != 0)
                {
                    if(gm.getMatriz()[atuLinha-1][atuColuna] == null)
                    {
                        GraphicsContext gc = gm.getCanvasDisplay().get((atuLinha-1) + (tamGrid*atuColuna)).getGraphicsContext2D();
                        gc.setFill(Color.RED);
                        gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                        String posicao = (atuLinha - 1) + "-" + atuColuna; 
                        gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                    }
                }
            }
            for(int m = atuColuna - 1; m >= 0; m--)
            {
                if(gm.getMatriz()[atuLinha][m] == null)
                {
                    GraphicsContext gc = gm.getCanvasDisplay().get(atuLinha + (tamGrid*m)).getGraphicsContext2D();
                    gc.setFill(Color.RED);
                    gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                    String posicao = (atuLinha) + "-" + m; 
                    gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                } else break;
            }
            for(int m = atuColuna + 1; m <= tamGrid - 1; m++)
            {
                if(gm.getMatriz()[atuLinha][m] == null)
                {
                    GraphicsContext gc = gm.getCanvasDisplay().get(atuLinha + (tamGrid*m)).getGraphicsContext2D();
                    gc.setFill(Color.RED);
                    gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
                    String posicao = (atuLinha) + "-" + m; 
                    gm.getMatriz()[atuLinha][atuColuna].insertPosicoesMovimentacao(posicao);
                } else break;
            }
        }
        else if(gm.getPersonagemAnterior() != null && gm.getPersonagemAnterior().getPlayer() == players.get(rodada) && gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] instanceof Healer)
        {
            if(gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()].searchPosicaoMovimentacao(gm.getLineGridSelected(), gm.getColumGridSelected()) && !gm.isClickMouse()){
                gm.getMatriz()[gm.getLineGridSelected()][gm.getColumGridSelected()] = gm.getPersonagemAnterior();
                gm.getMatriz()[gm.getLineGridAnterior()][gm.getColumGridAnterior()] = null;
                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * gm.getColumGridSelected()) + gm.getLineGridSelected()).getGraphicsContext2D();
                if(rodada == 0)
                {
                Image imgHealer = new Image(getClass().getClassLoader().getResourceAsStream("img/Healer.png"));
                gc.drawImage(imgHealer, 0, 0,dimensaoCanva, dimensaoCanva);
                }
                else
                {
                Image imgHealer = new Image(getClass().getClassLoader().getResourceAsStream("img/Healer2.png"));
                gc.drawImage(imgHealer, 0, 0,dimensaoCanva, dimensaoCanva);
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
        this.setNome("Healer");
        this.setLife(100);
        this.setMana(200);
    }

    @Override
    public void habilitPadrao() {
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Ataque Normal");
        habilidade.setDescricao("O healer troca soco com o inimigo");
        habilidade.setCustoMana(0);
        habilidade.setDano(10);
        this.setHabilidades(habilidade);
    }
    
    public void drenarVida(){
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Drenar Vida");
        habilidade.setDescricao("O healer drena o life do personagem inimigo");
        habilidade.setCustoMana(50);
        habilidade.setDano(20);
        this.setHabilidades(habilidade);
    }
    
    public void curar(){
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Curar");
        habilidade.setDescricao("O healer utiliza magia para currar um aliado");
        habilidade.setCustoMana(15);
        habilidade.setDano(-15);
        this.setHabilidades(habilidade);    
    }
    
    public void curaTodos(){
        Habilidade habilidade = new Habilidade();
        habilidade.setNome("Curar Todos");
        habilidade.setDescricao("O healer utiliza magia para curar todas as peças aliadas");
        habilidade.setCustoMana(100);
        habilidade.setDano(-10);
        this.setHabilidades(habilidade);
    }
     
    private void aplicarHabilidadeCurar(GameMecanic gm){
        int vidaRecuperada = 15;
        if(gm.getPersonagemSelected() instanceof Tank)
        {
            if(gm.getPersonagemSelected().getLife() + 15 > 300)
            {
                vidaRecuperada = 300 - gm.getPersonagemSelected().getLife();
            }
        }
        if(gm.getPersonagemSelected() instanceof Guerreiro)
        {
            if(gm.getPersonagemSelected().getLife() + 15 > 200)
            {
                vidaRecuperada = 200 - gm.getPersonagemSelected().getLife();
            }
        }
        if(gm.getPersonagemSelected() instanceof Assasino)
        {
            if(gm.getPersonagemSelected().getLife() + 15 > 150)
            {
                vidaRecuperada = 150 - gm.getPersonagemSelected().getLife();
            }
        }
        if(gm.getPersonagemSelected() instanceof Healer)
        {
            if(gm.getPersonagemSelected().getLife() + 15 > 100)
            {
                vidaRecuperada = 100 - gm.getPersonagemSelected().getLife();
            }
        }

        gm.getPersonagemSelected().setLife(gm.getPersonagemSelected().getLife() + vidaRecuperada);
        gm.getPersonagemAtacante().setMana(gm.getPersonagemAtacante().getMana() - 15);
        gm.setUsouHabilidade(true);
    }
    
    public void utilizarHabilitCurar(GameMecanic gm, int tamGrid) {
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null){
            if(gm.getPersonagemAtacante().getMana() >= 15){
                if(!gm.isUsouHabilidade()){
                    int atuLinha = gm.getLineGridSelected();
                    int atuColuna = gm.getColumGridSelected();
                    boolean ataqueFeito = false;
                    if(atuLinha +  1 < tamGrid)
                    {
                        if(gm.getMatriz()[atuLinha + 1][atuColuna] != null && gm.getMatriz()[atuLinha + 1][atuColuna] == gm.getPersonagemAtacante())
                        {
                            aplicarHabilidadeCurar(gm);
                            ataqueFeito = true;
                        }
                    }
                    if(atuLinha - 1 >= 0)
                    {
                        if(gm.getMatriz()[atuLinha - 1][atuColuna] != null && gm.getMatriz()[atuLinha - 1][atuColuna] == gm.getPersonagemAtacante() && ataqueFeito == false)
                        {
                            aplicarHabilidadeCurar(gm);
                            ataqueFeito = true;
                        }
                    }
                    if(atuColuna + 1 < tamGrid)
                    {
                        if(gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1] == gm.getPersonagemAtacante() && ataqueFeito == false)
                        {
                            aplicarHabilidadeCurar(gm);
                            ataqueFeito = true;
                        }
                    }
                    if(atuColuna - 1 >= 0)
                    {
                        if(gm.getMatriz()[atuLinha][atuColuna -  1] != null && gm.getMatriz()[atuLinha][atuColuna - 1] == gm.getPersonagemAtacante() && ataqueFeito == false)
                        {
                            aplicarHabilidadeCurar(gm);
                            ataqueFeito = true;
                        }
                    }
                    if(ataqueFeito){
                        gm.setUsouHabilidade(true);                
                    }else{
                        msgErroHeader = "Alcance da habilidade inválida";
                        msgErroContent = "Sua habilidade não alcança seus alidados | Aproxime-se  para ajuda-los";
                    }
                }                
            }else{
                msgErroHeader = "Mana insuficiente";
                msgErroContent = "Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho";
            }

        }else{
            msgErroHeader = "Não há aliados ai";
            msgErroContent = "Seus aliados precisam de você | Selecione uma peça aliada para utilizar a habilidade";
        }
        if(msgErroHeader.trim().length() > 0 && msgErroContent.trim().length() > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msgErroHeader);
            alert.setContentText(msgErroContent);
            alert.show();
        }
    }
    
    public void utilizarHabilitCurarTodos(GameMecanic gm, int tamGrid){
        if(gm.getPersonagemAtacante().getMana() > 15){
            if(!gm.isUsouHabilidade()){
                int rodada = gm.getRodada();
                int vidaRecuperada;
                for(int i = 0; i < tamGrid*2; i++)
                {
                    vidaRecuperada = 10;
                    if(players.get(rodada).getPersonagens().get(i) instanceof Healer)
                    {
                        if(players.get(rodada).getPersonagens().get(i).getLife() + 10 > 100)
                        {
                            vidaRecuperada = 100 - players.get(rodada).getPersonagens().get(i).getLife();
                        }
                    }
                    if(players.get(rodada).getPersonagens().get(i) instanceof Tank)
                    {
                        if(players.get(rodada).getPersonagens().get(i).getLife() + 10 > 300)
                        {
                            vidaRecuperada = 300 - players.get(rodada).getPersonagens().get(i).getLife();
                        }
                    }
                    if(players.get(rodada).getPersonagens().get(i) instanceof Guerreiro)
                    {
                        if(players.get(rodada).getPersonagens().get(i).getLife() + 10 > 200)
                        {
                            vidaRecuperada = 200 - players.get(rodada).getPersonagens().get(i).getLife();
                        }
                    }
                    if(players.get(rodada).getPersonagens().get(i) instanceof Assasino)
                    {
                        if(players.get(rodada).getPersonagens().get(i).getLife() + 10 > 150)
                        {
                            vidaRecuperada = 150 - players.get(rodada).getPersonagens().get(i).getLife();
                        }
                    }

                    players.get(rodada).getPersonagens().get(i).setLife(players.get(rodada).getPersonagens().get(i).getLife() + vidaRecuperada);
                }
                gm.getPersonagemAtacante().setMana(gm.getPersonagemAtacante().getMana() - 100);
                gm.setUsouHabilidade(true);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Mana insuficiente");
            alert.setContentText("Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho");
            alert.show();
        }
    }
    
    public void utilizarHabilitDrenarVida(GameMecanic gm, int tamGrid){
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null){
            if(gm.getPersonagemAtacante().getMana() >= 50){
                if(!gm.isUsouHabilidade()){
                    int atuLinha = gm.getLineGridSelected();
                    int atuColuna = gm.getColumGridSelected();
                    boolean ataqueFeito = false;
                    boolean blockAtaqueBaixo  = false;
                    boolean blockAtaqueCima = false;
                    boolean blockAtaqueDireita = false;
                    boolean blockAtaqueEsquerda = false;

                    for(int i = 0; i < 2; i++){
                        //PARA BAIXO 
                        if(atuLinha +  (i + 1) < tamGrid)
                        {
                            if(gm.getMatriz()[atuLinha + (i + 1)][atuColuna] != null && gm.getMatriz()[atuLinha + (i + 1)][atuColuna] == gm.getPersonagemAtacante() && !ataqueFeito && !blockAtaqueBaixo)
                            {
                                gm.getMatriz()[atuLinha][atuColuna].setLife(gm.getMatriz()[atuLinha][atuColuna].getLife() - 20);
                                gm.getMatriz()[atuLinha + (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha + (i + 1)][atuColuna].getMana() - 50);
                                gm.getMatriz()[atuLinha + (i + 1)][atuColuna].setLife(gm.getMatriz()[atuLinha + (i + 1)][atuColuna].getLife() + 20);
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
                                gm.getMatriz()[atuLinha - (i + 1)][atuColuna].setMana(gm.getMatriz()[atuLinha - (i + 1)][atuColuna].getMana() - 50);
                                gm.getMatriz()[atuLinha - (i + 1)][atuColuna].setLife(gm.getMatriz()[atuLinha - (i + 1)][atuColuna].getLife() + 20);
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
                                gm.getMatriz()[atuLinha][atuColuna + (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna + (i + 1)].getMana() - 50);
                                gm.getMatriz()[atuLinha][atuColuna + (i + 1)].setLife(gm.getMatriz()[atuLinha][atuColuna + (i + 1)].getLife() + 20);
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
                                gm.getMatriz()[atuLinha][atuColuna - (i + 1)].setMana(gm.getMatriz()[atuLinha][atuColuna - (i + 1)].getMana() - 50);
                                gm.getMatriz()[atuLinha][atuColuna - (i + 1)].setLife(gm.getMatriz()[atuLinha][atuColuna - (i + 1)].getLife() + 20);
                                ataqueFeito = true;
                                this.verifyMortePersonagem(gm, tamGrid, atuLinha, atuColuna);
                            }else if(gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != null && gm.getMatriz()[atuLinha][atuColuna - (i + 1)] != gm.getPersonagemAtacante()){
                                blockAtaqueEsquerda = true;
                            }
                        }
                    }
                    
                    if(ataqueFeito ){
                        gm.setUsouHabilidade(true);
                    }else{
                        msgErroHeader = "Alcance da habilidade inválida";
                        msgErroContent = "Sua habilidade não alcança seus inimigos | Aproxime-se  para ataca-los";
                    }
                }
            }else{
                msgErroHeader = "Mana insuficiente";
                msgErroContent = "Você não possui mana para utilizar essa habilidade | Suprimentos estão a caminho";
            }
        }else{
            msgErroHeader = "Não há inimigos ai";
            msgErroContent = "Seus aliados precisam de você | Selecione uma peça alida para utilizar a habilidade";
        }
        if(msgErroHeader.trim().length() > 0 && msgErroContent.trim().length() > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(msgErroHeader);
            alert.setContentText(msgErroContent);
            alert.show();
        }
    }
}