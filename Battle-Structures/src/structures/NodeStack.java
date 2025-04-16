package structures;

public class NodeStack<T> {
    public NodeStack<T> prev;
    public T data;

    public NodeStack(T data){
        prev = null;
        this.data = data;
    } 

}
