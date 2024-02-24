package com.example.myapplication.Model

class post {
    var postURL: String=""
    var caption: String=""
    var uid: String=""
    var time: String=""
    constructor()
    constructor(postURL: String, caption: String) {
        this.postURL = postURL
        this.caption = caption
    }

    constructor(postURL: String, caption: String, name: String, time: String) {
        this.postURL = postURL
        this.caption = caption
        this.uid = name
        this.time = time
    }

    constructor(postURL: String, caption: String, uid: String, time: Long)


}