package com.univer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static int readSizing() throws FileNotFoundException {
        Scanner input = new Scanner(new File("1_2.txt"));
        int size = 0;
        while (input.hasNextInt()) {

            // розмірність масиву
            size = input.nextInt();
            break;
        }
        return size;
    }
    // кількість вершин
    private static int V;

    static {
        try {
            V = readSizing();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // пошук вершини з мінімальним ключем
    int minKey(int key[], Boolean mstSet[])
    {
        // ініціалізував мінімум
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // допоміжна функція для мінімального кістякового дерева
    void printMST(int parent[], int graph[][])
    {
        int count = 0;
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
            count+=graph[i][parent[i]];
        }
        System.out.printf("Weight of the maximum Spanning-tree = " + count);
        System.out.println();
    }

    // Функція для побудови та друку MST для представленого графіка
    //використовуючи матрицю
    void primMST(int graph[][])
    {
        // Масив для зберігання побудованого MST
        int parent[] = new int[V];

        // Ключові значення, які використовуються для вибору краю мінімальної ваги в розрізі
        int key[] = new int[V];

        // Представляти набір вершин, включених до MST
        Boolean mstSet[] = new Boolean[V];

        //Ініціалізуйте всі ключі як БЕЗКІНЧЕННІ
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        //Завжди включайте першу 1-у вершину в MST.
        key[0] = 0; // Зробіть ключ 0 так, щоб ця вершина була
        // вибрана як перша вершина
        parent[0] = -1; // Перший вузол завжди є коренем MST

        // MST матиме V вершин
        for (int count = 0; count < V - 1; count++) {
            // Виберіть n- мінімальну ключову вершину з множини вершин
            // ще не включено в MST
            int u = minKey(key, mstSet);

            //Додайте вибрану вершину до набору MST
            mstSet[u] = true;

            //Оновити значення ключа та батьківський індекс суміжного
            // вершини виділеної вершини. Розглянемо лише ті
            // вершини, які ще не включені в MST
            for (int v = 0; v < V; v++)

                // graph[u][v] відмінний від нуля тільки для сусідніх вершин m
                // mstSet[v] є хибним для вершин, які ще не включені в MST
                // Оновлюємо ключ, лише якщо graph[u][v] менший за key[v]
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        System.out.println("Prim's Minimum Spanning Tree (MST) algorithm");

        //роздрукувати побудований MST
        printMST(parent, graph);
    }
    static int findMaxVertex(boolean visited[],
                             int weights[])
    {


        //Зберігає індекс вершини максимальної ваги
        // із множини невідвідуваних вершин
        int index = -1;


        //Зберігає максимальну вагу від
        // множина невідвідуваних вершин
        int maxW = Integer.MIN_VALUE;


        //Переберіть всі можливі
        // вузли графа
        for (int i = 0; i < V; i++)
        {


            //Якщо поточний вузол не відвідується
            // і вага поточної вершини дорівнює
            //більше, ніж maxW
            if (visited[i] == false && weights[i] > maxW)
            {

                // Оновити maxW
                maxW = weights[i];

                // Оновлення індексу
                index = i;
            }
        }
        return index;
    }

    //Функція корисності для знаходження максимуму
    //охоплююче дерево графа
    static void printMaximumSpanningTree(int graph[][],
                                         int parent[])
    {

        // Зберігає загальну вагу
        //максимальне остовне дерево
        // графа
        int MST = 0;

        // Перебирайте всі можливі вузли
        //графа
        for (int i = 1; i < V; i++)
        {

            // Оновити MST
            MST += graph[i][parent[i]];
        }

        System.out.println("Weight of the maximum Spanning-tree "
                + MST);
        System.out.println();
        System.out.println("Edges \tWeight");


        //Роздрукуйте краї та вагу
        // максимальне остовне дерево графа
        for (int i = 1; i < V; i++)
        {
            System.out.println(parent[i] + " - " + i + " \t"
                    + graph[i][parent[i]]);
        }
    }


    //Функція знаходження максимального охоплюючого дерева
    static void maximumSpanningTree(int[][] graph)
    {


        //visited[i]: Перевірте, чи вершина i
        //відвідують чи ні
        boolean[] visited = new boolean[V];


        //weights[i]: зберігає максимальну вагу
        //графік для з'єднання ребра з i
        int[] weights = new int[V];


        //parent[i]: зберігає батьківський вузол
        //вершини i
        int[] parent = new int[V];


        //Ініціалізуйте ваги як -INFINITE,
        //і відвіданий вузол як false
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            weights[i] = Integer.MIN_VALUE;
        }


        //Включіть 1-у вершину в
        //максимальне остовне дерево
        weights[0] = Integer.MAX_VALUE;
        parent[0] = -1;


        //Пошук інших (V-1) вершин
        //і побудувати дерево
        for (int i = 0; i < V - 1; i++) {

            //
            //Зберігає індекс вершини максимальної ваги
            // iз набору невідвідуваних вершин
            int maxVertexIndex
                    = findMaxVertex(visited, weights);

            //Позначте цю вершину як відвідану
            visited[maxVertexIndex] = true;


            //Оновити сусідні вершини
            //поточна відвідана вершина
            for (int j = 0; j < V; j++) {

                // Якщо між j є ребро
                //і поточна відвідана вершина і
                //  також j є невідвіданою вершиною
                if (graph[j][maxVertexIndex] != 0
                        && visited[j] == false) {

                    // Якщо graph[v][x] є
                    // більше ніж вага [v]
                    if (graph[j][maxVertexIndex]
                            > weights[j]) {


                        //Оновити ваги[j]
                        weights[j]
                                = graph[j][maxVertexIndex];


                        //Оновити батьківський елемент[j]
                        parent[j] = maxVertexIndex;
                    }
                }
            }
        }

        System.out.println("Prim's Maximum Spanning Tree (MST) algorithm");

        //Надрукувати максимальне охоплююче дерево
        printMaximumSpanningTree(graph, parent);
    }

    public static int[][] readMatrix() throws FileNotFoundException {

        //
        //Прочитати вхідний файл
            Scanner input = new Scanner(new File("1_2.txt"));

            int size = 0;
            int[][] a = new int[size][size];
            while (input.hasNextInt()) {


                //Це має бути тут, щоб отримати розмір масиву раніше
                //отримання кожного масиву
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


                //роздрукувати вхідну матрицю
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

        Main t = new Main();

        int graph [][] = readMatrix();



        //Роздрукуйте рішення
        t.primMST(graph);
        maximumSpanningTree(graph);
    }
}
