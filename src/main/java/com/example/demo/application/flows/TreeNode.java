package com.example.demo.application.flows;

class TreeNode<T> {
    private T data;
    private TreeNode<T> trueChild;
    private TreeNode<T> falseChild;
    private TreeNode<T> nextChild;

    public TreeNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getTrueChild() {
        return trueChild;
    }

    public void setTrueChild(TreeNode<T> trueChild) {
        this.trueChild = trueChild;
    }

    public TreeNode<T> getFalseChild() {
        return falseChild;
    }

    public void setFalseChild(TreeNode<T> falseChild) {
        this.falseChild = falseChild;
    }

    public TreeNode<T> getNextChild() {
        return nextChild;
    }

    public void setNextChild(TreeNode<T> nextChild) {
        this.nextChild = nextChild;
    }
}
