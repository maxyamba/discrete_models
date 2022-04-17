package com.univer;

public class Main {
    Node root1, root2;

    /* Для бінарного дерева виведіть його вузли в порядку, зворотному рівням */
    boolean isIsomorphic(Node n1, Node n2)
    {
        // Обидва корені є NULL, дерева ізоморфні за визначенням
        if (n1 == null && n2 == null)
            return true;

        // Точно одне з n1 і n2 є NULL, дерева не ізоморфні
        if (n1 == null || n2 == null)
            return false;

        if (n1.data != n2.data)
            return false;

        // Є два можливих випадки ізоморфності n1 і n2
        // Випадок 1: піддерева, вкорінені в цих вузлах, НЕ були
        //«Перевернуто».
        //Обидва ці піддерева мають бути ізоморфними.
        // Випадок 2: піддерева, вкорінені в цих вузлах, були «перевернуті»
        return (isIsomorphic(n1.left, n2.left) &&
                isIsomorphic(n1.right, n2.right))
                || (isIsomorphic(n1.left, n2.right) &&
                isIsomorphic(n1.right, n2.left));
    }

    public static void main(String[] args) {
        Main tree = new Main();

        //Давайте створимо дерева, показані на схемі вище
        tree.root1 = new Node(6);
        tree.root1.left = new Node(10);
        tree.root1.right = new Node(12);
        tree.root1.left.left = new Node(14);
        tree.root1.left.right = new Node(15);
        tree.root1.right.left = new Node(18);
        tree.root1.left.right.left = new Node(20);
        tree.root1.left.right.right = new Node(24);

        tree.root2 = new Node(6);
        tree.root2.left = new Node(12);
        tree.root2.right = new Node(10);
        tree.root2.right.left = new Node(15);
        tree.root2.right.right = new Node(14);
        tree.root2.left.right = new Node(18);
        tree.root2.right.left.left = new Node(24);
        tree.root2.right.left.right = new Node(20);

        if (tree.isIsomorphic(tree.root1, tree.root2) == true)
            System.out.println("Yes");
        else
            System.out.println("No");
    }

}

class Node
{
    int data;
    Node left, right;

    Node(int item)
    {
        data = item;
        left = right;
    }
}
