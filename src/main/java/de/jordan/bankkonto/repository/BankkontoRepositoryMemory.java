package de.jordan.bankkonto.repository;

import de.jordan.bankkonto.model.Bankkonto;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class BankkontoRepositoryMemory implements BankkontoRepository {
    
    private final List<Bankkonto> bankkonten = new ArrayList<>();

    @Override
    public void speichern(Bankkonto bankkonto) {
        bankkonten.add(bankkonto);
    }

    @Override
    public Optional<Bankkonto> findeNachKontonummer(String kontonummer) {
        return bankkonten.stream()
            .filter(bankonto -> bankonto.getKontonummer().equals(kontonummer))
            .findFirst();
    }

    @Override
    public List<Bankkonto> findeAlle() {
        return new ArrayList<>(bankkonten);
    }

    @Override
    public void aktualisieren(Bankkonto bankkonto) {

    }

    @Override
    public boolean loeschenNachKontonummer(String kontonummer) {
        return bankkonten.removeIf(bankkonto -> bankkonto.getKontonummer().equals(kontonummer));
    }
}
