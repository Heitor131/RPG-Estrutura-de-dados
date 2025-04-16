package ui;

import java.util.Random;
import java.util.Scanner;

import entities.Habilidade;
import entities.Monstro;
import entities.PersonagemJogador;
import game.Arena;
import game.Jogador;
import persistence.Persistencia;
import utils.LinkedList;

public class InterfaceUsuario {
    private Scanner scanner;
    private LinkedList<Jogador> jogadores;
    private Jogador jogadorLogado;
    
    public InterfaceUsuario() {
        this.scanner = new Scanner(System.in);
        this.jogadores = Persistencia.carregarJogadores();
    }
    
    public void iniciar() {
        exibirTelaInicial();
    }
    
    private void exibirTelaInicial() {
        while (true) {
            System.out.println("\n=== RPG BATTLE SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            
            int escolha = scanner.nextInt();
            scanner.nextLine();
            
            switch (escolha) {
                case 1:
                    realizarLogin();
                    break;
                case 2:
                    cadastrarNovoJogador();
                    break;
                case 3:
                    Persistencia.salvarJogadores(jogadores);
                    System.out.println("Jogo encerrado. Dados salvos.");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private void realizarLogin() {
        System.out.print("\nNome de usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador j = jogadores.get(i);
            if (j.getNome().equals(nome)) {
                if (j.autenticar(senha)) {
                    jogadorLogado = j;
                    menuPrincipal();
                    return;
                }
            }
        }
        System.out.println("Usuário ou senha incorretos!");
    }
    
    private void cadastrarNovoJogador() {
        System.out.print("\nNovo nome de usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Nova senha: ");
        String senha = scanner.nextLine();
        
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i).getNome().equals(nome)) {
                System.out.println("Usuário já existe!");
                return;
            }
        }
        
        Jogador novoJogador = new Jogador(jogadores.size() + 1, nome, senha);
        jogadores.add(novoJogador);
        System.out.println("Cadastro realizado com sucesso!");
    }
    
    private void menuPrincipal() {
        while (jogadorLogado != null) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Personagens");
            System.out.println("2. Batalha PvE");
            System.out.println("3. Batalha PvP");
            System.out.println("4. Loja");
            System.out.println("5. Desconectar");
            System.out.print("Escolha: ");
            
            int escolha = scanner.nextInt();
            scanner.nextLine();
            
            switch (escolha) {
                case 1:
                    gerenciarPersonagens();
                    break;
                case 2:
                    iniciarBatalhaPvE();
                    break;
                case 3:
                    iniciarBatalhaPvP();
                    break;
                case 4:
                    menuLoja();
                    break;
                case 5:
                    jogadorLogado = null;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private void gerenciarPersonagens() {
        while (true) {
            System.out.println("\n=== GERENCIAR PERSONAGENS ===");
            System.out.println("1. Criar Novo Personagem");
            System.out.println("2. Listar Personagens");
            System.out.println("3. Selecionar Personagem Ativo");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            
            int escolha = scanner.nextInt();
            scanner.nextLine();
            
            switch (escolha) {
                case 1:
                    criarNovoPersonagem();
                    break;
                case 2:
                    listarPersonagens();
                    break;
                case 3:
                    selecionarPersonagemAtivo();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private void criarNovoPersonagem() {
        System.out.print("Nome do novo personagem: ");
        String nome = scanner.nextLine();
        jogadorLogado.criarPersonagem(nome);
        System.out.println("Personagem criado com sucesso!");
    }
    
    private void listarPersonagens() {
        System.out.println("\n=== SEUS PERSONAGENS ===");
        if (jogadorLogado.getPersonagens().size() == 0) {
            System.out.println("Você não tem personagens ainda!");
            return;
        }
        
        for (int i = 0; i < jogadorLogado.getPersonagens().size(); i++) {
            PersonagemJogador p = jogadorLogado.getPersonagens().get(i);
            System.out.println(p.getId() + ": " + p.getNome() + " (Nível " + p.getNivel() + 
                             ") - XP: " + p.getExperiencia() + "/" + p.getProximoNivel());
        }
    }
    
    private void selecionarPersonagemAtivo() {
        listarPersonagens();
        if (jogadorLogado.getPersonagens().size() == 0) return;
        
        System.out.print("Escolha o ID do personagem: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        PersonagemJogador personagem = jogadorLogado.selecionarPersonagem(id);
        if (personagem != null) {
            System.out.println(personagem.getNome() + " selecionado como personagem ativo!");
        } else {
            System.out.println("ID inválido!");
        }
    }
    
    private void iniciarBatalhaPvE() {
        if (jogadorLogado.getPersonagemAtivo() == null) {
            System.out.println("Selecione um personagem primeiro!");
            return;
        }
    
        Arena arena = new Arena(1);
        
        // Adiciona participantes já curados
        PersonagemJogador personagem = jogadorLogado.getPersonagemAtivo();
        personagem.curarCompletamente();
        
        arena.adicionarParticipante(personagem);
        arena.adicionarParticipante(gerarMonstroAleatorio());
        arena.adicionarParticipante(gerarMonstroAleatorio());
        
        arena.iniciarBatalha();
        
        while (arena.getEstadoBatalha().equals("Em Andamento")) {
            arena.executarTurno(scanner);
        }
        
        if (personagem.estaVivo()) {
            personagem.ganharExperiencia(30);
            jogadorLogado.setSaldoMoedas(jogadorLogado.getSaldoMoedas() + 20);
            System.out.println("Você ganhou 30 XP e 20 moedas!");
        }
    }
    
    private void iniciarBatalhaPvP() {
        if (jogadorLogado.getPersonagemAtivo() == null) {
            System.out.println("Selecione um personagem primeiro!");
            return;
        }
        
        System.out.println("\nJogadores disponíveis para PvP:");
        int i = 1;
        LinkedList<Jogador> oponentes = new LinkedList<>();
        
        for (int j = 0; j < jogadores.size(); j++) {
            Jogador jg = jogadores.get(j);
            if (jg != jogadorLogado && jg.getPersonagemAtivo() != null) {
                System.out.println(i + ". " + jg.getNome() + " (" + 
                                  jg.getPersonagemAtivo().getNome() + " - Nível " + 
                                  jg.getPersonagemAtivo().getNivel() + ")");
                oponentes.add(jg);
                i++;
            }
        }
        
        if (oponentes.size() == 0) {
            System.out.println("Nenhum outro jogador disponível para PvP!");
            return;
        }
        
        System.out.print("Escolha um oponente: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        
        Jogador oponente = oponentes.get(escolha - 1);
        
        // Cria uma nova instância de Arena para cada batalha
        Arena arena = new Arena(2);
    
        // Garante cura completa antes da batalha
        jogadorLogado.getPersonagemAtivo().curarCompletamente();
        oponente.getPersonagemAtivo().curarCompletamente();
    
        arena.iniciarBatalhaPvP(jogadorLogado.getPersonagemAtivo(), 
                            oponente.getPersonagemAtivo());
        
        while (arena.getEstadoBatalha().equals("Em Andamento")) {
            arena.executarTurno(scanner);
        }
        
        if (arena.getEliminados().peek() == jogadorLogado.getPersonagemAtivo()) {
            System.out.println("Você perdeu a batalha!");
        } else {
            System.out.println("Você venceu a batalha!");
            jogadorLogado.getPersonagemAtivo().ganharExperiencia(50);
            jogadorLogado.setSaldoMoedas(jogadorLogado.getSaldoMoedas() + 30);
            System.out.println("Você ganhou 50 XP e 30 moedas!");
        }
    }
    
    private Monstro gerarMonstroAleatorio() {
        String[] nomes = {"Goblin", "Orc", "Esqueleto", "Zumbi", "Lobisomem"};
        String[] tipos = {"Terrestre", "Voador", "Aquático", "Mágico"};
        
        Random rand = new Random();
        String nome = nomes[rand.nextInt(nomes.length)];
        String tipo = tipos[rand.nextInt(tipos.length)];
        int nivel = jogadorLogado.getPersonagemAtivo().getNivel() + rand.nextInt(3) - 1;
        if (nivel < 1) nivel = 1;
        
        int vida = 30 + (nivel * 8);
        int mana = 10 + (nivel * 3);
        
        return new Monstro(rand.nextInt(1000) + 1, nome, nivel, vida, mana, tipo);
    }
    
    private void menuLoja() {
        while (true) {
            System.out.println("\n=== LOJA ===");
            System.out.println("Saldo: " + jogadorLogado.getSaldoMoedas() + " moedas");
            System.out.println("1. Poção de Vida (20 moedas)");
            System.out.println("2. Poção de Mana (20 moedas)");
            System.out.println("3. Habilidade Nova (50 moedas)");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            
            int escolha = scanner.nextInt();
            scanner.nextLine();
            
            switch (escolha) {
                case 1:
                    comprarPocaoVida();
                    break;
                case 2:
                    comprarPocaoMana();
                    break;
                case 3:
                    comprarHabilidade();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private void comprarPocaoVida() {
        if (jogadorLogado.getSaldoMoedas() < 20) {
            System.out.println("Moedas insuficientes!");
            return;
        }
        
        jogadorLogado.getPersonagemAtivo().curar(30);
        jogadorLogado.setSaldoMoedas(jogadorLogado.getSaldoMoedas() - 20);
        System.out.println("Poção de Vida comprada! +30 de vida.");
    }
    
    private void comprarPocaoMana() {
        if (jogadorLogado.getSaldoMoedas() < 20) {
            System.out.println("Moedas insuficientes!");
            return;
        }
        
        PersonagemJogador personagem = jogadorLogado.getPersonagemAtivo();
        personagem.setManaAtual(personagem.getManaAtual() + 30);
        jogadorLogado.setSaldoMoedas(jogadorLogado.getSaldoMoedas() - 20);
        System.out.println("Poção de Mana comprada! +30 de mana.");
    }
    
    private void comprarHabilidade() {
        if (jogadorLogado.getSaldoMoedas() < 50) {
            System.out.println("Moedas insuficientes!");
            return;
        }
        
        Random rand = new Random();
        String[] nomes = {"Raio Elétrico", "Golpe de Gelo", "Corte Sombrio", "Investida Divina"};
        int dano = 20 + jogadorLogado.getPersonagemAtivo().getNivel() * 3;
        int custo = 15 + jogadorLogado.getPersonagemAtivo().getNivel();
        
        String nome = nomes[rand.nextInt(nomes.length)];
        Habilidade nova = new Habilidade(
            jogadorLogado.getPersonagemAtivo().getHabilidades().size() + 1,
            nome,
            custo,
            dano,
            "Dano especial"
        );
        
        jogadorLogado.getPersonagemAtivo().aprenderHabilidade(nova);
        jogadorLogado.setSaldoMoedas(jogadorLogado.getSaldoMoedas() - 50);
        System.out.println("Habilidade " + nome + " aprendida! (Dano: " + dano + ", Custo: " + custo + ")");
    }
}