package model.data_model;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data = null;
    private double id;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    private int depth = 0;

    public Node(T data) {
        this.data = data;
    }

    public Node getRoot() {
        Node<T> root = parent;

        while(root != null){
            root = root.parent;
        }
        return root;
    }

    public Node<T> addChild(Node<T> child) {
        //add the node to the ArrayList
        children.add(child);
        child.setParent(this);
        child.setDepth(depth+1);

        return child;
    }

    public void addChildren(List<Node<T>> children) {
        //add multiple child
        for(Node<T> n: children){
            this.children.add(n);
        }
    }

    public List<Node<T>> getChildren() {return this.children;}

    public T getData() {return this.data;}

    public void setData(T data) {this.data = data;}

    private void setParent(Node<T> parent) {this.parent = parent;}

    public Node<T> getParent() {return this.parent;}

    public int getDepth(){return depth;}
    public void setDepth(int depth){this.depth = depth;}

}

