package com.lin.flow.base;

public class LinkedList<E> {

    private Node head;
    private int size;


    public void addNode(E e) {
        if (head == null) {
            head = new Node(e);
            return;
        }
        Node tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = new Node(e);
    }

    public boolean deleteNode(Node n) {
        if (n == null || n.next == null)
            return false;
        E tmp = n.e;
        n.e = n.next.e;
        n.next.e = tmp;
        n.next = n.next.next;
        return true;
    }

    public int length() {
        int length = 0;
        Node tmp = head;
        while (tmp != null) {
            length++;
            tmp = tmp.next;
        }
        return length;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    class asyncNode {
        public E e;
        public Node next;


    }

    class Node {
        public E e;
        public Node next;

        public Node(E e) {
            this.e = e;
        }
    }
}
