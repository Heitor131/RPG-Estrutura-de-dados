package game;

import java.io.Serializable;

import entities.PersonagemJogador;
import utils.LinkedList;

public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idJogador;
    private String nome;
    private String senha;
    private int saldoMoedas;
    private LinkedList<PersonagemJogador> personagens;
    private PersonagemJogador personagemAtivo;
    
    public Jogador(int idJogador, String nome, String senha) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.senha = senha;
        this.saldoMoedas = 100;
        this.personagens = new LinkedList<>();
        this.personagemAtivo = null;
    }
    
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
    
    public void criarPersonagem(String nome) {
        int id = personagens.size() + 1;
        int nivel = 1;
        int vidaBase = 50 + (nivel * 10);
        int manaBase = 30 + (nivel * 5);
        PersonagemJogador novoPersonagem = new PersonagemJogador(id, nome, nivel, vidaBase, manaBase);
        personagens.add(novoPersonagem);
        
        if (personagemAtivo == null) {
            personagemAtivo = novoPersonagem;
        }
    }
    
    public PersonagemJogador selecionarPersonagem(int idPersonagem) {
        for (int i = 0; i < personagens.size(); i++) {
            PersonagemJogador p = personagens.get(i);
            if (p.getId() == idPersonagem) {
                personagemAtivo = p;
                return p;
            }
        }
        return null;
    }
    
    public PersonagemJogador getPersonagemAtivo() {
        return personagemAtivo;
    }
    
    public int getIdJogador() { return idJogador; }
    public String getNome() { return nome; }
    public int getSaldoMoedas() { return saldoMoedas; }
    public LinkedList<PersonagemJogador> getPersonagens() { return personagens; }
    public void setSaldoMoedas(int saldo) { this.saldoMoedas = saldo; }
}