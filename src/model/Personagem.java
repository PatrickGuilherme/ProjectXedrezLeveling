
package model;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import static model.XlProject.players;

public abstract class Personagem {
    private String nome;
    private int life;
    private int mana;
    private int tipoMovimento;
    private Player player;
    private ArrayList<Habilidade> habilidades = new ArrayList<>();
    private ArrayList<String> posicoesMovimentacao;
        
    public abstract void descricaoPersonagem();
    
    public abstract void movimentacao(GameMecanic gm);
    
    public abstract void habilitPadrao();

    public ArrayList<Habilidade> getHabilidades() {
        return habilidades;
    }
    
    public void setHabilidades(Habilidade habilidade) {
        this.habilidades.add(habilidade);
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(int tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }
    
    public void insertPosicoesMovimentacao(String posicao){
        this.posicoesMovimentacao.add(posicao);
    }
    
    public void setPosicoesMovimentacao(ArrayList<String> als){
        this.posicoesMovimentacao = als;
    }
    
    public ArrayList<String> getPosicoesMovimentacao(){
        return this.posicoesMovimentacao;
    }
    
    public boolean searchPosicaoMovimentacao(int buscaline, int buscaColum){
        if(posicoesMovimentacao != null){
            int linha, coluna;
            String posicaoLC[];
            for(String posicao: posicoesMovimentacao){
                posicaoLC = new String[2]; 
                posicaoLC = posicao.split("-");
                linha = Integer.parseInt(posicaoLC[0]);
                coluna = Integer.parseInt(posicaoLC[1]);
                if(linha == buscaline && coluna == buscaColum){
                    return true;
                }
            }    
        }
        return false;
    }
    public void utilizarHabilitPadrao(GameMecanic gm, int tamGrid){
        String msgErroHeader = "";
        String msgErroContent = "";
        
        if(gm.getPersonagemSelected() != null){
            if(!gm.isUsouHabilidade()){
                int atuLinha = gm.getLineGridSelected();
                int atuColuna = gm.getColumGridSelected();
                boolean ataqueFeito = false;
                if(atuLinha +  1 < tamGrid)
                {
                    if(gm.getMatriz()[atuLinha + 1][atuColuna] != null && gm.getMatriz()[atuLinha + 1][atuColuna] == gm.getPersonagemAtacante())
                    {
                        aplicarHabilidadePadrao(gm);
                        ataqueFeito = true;
                        this.verifyMortePersonagem(gm,tamGrid,atuLinha,atuColuna);
//                        System.out.println("MORTO: "+gm.getMatriz()[atuLinha][atuColuna].getNome());
                    }
                }
                if(atuLinha - 1 >= 0)
                {
                    if(gm.getMatriz()[atuLinha - 1][atuColuna] != null && gm.getMatriz()[atuLinha - 1][atuColuna] == gm.getPersonagemAtacante() && ataqueFeito == false)
                    {
                        aplicarHabilidadePadrao(gm);
                        ataqueFeito = true;
                        this.verifyMortePersonagem(gm,tamGrid,atuLinha,atuColuna);
//                        System.out.println("MORTO: "+gm.getMatriz()[atuLinha][atuColuna].getNome());
                    }
                }
                if(atuColuna + 1 < tamGrid)
                {
                    if(gm.getMatriz()[atuLinha][atuColuna + 1] != null && gm.getMatriz()[atuLinha][atuColuna + 1] == gm.getPersonagemAtacante() && ataqueFeito == false)
                    {
                        aplicarHabilidadePadrao(gm);
                        ataqueFeito = true;
                        this.verifyMortePersonagem(gm,tamGrid,atuLinha,atuColuna);
//                        System.out.println("MORTO: "+gm.getMatriz()[atuLinha][atuColuna].getNome());
                    }
                }
                if(atuColuna - 1 >= 0)
                {
                    if(gm.getMatriz()[atuLinha][atuColuna -  1] != null && gm.getMatriz()[atuLinha][atuColuna - 1] == gm.getPersonagemAtacante() && ataqueFeito == false)
                    {
                        aplicarHabilidadePadrao(gm);
                        ataqueFeito = true;
                        this.verifyMortePersonagem(gm,tamGrid,atuLinha,atuColuna);
//                        System.out.println("MORTO: "+gm.getMatriz()[atuLinha][atuColuna].getNome());
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
    private void aplicarHabilidadePadrao(GameMecanic gm){
        int rodada = gm.getRodada();
        
        if(gm.getPersonagemAtacante()instanceof Tank && gm.getPersonagemAtacante().getPlayer() == players.get(rodada) && gm.getPersonagemSelected() != null)
        {
            gm.getPersonagemSelected().setLife(gm.getPersonagemSelected().getLife() - 10);//MUDAR PRA TIRAR -10 DE LIFE
        }
        if(gm.getPersonagemAtacante()instanceof Guerreiro && gm.getPersonagemAtacante().getPlayer() == players.get(rodada) && gm.getPersonagemSelected() != null)
        {
            gm.getPersonagemSelected().setLife(gm.getPersonagemSelected().getLife() - 20);//MUDAR PRA TIRAR -20 DE LIFE
        }
        if(gm.getPersonagemAtacante() instanceof Assasino && gm.getPersonagemAtacante().getPlayer() == players.get(rodada) && gm.getPersonagemSelected() != null)
        {
            gm.getPersonagemSelected().setLife(gm.getPersonagemSelected().getLife() - 15);//MUDAR PRA TIRAR -15 DE LIFE
        }
        if(gm.getPersonagemAtacante() instanceof Healer && gm.getPersonagemAtacante().getPlayer() == players.get(rodada) && gm.getPersonagemSelected() != null)
        {
            gm.getPersonagemSelected().setLife(gm.getPersonagemSelected().getLife() - 10);//MUDAR PRA TIRAR -10 DE LIFE
        }
    }
    
    public void verifyMortePersonagem(GameMecanic gm, int tamGrid, int linha, int coluna){
        int dimensaoCanva = (int) gm.getCanvasDisplay().get(1).getWidth();
        if(gm.getMatriz()[linha][coluna] != null){
            if(gm.getMatriz()[linha][coluna].getLife() <= 0){
                gm.getMatriz()[linha][coluna] = null;
                GraphicsContext gc = gm.getCanvasDisplay().get((tamGrid * coluna) + linha).getGraphicsContext2D();
                if(gm.getMatrizGridPadrao()[linha][coluna] == 1){
                    gc.setFill(Color.valueOf("#0A4167"));
                }else{
                    gc.setFill(Color.valueOf("#fefefe"));
                }
                gc.fillRect(0, 0, dimensaoCanva, dimensaoCanva);
            }
        }
    }
}
