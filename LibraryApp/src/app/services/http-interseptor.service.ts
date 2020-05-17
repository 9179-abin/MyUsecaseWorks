import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import { retry, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class HttpInterseptorService implements HttpInterceptor{

  constructor(private router:Router) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      setHeaders: {
        Authorization: sessionStorage.getItem('token')
      }
    });
    return next.handle(req).pipe(
      retry(0),
      catchError((error: HttpErrorResponse) => {
        let errorMessage = '';
        if(error.status==404){
          errorMessage='Username or Password not Correct!'
        }
        else if (error.error instanceof ErrorEvent) {
          // client-side error
          errorMessage = `Error: ${error.error.message}`;
        } else {
          // server-side error
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        window.alert(errorMessage);
        this.router.navigate(['/home']);
        return throwError(errorMessage);
      })
    );
    
  }
}
