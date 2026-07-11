package de.jordan.bankkonto.repository;

import de.jordan.bankkonto.model.Bankkonto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BankkontoRepositoryJdbcTest {
    private Connection connection;
    private BankkontoRepository repository;

    @BeforeEach
    void vorbereiten() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try(Statement statement = connection.createStatement()) {
            statement.execute("""
                DROP TABLE IF EXISTS bankkonto
            """);

            statement.execute("""
                CREATE TABLE bankkonto (
                kontonummer VARCHAR(30) PRIMARY KEY,
                kontoinhaber VARCHAR(100) NOT NULL,
                kontostand DECIMAL(15, 2) NOT NULL
            )
            """);
        }

        repository = new BankkontoRepositoryJdbc(connection);
    }

    @AfterEach
    void aufaeumen() throws Exception {
        connection.close();
    }

    @Test
    void bankkontoSpeichernUndFinden() {
        Bankkonto konto = new Bankkonto(
            "DE001", 
            "Max Mustermann", 
            new BigDecimal("500.00"));
        
        repository.speichern(konto);

        Optional<Bankkonto> ergebnis = repository.findeNachKontonummer("DE001");

        assertTrue(ergebnis.isPresent());

        assertEquals("Max Mustermann", ergebnis.get().getKontoinhaber());

        assertEquals(new BigDecimal("500.00"), ergebnis.get().getKontostand());
    }

    @Test
    void alleBankkontenFinden() {
        repository.speichern( new Bankkonto(
            "DE001", 
            "Max Mustermann", 
            new BigDecimal("500.00")));

        repository.speichern( new Bankkonto(
            "DE002", 
            "Erika Mustermann", 
            new BigDecimal("750.00")));  
            
        List<Bankkonto> bankkonten = repository.findeAlle();
        
        assertEquals(2, bankkonten.size());
        assertEquals("Erika Mustermann", bankkonten.get(1).getKontoinhaber());
    }

    @Test
    void bankkontoAktualisieren() {
        Bankkonto konto = new Bankkonto(
            "DE001", 
            "Max Mustermann", 
            new BigDecimal("500.00"));
        repository.speichern(konto);  
        
        konto.kontoinhaberAendern("Maximilian Mustermann");
        repository.aktualisieren(konto);

        Bankkonto aktualisiertesKonto = repository
            .findeNachKontonummer("DE001")
            .orElseThrow();

        assertEquals("Maximilian Mustermann", aktualisiertesKonto.getKontoinhaber());    
    }

    @Test
    void bankkontoLoeschen() {
        repository.speichern( new Bankkonto(
            "DE001", 
            "Max Mustermann", 
            new BigDecimal("500.00")));
        
        boolean wurdeGeloescht = repository.loeschenNachKontonummer("DE001");  
        
        assertTrue(wurdeGeloescht);

        assertTrue(repository
            .findeNachKontonummer("DE001")
            .isEmpty()
        );
    }
}
