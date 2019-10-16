public class Nodo<T> {
    private Nodo<T> next;
    private T element;

    public void Node(T element){
        setElement(element);
    }

    public Nodo<T> getNext() {
        return next;
    }

    public void setNext(Nodo<T> next) {
        this.next = next;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}
