package de.jordan.bankkonto.model;

import java.math.BigDecimal;

public class Bankkonto {
    private final String kontonummer;
    private final String kontoinhaber;
    private BigDecimal kontostand;
    
    public Bankkonto(
            String kontonummer,
            String kontoinhaber,
            BigDecimal kontostand) {
        
        this.kontonummer = kontonummer;
        this.kontoinhaber = kontoinhaber;
        this.kontostand= kontostand;
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
}
