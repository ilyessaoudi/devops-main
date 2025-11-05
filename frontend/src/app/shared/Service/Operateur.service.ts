import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NGXLogger } from 'ngx-logger'; // Importation du service de journalisation

@Injectable({
  providedIn: 'root'
})
export class OperateurService {
  readonly API_URL = 'http://localhost:8089/SpringMVC/operateur';

  constructor(
    private httpClient: HttpClient,
    private logger: NGXLogger // Injection du service de journalisation
  ) { }

  getAllOperateurs() {
    this.logger.info('Fetching all operateurs'); // Log lorsque la méthode est appelée
    return this.httpClient.get(`${this.API_URL}/retrieve-all-operateurs`);
  }

  addOperateur(operateur: any) {
    this.logger.info('Adding a new operateur', operateur); // Log avant l'ajout
    return this.httpClient.post(`${this.API_URL}/add-operateur`, operateur);
  }

  editOperateur(operateur: any) {
    this.logger.info('Editing operateur', operateur); // Log avant la modification
    return this.httpClient.put(`${this.API_URL}/modify-operateur`, operateur);
  }

  deleteOperateur(idOperateur: any) {
    this.logger.warn(`Deleting operateur with id ${idOperateur}`); // Log avant la suppression
    return this.httpClient.delete(`${this.API_URL}/remove-operateur/${idOperateur}`);
  }
}
