package oppgaver;

import java.util.Objects;

public class oppgave2<T> {
    private Node hode; // Peker til første node
    private Node hale; // Peker til siste node
    private int antall; // Antall elementer i listen
    private int endringer; // Antall endringer i listen

    private class Node {
        T verdi;
        Node forrige;
        Node neste;

        Node(T verdi, Node forrige, Node neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }
    }

    public oppgave2() {
        // Konstruktør for en tom liste
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public oppgave2(T[] a) {
        // Konstruktør for en liste fra et array a
        Objects.requireNonNull(a);

        for (T element : a) {
            if (element != null) {
                leggInn(element);
            }
        }
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public void leggInn(T verdi) {
        Objects.requireNonNull(verdi);

        Node nyNode = new Node(verdi, hale, null);
        if (tom()) {
            hode = nyNode;
        } else {
            hale.neste = nyNode;
        }
        hale = nyNode;
        antall++;
        endringer++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = hode;
        while (current != null) {
            sb.append(current.verdi);
            if (current.neste != null) {
                sb.append(", ");
            }
            current = current.neste;
        }
        sb.append("]");
        return sb.toString();
    }

    public String omvendtString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = hale;
        while (current != null) {
            sb.append(current.verdi);
            if (current.forrige != null) {
                sb.append(", ");
            }
            current = current.forrige;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Eksempel på bruk av oppgave2
        Integer[] a = {1, 2, null, 3, 4};
        oppgave2<Integer> liste = new oppgave2<>(a);
        System.out.println("toString(): " + liste.toString());
        System.out.println("omvendtString(): " + liste.omvendtString());
        System.out.println("Endringer: " + liste.endringer);
    }
}

