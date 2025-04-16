package utils;

import java.io.Serializable;

public class Stack<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> top;
    private int size;
    
    public Stack() {
        top = null;
        size = 0;
    }
    
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    public T pop() {
        if (top == null) {
            return null;
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int size() {
        return size;
    }
    
    public T peek() {
        return top != null ? top.data : null;
    }
}