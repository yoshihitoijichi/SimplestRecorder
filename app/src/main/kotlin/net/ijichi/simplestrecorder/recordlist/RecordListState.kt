package net.ijichi.simplestrecorder.recordlist

import net.ijichi.simplestrecorder.mediaplayer.SimpleMediaPlayer
import net.ijichi.simplestrecorder.util.Util
import java.io.File
import java.util.*

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class RecordListState {

  var index: Int = 0
  var title: String = ""
  var time: String = ""
  var filePath: String = ""
  var makeDate = Date()

  companion object{
    fun createStateFromIndexAndFile(i: Int, file: File): RecordListState{
      val state = RecordListState()
      state.index = i
      state.title = Util.getFileNameRemoveSuffix(file.name) ?: ""
      state.time = Util.timeFormat(SimpleMediaPlayer.getDuration(file.canonicalPath).toLong())
      state.filePath = file.canonicalPath
      return state
    }

    fun createStateFromFilePath(index: Int, filePath: String): RecordListState{
      val newState = RecordListState()
      newState.index = index
      newState.title = Util.getFileNameRemoveSuffix(File(filePath).name) ?: ""
      newState.time = Util.timeFormat(SimpleMediaPlayer.getDuration(filePath).toLong())
      newState.filePath = filePath
      return newState
    }

    fun createStateChangeTitle(state: RecordListState, newFile: File): RecordListState{
      val file = File(state.filePath)
      if(file.exists())file.renameTo(newFile)

      val newState = RecordListState()
      newState.index = state.index
      newState.title = Util.getFileNameRemoveSuffix(newFile.name) ?: ""
      newState.time = Util.timeFormat(SimpleMediaPlayer.getDuration(newFile.canonicalPath).toLong())
      newState.filePath = newFile.canonicalPath
      newState.makeDate = state.makeDate

      return newState
    }


  }


}