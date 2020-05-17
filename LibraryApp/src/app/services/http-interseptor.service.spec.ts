import { TestBed } from '@angular/core/testing';

import { HttpInterseptorService } from './http-interseptor.service';

describe('HttpInterseptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpInterseptorService = TestBed.get(HttpInterseptorService);
    expect(service).toBeTruthy();
  });
});
