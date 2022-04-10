package com.under.myapplication.model

class User(private var email:String ,//ESTE ES EL ID
           private var password:String,
           private var name:String,
           private var imageProfilePath:String) {

    fun verifyCredential(e:String, p:String):Boolean{
        return e == email && p == password
    }
    fun verifyEmail(e:String):Boolean{return e==email}
    fun getName():String{return name}
    fun getImageProfilePaht():String{return imageProfilePath}
    fun setName(n:String){name = n}
    fun setImageProfilePath(p:String){imageProfilePath = p}
    fun getEmail():String{return email}
}