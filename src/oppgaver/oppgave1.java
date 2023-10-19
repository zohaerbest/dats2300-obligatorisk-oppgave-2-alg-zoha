package oppgaver;

import java.util.Objects;

public class oppgave1<T> {
    private Node hode; // Peker til første node
    private Node hale; // Peker til siste node
    private int antall; // Antall elementer i listen

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

    public oppgave1() {
        // Konstruktør for en tom liste
        hode = null;
        hale = null;
        antall = 0;
    }

    public oppgave1(T[] a) {
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
        Node nyNode = new Node(verdi, hale, null);
        if (tom()) {
            hode = nyNode;
        } else {
            hale.neste = nyNode;
        }
        hale = nyNode;
        antall++;
    }

    public static void main(String[] args) {
        // Eksempel på bruk av oppgave1
        Integer[] a = {1, 2, null, 3, 4};
        oppgave1<Integer> liste = new oppgave1<>(a);
        System.out.println("Antall elementer: " + liste.antall());
        System.out.println("Er listen tom? " + liste.tom());
    }
}


