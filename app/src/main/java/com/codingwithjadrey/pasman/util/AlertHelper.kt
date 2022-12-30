package com.codingwithjadrey.pasman.util

import android.content.Context
import android.view.View
import com.codingwithjadrey.pasman.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/** class the alerts a user of the current actions undertaken
 * on deleting
 * and on restoring */
class AlertHelper @Inject constructor(@ActivityContext val context: Context) {

    /** method that alerts the user about the action to delete an item */
    fun createAlertToDelete(account: String, onConfirmation: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.apply {
            setTitle(account)
            setMessage(context.getString(R.string.are_you_sure_to_delete))
            setCancelable(false)
            setNegativeButton(context.getString(R.string.no)) { _, _ -> }
            setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                onConfirmation.invoke()
            }
        }
            .show()
    }

    /** method that shows snackBar that alerts a user
     *  about restoring a deleted item into the database */
    fun restoreSnackBar(view: View, account: String, action: () -> Unit) {
        Snackbar.make(
            view,
            context.getString(R.string.confirm_delete_item, account),
            Snackbar.LENGTH_LONG
        )
            .setAction(context.getString(R.string.restore_password)) { action.invoke() }
            .show()
    }
}