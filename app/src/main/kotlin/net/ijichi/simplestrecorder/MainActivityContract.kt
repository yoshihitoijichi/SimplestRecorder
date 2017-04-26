package net.ijichi.simplestrecorder

import net.ijichi.simplestrecorder.recordlist.RecordListState

/**
 * Created by ijichiyoshihito on 2017/02/08.
 */
interface MainActivityContract{
  fun addStateFromFilePath(filePath: String)

  fun addState(state: RecordListState)
  fun removeState(state: RecordListState)


}