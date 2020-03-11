package com.polytech.bookslibrary

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.polytech.bookslibrary.adapter.BookAdapter
import com.polytech.bookslibrary.model.Book

class MainBooksList : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: BookAdapter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_books_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = BookAdapter()
        db = FirebaseFirestore.getInstance()

        getBookData()

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.setBackgroundColor(Color.parseColor("#f39c12"))
    }

    private fun getBookData() {
        var bookListFromAPI = mutableListOf<Book>()
        db.collection("books").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    bookListFromAPI.add(Book(
                        document.get("author") as String,
                        document.get("description") as String,
                        document.get("imageUrl") as String,
                        document.get("isChecked") as Boolean,
                        document.get("nbPages") as Long,
                        document.get("title") as String,
                        document.get("year") as Long
                        ))
                }
                viewAdapter.bookList = bookListFromAPI
                viewAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                print(exception)
            }
    }
}
