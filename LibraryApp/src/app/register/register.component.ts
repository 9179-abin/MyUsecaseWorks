import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerUser: FormGroup;
  constructor(private formBuilder: FormBuilder, private userService: UserService, private router:Router) { }

  addUser(){
    console.log(this.registerUser.value);
    this.userService.userRegister(this.registerUser.value).subscribe(data=>{
      if(data!=null){
        this.router.navigate(['/login']);
      }
      
    })
  }

  ngOnInit() {

    this.registerUser = this.formBuilder.group({
      userid: [56],
      username: ['', Validators.required],
      password: ['',Validators.required],
      seatno: ['', Validators.required],
      usertype: ['user']
    });

  }

}
