package com.example.demo.application.flows;

import lombok.Data;

@Data
class TreeNode<T> {
    private T data;
    private boolean terminal;
    private TreeNode<T> parent;
    private TreeNode<T> trueChild;
    private TreeNode<T> falseChild;
    private TreeNode<T> nextChild;

    public TreeNode(T data) {
        this.data = data;
    }

}
