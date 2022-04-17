package com.univer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static int N = 8;
    public static int K = 100000;



    public static int sum_edges_vertexes(int con_matr[][]) {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += con_matr[i][j];
            }
        }
        return sum;
    }

    public static int euler(int matr[][], int con_matr_[][]) {
        int con_matr[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                con_matr[i][j] = con_matr_[i][j];
            }
        }
        int p[] = new int[K + 1]; // для економії пам'яті цей масив використовується для
        // зберігання стеку з тілом і стеку-результату
        // стеки ростуть назустріч один одному
        int p1; // голова
        int p2; // вершина стеку-результату
        int s = 0; // стартова вершина =0
        int k = sum_edges_vertexes(con_matr) / 2; //кількість ребер
        int weight = 0;

        p1 = 0;
        p2 = k + 1;
        p[p1] = s;

        while (p1 >= 0)
        {
            int i, v = p[p1];
            for (i = 0; i < N; ) {
                if (con_matr[v][i] != 0) // є шлях з вершини v
                {
                    con_matr[v][i] = con_matr[v][i] - 1; // викреслюю ребро, в прямому
                    con_matr[i][v] = con_matr[i][v] - 1; // в зворотньому напрямі
                    p[++p1] = i; // збільшую
                    v = i;
                    i = 0;
                }
                else {
                    i++;
                }
            }
            if (i >= N) {// тупік
                p[--p2] = p[p1--]; // повертаємося до попередньої вершини
                // голову помещаємо в результат
            }
        }

        if (p2 > 0)
        { // обійшли не всі ребра
            System.out.println("граф не Ейлеровий" );
        }
        else {
            System.out.println( " Ребро : Вага \n");
            for (int i = 0; i < k; i++) {
                System.out.println( " " + p[i] + 1 + " - " + p[i + 1] + 1 + " : " + matr[p[i]][p[i + 1]]);
                weight += matr[p[i]][p[i + 1]];
            }
            System.out.println( "\n Загальна вага шляху: " + weight );
        }

        return 0;
    }

    public static int add_edges(int con_matr[][], int vert_degr[], int matr[][]) {
        for (int i = 0; i < N; i++) {
            if (vert_degr[i] % 2 != 0) {
                for (int j = 0; j < N; j++) {
                    if (vert_degr[j] % 2 != 0 && con_matr[i][j] != 0) {
                        con_matr[i][j] = con_matr[i][j] + 1;
                    }
                }
            }
        }

        System.out.println( "\nЗмiнена матриця зв'язностi: ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print( con_matr[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();

        euler(matr, con_matr);

        return 0;
    }

    public static int[][] connectivity_matrix(int con_matr[][], int matr[][]) {
        System.out.println( "\nМатриця зв'язностi: " );
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matr[i][j] > 0) {
                    con_matr[i][j] = 1;
                }
                else {
                    con_matr[i][j] = matr[i][j];
                }
                System.out.print( con_matr[i][j]+ "\t");
            }
            System.out.println();
        }

        return con_matr;
    }

    static int if_euler(int con_matr[][], int vert_degr[], int matr[][]) {
        for (int i = 0; i < N; i++) {
            if (vert_degr[i] != 0) {
                add_edges(con_matr, vert_degr, matr);
                break;
            }
            else {
                euler(matr, con_matr);
            }
        }

        return 0;
    }

    public static int vertex_degree(int con_matr[][], int vert_degr[], int matr[][]) {
        System.out.println();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                vert_degr[i] += con_matr[i][j];
            }
            if (vert_degr[i] % 2 != 0)
            {
                System.out.printf(" Вершина "+i + 1 + " - непарна \n");
            }
        }

        if_euler(con_matr, vert_degr, matr);

        return 0;
    }

    public static int[][] read_matrix(int matr[][]) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2-1.txt"));

        int size = 0;
        int[][] a = new int[size][size];
        while (input.hasNextInt()) {


            size = input.nextInt();
            a = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    try{
                        a[i][j] = input.nextInt();
                        matr[i][j]=a[i][j];
                    }
                    catch (java.util.NoSuchElementException e) {
                        // e.printStackTrace();
                    }
                }
            }


            System.out.println("The input matrix is : ");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.printf("%5d ", a[i][j]);
                }
                System.out.println();
            }

        }
        return a;

    }



    public static void main(String[] args) throws FileNotFoundException {
        int matr[][] = new int[N][N];
        int con_matr[][] = new int[N][N];
        int vert_degr[] = new int[N];

        read_matrix(matr);
        connectivity_matrix(con_matr, matr);
        vertex_degree(con_matr, vert_degr, matr);
    }
}
