package net.ijichi.simplestrecorder.recordlist

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class RecordListItemPresenter {

  var onClickCell: ((state: RecordListState)->Unit)? = null
  var onLongClickCell: ((state: RecordListState)->Unit)? = null
  var onClickPlay: (()->Unit)? = null
  var onClickPause: (()->Unit)? = null
  var onClickStop: (()->Unit)? = null


}