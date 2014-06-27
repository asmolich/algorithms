import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import java.util.stream.Stream;
import java.util.stream.IntStream;

public class BST<T extends Comparable<T>> {

    private static class Node<T> {
        Node<T> left = null;
        Node<T> right = null;
        T value;
        Node(T val) {
            value = val;
        }
    }

    private Node<T> root = null;

    public BST() {}

    public Node<T> find(T val) {
        return val == null ? null : find(root, val);
    }

    public void add(T val) {
        if (val == null) {
            throw new IllegalArgumentException("argument can't be null");
        }
        if (root == null) {
            root = new Node<T>(val);
        }
        else {
            add(root, val);
        }
    }

    private void add(Node<T> node, T val) {
        if (less(val, node.value)) {
            if (node.left == null) {
                node.left = new Node<T>(val);
            }
            else {
                add(node.left, val);
            }
        }
        else {
            if (node.right == null) {
                node.right = new Node<T>(val);
            }
            else {
                add(node.right, val);
            }
        }    
    }

    private Node<T> find(Node<T> node, T val) {
        if (node == null || val == null) {
            return null;
        }
        else {
            Node<T> result;
            if (less(val, node.value)) {
                result = find(node.left, val);
            }
            else {
                result = find(node.right, val);
            }
            return result;
        }
    }
 
    private boolean less(T k1, T k2) {
        return k1.compareTo(k2) <= 0;
    }

    public List<T> inOrder() {
        if (root == null) {
            return Collections.emptyList();
        }
        List<T> list = new LinkedList<>();
        inOrder(root, list);
        return list;
    }

    private void inOrder(Node<T> node, List<T> list) {
        if (node.left != null) inOrder(node.left, list);
        list.add(node.value);
        if (node.right != null) inOrder(node.right, list);
    }

    public List<T> levelOrder() {
        if (root == null) {
            return Collections.emptyList();
        }
        List<T> list = new LinkedList<>();
        Queue<Node<T>> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while(!nodeQueue.isEmpty()) {
            Node<T> node = nodeQueue.poll();
            list.add(node.value);
            if (node.left != null) nodeQueue.add(node.left);
            if (node.right != null) nodeQueue.add(node.right);
        }
        return list;
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        //IntStream.rangeClosed(1, 10).forEach(i -> bst.add(i));

        Stream.of(new Integer[]{3, 1, 5, 2, 4}).forEach(i -> bst.add(i));

        System.out.println("level order = " + bst.levelOrder());
        System.out.println("in order = " + bst.inOrder());
    }
}

