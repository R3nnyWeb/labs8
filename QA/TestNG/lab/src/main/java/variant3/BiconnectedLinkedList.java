package variant3;


// null <> (*head) x1 <-> x2 <-> x3 (*tail) <> null // next ->
public class BiconnectedLinkedList<T> {
    private Element<T> head = null;
    private Element<T> tail = null;

    public BiconnectedLinkedList() {
    }

    /**
     * Добавляет элемент в конец списка
     */
    void addInEnd(T value) {
        Element<T> newElem = new Element<>(value);
        if (head == null) {
            head = tail = newElem;
        } else {
            tail.next = newElem;
            newElem.prev = tail;
            tail = newElem;
        }
    }

    /**
     * Добавляет элемент в начало списка
     */
    void addInBeginning(T value) {
        Element<T> newElem = new Element<>(value);
        if (head == null) {
            head = tail = newElem;
        } else {
            head.prev = newElem;
            newElem.next = head;
            //head = null;
            head = newElem;
        }
    }

    /**
     * Возвращает количество элементов
     */
    int getElementsNumber() {
        int counter = 0;
        Element<T> temp = head;
        while (temp != null) {
            temp = temp.next;
            counter++;
        }
        return counter;
    }

    /**
     * Удаляет список
     */
    void deleteList() {
        head = null;
    }

    /**
     * Удаляет первый элемент
     */
    public T deleteFirst() {
        if (head != null) {
            T value = head.value;
            if (head.next == null) {
                // элемент первый
                head = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            return value;
        } else {
            throw new NullPointerException("You're trying to " +
                    "get element from empty biconnected linked list!");
        }
    }

    /**
     * Удаляет последний элемент
     */
    public T deleteLast() {
        if (head != null) {
            if (head.next == null) {
                // элемент единственный
                T value = head.value;
                head = tail = null;
                return value;
            } else {
                T value = tail.value;
                tail = tail.prev;
                tail.next = null;
                return value;
            }
        } else {
            throw new NullPointerException("You're trying to " +
                    "get element from empty biconnected linked list!!");
        }
    }

    /**
     * Проверка пустоты списка
     */
    boolean isEmpty() {
        return head == null;
    }

    private class Element<T> {
        Element<T> next;
        Element<T> prev;
        T value;

        Element(T value) {
            this.value = value;
        }
    }
}
