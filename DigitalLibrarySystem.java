import java.util.*;

class Book {
    int id;
    String title;
    double rating;

    Book(int id, String title, double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Rating: " + rating);
        System.out.println();
    }
}

class BST {

    class Node {
        Book book;
        Node left, right;

        Node(Book book) {
            this.book = book;
        }
    }

    Node root;

    Node insert(Node root, Book book) {

        if (root == null)
            return new Node(book);

        if (book.id < root.book.id)
            root.left = insert(root.left, book);
        else
            root.right = insert(root.right, book);

        return root;
    }

    Book search(Node root, int id) {

        if (root == null)
            return null;

        if (root.book.id == id)
            return root.book;

        if (id < root.book.id)
            return search(root.left, id);

        return search(root.right, id);
    }

    void inorder(Node root) {

        if (root != null) {
            inorder(root.left);
            root.book.display();
            inorder(root.right);
        }
    }
}

class AVLTree {

    class Node {
        Book book;
        int height;
        Node left, right;

        Node(Book book) {
            this.book = book;
            height = 1;
        }
    }

    Node root;

    int height(Node n) {
        return n == null ? 0 : n.height;
    }

    int balance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {

        Node x = y.left;
        Node t = x.right;

        x.right = y;
        y.left = t;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {

        Node y = x.right;
        Node t = y.left;

        y.left = x;
        x.right = t;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, Book book) {

        if (node == null)
            return new Node(book);

        if (book.id < node.book.id)
            node.left = insert(node.left, book);
        else if (book.id > node.book.id)
            node.right = insert(node.right, book);
        else
            return node;

        node.height =
                1 + Math.max(height(node.left),
                             height(node.right));

        int bal = balance(node);

        if (bal > 1 &&
                book.id < node.left.book.id)
            return rightRotate(node);

        if (bal < -1 &&
                book.id > node.right.book.id)
            return leftRotate(node);

        return node;
    }

    void inorder(Node root) {

        if (root != null) {
            inorder(root.left);
            root.book.display();
            inorder(root.right);
        }
    }
}

class Sorting {

    static void sortBooks(ArrayList<Book> books) {

        books.sort((b1, b2) ->
                b1.title.compareToIgnoreCase(b2.title));

        System.out.println("\nBooks Sorted By Title\n");

        for (Book b : books)
            b.display();
    }
}

class GreedyRecommendation {

    static void recommend(ArrayList<Book> books) {

        books.sort((b1, b2) ->
                Double.compare(b2.rating, b1.rating));

        System.out.println("\nTop Recommended Books (Greedy)\n");

        for (Book b : books)
            b.display();
    }
}

class RecommendationDP {

    static void bestBook(Book[] books) {

        if (books.length == 0) {
            System.out.println("No Books Available");
            return;
        }

        double bestRating = books[0].rating;
        Book bestBook = books[0];

        for (int i = 1; i < books.length; i++) {

            if (books[i].rating > bestRating) {

                bestRating = books[i].rating;
                bestBook = books[i];
            }
        }

        System.out.println("\nBest Book Recommendation (DP)\n");
        bestBook.display();
    }
}

public class DigitalLibrarySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BST bst = new BST();
        BST.Node bstRoot = null;

        AVLTree avl = new AVLTree();
        AVLTree.Node avlRoot = null;

        ArrayList<Book> books = new ArrayList<>();

        while (true) {

            System.out.println("\n===== DIGITAL LIBRARY SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book (BST)");
            System.out.println("3. Display Books (BST)");
            System.out.println("4. Sort Books");
            System.out.println("5. Display AVL Tree");
            System.out.println("6. Greedy Recommendation");
            System.out.println("7. Best Recommendation (DP)");
            System.out.println("8. Exit");

            System.out.print("Enter Choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:

                    System.out.print("Enter Book ID: ");
                    int id = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Rating: ");
                    double rating =
                            Double.parseDouble(sc.nextLine());

                    Book book =
                            new Book(id, title, rating);

                    bstRoot =
                            bst.insert(bstRoot, book);

                    avlRoot =
                            avl.insert(avlRoot, book);

                    books.add(book);

                    System.out.println("Book Added Successfully");

                    break;

                case 2:

                    System.out.print("Enter Book ID: ");
                    int searchId =
                            Integer.parseInt(sc.nextLine());

                    Book found =
                            bst.search(bstRoot, searchId);

                    if (found != null)
                        found.display();
                    else
                        System.out.println("Book Not Found");

                    break;

                case 3:

                    System.out.println("\nBooks in BST\n");
                    bst.inorder(bstRoot);

                    break;

                case 4:

                    Sorting.sortBooks(books);

                    break;

                case 5:

                    System.out.println("\nBooks in AVL Tree\n");
                    avl.inorder(avlRoot);

                    break;

                case 6:

                    GreedyRecommendation.recommend(books);

                    break;

                case 7:

                    Book[] arr =
                            books.toArray(new Book[0]);

                    RecommendationDP.bestBook(arr);

                    break;

                case 8:

                    System.out.println("Thank You");
                    System.exit(0);

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }
}
