import model.Tuple;

import java.util.ArrayList;
import java.util.List;

class Node{

    Node left;

    Node right;

    Tuple data;

    public Node(Tuple data) {
        this.data = data;
    }
}

public class BinaryTree
{
    private Node parent;

    private Tuple data;

    private int  size = 0;

    public BinaryTree() {
        parent = new Node(data);
    }

    public void add(Tuple data) {

        if (size == 0) {
            parent.data = data;
            size++;
        } else {
            add(parent, new Node(data));
        }
    }

    private void add(Node root, Node newNode) {

        if (root == null) {
            return;
        }

        if (newNode.data.x.toString().compareTo(root.data.y.toString())>0) {

            if (root.left == null) {
                root.left = newNode;
                size++;
            } else {
                add(root.left, newNode);
            }
        } else {

            if (root.right == null) {
                root.right = newNode;
                size++;
            } else {
                add(root.right, newNode);
            }
        }
    }

    public Tuple getLow() {

        Node current = parent;

        while (current.left != null) {
            current = current.left;
        }

        return current.data;
    }

    public Tuple getHigh() {

        Node current = parent;

        while (current.right != null) {
            current = current.right;
        }

        return current.data;
    }

    private void in(Node node,List<Tuple> list) {

        if (node != null) {

            in(node.left,list);
            list.add(node.data);
            in(node.right,list);
        }
    }

    private void pre(Node node,List<Tuple> list) {

        if (node != null) {

            list.add(node.data);
            pre(node.left,list);
            pre(node.right,list);
        }
    }

    private void post(Node node,List<Tuple> list) {

        if (node != null) {

            post(node.left,list);
            post(node.right,list);
            list.add(node.data);
        }
    }

    public List<Tuple> preorder() {

        //System.out.print("Preorder Traversal->");
        if (size == 0)
            return new ArrayList<>();
        List<Tuple> list = new ArrayList<>();
        pre(parent,list);
        return list;
    }

    public List<Tuple> postorder() {

        if (size == 0)
            return new ArrayList<>();
        //System.out.print("Postorder Traversal->");
        List<Tuple> list = new ArrayList<>();
        post(parent,list);
        return list;
    }

    public List<Tuple> inorder() {
        if (size == 0)
            return new ArrayList<>();
        //System.out.print("Inorder Traversal->");
        List<Tuple> list = new ArrayList<>();
        in(parent,list);
        if (list.get(0)==null)
            return new ArrayList<>();
        return list;
    }

    public String toString() {

        Node current = parent;
        System.out.print("Traverse From Left ");

        while (current.left != null && current.right != null) {

            System.out.print(current.data + "->[" + current.left.data + " " + current.right.data + "] ");
            current = current.left;
        }

        System.out.println();
        System.out.print("Traverse From Right ");

        current = parent;

        while (current.left != null && current.right != null) {

            System.out.print(current.data + "->[" + current.left.data + " " + current.right.data + "] ");
            current = current.right;
        }

        return "";
    }
}