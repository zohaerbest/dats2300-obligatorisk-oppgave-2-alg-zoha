package oppgaver;

import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T> {
    private Node hode;
    private Node hale;
    private int antall;
    private int endringer;

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

    public DobbeltLenketListe() {
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    @Override
    public int size() {
        return antall;
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node denne;
        private boolean kanFjerne;
        private int forventedeEndringer;

        public DobbeltLenketListeIterator() {
            denne = hode;
            kanFjerne = false;
            forventedeEndringer = endringer;
        }

        public DobbeltLenketListeIterator(int indeks) {
            if (indeks < 0 || indeks >= antall) {
                throw new IndexOutOfBoundsException("Ulovlig indeks: " + indeks);
            }
            denne = hentNode(indeks);
            kanFjerne = false;
            forventedeEndringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if (forventedeEndringer != endringer) {
                throw new ConcurrentModificationException("Endringer i listen er ikke tillatt under iterasjon.");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Ingen flere elementer i listen.");
            }
            T verdi = denne.verdi;
            denne = denne.neste;
            kanFjerne = true;
            return verdi;
        }

        @Override
        public void remove() {
            if (!kanFjerne) {
                throw new IllegalStateException("Fjerning er ikke tillatt i denne tilstanden.");
            }
            if (forventedeEndringer != endringer) {
                throw new ConcurrentModificationException("Endringer i listen er ikke tillatt under iterasjon.");
            }
            Node fjernes = denne.forrige;
            if (fjernes == hode) {
                hode = denne;
            } else {
                fjernes.forrige.neste = denne;
            }
            if (denne == null) {
                hale = fjernes.forrige;
            } else {
                denne.forrige = fjernes.forrige;
            }
            fjernes.verdi = null;
            antall--;
            endringer++;
            kanFjerne = false;
            forventedeEndringer++;
        }
    }

    // Andre nødvendige metoder og klasser for å fullføre DobbeltLenketListe

    // ... (Legg til andre metoder som kreves for å fullføre DobbeltLenketListe)

    private Node hentNode(int indeks) {
        indeksKontroll(indeks);
        if (indeks < antall / 2) {
            Node node = hode;
            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }
            return node;
        } else {
            Node node = hale;
            for (int i = antall - 1; i > indeks; i--) {
                node = node.forrige;
            }
            return node;
        }
    }

    private void indeksKontroll(int indeks) {
        if (indeks < 0 || indeks >= antall) {
            throw new IndexOutOfBoundsException("Ulovlig indeks: " + indeks);
        }
    }
}


