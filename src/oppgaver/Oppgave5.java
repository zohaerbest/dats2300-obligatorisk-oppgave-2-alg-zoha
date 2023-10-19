package oppgaver;

import java.util.Objects;
import java.util.StringJoiner;

public class Oppgave5<T> {
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

    public Oppgave5() {
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public void leggInn(T verdi) {
        leggInn(antall, verdi);
    }

    public void leggInn(int indeks, T verdi) {
        indeksKontroll(indeks, true); // Sjekk for lovlig indeks

        Objects.requireNonNull(verdi); // Kast NullPointerException for null-verdier

        if (indeks == 0) {
            // Legg til først i listen
            Node nyNode = new Node(verdi, null, hode);
            hode = nyNode;

            if (tom()) {
                hale = nyNode;
            } else {
                hode.neste.forrige = nyNode;
            }
        } else if (indeks == antall) {
            // Legg til sist i listen
            Node nyNode = new Node(verdi, hale, null);
            hale = nyNode;

            if (tom()) {
                hode = nyNode;
            } else {
                hale.forrige.neste = nyNode;
            }
        } else {
            // Legg til i midten av listen
            Node current = finnNode(indeks);
            Node nyNode = new Node(verdi, current.forrige, current);
            current.forrige = nyNode;
            nyNode.forrige.neste = nyNode;

            if (nyNode.forrige == null) {
                hode = nyNode;
            } else if (nyNode.neste == null) {
                hale = nyNode;
            }
        }

        antall++;
        endringer++;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        Node current = hode;
        while (current != null) {
            joiner.add(current.verdi.toString());
            current = current.neste;
        }
        return joiner.toString();
    }

    private Node finnNode(int indeks) {
        if (indeks < antall / 2) {
            Node current = hode;
            for (int i = 0; i < indeks; i++) {
                current = current.neste;
            }
            return current;
        } else {
            Node current = hale;
            for (int i = antall - 1; i > indeks; i--) {
                current = current.forrige;
            }
            return current;
        }
    }

    private void indeksKontroll(int indeks, boolean leggInn) {
        if (leggInn) {
            if (indeks < 0 || indeks > antall) {
                throw new IndexOutOfBoundsException("Ulovlig indeks: " + indeks);
            }
        } else {
            if (indeks < 0 || indeks >= antall) {
                throw new IndexOutOfBoundsException("Ulovlig indeks: " + indeks);
            }
        }
    }

    public static void main(String[] args) {
        Oppgave5<Integer> liste = new Oppgave5<>();
        liste.leggInn(1);
        liste.leggInn(2);
        liste.leggInn(3);

        System.out.println("Opprinnelig liste: " + liste);

        liste.leggInn(1, 42);
        System.out.println("Verdi på indeks 2 oppdatert fra 3 til 42: " + liste);
    }
}



