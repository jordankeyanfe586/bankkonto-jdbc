package de.jordan.bankkonto.model;

import java.math.BigDecimal;

public class Bankkonto {
    private final String kontonummer;
    private String kontoinhaber;
    private BigDecimal kontostand;
    
    public Bankkonto(
            String kontonummer,
            String kontoinhaber,
            BigDecimal kontostand) {
        
        this.kontonummer = kontonummer;
        this.kontoinhaber = kontoinhaber;
        this.kontostand= kontostand;
    }

    public void kontoinhaberAendern(String neuerKontoinhaber) {
        if(neuerKontoinhaber == null || neuerKontoinhaber.isBlank()) {
            throw new IllegalArgumentException(
                "Der Kontoinhaber darf nicht leer sein."
            );
        }

        this.kontoinhaber = neuerKontoinhaber;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public String getKontoinhaber() {
        return kontoinhaber;
    }

    public BigDecimal getKontostand() {
        return kontostand;
    }

    @Override
    public String toString(){
        return "Bankkonto{Kontonummer = '"+kontonummer+
            "', Kontonummer = '"+kontoinhaber+
            "', KontoStand = "+kontostand+"}";
    }
}
