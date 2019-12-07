package model;

import java.util.ArrayList;

public class Player {
    private String nome;
    private String nick;
    private ArrayList<Personagem> Personagens = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public ArrayList<Personagem> getPersonagens() {
        return Personagens;
    }

    public void setPersonagens(Personagem personagem) {
        this.Personagens.add(personagem);
    }
    
    
    
}
