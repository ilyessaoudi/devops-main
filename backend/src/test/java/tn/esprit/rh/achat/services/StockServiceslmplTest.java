package tn.esprit.rh.achat.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StockServiceslmplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllStocks() {
        Stock s1 = new Stock();
        Stock s2 = new Stock();
        when(stockRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Stock> result = stockService.retrieveAllStocks();
        assertEquals(2, result.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock();
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock saved = stockService.addStock(stock);
        assertNotNull(saved);
        verify(stockRepository).save(stock);
    }

    @Test
    public void testDeleteStock() {
        Long stockId = 1L;
        doNothing().when(stockRepository).deleteById(stockId);

        stockService.deleteStock(stockId);
        verify(stockRepository).deleteById(stockId);
    }

    @Test
    public void testUpdateStock() {
        Stock stock = new Stock();
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock updated = stockService.updateStock(stock);
        assertNotNull(updated);
        verify(stockRepository).save(stock);
    }

    @Test
    public void testRetrieveStock() {
        Long stockId = 1L;
        Stock stock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock result = stockService.retrieveStock(stockId);
        assertNotNull(result);
        assertEquals(stock, result);
        verify(stockRepository).findById(stockId);
    }

    @Test
    public void testRetrieveStatusStock() {
        Stock s1 = new Stock();
        s1.setLibelleStock("Stock1");
        s1.setQte(5);
        s1.setQteMin(10);

        Stock s2 = new Stock();
        s2.setLibelleStock("Stock2");
        s2.setQte(3);
        s2.setQteMin(5);

        when(stockRepository.retrieveStatusStock()).thenReturn(Arrays.asList(s1, s2));

        String status = stockService.retrieveStatusStock();
        assertTrue(status.contains("Stock1"));
        assertTrue(status.contains("Stock2"));
        verify(stockRepository).retrieveStatusStock();
    }
}

