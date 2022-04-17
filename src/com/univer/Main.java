package com.univer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    //Функція знаходження мінімальної ваги
    //Гамільтонів цикл
    static int tsp(int[][] graph, boolean[] v,
                   int currPos, int n,
                   int count, int cost, int ans)
    {

        //Якщо досягнуто останнього вузла, і він має посилання
        // до початкового вузла, тобто джерела
        //  зберігати мінімальне значення за загальними витратами
        // обходу та "ans"
        //Нарешті поверніться, щоб перевірити інші можливі значення
        if (count == n && graph[currPos][0] > 0)
        {
            ans = Math.min(ans, cost + graph[currPos][0]);
            return ans;
        }


        //КРОК ПОВЕРНЕННЯ
        // Цикл для проходження списку суміжності
        //вузла currPos і збільшення кількості
        //на 1 і вартість за значенням graph[currPos,i].
        for (int i = 0; i < n; i++)
        {
            if (v[i] == false && graph[currPos][i] > 0)
            {

                //Позначити як відвідані
                v[i] = true;
                ans = tsp(graph, v, i, n, count + 1,
                        cost + graph[currPos][i], ans);

                //Позначте ith вузол як невідвідуваний
                v[i] = false;
            }
        }
        return ans;
    }
    public static int readSizing() throws FileNotFoundException {
        Scanner input = new Scanner(new File("3-1.txt"));
        int size = 0;
        while (input.hasNextInt()) {


            size = input.nextInt();
            break;
        }
        return size;
    }


    public static int[][] readMatrix() throws FileNotFoundException {


        Scanner input = new Scanner(new File("3-1.txt"));

        int size = 0;
        int[][] a = new int[size][size];
        while (input.hasNextInt()) {

            size = input.nextInt();
            a = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    try{
                        a[i][j] = input.nextInt();

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

        int n = readSizing();

        int[][] graph = readMatrix();


        //Булевий масив, щоб перевірити, чи є вузол
        //відвідували чи ні
        boolean[] v = new boolean[n];


        //Позначте 0-й вузол як відвіданий
        v[0] = true;
        int ans = Integer.MAX_VALUE;


        //Знайти гамільтонів цикл з мінімальною вагою
        ans = tsp(graph, v, 0, n, 1, 0, ans);


        //ans — мінімальна вага гамільтонового циклу
        System.out.println(ans);
    }
}
