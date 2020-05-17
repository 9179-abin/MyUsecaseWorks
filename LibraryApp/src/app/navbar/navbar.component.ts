import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  navbarOpen = false;
  constructor(private router:Router) { }
  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

  goHome(){
    alert("You will be logged out!");
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("usertype");
    sessionStorage.removeItem("userid");
    this.router.navigate(['/home']);
  }

  logout(){
    alert("Are you sure?");
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("usertype");
    sessionStorage.removeItem("userid");
    this.router.navigate(['/home']);
  }
  ngOnInit() {
  }

}
