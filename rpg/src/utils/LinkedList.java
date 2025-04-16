package utils;

import java.io.Serializable;

public class LinkedList<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    public boolean remove(T data) {
        Node<T> current = head;
        Node<T> previous = null;
        
        while (current != null) {
            if (current.data.equals(data)) {
                if (previous != null) {
                    previous.next = current.next;
                    if (current == tail) {
                        tail = previous;
                    }
                } else {
                    head = current.next;
                    if (head == null) {
                        tail = null;
                    }
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
    
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    public int size() {
        return size;
    }
    
    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}