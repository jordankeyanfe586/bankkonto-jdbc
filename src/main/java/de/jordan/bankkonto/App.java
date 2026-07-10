package de.jordan.bankkonto;

import java.math.BigDecimal;

import de.jordan.bankkonto.model.Bankkonto;
import de.jordan.bankkonto.repository.BankkontoRepository;
import de.jordan.bankkonto.repository.BankkontoRepositoryMemory;
import de.jordan.bankkonto.service.BankkontoService;

import de.jordan.bankkonto.database.DatenbankManager;

import java.sql.Connection;
import java.sql.SQLException;

public class App 
{
    public static void main( String[] args ) {

        try (Connection connection = DatenbankManager.verbindungOeffnen()) {

            DatenbankManager.tabelleErstellen(connection);
            System.out.println("Datenbankverbing hergestellet.");
            System.out.println("Tabelle bankkonto ist bereit.");
        } catch (SQLException e) {
            System.err.println("Datenbankfehler: " +e.getMessage());
            return;
        }
        
        BankkontoRepository repository = new BankkontoRepositoryMemory();
        BankkontoService service = new BankkontoService(repository);

        Bankkonto konto = new Bankkonto(
            "DE001", 
            "Max Mustermann", 
            new BigDecimal("500.00"));

        service.bankkontoErstellen(konto);    

        Bankkonto gefundenesKonto = service.bankkontoFinden("DE001");
        System.out.println(gefundenesKonto);

        service.kontoinhaberAendern("DE001", "Maximilian Musternmann");
        System.out.println(gefundenesKonto);
    }
}
