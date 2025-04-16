package entities;

import java.io.Serializable;

public abstract class Entidade implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String nome;
    protected int nivel;
    protected int vidaMaxima;
    protected int vidaAtual;
    protected int manaMaxima;
    protected int manaAtual;
    
    public Entidade(int id, String nome, int nivel, int vidaMaxima, int manaMaxima) {
        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.manaMaxima = manaMaxima;
        this.manaAtual = manaMaxima;
    }
    
    public void receberDano(int valor) {
        vidaAtual = Math.max(vidaAtual - valor, 0);
    }
    
    public void curar(int valor) {
        vidaAtual = Math.min(vidaAtual + valor, vidaMaxima);
    }
    
    public boolean estaVivo() {
        return vidaAtual > 0;
    }
    
    public void subirNivel() {
        nivel++;
        vidaMaxima += 10;
        manaMaxima += 5;
        vidaAtual = vidaMaxima;
        manaAtual = manaMaxima;
    }

    public void curarCompletamente() {
        this.vidaAtual = this.vidaMaxima;
        this.manaAtual = this.manaMaxima;
    }
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getNivel() { return nivel; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getVidaAtual() { return vidaAtual; }
    public int getManaMaxima() { return manaMaxima; }
    public int getManaAtual() { return manaAtual; }
    
    public void setManaAtual(int manaAtual) {
        this.manaAtual = Math.min(manaAtual, manaMaxima);
    }
    
    @Override
    public String toString() {
        return nome + " (Nível " + nivel + ") - Vida: " + vidaAtual + "/" + vidaMaxima + 
            " Mana: " + manaAtual + "/" + manaMaxima;
    }
}