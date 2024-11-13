import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MergeSort {
    private static long numTrocas;
    private static long numIteracoes;

    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 500000, 1000000};
        int rodadas = 5;
        long seed = 42; // Semente fixa para replicabilidade

        try (FileWriter writer = new FileWriter("resultados_merge_sort.csv")) {
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
                    mergeSort(array, 0, tamanho - 1);
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

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = array[left + i];
            numIteracoes++;
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[middle + 1 + j];
            numIteracoes++;
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            numIteracoes++;
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
                numTrocas++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
            numIteracoes++;
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
            numIteracoes++;
        }
    }
}
