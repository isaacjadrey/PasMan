package com.codingwithjadrey.pasman.util

import android.content.Context
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/** function that uses the Toast Function to invoke toasts */
fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/** functionality to display searched item in real time( liveData ) */
fun SearchView.searchItems(onQueryTextChange: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { onQueryTextChange.invoke(newText) }
            return true
        }
    })
}

/** method that implements the swipe functionality of the recyclerView by
 * calling the invoking the SwipeToDelete method */
fun RecyclerView.swipeToDeleteItem(onSwipe: (RecyclerView.ViewHolder) -> Unit) {
    val swipeToDeleteCallBack = object : SwipeToDelete() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipe.invoke(viewHolder)
        }
    }
    val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
    itemTouchHelper.attachToRecyclerView(this)
}