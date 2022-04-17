package com.univer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static int readSizing() throws FileNotFoundException {
        Scanner input = new Scanner(new File("4-1.txt"));
        int size = 0;
        while (input.hasNextInt()) {


            size = input.nextInt();
            break;
        }
        return size;
    }

    private static int V;

    static {
        try {
            V = readSizing();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /* Повертає істину, якщо є шлях від джерела 's' до
          стоку 't' в залишковому графі. Також заповнює parent[] to
          зберегти шлях */
    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        //
        //Створіть відвіданий масив і позначте всі вершини як
        // не відвідав
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;


        //Створіть чергу, поставте в чергу вихідну вершину та позначте
        //вихідна вершина як відвідана
        LinkedList<Integer> queue
                = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Стандартний цикл BFS
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false
                        && rGraph[u][v] > 0) {

                    //Якщо ми знайдемо підключення до раковини
                    // вузол, то в BFS немає сенсу
                    // більше Нам просто потрібно встановити його батьківський елемент
                    //і може повертати true
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }


        //Ми не досягли приймача в BFS, починаючи з джерела,
        //тому повертаємо false
        return false;
    }

    //Повертає tne максимальний потік від s до t у заданому
    //графік
    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        //Створіть графік залишків і заповніть залишок
        //графік із заданими ємностями в оригінальному графіку
        // як залишкові ємності в залишковому графі

        //Графік залишків, де вказує rGraph[i][j].
        // залишкова ємність краю від i до j (якщо є
        // є ребром. Якщо rGraph[i][j] дорівнює 0, то є
        // ні)
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        //Цей масив заповнюється BFS і для збереження шляху
        int parent[] = new int[V];

        int max_flow = 0; //Спочатку потоку немає

        //Збільште потік, поки є шлях від джерела
        //тонути
        while (bfs(rGraph, s, t, parent)) {

            //Знайти мінімальну залишкову ємність
            //уздовж шляху, заповненого BFS. Або ми можемо сказати
            // знайти максимальний потік через знайдений шлях.
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow
                        = Math.min(path_flow, rGraph[u][v]);
            }

            //оновлення залишкових ємностей кромок і
            //зворотні ребра вздовж шляху
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            //Додайте потік шляху до загального потоку
            max_flow += path_flow;
        }

        //Поверніть загальний потік
        return max_flow;
    }
    public static int[][] readMatrix() throws FileNotFoundException {


        Scanner input = new Scanner(new File("4-1.txt"));

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
        {

            int graph[][] = readMatrix();
            Main m = new Main();

            System.out.println("The maximum possible flow is "
                    + m.fordFulkerson(graph, 0, V-1));
        }
    }
}
