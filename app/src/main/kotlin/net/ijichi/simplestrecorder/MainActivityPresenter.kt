package net.ijichi.simplestrecorder

import android.content.Context
import android.widget.EditText
import net.ijichi.simplestrecorder.mediaplayer.SimpleMediaPlayer
import net.ijichi.simplestrecorder.recordlist.RecordListState
import net.ijichi.simplestrecorder.util.DialogUtil
import net.ijichi.simplestrecorder.util.Util
import java.io.File

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class MainActivityPresenter(val context: Context, val contract: MainActivityContract, val mediaPlayer: SimpleMediaPlayer) {

  fun onCLickNewRecord() {
    mediaPlayer.record()
  }

  fun onLongClickCell(state: RecordListState) {
    // リスト表示用のアラートダイアログ
    val items = arrayOf<CharSequence>("Remove", "Edit", "Cancel")
    DialogUtil(context).showListDialog(items, { which ->
      when (which) {
        0 -> DialogUtil(context).showButtonDialog (
          "Confirmation",
          "Remove this file?",
          true,
          true,
          { removeListItem(state) }
        )
        1 ->
          showEditDialog { editText -> editListItemTitle(state, editText) }
        2 -> { }
      }
    })
  }

  private fun showEditDialog(editCallback: (String) -> Unit){
    val editView = EditText(context)
    editView.setSingleLine()
    DialogUtil(context).showEditViewDoubleButtonDialog(editView, { editText ->
      editCallback.invoke(editText)
    })
  }

  fun showEdiTextOverlapDialog(){
    val title = "overlap error"
    val message = "Please Change other edit text"
    DialogUtil(context).showButtonDialog(title, message, true)
  }

  fun addStateIfTargetFile(i: Int, file: File, callback: (RecordListState)-> Unit){
    if(!file.isFile || Util.getSuffix(file.canonicalPath ?: "") != mediaPlayer.fileSuffix)return
    val state = RecordListState.createStateFromIndexAndFile(i, file)
    callback.invoke(state)
  }

  private fun removeListItem(state: RecordListState){
    val file = File(state.filePath)
    if(file.exists())contract.removeState(state)
  }

  private fun editListItemTitle(state: RecordListState ,editText: String){
    val newFileName = "$editText.wav"
    val newFilePath = Util.changeFilePath(state.filePath, newFileName)
    val newFile = File(newFilePath)
    if(newFile.exists()){// 既に存在する場合エラー
      showEdiTextOverlapDialog()
    }else{
      val newState = RecordListState.createStateChangeTitle(state, newFile)
      contract.removeState(state)
      contract.addState(newState)
    }
  }
}