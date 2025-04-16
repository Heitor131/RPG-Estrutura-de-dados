package game;

import java.io.Serializable;
import java.util.Scanner;

import entities.Entidade;
import entities.Monstro;
import entities.PersonagemJogador;
import utils.LinkedList;
import utils.Queue;
import utils.Stack;

public class Arena implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idBatalha;
    private LinkedList<Entidade> participantes;
    private Queue<Entidade> ordemTurnos;
    private Stack<Entidade> eliminados;
    private int turnoAtual;
    private String estadoBatalha;
    
    public Arena(int idBatalha) {
        this.idBatalha = idBatalha;
        this.participantes = new LinkedList<>();
        this.ordemTurnos = new Queue<>();
        this.eliminados = new Stack<>();
        this.turnoAtual = 0;
        this.estadoBatalha = "Não Iniciada";
    }
    
    public void adicionarParticipante(Entidade entidade) {
        participantes.add(entidade);
    }
    
    public void iniciarBatalha() {
        this.ordemTurnos = new Queue<>();  // Nova fila de turnos
        this.eliminados = new Stack<>();   // Nova pilha de eliminados
        this.turnoAtual = 1;
        
        for (int i = 0; i < participantes.size(); i++) {
            Entidade e = participantes.get(i);
            e.curarCompletamente();  // Garante que todos começam com vida/max
            if (e.estaVivo()) {
                ordemTurnos.enqueue(e);
            }
        }
        
        this.estadoBatalha = "Em Andamento";
        System.out.println("Batalha iniciada com " + ordemTurnos.size() + " participantes!");
    }
    
    public void iniciarBatalhaPvP(Entidade jogador1, Entidade jogador2) {
        participantes.add(jogador1);
        participantes.add(jogador2);
        iniciarBatalha();
    }
    
    public void executarTurno(Scanner scanner) {
        if (ordemTurnos.isEmpty()) {
            System.out.println("Nenhum participante na fila de turnos!");
            return;
        }
        
        System.out.println("\n=== Turno " + turnoAtual + " ===");
        
        Entidade atuante = ordemTurnos.dequeue();
        if (!atuante.estaVivo()) {
            System.out.println(atuante.getNome() + " está morto e não pode agir.");
            return;
        }
        
        System.out.println("Vez de " + atuante.getNome());
        
        Entidade alvo = null;
        
        if (atuante instanceof PersonagemJogador) {
            alvo = turnoJogador((PersonagemJogador) atuante, scanner);
        } else if (atuante instanceof Monstro) {
            alvo = turnoMonstro((Monstro) atuante);
        }
        
        processarResultadoTurno(atuante, alvo);
        turnoAtual++;
    }
    
    private Entidade turnoJogador(PersonagemJogador jogador, Scanner scanner) {
        System.out.println("\n=== Menu de Ações ===");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade");
        System.out.println("3. Defender");
        System.out.print("Escolha uma ação: ");
        
        int escolha = scanner.nextInt();
        scanner.nextLine();
        
        Entidade alvo = selecionarAlvo(scanner, jogador);
        if (alvo == null) return null;
        
        switch (escolha) {
            case 1:
                int dano = jogador.getNivel() * 3;
                alvo.receberDano(dano);
                System.out.println(jogador.getNome() + " atacou " + alvo.getNome() + 
                                 " causando " + dano + " de dano!");
                break;
            case 2:
                jogador.listarHabilidades();
                System.out.print("Escolha uma habilidade: ");
                int idHabilidade = scanner.nextInt();
                scanner.nextLine();
                jogador.usarHabilidade(idHabilidade, alvo);
                break;
            case 3:
                jogador.curar(jogador.getNivel() * 2);
                System.out.println(jogador.getNome() + " se defendeu e recuperou um pouco de vida!");
                break;
            default:
                System.out.println("Ação inválida! Perdeu a vez.");
        }
        return alvo;
    }
    
    private Entidade turnoMonstro(Monstro monstro) {
        Entidade alvo = encontrarAlvo(monstro);
        if (alvo != null) {
            monstro.atacar(alvo);
        }
        return alvo;
    }
    
    private void processarResultadoTurno(Entidade atuante, Entidade alvo) {
        if (alvo != null && !alvo.estaVivo()) {
            System.out.println(alvo.getNome() + " foi derrotado!");
            eliminados.push(alvo);
        }
        
        if (atuante.estaVivo()) {
            ordemTurnos.enqueue(atuante);
        } else {
            System.out.println(atuante.getNome() + " foi derrotado e não voltará para a fila!");
            eliminados.push(atuante);
        }
        
        if (verificarVencedor()) {
            estadoBatalha = "Finalizada";
            System.out.println("Batalha finalizada!");
            exibirRankingFinal();
        }
    }
    
    private Entidade selecionarAlvo(Scanner scanner, Entidade atuante) {
        System.out.println("\nAlvos disponíveis:");
        int i = 1;
        LinkedList<Entidade> alvosDisponiveis = new LinkedList<>();
        
        for (int j = 0; j < participantes.size(); j++) {
            Entidade e = participantes.get(j);
            if (e.estaVivo() && e != atuante) {
                System.out.println(i + ". " + e.getNome() + " (Vida: " + e.getVidaAtual() + ")");
                alvosDisponiveis.add(e);
                i++;
            }
        }
        
        if (alvosDisponiveis.size() == 0) {
            System.out.println("Nenhum alvo disponível!");
            return null;
        }
        
        System.out.print("Escolha um alvo: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        
        return alvosDisponiveis.get(escolha - 1);
    }
    
    private Entidade encontrarAlvo(Entidade atuante) {
        for (int i = 0; i < participantes.size(); i++) {
            Entidade e = participantes.get(i);
            if (e.estaVivo() && e != atuante) {
                return e;
            }
        }
        return null;
    }
    
    public boolean verificarVencedor() {
        int vivos = 0;
        Entidade ultimoVivo = null;
        
        for (int i = 0; i < participantes.size(); i++) {
            Entidade e = participantes.get(i);
            if (e.estaVivo()) {
                vivos++;
                ultimoVivo = e;
            }
        }
        
        if (vivos <= 1) {
            if (ultimoVivo != null) {
                eliminados.push(ultimoVivo);
                System.out.println(ultimoVivo.getNome() + " venceu a batalha!");
            } else {
                System.out.println("Todos os participantes foram derrotados - empate!");
            }
            return true;
        }
        return false;
    }
    
    public void exibirRankingFinal() {
        System.out.println("\n=== Ranking Final ===");
        
        // Cria uma lista temporária para armazenar na ordem correta
        LinkedList<Entidade> ranking = new LinkedList<>();
        
        // Transfere da pilha para a lista (ordem inversa)
        while (!eliminados.isEmpty()) {
            ranking.add(eliminados.pop());
        }
        
        // Exibe do primeiro eliminado ao vencedor
        int posicao = 1;
        for (int i = ranking.size() - 1; i >= 0; i--) {
            Entidade e = ranking.get(i);
            System.out.println(posicao + "º Lugar: " + e.getNome());
            posicao++;
        }
        
        // Restaura a pilha de eliminados (opcional)
        for (int i = ranking.size() - 1; i >= 0; i--) {
            eliminados.push(ranking.get(i));
        }
    }
    
    public int getIdBatalha() { return idBatalha; }
    public int getTurnoAtual() { return turnoAtual; }
    public String getEstadoBatalha() { return estadoBatalha; }
    public Stack<Entidade> getEliminados() { return eliminados; }
}