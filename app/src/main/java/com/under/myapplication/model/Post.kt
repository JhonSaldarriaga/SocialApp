package com.under.myapplication.model

class Post(var updateText:String, var location:String, var date:String, private var user:User) {
    var updateImagePath:String? = null
    fun haveImage():Boolean{return updateImagePath!=null}
    fun getProfilePic():String{return user.getImageProfilePaht()}
    fun getProfileName():String{return user.getName()}
}