package utils;

import java.io.Serializable;

public class Queue<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> front;
    private Node<T> rear;
    private int size;
    
    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }
    
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    
    public T dequeue() {
        if (front == null) {
            return null;
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
    
    public int size() {
        return size;
    }
    
    public T peek() {
        return front != null ? front.data : null;
    }
}