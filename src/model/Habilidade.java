
package model;

public class Habilidade {
    private String nome;
    private String descricao;
    private int dano;
    private int custoMana;
    private int limiteUso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getCustoMana() {
        return custoMana;
    }

    public void setCustoMana(int custoMana) {
        this.custoMana = custoMana;
    }

    public int getLimiteUso() {
        return limiteUso;
    }

    public void setLimiteUso(int limiteUso) {
        this.limiteUso = limiteUso;
    }
    
    
}
