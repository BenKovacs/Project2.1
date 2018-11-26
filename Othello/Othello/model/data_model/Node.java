package model.data_model;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data;

    private List<Node<T>> children;

    private Node<T> parent = null;

    private int depth = 0;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public Node getRoot() {
        Node<T> root = parent;

        while(root != null){
            root = root.parent;
        }
        return root;
    }

    public void addChild(Node<T> child) {
        //add the node to the ArrayList
        //child.setParent(this);
        this.children.add(child);
    }

    public List<Node<T>> getChildren() {return this.children;}

    public T getData() {return this.data;}

    public int getDepth(){return depth;}

    public void setDepth(int depth){this.depth = depth;}
}

