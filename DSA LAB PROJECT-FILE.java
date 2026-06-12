// Name : Ayesha Tahir , RollNo: L1F24BSSE0042
import java.util.*;

// Node for linked list (stores one line of text)
class Node {
    String text;
    Node next;

    Node(String text) {
        this.text = text;
    }
}

// Linked list to store text lines
class TextLinkedList {
    Node head;

    // add new line at end
    public void addLine(String text) {
        Node n = new Node(text);

        if (head == null) head = n;
        else {
            Node t = head;
            while (t.next != null) t = t.next;
            t.next = n;
        }
    }

    // get line by number
    public String getLine(int index) {
        Node t = head;
        int i = 1;

        while (t != null) {
            if (i == index) return t.text;
            t = t.next;
            i++;
        }
        return null;
    }

    // update line
    public void updateLine(int index, String newText) {
        Node t = head;
        int i = 1;

        while (t != null) {
            if (i == index) {
                t.text = newText;
                return;
            }
            t = t.next;
            i++;
        }
        System.out.println("Line not found");
    }

    // delete line
    public void deleteLine(int index) {
        if (head == null) return;

        if (index == 1) {
            head = head.next;
            return;
        }

        Node t = head;
        int i = 1;

        while (t.next != null) {
            if (i == index - 1) {
                if (t.next != null)
                    t.next = t.next.next;
                return;
            }
            t = t.next;
            i++;
        }

        System.out.println("Line not found");
    }

    // display all text
    public void display() {
        Node t = head;
        int i = 1;

        if (t == null) {
            System.out.println("Empty");
            return;
        }

        while (t != null) {
            System.out.println(i + ": " + t.text);
            t = t.next;
            i++;
        }
    }
}

// Stack for undo/redo actions
class ActionStack {
    Stack<String> undo = new Stack<>();
    Stack<String> redo = new Stack<>();

    // store action
    void push(String action) {
        undo.push(action);
        redo.clear();
    }

    // undo last action
    String undo() {
        if (undo.isEmpty()) return "Nothing to undo";
        String a = undo.pop();
        redo.push(a);
        return a;
    }

    // redo last action
    String redo() {
        if (redo.isEmpty()) return "Nothing to redo";
        String a = redo.pop();
        undo.push(a);
        return a;
    }
}

// Clipboard for copy/paste
class Clipboard {
    ArrayList<String> list = new ArrayList<>();

    // copy text
    void copy(String s) {
        list.add(s);
    }

    // show copied text
    void show() {
        if (list.isEmpty()) {
            System.out.println("Clipboard empty");
            return;
        }

        for (String s : list)
            System.out.println("- " + s);
    }
}

// BST node for word storage
class BSTNode {
    String word;
    BSTNode left, right;

    BSTNode(String w) {
        word = w;
    }
}

// BST for fast search
class BST {
    BSTNode root;

    // insert word
    BSTNode insert(BSTNode r, String w) {
        if (r == null) return new BSTNode(w);

        if (w.compareTo(r.word) < 0)
            r.left = insert(r.left, w);
        else if (w.compareTo(r.word) > 0)
            r.right = insert(r.right, w);

        return r;
    }

    // search word
    boolean search(BSTNode r, String w) {
        if (r == null) return false;
        if (r.word.equals(w)) return true;

        if (w.compareTo(r.word) < 0)
            return search(r.left, w);
        else
            return search(r.right, w);
    }
}

// Main class
public class Main {

    // safe input (prevents crash)
    static int safeInt(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TextLinkedList text = new TextLinkedList();
        ActionStack actions = new ActionStack();
        Clipboard clip = new Clipboard();
        BST bst = new BST();

        int choice;

        do {

            // menu
            System.out.println("\nSMART TEXT EDITOR");
            System.out.println("1 Add Line");
            System.out.println("2 Display");
            System.out.println("3 Update Line");
            System.out.println("4 Delete Line");
            System.out.println("5 Undo");
            System.out.println("6 Redo");
            System.out.println("7 Copy Line");
            System.out.println("8 Search Word");
            System.out.println("9 Show Clipboard");
            System.out.println("0 Exit");

            System.out.print("Choice: ");
            choice = safeInt(sc);

            switch (choice) {

                case 1:
                    System.out.print("Enter text: ");
                    String t = sc.nextLine();

                    text.addLine(t);
                    actions.push("ADD"); // store action

                    System.out.println("Line added");

                    // split words and insert in BST
                    for (String w : t.split(" "))
                        bst.root = bst.insert(bst.root, w);

                    break;

                case 2:
                    text.display();
                    break;

                case 3:
                    System.out.print("Line no: ");
                    int u = safeInt(sc);

                    System.out.print("New text: ");
                    String nt = sc.nextLine();

                    if (u > 0) {
                        text.updateLine(u, nt);
                        actions.push("UPDATE");
                        System.out.println("Updated");
                    } else {
                        System.out.println("Invalid input");
                    }
                    break;

                case 4:
                    System.out.print("Line no: ");
                    int d = safeInt(sc);

                    if (d > 0) {
                        text.deleteLine(d);
                        actions.push("DELETE");
                        System.out.println("Deleted");
                    } else {
                        System.out.println("Invalid input");
                    }
                    break;

                case 5:
                    System.out.println(actions.undo());
                    break;

                case 6:
                    System.out.println(actions.redo());
                    break;

                case 7:
                    System.out.print("Line no: ");
                    int c = safeInt(sc);

                    String copy = text.getLine(c);

                    if (copy != null) {
                        clip.copy(copy);
                        System.out.println("Copied: " + copy);
                    } else {
                        System.out.println("Line not found");
                    }
                    break;

                case 8:
                    System.out.print("Word: ");
                    String w = sc.nextLine();

                    System.out.println(
                            bst.search(bst.root, w) ? "Found" : "Not Found"
                    );
                    break;

                case 9:
                    clip.show();
                    break;

                case 0:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 0);

        sc.close();
    }
}