package com.under.myapplication.model

class Post(var updateText:String, var location:String, var date:String, private var userID:String) {
    var updateImagePath:String? = null
    fun haveImage():Boolean{return updateImagePath!=null}
    fun getProfilePic():String{return DataBase.getUserByID(userID)!!.getImageProfilePaht()}
    fun getProfileName():String{return DataBase.getUserByID(userID)!!.getName()}
}