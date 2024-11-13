import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CountingSort {
    private static long numTrocas;
    private static long numIteracoes;

    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 500000, 1000000};
        int rodadas = 5;
        long seed = 42;

        try (FileWriter writer = new FileWriter("resultados_counting_sort.csv")) {
            writer.append("Tamanho,Rodada,Tempo,Trocas,Iteracoes\n");

            for (int tamanho : tamanhos) {
                long tempoTotal = 0;
                long trocasTotais = 0;
                long iteracoesTotais = 0;

                for (int i = 0; i < rodadas; i++) {
                    int[] array = new int[tamanho];
                    preencherVetorAleatoriamente(array, tamanho, seed + i);

                    numTrocas = 0;
                    numIteracoes = 0;

                    long inicio = System.nanoTime();
                    countingSort(array, tamanho);
                    long fim = System.nanoTime();

                    long tempo = fim - inicio;
                    tempoTotal += tempo;
                    trocasTotais += numTrocas;
                    iteracoesTotais += numIteracoes;

                    writer.append(tamanho + "," + (i + 1) + "," + tempo + "," + numTrocas + "," + numIteracoes + "\n");
                }

                long tempoMedio = tempoTotal / rodadas;
                long trocasMedias = trocasTotais / rodadas;
                long iteracoesMedias = iteracoesTotais / rodadas;

                writer.append(tamanho + ",Média," + tempoMedio + "," + trocasMedias + "," + iteracoesMedias + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void preencherVetorAleatoriamente(int[] vetor, int tamanho, long seed) {
        Random random = new Random(seed);
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt(1000000);
        }
    }

    public static void countingSort(int[] array, int tamanho) {
        int max = findMax(array, tamanho);
        int min = findMin(array, tamanho);
        int range = max - min + 1;

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

    public static int findMax(int[] array, int tamanho) {
        int max = array[0];
        for (int i = 1; i < tamanho; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            numIteracoes++;
        }
        return max;
    }

    public static int findMin(int[] array, int tamanho) {
        int min = array[0];
        for (int i = 1; i < tamanho; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            numIteracoes++;
        }
        return min;
    }
}
