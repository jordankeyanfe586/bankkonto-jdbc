package de.jordan.bankkonto;

import java.math.BigDecimal;

import de.jordan.bankkonto.model.Bankkonto;
import de.jordan.bankkonto.repository.BankkontoRepository;
// import de.jordan.bankkonto.repository.BankkontoRepositoryMemory;
import de.jordan.bankkonto.service.BankkontoService;

import de.jordan.bankkonto.repository.BankkontoRepositoryJdbc;
import de.jordan.bankkonto.database.DatenbankManager;

import java.sql.Connection;
import java.sql.SQLException;

public class App 
{
    public static void main( String[] args ) {

        try (Connection connection = DatenbankManager.verbindungOeffnen()) {

            DatenbankManager.tabelleErstellen(connection);
            System.out.println("Datenbankverbing hergestellt.");
            System.out.println("Tabelle bankkonto ist bereit.");

            BankkontoRepository repository = new BankkontoRepositoryJdbc(connection);
            BankkontoService service = new BankkontoService(repository);

            Bankkonto konto;
            if(repository.findeNachKontonummer("DE001").isEmpty()) {
                konto = new Bankkonto(
                "DE001", 
                "Max Mustermann", 
                new BigDecimal("500.00"));

                service.bankkontoErstellen(konto);   
            } else{
               konto = service.bankkontoFinden("DE001");
            }

             

            Bankkonto gefundenesKonto = service.bankkontoFinden("DE001");
            System.out.println(gefundenesKonto);

            service.kontoinhaberAendern("DE001", "Maximilian Mustermann");
            Bankkonto gefundenesKonto1 = service.bankkontoFinden("DE001");
            System.out.println(gefundenesKonto1);

            Bankkonto konto2;
            if(repository.findeNachKontonummer("DE002").isEmpty()){
                konto2 = new Bankkonto(
                "DE002", 
                "ERIKA Mustermann", 
                new BigDecimal("750.00"));

            service.bankkontoErstellen(konto2);   
            } else{
                konto2 = service.bankkontoFinden("DE002");
            }
             

            System.out.println("Alle Bankkonten:");
            for(Bankkonto bankkonto : service.alleBankkonten()) {
                System.out.println(
                    bankkonto.getKontonummer()
                    + " |" + bankkonto.getKontoinhaber() + " |" + bankkonto.getKontostand() + "EUR"
                );
            }


        } catch (SQLException e) {
            System.err.println("Datenbankfehler: " +e.getMessage());
            return;
        }
        
  
    }
}
