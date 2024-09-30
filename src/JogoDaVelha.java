import java.util.Scanner;

public class JogoDaVelha {
    // Representação do tabuleiro 3x3
    static char[][] tabuleiro = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    // Constantes para os jogadores
    static final char JOGADOR = 'X';
    static final char COMPUTADOR = 'O';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Loop principal do jogo
        while (true) {
            imprimirTabuleiro(); // Mostrar o tabuleiro
            jogadorMove(sc); // Jogador faz a jogada
            if (fimDeJogo()) break; // Verifica se o jogo terminou
            computadorMove(); // Computador faz a jogada
            if (fimDeJogo()) break; // Verifica se o jogo terminou
        }

        sc.close();
    }

    // Função que imprime o tabuleiro
    public static void imprimirTabuleiro() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + tabuleiro[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("-------------");
        }
    }

    // Função para a jogada do jogador
    public static void jogadorMove(Scanner sc) {
        int linha, coluna;

        while (true) {
            System.out.print("Insira sua jogada (linha e coluna de 1 a 3): ");
            linha = sc.nextInt() - 1;
            coluna = sc.nextInt() - 1;

            if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3 && tabuleiro[linha][coluna] == ' ') {
                tabuleiro[linha][coluna] = JOGADOR;
                break;
            } else {
                System.out.println("Jogada inválida! Tente novamente.");
            }
        }
    }

    // Função para a jogada do computador usando Minimax
    public static void computadorMove() {
        int[] melhorMovimento = minimax(0, COMPUTADOR);
        tabuleiro[melhorMovimento[1]][melhorMovimento[2]] = COMPUTADOR;
    }

    // Função para verificar se o jogo terminou
    public static boolean fimDeJogo() {
        if (checarVencedor(JOGADOR)) {
            imprimirTabuleiro();
            System.out.println("Você venceu!");
            return true;
        }
        if (checarVencedor(COMPUTADOR)) {
            imprimirTabuleiro();
            System.out.println("O computador venceu!");
            return true;
        }
        if (tabuleiroCompleto()) {
            imprimirTabuleiro();
            System.out.println("Empate!");
            return true;
        }
        return false;
    }

    // Função que verifica se há um vencedor
    public static boolean checarVencedor(char jogador) {
        // Checar linhas, colunas e diagonais
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador)
                return true;
            if (tabuleiro[0][i] == jogador && tabuleiro[1][i] == jogador && tabuleiro[2][i] == jogador)
                return true;
        }
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador)
            return true;
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador)
            return true;

        return false;
    }

    // Função que verifica se o tabuleiro está completo (empate)
    public static boolean tabuleiroCompleto() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Função Minimax
    public static int[] minimax(int profundidade, char jogador) {
        // Verificar se o estado atual é terminal (vitória ou empate)
        if (checarVencedor(COMPUTADOR)) {
            return new int[] {profundidade - 10, -1, -1 };
        }
        if (checarVencedor(JOGADOR)) {
            return new int[] {10 - profundidade, -1, -1 };
        }
        if (tabuleiroCompleto()) {
            return new int[] { 0, -1, -1 };
        }

        // Variáveis para armazenar a melhor jogada
        int melhorPontuacao = (jogador == COMPUTADOR) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int linha = -1;
        int coluna = -1;

        // Percorrer todas as posições vazias do tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    tabuleiro[i][j] = jogador; // Fazer a jogada temporariamente

                    // Chamada recursiva para o próximo jogador
                    int[] pontuacaoAtual = minimax(profundidade + 1, (jogador == COMPUTADOR) ? JOGADOR : COMPUTADOR);

                    // Desfazer a jogada temporária
                    tabuleiro[i][j] = ' ';

                    // Atualizar a melhor jogada com base no jogador atual
                    if (jogador == COMPUTADOR) {
                        if (pontuacaoAtual[0] < melhorPontuacao) {
                            melhorPontuacao = pontuacaoAtual[0];
                            linha = i;
                            coluna = j;
                        }
                    } else {
                        if (pontuacaoAtual[0] > melhorPontuacao) {
                            melhorPontuacao = pontuacaoAtual[0];
                            linha = i;
                            coluna = j;
                        }
                    }
                }
            }
        }

        // Retorna a melhor pontuação e a posição correspondente
        return new int[] { melhorPontuacao, linha, coluna };
    }
}
