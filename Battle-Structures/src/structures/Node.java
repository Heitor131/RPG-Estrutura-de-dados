package structures;

public class Node<T> {
    public Node<T> next;
    public Node<T> prev;
    public T data;

    public Node(T data){
        next = null;
        prev = null;
        this.data = data;
    }
}
