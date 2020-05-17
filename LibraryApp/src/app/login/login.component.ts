import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router, ActivatedRoute, Params } from '@angular/router';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Users } from '../models/users';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user:Users;
  loginForm:FormGroup;
  constructor(private router:Router, public activatedRoute: ActivatedRoute, private services:UserService, private formBuilder:FormBuilder) { }
  login(){
    let username = this.loginForm.get('username').value;
    let password = this.loginForm.get('password').value;
    let authenticationToken = "Basic " + window.btoa("admin" + ":" + "service1");
    sessionStorage.removeItem("token");
    sessionStorage.setItem("token", authenticationToken);
    this.services.userLogin(this.loginForm.value).subscribe(data=>{
      this.user = data;
      console.log(data);
      sessionStorage.removeItem("usertype");
      sessionStorage.setItem("usertype",this.user.usertype);
      sessionStorage.removeItem("userid");
      sessionStorage.setItem("userid",this.user.userid.toString());
      if(this.user.usertype=="user"){
        sessionStorage.setItem("username",this.user.username)
        this.router.navigate(['/user']);
      }
      else if(this.user.usertype=="admin"){
        this.router.navigate(['/librarian']);
      }
      else{
        this.router.navigate(['/deliveryboy']);
      }
    });
  }
  ngOnInit() {
    this.loginForm=this.formBuilder.group({
      username:[''],
      password:['']
    })
  }

}
