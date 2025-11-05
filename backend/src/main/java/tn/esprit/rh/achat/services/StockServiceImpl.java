package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

	@Autowired
	StockRepository stockRepository;

	@Override
	public List<Stock> retrieveAllStocks() {
		log.info("In method retrieveAllStocks");
		List<Stock> stocks = (List<Stock>) stockRepository.findAll();

		if (stocks.isEmpty()) {
			log.warn("No stocks found.");
		} else {
			for (Stock stock : stocks) {
				log.info("Stock found: {}", stock);
			}
		}

		log.info("Out of method retrieveAllStocks");
		return stocks;
	}

	@Override
	public Stock addStock(Stock s) {
		log.info("In method addStock - Adding stock: {}", s);
		Stock savedStock = stockRepository.save(s);
		log.info("Stock added successfully: {}", savedStock);
		return savedStock;
	}

	@Override
	public void deleteStock(Long stockId) {
		log.info("In method deleteStock - Deleting stock with ID: {}", stockId);
		stockRepository.deleteById(stockId);
		log.info("Stock with ID {} deleted successfully", stockId);
	}

	@Override
	public Stock updateStock(Stock s) {
		log.info("In method updateStock - Updating stock: {}", s);
		Stock updatedStock = stockRepository.save(s);
		log.info("Stock updated successfully: {}", updatedStock);
		return updatedStock;
	}

	@Override
	public Stock retrieveStock(Long stockId) {
		long start = System.currentTimeMillis();
		log.info("In method retrieveStock - Retrieving stock with ID: {}", stockId);
		Stock stock = stockRepository.findById(stockId).orElse(null);

		if (stock == null) {
			log.error("Stock with ID {} not found.", stockId);
		} else {
			log.info("Stock retrieved: {}", stock);
		}

		long elapsedTime = System.currentTimeMillis() - start;
		log.info("Method execution time for retrieveStock: {} milliseconds.", elapsedTime);
		return stock;
	}

	@Override
	public String retrieveStatusStock() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String msgDate = sdf.format(now);
		String finalMessage = "";
		String newLine = System.getProperty("line.separator");

		List<Stock> stocksEnRouge = (List<Stock>) stockRepository.retrieveStatusStock();

		if (stocksEnRouge.isEmpty()) {
			log.info("No stocks are in the critical status.");
		}

		for (Stock stock : stocksEnRouge) {
			finalMessage = newLine + finalMessage + msgDate + newLine + ": Stock "
					+ stock.getLibelleStock() + " has a quantity of " + stock.getQte()
					+ " which is lower than the minimum quantity of " + stock.getQteMin()
					+ newLine;
			log.warn("Critical stock status: {}", finalMessage);
		}

		return finalMessage;
	}
}
