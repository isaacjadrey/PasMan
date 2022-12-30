package com.codingwithjadrey.pasman.util

import android.view.View
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.codingwithjadrey.pasman.data.entity.Pas
import com.codingwithjadrey.pasman.ui.list.PasswordListFragmentDirections

class BindingAdapters {

    /** removes the imageView and textView when passwords exist in the database
     * or a particular password being searched fro doesn't exist */
    companion object {
        @BindingAdapter("android:noPasswords")
        @JvmStatic
        fun View.noPasswords(noPasswords: LiveData<Boolean>) {
            when (noPasswords.value) {
                true -> this.visibility = View.VISIBLE
                else -> this.visibility = View.INVISIBLE
            }
        }

        /** navigates to the updatePasswordFragment and sets the
         * input fields with the details of the selected
         * password item from the pass list fragment using its id */
        @BindingAdapter("android:updatePasswordFragmentDetails")
        @JvmStatic
        fun CardView.updatePasswordFragmentDetails(currentPassword: Pas) {
            this.setOnClickListener {
                val action = PasswordListFragmentDirections
                    .actionPasswordListFragmentToUpdatePasswordFragment(currentPassword)
                this.findNavController().navigate(action)
            }
        }
    }
}