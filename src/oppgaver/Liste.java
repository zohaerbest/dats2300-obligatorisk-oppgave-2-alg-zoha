package oppgaver;

import java.util.Iterator;

public interface Liste<T> {
    Iterator<T> iterator();

    int size();
}
