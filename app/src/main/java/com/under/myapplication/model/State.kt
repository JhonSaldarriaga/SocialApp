package com.under.myapplication.model

data class State(val users: HashMap<String,User>, val posts:ArrayList<Post>, val session: Session?)
