package net.ijichi.simplestrecorder.recordlist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import java.util.*

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class RecordListAdapter(context: Context, states: ArrayList<RecordListState>, val presenter: RecordListItemPresenter) : ArrayAdapter<RecordListState>
(context, 0, states) {

  var states: ArrayList<RecordListState>? = states
  var positionView: HashMap<Int, RecordListItemView> = HashMap()

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view = if(convertView == null)RecordListItemView(context, presenter) else convertView as RecordListItemView
    view.update(getItem(position))
    positionView.put(position, view)
    return view
  }

  fun getPositionView(targetPosition: Int): RecordListItemView? {
    val targetView = positionView[targetPosition]
    return targetView
  }

  override fun getCount(): Int {
    return states?.size ?: 0
  }


}