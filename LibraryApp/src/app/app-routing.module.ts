import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { LibrarianComponent } from './librarian/librarian.component';
import { DeliveryboyComponent } from './deliveryboy/deliveryboy.component';


const routes: Routes = [
  {path: '', component:HomeComponent},
  {path: 'home', component:HomeComponent},
  {path:'register', component:RegisterComponent},
  {path:'login' , component:LoginComponent},
  {path:'user' , component:UserComponent},
  {path:'librarian' , component:LibrarianComponent},
  {path:'deliveryboy' , component:DeliveryboyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
