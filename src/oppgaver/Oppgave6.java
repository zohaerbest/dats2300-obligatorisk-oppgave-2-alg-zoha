package oppgaver;

import java.util.Objects;

public class Oppgave6<T> {
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

    public Oppgave6() {
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

    public T fjern(int indeks) {
        indeksKontroll(indeks, false); // Sjekk for lovlig indeks

        Node current = finnNode(indeks);
        T verdi = current.verdi;

        if (antall == 1) {
            hode = null;
            hale = null;
        } else if (indeks == 0) {
            hode = current.neste;
            hode.forrige = null;
        } else if (indeks == antall - 1) {
            hale = current.forrige;
            hale.neste = null;
        } else {
            current.forrige.neste = current.neste;
            current.neste.forrige = current.forrige;
        }

        current.neste = null;
        current.forrige = null;

        antall--;
        endringer++;

        return verdi;
    }


    public boolean fjern(T verdi) {
        Node current = hode;
        int indeks = 0;

        while (current != null) {
            if (current.verdi.equals(verdi)) {
                fjern(indeks);
                return true;
            }

            current = current.neste;
            indeks++;
        }

        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node current = hode;
        while (current != null) {
            builder.append(current.verdi);
            if (current.neste != null) {
                builder.append(", ");
            }
            current = current.neste;
        }
        builder.append("]");
        return builder.toString();
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
        Oppgave6<Integer> liste = new Oppgave6<>();
        liste.leggInn(1);
        liste.leggInn(2);
        liste.leggInn(3);

        System.out.println("Opprinnelig liste: " + liste);

        int fjernet = liste.fjern(1);
        System.out.println("Fjernet verdi på indeks 1: " + fjernet);
        System.out.println("Ny liste etter fjerning: " + liste);

        Integer fjernetVerdi = liste.fjern(42); // Endre datatypen til Integer
        System.out.println("Fjernet verdi 42: " + fjernetVerdi);
        System.out.println("Ny liste etter fjerning: " + liste);
    }
}


