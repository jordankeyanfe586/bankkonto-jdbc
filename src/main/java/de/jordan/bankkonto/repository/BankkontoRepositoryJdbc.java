package de.jordan.bankkonto.repository;

import de.jordan.bankkonto.model.Bankkonto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankkontoRepositoryJdbc implements BankkontoRepository {

    private final Connection connection;

    public BankkontoRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void speichern(Bankkonto bankkonto) {
        String sql = """
                INSERT INTO bankkonto(
                    kontonummer,
                    kontoinhaber,
                    kontostand
                )
                VALUES (?, ?, ?)    
                """;
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bankkonto.getKontonummer());
            statement.setString(2, bankkonto.getKontoinhaber());
            statement.setBigDecimal(3, bankkonto.getKontostand());

            statement.executeUpdate();

        } catch(SQLException e) {
            throw new IllegalStateException(
                "Das Bankkonto konnte nicht gespeichert werden.", e
            );
        }        
    }

    @Override
    public Optional<Bankkonto> findeNachKontonummer(String kontonummer) {
        String sql ="""
                SELECT kontonummer, kontoinhaber, kontostand
                FROM bankkonto
                WHERE kontonummer = ?
                """;
        
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, kontonummer);

            try(ResultSet resultSet = statement.executeQuery()) {

                if(resultSet.next()) {
                    Bankkonto bankkonto = bankkontoErstellen(resultSet);

                    return Optional.of(bankkonto);
                }

                return Optional.empty();
            }
        } catch(SQLException e) {
            throw new IllegalStateException(
                "Das Bankonto konnte nicht gefunden werden.", e
           );
        }      
    }

    @Override
    public List<Bankkonto> findeAlle() {
        String sql = """
                SELECT kontonummer, kontoinhaber, kontostand
                FROM bankkonto
                ORDER BY kontonummer
                """;
        
        List<Bankkonto> bankkonten = new ArrayList<>();
        
        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            
            while(resultSet.next()) {
                bankkonten.add(bankkontoErstellen(resultSet));
            }

            return bankkonten;
        } catch(SQLException e) {
            throw new IllegalStateException("Die Bankkonten konnten nicht geladen wedern.", e);
        }
    }

    @Override
    public void aktualisieren(Bankkonto bankkonto) {
        String sql = """
                UPDATE bankkonto
                SET kontoinhaber = ?, kontostand = ?
                WHERE kontonummer = ?
                """;
        
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bankkonto.getKontoinhaber());
            statement.setBigDecimal(2, bankkonto.getKontostand());
            statement.setString(3, bankkonto.getKontonummer());

            int geaenderteZeilen = statement.executeUpdate();

            if(geaenderteZeilen == 0) {
                throw new IllegalArgumentException("Kein Bankkonto mit dieser Kontonummer gefunden.");
            }
        } catch(SQLException e) {
            throw new IllegalStateException("Das Bankkonto konnte nicht aktualisiert werden.", e);
        }
    }

    @Override
    public boolean loeschenNachKontonummer(String kontonummer) {
        String sql = """
                DELETE FROM bankkonto
                WHERE kontonummer = ?
                """;
        
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, kontonummer);

            int geloeschteZeilen= statement.executeUpdate();

            return geloeschteZeilen>0;
        } catch(SQLException e){
            throw new IllegalStateException("Das Bankkonto konnte nicht geloescht werden.", e);
        }

    }

    private Bankkonto bankkontoErstellen(ResultSet resultSet) throws SQLException {
        return new Bankkonto(
            resultSet.getString("kontonummer"), 
            resultSet.getString("kontoinhaber"), 
            resultSet.getBigDecimal("kontostand")
        );
    }
}
