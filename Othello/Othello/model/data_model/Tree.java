package model.data_model;

public class Tree {

    private Node<String> root;

    public Tree(){

        //create the Tree
        this.root = new Node<>("root");

        Node<String> node1 = this.root.addChild(new Node<>("Joseph"));

    }

    //method to print the tree
    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> {
            printTree(each, appender + appender);
        });
    }

    public Node<String> getRoot(){return root;}
}
