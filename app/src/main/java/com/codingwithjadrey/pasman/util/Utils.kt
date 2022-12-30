package com.codingwithjadrey.pasman.util

import android.content.Context
import android.widget.Toast
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/** function that uses the Toast Function to invoke toasts */
fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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