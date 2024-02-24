package com.example.myapplication.Model

class reel {
    var reelURL: String=""
    var caption: String=""
    var profileLink:String?=null
    constructor()
    constructor(reelURL: String, caption: String) {
        this.reelURL = reelURL
        this.caption = caption
    }

    constructor(reelURL: String, caption: String, profileLink: String) {
        this.reelURL = reelURL
        this.caption = caption
        this.profileLink = profileLink
    }

}