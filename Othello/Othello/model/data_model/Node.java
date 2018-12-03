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
        this.children.add(child);
        child.setParent(this); //Hong: Need this for mcts
    }

    public List<Node<T>> getChildren() {return this.children;}

    public T getData() {return this.data;}

    public int getDepth(){return depth;}

    public void setDepth(int depth){this.depth = depth;}

    public void setParent(Node<T> parent) {this.parent = parent;} //Hong: Need this for mcts

    public Node<T> getParent() {return this.parent;} //Hong: Need this for mcts

}

