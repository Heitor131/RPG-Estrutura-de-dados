package entities;

public class Monstro extends Entidade {
    private String tipo;
    
    public Monstro(int id, String nome, int nivel, int vidaMaxima, int manaMaxima, String tipo) {
        super(id, nome, nivel, vidaMaxima, manaMaxima);
        this.tipo = tipo;
    }
    
    public void atacar(Entidade alvo) {
        int dano = getNivel() * 2;
        alvo.receberDano(dano);
        System.out.println(getNome() + " atacou " + alvo.getNome() + " causando " + dano + " de dano!");
    }
    
    public String getTipo() { return tipo; }
}