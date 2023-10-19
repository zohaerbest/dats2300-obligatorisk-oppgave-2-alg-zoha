package oppgaver;

import java.util.Objects;

public class oppgave4<T> {
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

    public oppgave4() {
        // Konstruktør for en tom liste
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public oppgave4(T[] a) {
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

    private Node finnNode(int indeks) {
        if (indeks < 0 || indeks >= antall) {
            throw new IndexOutOfBoundsException("Ulovlig indeks: " + indeks);
        }

        Node current;
        if (indeks < antall / 2) {
            current = hode;
            for (int i = 0; i < indeks; i++) {
                current = current.neste;
            }
        } else {
            current = hale;
            for (int i = antall - 1; i > indeks; i--) {
                current = current.forrige;
            }
        }

        return current;
    }

    private void indeksKontroll(int indeks) {
        if (indeks < 0 || indeks >= antall) {
            throw new IndexOutOfBoundsException("Ulovlig indeks: " + indeks);
        }
    }

    public T hent(int indeks) {
        indeksKontroll(indeks);
        Node node = finnNode(indeks);
        return node.verdi;
    }

    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks);
        Node node = finnNode(indeks);
        T gammelVerdi = node.verdi;
        node.verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }

    private void fraTilKontroll(int fra, int til) {
        if (fra < 0 || til > antall || fra > til) {
            throw new IllegalArgumentException("Ulovlig indeksintervall: [" + fra + ", " + til + ")");
        }
    }

    public oppgave4<T> subliste(int fra, int til) {
        fraTilKontroll(fra, til);
        oppgave4<T> subListe = new oppgave4<>();
        Node current = finnNode(fra);
        for (int i = fra; i < til; i++) {
            subListe.leggInn(current.verdi);
            current = current.neste;
        }
        return subListe;
    }

    public int indeksTil(T verdi) {
        if (verdi == null) {
            return -1;
        }

        Node current = hode;
        for (int indeks = 0; indeks < antall; indeks++) {
            if (verdi.equals(current.verdi)) {
                return indeks;
            }
            current = current.neste;
        }

        return -1;
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public String toString() {
        if (tom()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node current = hode;
        sb.append(current.verdi);

        while (current.neste != null) {
            current = current.neste;
            sb.append(", ");
            sb.append(current.verdi);
        }

        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5};
        oppgave4<Integer> liste = new oppgave4<>(a);
        System.out.println("Opprinnelig liste: " + liste.toString());

        int indeks = 2;
        int nyVerdi = 42;
        int gammelVerdi = liste.oppdater(indeks, nyVerdi);
        System.out.println("Verdi på indeks " + indeks + " oppdatert fra " + gammelVerdi + " til " + nyVerdi);

        int fra = 1;
        int til = 4;
        oppgave4<Integer> subListe = liste.subliste(fra, til);
        System.out.println("Subliste fra " + fra + " til " + til + ": " + subListe.toString());

        int søktVerdi = 3;
        System.out.println("Finner verdi " + søktVerdi + " på indeks " + liste.indeksTil(søktVerdi));
        System.out.println("Inneholder verdi " + søktVerdi + "? " + liste.inneholder(søktVerdi));
    }
}

