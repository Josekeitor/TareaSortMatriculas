import java.util.ArrayList;



public class LinkedList<T extends Comparable<T>> {
public Nodo<T> initial;



    public boolean isEmpty(){
    return initial == null;

}

public void insertAtStart(T element){
    Nodo<T> nodoToInsert = new Nodo<>();
    nodoToInsert.setElement(element);
    if(initial == null){
        initial = nodoToInsert;
    }else{
        Nodo<T> temp = initial;
        initial = nodoToInsert;
        initial.setNext(temp);
    }

}

public void insertAtEnd(T element){
    Nodo<T> nodoToInsert = new Nodo<>();
    nodoToInsert.setElement(element);
    if(isEmpty()){
        initial= nodoToInsert;
    }else{
        Nodo<T> temp = initial;
        while (temp.getNext()!= null){
            temp = temp.getNext();
        }

        temp.setNext(nodoToInsert);
    }
}

public int size(){
    if(isEmpty()){
        return 0;
    }else{
        int count = 0;
        Nodo<T> temp =initial;
        while (temp != null){
            temp = temp.getNext();
            count++;
        }
        return count;
    }
}

public void removeLastElement(){
    removeAt(size()-1);
}

public void removeFirstElement(){
    removeAt(0);
}

public void removeAt(int index){
    if (isEmpty()){
        System.out.println("You cannot remove elements from an empty list");
    }else if(index == 0) {
        initial= initial.getNext();
    }else if(index>=size()){
        System.out.println("The index you provided is out of bounds");
    }else {
        Nodo<T> temp= initial;
        int count= 0;

        for(int i = 0; i<index-1; i++){
            temp=temp.getNext();
        }

        temp.setNext(temp.getNext().getNext());

    }
}

public void print(){
    if(isEmpty()){
        System.out.println("The list is empty");
    }else{
        Nodo<T> temp = initial;
        while (temp != null){
            System.out.println(temp.getElement());
            temp = temp.getNext();
        }
    }
}

public T getElementAt(int index){
    T element = null;
    if (isEmpty()){
        System.out.println("The list is empty");
    }else if(index == 0) {
        element= initial.getElement();
    }else if(index>=size()){
        System.out.println("The index you provided is out of bounds");
    }else{
        Nodo<T> temp= initial;


        for(int i = 0; i<index; i++){
            temp=temp.getNext();
        }

        element = temp.getElement();
    }
    return element;
}

public T getFirstElement(){
    return getElementAt(0);
}

public T getLastElement(){
    return getElementAt(size()-1);
}

public Nodo<T> getNodeAt(int index){
    Nodo<T> nodo = null;
    if (isEmpty()){
        System.out.println("The list is empty");
    }else if(index == 0) {
        nodo = initial;
    }else if(index>=size()){
        System.out.println("The index you provided is out of bounds");
    }else{
        Nodo<T> temp= initial;


        for(int i = 0; i<index; i++){
            temp=temp.getNext();
        }

        nodo = temp;
    }
    return nodo;
}

public void setElementAt( int index, T element) {
    if (isEmpty()) {
        System.out.println("The list is empty");
    } else if (size() - 1 < index) {
        System.out.println("The index you provided is out of bounds");
    } else {
        getNodeAt(index).setElement(element);

    }
}



public void bubbleSort(){
    boolean swap = true;
    while (swap == true) {

        for (int i = 0; i < size() - 1; i++) {
            for( int j=i; j<size(); j++){
                if (getElementAt(i).compareTo(getElementAt(j)) > 0) {
                    swap(i, j);
                } else {
                    swap = false;
                }
            }

        }
    }
}

public void selectionSort(){
    for(int i=0; i<size(); i++){
        int minElementIndex = i ;
        for(int j=0; j<size(); j++){
            if(getElementAt(j).compareTo(getElementAt(minElementIndex))<0){
                minElementIndex = j;
            }
        }
        swap(minElementIndex,i);

    }


}
public void insertionSort(){
    int i=1;
    while (i<size()){
        int j=i;
        while(j>0 && getElementAt(j-1).compareTo(getElementAt(j))>0){
            swap(j-1,j);
            j--;
        }
        i++;
    }
}


public void swap(int index, int index1 ){

   Nodo<T> temp = getNodeAt(index);
   Nodo<T> temp2 = getNodeAt(index1);
   T tempElement = temp2.getElement();
   temp2.setElement(temp.getElement());
   temp.setElement(tempElement);



}

public void mergeSort(){
    initial = mergeSort(getNodeAt(0));
}
    public int count;
public Nodo<T> mergeSort(Nodo<T> initial){
    Nodo<T> start = initial;
    count++;

    int mid = countSize(initial)/2;
    if(start.getNext() == null){
        return start;
    }
    while(mid-1>0){
        start = start.getNext();
        mid--;
    }
    Nodo<T> newStart = start.getNext();

    start.setNext(null);
    start = initial;

    Nodo<T> temp1 = mergeSort(start);
    Nodo<T> temp2 = mergeSort(newStart);
    return merge(temp1,temp2);

}

public Nodo<T> merge(Nodo<T> a, Nodo<T> b){
    Nodo<T> result;
    if(a == null){
        result = b;
        return result;
    }
    if(b == null){
        result = a;
        return result;
    }
    if(a.getElement().compareTo(b.getElement())>0){
        result = b;
        result.setNext(merge(a,b.getNext()));

    }else{
        result = a;
        result.setNext(merge(a.getNext(),b));
    }
    return result;
}

public void quickSort(){
    quickSort(0,size()-1);
}

private void quickSort(int left, int right){
    int position = (left+right)/2;
    T pivot =  getElementAt(position);
    int i = left;
    int j = right;
    while (i<=j){
        while (getElementAt(i).compareTo(pivot)<0){
            i++;
        }
        while(getElementAt(j).compareTo(pivot)>0){
            j--;
        }
        if(i <= j){
            if(i != j){
                swap(i,j);
            }
            i++;
            j--;
        }
    }
    if(left < j) {
        quickSort(left, j);
    }
    if(i < right) {
        quickSort(i, right);
    }

}

public int countSize(Nodo<T> initial){
    Nodo<T> temp = initial;
    int count = 0;
    while (! (temp.getNext() == null)){
        count++;
        temp = temp.getNext();
    }
    return count;
}

public ArrayList<T> toArrayList(){
    ArrayList<T> list = new ArrayList<>();
    int i=0;
    while (i<size()){
        list.add(getElementAt(i));
        i++;
    }
    return list;
}

public int find(T element){
    int i=0;
    while (i<size()){
        if(getElementAt(i).equals(element)){
            break;
        }
        i++;
    }
    return i;
}
}
