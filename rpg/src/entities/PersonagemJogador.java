package entities;

import java.io.Serializable;

import utils.LinkedList;

public class PersonagemJogador extends Entidade implements Serializable {
    private static final long serialVersionUID = 1L;
    private LinkedList<Habilidade> habilidades;
    private int experiencia;
    private int proximoNivel;
    
    public PersonagemJogador(int id, String nome, int nivel, int vidaMaxima, int manaMaxima) {
        super(id, nome, nivel, vidaMaxima, manaMaxima);
        this.habilidades = new LinkedList<>();
        this.experiencia = 0;
        this.proximoNivel = nivel * 100;
        inicializarHabilidades();
    }
    
    private void inicializarHabilidades() {
        habilidades.add(new Habilidade(1, "Ataque Básico", 0, 5 + getNivel(), "Dano físico"));
        habilidades.add(new Habilidade(2, "Bola de Fogo", 10, 15 + getNivel() * 2, "Dano mágico"));
    }
    
    public void ganharExperiencia(int xp) {
        experiencia += xp;
        while (experiencia >= proximoNivel) {
            subirNivel();
            experiencia -= proximoNivel;
            proximoNivel = getNivel() * 100;
            System.out.println(getNome() + " subiu para o nível " + getNivel() + "!");
        }
    }
    
    public void aprenderHabilidade(Habilidade habilidade) {
        habilidades.add(habilidade);
    }
    
    public void usarHabilidade(int idHabilidade, Entidade alvo) {
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidade h = habilidades.get(i);
            if (h.getId() == idHabilidade) {
                h.usar(this, alvo);
                return;
            }
        }
        System.out.println("Habilidade não encontrada!");
    }
    
    public void listarHabilidades() {
        System.out.println("Habilidades de " + getNome() + ":");
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidade h = habilidades.get(i);
            System.out.println(h.getId() + ": " + h.getNome() + " (Dano: " + h.getDano() + 
                             ", Custo: " + h.getCustoMana() + ")");
        }
    }
    
    public int getExperiencia() { return experiencia; }
    public int getProximoNivel() { return proximoNivel; }
    public LinkedList<Habilidade> getHabilidades() { return habilidades; }
}