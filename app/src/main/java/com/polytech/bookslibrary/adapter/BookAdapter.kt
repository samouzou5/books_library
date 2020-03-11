package com.polytech.bookslibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polytech.bookslibrary.R
import com.polytech.bookslibrary.model.Book
import com.squareup.picasso.Picasso

class BookAdapter() : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    var bookList = mutableListOf<Book>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bookTitle: TextView = view.findViewById(R.id.book_title)
        private val bookAuthor: TextView = view.findViewById(R.id.book_author)
        val bookImage: ImageView = view.findViewById(R.id.img_book)

        fun bindItems(item: Book) {
            Picasso.get().load(item.imageUrl).resize(400, 400).into(bookImage)
            bookTitle.text = item.title
            bookAuthor.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(bookList[position])
    }
}