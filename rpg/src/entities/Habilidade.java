package entities;

import java.io.Serializable;

public class Habilidade implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private int custoMana;
    private int dano;
    private String efeito;
    
    public Habilidade(int id, String nome, int custoMana, int dano, String efeito) {
        this.id = id;
        this.nome = nome;
        this.custoMana = custoMana;
        this.dano = dano;
        this.efeito = efeito;
    }
    
    public void usar(Entidade usuario, Entidade alvo) {
        if (usuario.getManaAtual() >= custoMana) {
            usuario.setManaAtual(usuario.getManaAtual() - custoMana);
            alvo.receberDano(dano);
            System.out.println(usuario.getNome() + " usou " + nome + " em " + alvo.getNome() + 
                             " causando " + dano + " de dano!");
        } else {
            System.out.println(usuario.getNome() + " não tem mana suficiente para " + nome);
        }
    }
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getCustoMana() { return custoMana; }
    public int getDano() { return dano; }
    public String getEfeito() { return efeito; }
}