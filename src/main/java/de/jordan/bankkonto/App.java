package de.jordan.bankkonto;

import java.math.BigDecimal;

import de.jordan.bankkonto.model.Bankkonto;
import de.jordan.bankkonto.repository.BankkontoRepository;
import de.jordan.bankkonto.repository.BankkontoRepositoryMemory;
import de.jordan.bankkonto.service.BankkontoService;

public class App 
{
    public static void main( String[] args ) {
        
        BankkontoRepository repository = new BankkontoRepositoryMemory();
        BankkontoService service = new BankkontoService(repository);

        Bankkonto konto = new Bankkonto(
            "DE001", 
            "Max Mustermann", 
            new BigDecimal("500.00"));

        service.bankkontoErstellen(konto);    

        Bankkonto gefundenesKonto = service.bankkontoFinden("DE001");
        System.out.println(gefundenesKonto);

        service.kontoinhaberaender("DE001", "Maximilian Musternman");
        System.out.println(gefundenesKonto);
    }
}
