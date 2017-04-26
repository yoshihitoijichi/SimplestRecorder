package net.ijichi.simplestrecorder.util

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

/**
 * Created by ijichiyoshihito on 2017/02/07.
 */
class DialogUtil(val context: Context){

  fun showButtonDialog(title: String = "",
                       message: String = "",
                       displayIcon: Boolean = false,
                       displayNegativeButton: Boolean = false,
                       positiveCallback: (()->Unit)? = null,
                       negativeCallback: (()->Unit)? = null){
    val dialog = AlertDialog.Builder(context)
      .setPositiveButton("OK") { dialog, whichButton ->
        positiveCallback?.invoke()
      }
      .setCancelable(false)
    if(title.isNotEmpty())dialog.setTitle(title)
    if(message.isNotEmpty())dialog.setMessage(message)
    if(displayIcon)dialog.setIcon(android.R.drawable.ic_dialog_info)
    if(displayNegativeButton)dialog.setNegativeButton("Cancel") { dialog, whichButton ->
      negativeCallback?.invoke()
    }
    dialog.show()
  }

  fun showListDialog(items: Array<CharSequence>, choiceCallback: (Int)->Unit){
    val listDlg = AlertDialog.Builder(context)
    listDlg.setTitle("Choice")
    listDlg.setItems(
      items,
      { dialog, which ->
        choiceCallback.invoke(which)
      })
    listDlg.setCancelable(false)
    listDlg.create().show()
  }

  fun showEditViewDoubleButtonDialog(editView: EditText, positiveCallback: ((String)->Unit)? = null, negativeCallback: (()->Unit)? = null){
    AlertDialog.Builder(context)
      .setTitle("Change title")
      .setView(editView)
      .setPositiveButton("OK") { dialog, whichButton ->
        editView.text.toString().apply {
          if(this.isNotEmpty()) positiveCallback?.invoke(this)
        }
      }
      .setNegativeButton("Cancel") { dialog, whichButton ->
        negativeCallback?.invoke()
      }
      .setCancelable(false)
      .show()
  }
}
