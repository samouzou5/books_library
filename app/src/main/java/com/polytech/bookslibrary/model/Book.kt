package com.polytech.bookslibrary.model

data class Book(var author: String, var description: String, var imageUrl: String, var isChecked: Boolean, var nbPages: Long, var title: String, var year: Long)