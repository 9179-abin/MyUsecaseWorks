import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterseptorService } from './services/http-interseptor.service';
import { UserComponent } from './user/user.component';
import { LibrarianComponent } from './librarian/librarian.component';
import { DeliveryboyComponent } from './deliveryboy/deliveryboy.component';
import { NavbarComponent } from './navbar/navbar.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UserComponent,
    LibrarianComponent,
    DeliveryboyComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    {
    provide: HTTP_INTERCEPTORS,
    useClass:HttpInterseptorService,
    multi: true
  }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
