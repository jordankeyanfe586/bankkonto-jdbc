package de.jordan.bankkonto.repository;

import de.jordan.bankkonto.model.Bankkonto;

import java.util.List;
import java.util.Optional;

public interface BankkontoRepository {

    void speichern(Bankkonto bankkonto);

    Optional<Bankkonto> findeNachKontonummer(String kontonummer);

    List<Bankkonto> findeAlle();
    
    void aktualisieren(Bankkonto bankonto);

    boolean loeschenNachKontonummer(String kontonummer);
}
