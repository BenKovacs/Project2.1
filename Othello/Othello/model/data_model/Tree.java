package model.data_model;

import java.util.ArrayList;
import model.data_model.Node;

public class Tree<T> {

    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>(rootData);
    }

    public Node<T> getRoot() {
        return root;
    }

}