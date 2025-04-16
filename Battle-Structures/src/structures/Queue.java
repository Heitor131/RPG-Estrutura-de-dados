package structures;

import model.entities.*;

public class Queue {
    private Node<Entity> head;
    private Node<Entity> tail;

    public void enqueue(Entity newEntity){
        Node<Entity> newNode = new Node<>(newEntity);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.prev = newNode;
        newNode.next = tail;
        tail = newNode;
    
    }

    public Entity dequeue(){
        if (isEmpty()){
            throw  new RuntimeException("Queue is empty. Cannot dequeue.");
        }
        if (head == tail){
            Entity temp = head.data;
            head = null;
            tail = null;
            return temp;
        }
        Node<Entity> temp = head;
        head = head.prev;
        head.next = null;
        temp.prev = null;

        return  temp.data;
    }

    public Entity getElementbyIndex(int index){
        if (isEmpty()){
            throw new RuntimeException("Queue is empty. Cannot get item by index.");
        }
        Node<Entity> current = head;
        for (int i = 0; i < index; i++){
            if (current == null){
                throw new RuntimeException("Index out of bounds.");
            }
            current = current.prev;
        }
        return current.data;
    }

    public Entity peekEntity(){
        return  head.data;
    }

    public Boolean isEmpty() {
        return head == null;
    }

    public Node<Entity> getHead() {
        return head;
    }

    public void setHead(Node<Entity> head) {
        this.head = head;
    }

    public Node<Entity> getTail() {
        return tail;
    }

    public void setTail(Node<Entity> tail) {
        this.tail = tail;
    }

}
