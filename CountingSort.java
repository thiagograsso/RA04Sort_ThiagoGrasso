import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CountingSort {
    private static long numTrocas;
    private static long numIteracoes;

    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 500000, 1000000};
        int rodadas = 5;
        long seed = 42; // Semente fixa para replicabilidade

        try (FileWriter writer = new FileWriter("resultados_counting_sort.csv")) {
            writer.append("Tamanho,TempoMedio,TrocasMedias,IteracoesMedias\n");

            for (int tamanho : tamanhos) {
                long tempoTotal = 0;
                long trocasTotais = 0;
                long iteracoesTotais = 0;

                for (int i = 0; i < rodadas; i++) {
                    int[] array = new int[tamanho];
                    preencherVetorAleatoriamente(array, seed + i); // Variar a semente para cada rodada

                    numTrocas = 0;
                    numIteracoes = 0;

                    long inicio = System.nanoTime();
                    countingSort(array);
                    long fim = System.nanoTime();

                    tempoTotal += (fim - inicio);
                    trocasTotais += numTrocas;
                    iteracoesTotais += numIteracoes;
                }

                long tempoMedio = tempoTotal / rodadas;
                long trocasMedias = trocasTotais / rodadas;
                long iteracoesMedias = iteracoesTotais / rodadas;

                writer.append(tamanho + "," + tempoMedio + "," + trocasMedias + "," + iteracoesMedias + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void preencherVetorAleatoriamente(int[] vetor, long seed) {
        Random random = new Random(seed);
        int tamanho = vetor.length;
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt(1000000); // Gera um número inteiro aleatório positivo até 1.000.000
        }
    }

    public static void countingSort(int[] array) {
        int max = findMax(array);
        int min = findMin(array);
        int range = max - min + 1;
        int tamanho = array.length;

        int[] count = new int[range];
        int[] output = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            count[array[i] - min]++;
            numIteracoes++;
        }

        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
            numIteracoes++;
        }

        for (int i = tamanho - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
            numIteracoes++;
        }

        for (int i = 0; i < tamanho; i++) {
            array[i] = output[i];
            numIteracoes++;
        }
    }

    public static int findMax(int[] array) {
        int max = array[0];
        int tamanho = array.length;
        for (int i = 1; i < tamanho; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            numIteracoes++;
        }
        return max;
    }

    public static int findMin(int[] array) {
        int min = array[0];
        int tamanho = array.length;
        for (int i = 1; i < tamanho; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            numIteracoes++;
        }
        return min;
    }
}
