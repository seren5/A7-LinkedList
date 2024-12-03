/**
 * Class to implement a singly linked list
 *
 * @author 
 * @version Spring 2024
 */

 public class SLL<T> implements Phase1SLL<T>, Phase2SLL<T> {
    private NodeSL<T> head;  // Head of the list

    /** Constructor: Initializes an empty list (head is null) */
    public SLL() {
        this.head = null;
    }

    // Phase 1 Methods

    /** 
     * Accessor for the head node.
     * @return the head node of the list
     */
    public NodeSL<T> getHead() {
        return this.head;
    }

    /** 
     * Accessor for the tail node.
     * @return the tail node of the list, or null if the list is empty
     */
    public NodeSL<T> getTail() {
        if (this.head == null) {
            return null;
        }
        NodeSL<T> current = this.head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    /** 
     * Determines whether the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /** 
     * Inserts the given item at the head of the list.
     * @param v the item to insert
     */
    public void addFirst(T v) {
        NodeSL<T> newNode = new NodeSL<>(v, this.head);
        this.head = newNode;
    }

    /** 
     * Converts the list to a string representation.
     * @return a string representing the list in the format [item1, item2, ...]
     */
    public String toString() {
        if (this.head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        NodeSL<T> current = this.head;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    // Phase 2 Methods

    /** 
     * Inserts the given item at the tail of the list.
     * @param v the item to insert
     */
    public void addLast(T v) {
        if (this.head == null) {
            addFirst(v);  // If the list is empty, add to the front
        } else {
            NodeSL<T> newNode = new NodeSL<>(v, null);
            NodeSL<T> current = this.head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    /** 
     * Inserts the given item after the specified node.
     * @param here the node to insert after
     * @param v the item to insert
     * @throws SelfInsertException if the node is inserted after itself
     */
    public void addAfter(NodeSL<T> here, T v) {
        if (here == null) {
            throw new IllegalArgumentException("The Node cannot be null.");
        }
        if (here == here.getNext()) {
            throw new SelfInsertException();  // Node cannot be inserted after itself
        }
        NodeSL<T> newNode = new NodeSL<>(v, here.getNext());
        here.setNext(newNode);
    }

    /** 
     * Removes the node at the head of the list and returns its value.
     * @return the item that was removed from the head of the list
     * @throws MissingElementException if the list is empty
     */
    public T removeFirst() {
        if (this.head == null) {
            throw new MissingElementException();  // No element to remove
        }
        T value = this.head.getData();
        this.head = this.head.getNext();
        return value;
    }

    /** 
     * Removes the last node from the list and returns its value.
     * @return the item that was removed from the tail of the list
     * @throws MissingElementException if the list is empty
     */
    public T removeLast() {
        if (this.head == null) {
            throw new MissingElementException();  // No element to remove
        }
        if (this.head.getNext() == null) {
            return removeFirst();  // If only one node exists, remove it as the first node
        }
        NodeSL<T> current = this.head;
        while (current.getNext() != null && current.getNext().getNext() != null) {
            current = current.getNext();
        }
        T value = current.getNext().getData();
        current.setNext(null);  // Remove the last node
        return value;
    }

    /** 
     * Removes the node after the given node and returns its value.
     * @param here the node to remove the next node after
     * @return the item that was removed after the specified node
     * @throws MissingElementException if there is no node after the given node
     */
    public T removeAfter(NodeSL<T> here) {
        if (here == null || here.getNext() == null) {
            throw new MissingElementException();  // No node to remove after 'here'
        }
        NodeSL<T> toRemove = here.getNext();
        T value = toRemove.getData();
        here.setNext(toRemove.getNext());  // Skip over the node to remove
        return value;
    }

    /** 
     * Returns the size (count of elements) in the list.
     * @return the current number of nodes in the list
     */
    public int size() {
        int count = 0;
        NodeSL<T> current = this.head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    /**
     * Copy Constructor
     */
    public SLL(SLL<T> other) {
        if (other.head == null) {
            this.head = null;
        } else {
            this.head = new NodeSL<>(other.head.getData(), null);
            NodeSL<T> currentOther = other.head.getNext();
            NodeSL<T> currentNew = this.head;

            while (currentOther != null) {
                NodeSL<T> newNode = new NodeSL<>(currentOther.getData(), null);
                currentNew.setNext(newNode);
                currentNew = newNode;
                currentOther = currentOther.getNext();
            }
        }
    }

    /**
     * Subsequence by Copy
     */
    public SLL<T> subseqByCopy(NodeSL<T> here, int n) {
        if (here == null) {
            throw new MissingElementException();
        }

        SLL<T> subList = new SLL<>();
        NodeSL<T> current = here;

        for (int i = 0; i < n && current != null; i++) {
            subList.addLast(current.getData());
            current = current.getNext();
        }

        if (current != null) {
            throw new MissingElementException();
        }

        return subList;
    }

    /**
     * Splice by Copy
     */
    public void spliceByCopy(SLL<T> list, NodeSL<T> afterHere) {
        if (list.head == null) {
            return;
        }

        NodeSL<T> current = list.head;
        while (current != null) {
            addAfter(afterHere, current.getData());
            current = current.getNext();
            afterHere = afterHere.getNext();
        }
    }

    /**
     * Subsequence by Transfer
     * @param afterHere
     * @param toHere
     * @return transferredList (the new extracted list)
     */
    public SLL<T> subseqByTransfer(NodeSL<T> afterHere, NodeSL<T> toHere) {
        if (afterHere == null) {
            throw new MissingElementException();
        }

        SLL<T> transferredList = new SLL<>();
        NodeSL<T> current = afterHere.getNext();

        while (current != null && current != toHere.getNext()) {
            transferredList.addLast(current.getData());
            current = current.getNext();
        }

        if (toHere != null) {
            afterHere.setNext(toHere.getNext());
        }

        return transferredList;
    }

    /**
     * Splice by Transfer
     * @param list
     * @param afterHere
     */
    public void spliceByTransfer(SLL<T> list, NodeSL<T> afterHere) {
        if (list.head == null) {
            return;
        }

        NodeSL<T> current = list.head;
        while (current != null) {
            NodeSL<T> nextNode = current.getNext();
            current.setNext(null);
            addAfter(afterHere, current.getData());
            current = nextNode;
            afterHere = afterHere.getNext();
        }
        list.head = null;
    }
}
        