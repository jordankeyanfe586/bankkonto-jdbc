package de.jordan.bankkonto.service;

import de.jordan.bankkonto.model.Bankkonto;
import de.jordan.bankkonto.repository.BankkontoRepository;

import java.util.List;

public class BankkontoService {
    private final BankkontoRepository repository;
    
    public BankkontoService(BankkontoRepository repository) {
        this.repository = repository;
    }

    public void bankkontoErstellen(Bankkonto bankkonto) {
        boolean existiertBereits = repository.findeNachKontonummer(bankkonto.getKontonummer()).isPresent();

        if(existiertBereits) {
            throw new IllegalArgumentException("Ein Konto mit diesem Kontonummer existiert bereits.");
        }

        repository.speichern(bankkonto);
    }

    public Bankkonto bankkontoFinden(String Kontonummer) {
        return repository.findeNachKontonummer(Kontonummer).orElseThrow(
            () -> new IllegalArgumentException("Kein Bankkonto mit diesem kontonummer gefunden."));
    }

    public List<Bankkonto> alleBankkonten() {
        return repository.findeAlle();
    }

    public void kontoinhaberAendern(String kontonummer, String neuerKontoinhaber) {
        Bankkonto bankkonto = bankkontoFinden(kontonummer);
        bankkonto.kontoinhaberAendern(neuerKontoinhaber);
        repository.aktualisieren(bankkonto);
    }

    public boolean bankkontoLoeschen(String kontonummer) {
        return repository.loeschenNachKontonummer(kontonummer);
    }
}
