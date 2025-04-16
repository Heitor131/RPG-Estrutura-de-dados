package structures;

import model.entities.Entity;

public class Stack {
    private NodeStack<Entity> head;
    private int length;

    public Stack(){
        head = null;
        length = 0;
    }

    public void push(Entity data) {
        NodeStack<Entity> newNode = new NodeStack<>(data);
        newNode.prev = head; 
        head = newNode;
        length++;
    }

    public Entity pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty. Cannot pop.");
        }
        Entity data = head.data; 
        head = head.prev; 
        length--;
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return length;
    }

    public Entity peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data; 
    }

    public void clear() {
        head = null;
        length = 0;
    }

    public NodeStack<Entity> getHead() {
        return head;
    }

    

}
