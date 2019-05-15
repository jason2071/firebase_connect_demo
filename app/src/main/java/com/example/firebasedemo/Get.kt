package com.example.firebasedemo

class Get {
    var title: String = ""
    var content: String = ""
    var startAt: String = ""
    var image: String = ""

    constructor()
    constructor(title: String, content: String, startAt: String, image: String) {
        this.title = title
        this.content = content
        this.startAt = startAt
        this.image = image
    }


}