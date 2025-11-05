import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StockService } from './Stock.service';
import { NGXLogger } from 'ngx-logger';

describe('StockService', () => {
  let service: StockService;
  let httpMock: HttpTestingController;
  let mockLogger: jasmine.SpyObj<NGXLogger>;  // Mock logger

  beforeEach(() => {
    // Mock the logger service
    mockLogger = jasmine.createSpyObj('NGXLogger', ['info', 'warn']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        StockService,
        { provide: NGXLogger, useValue: mockLogger }  // Provide the mock logger
      ]
    });

    service = TestBed.inject(StockService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve all stocks', () => {
    const mockStocks = [
      { idStock: 1, libelleStock: 'Stock A', qte: 10, qteMin: 5 },
      { idStock: 2, libelleStock: 'Stock B', qte: 20, qteMin: 10 }
    ];

    service.getAllStocks().subscribe((res: any) => {
      expect(res.length).toBe(2);
      expect(res).toEqual(mockStocks);
      expect(mockLogger.info).toHaveBeenCalledWith('Fetching all stocks'); // Verify that the log method was called
    });

    const req = httpMock.expectOne('http://localhost:8089/SpringMVC/stock/retrieve-all-stocks');
    expect(req.request.method).toBe('GET');
    req.flush(mockStocks);
  });

  it('should add a new stock', () => {
    const newStock = { libelleStock: 'Stock C', qte: 15, qteMin: 8 };

    service.addStock(newStock).subscribe((res: any) => {
      expect(res).toEqual(newStock);
      expect(mockLogger.info).toHaveBeenCalledWith('Adding a new stock', newStock); // Verify the log for adding a stock
    });

    const req = httpMock.expectOne('http://localhost:8089/SpringMVC/stock/add-stock');
    expect(req.request.method).toBe('POST');
    req.flush(newStock);
  });

  it('should edit an existing stock', () => {
    const stockToEdit = { idStock: 1, libelleStock: 'Stock A', qte: 15, qteMin: 8 };

    service.editStock(stockToEdit).subscribe((res: any) => {
      expect(res).toEqual(stockToEdit);
      expect(mockLogger.info).toHaveBeenCalledWith('Editing stock', stockToEdit); // Verify the log for editing a stock
    });

    const req = httpMock.expectOne('http://localhost:8089/SpringMVC/stock/modify-stock');
    expect(req.request.method).toBe('PUT');
    req.flush(stockToEdit);
  });

  it('should delete a stock', () => {
    const stockId = 1;

    service.deleteStock(stockId).subscribe((res: any) => {
      expect(res).toEqual({});  // Vérifier que la réponse est un objet vide
      expect(mockLogger.warn).toHaveBeenCalledWith(`Deleting stock with id ${stockId}`);  // Vérifier le log pour la suppression du stock
    });

    const req = httpMock.expectOne(`http://localhost:8089/SpringMVC/stock/remove-stock/${stockId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});  // Retourner un objet vide dans la réponse après suppression
  });
});
