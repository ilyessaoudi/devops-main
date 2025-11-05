import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NGXLogger } from 'ngx-logger'; // Importation du service de journalisation

@Injectable({
  providedIn: 'root'
})
export class StockService {
  readonly API_URL = 'http://localhost:8089/SpringMVC/stock';

  constructor(
    private httpClient: HttpClient,
    private logger: NGXLogger // Injection du service de journalisation
  ) { }

  getAllStocks() {
    this.logger.info('Fetching all stocks'); // Log avant la récupération des stocks
    return this.httpClient.get(`${this.API_URL}/retrieve-all-stocks`);
  }

  addStock(stock: any) {
    this.logger.info('Adding a new stock', stock); // Log avant l'ajout du stock
    return this.httpClient.post(`${this.API_URL}/add-stock`, stock);
  }

  editStock(stock: any) {
    this.logger.info('Editing stock', stock); // Log avant la modification du stock
    return this.httpClient.put(`${this.API_URL}/modify-stock`, stock);
  }

  deleteStock(idStock: any) {
    this.logger.warn(`Deleting stock with id ${idStock}`); // Log avant la suppression du stock
    return this.httpClient.delete(`${this.API_URL}/remove-stock/${idStock}`);
  }
}
