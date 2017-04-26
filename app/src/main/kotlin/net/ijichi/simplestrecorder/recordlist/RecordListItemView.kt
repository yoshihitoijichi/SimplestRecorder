package net.ijichi.simplestrecorder.recordlist

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import net.ijichi.simplestrecorder.R

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class RecordListItemView: FrameLayout {

  lateinit var title: TextView
  lateinit var time: TextView

  lateinit var playerButtonRoot: View
  lateinit var playerButtonPause: Button
  lateinit var playerButtonStop: Button

  val presenter: RecordListItemPresenter

  private var pauseButtonType: PauseButtonType = PauseButtonType.PAUSE

  constructor(context: Context, presenter: RecordListItemPresenter): super(context){
    init(context)
    this.presenter = presenter
  }

  private fun init(context: Context){
    val v = View.inflate(context, getLayout(), this)
    title = v?.findViewById(R.id.record_list_item_title) as TextView
    time = v?.findViewById(R.id.record_list_item_time) as TextView

    playerButtonRoot = v?.findViewById(R.id.record_list_item_player_button_root) as View
    playerButtonPause = v?.findViewById(R.id.record_list_item_player_button_pause) as Button
    playerButtonStop = v?.findViewById(R.id.record_list_item_player_button_stop) as Button

    hidePlayerButtons()
    changePauseButtonPause()
  }

  private fun getLayout(): Int{
    return R.layout.record_list_item_view
  }

  fun update(state: RecordListState){
    title.text = state.title
    time.text = state.time

    setOnClickListener {
      presenter.onClickCell?.invoke(state)
      showPlayerButtons()
    }

    setOnLongClickListener {
      presenter.onLongClickCell?.invoke(state)
      true
    }

    playerButtonPause.setOnClickListener {
      when(pauseButtonType){
        PauseButtonType.PAUSE -> presenter.onClickPause?.invoke()
        PauseButtonType.PLAY -> presenter.onClickPlay?.invoke()
      }
    }

    playerButtonStop.setOnClickListener {
      hidePlayerButtons()
      presenter.onClickStop?.invoke()
    }
  }

  fun showPlayerButtons(){
    playerButtonRoot.visibility = View.VISIBLE
  }

  fun hidePlayerButtons(){
    playerButtonRoot.visibility = View.GONE
  }

  enum class PauseButtonType{
    PAUSE,
    PLAY;

    fun getText(): String{
      return when(this){
        PAUSE -> "Ⅱ"
        PLAY -> "▶"
      }
    }

    fun getNextType(): PauseButtonType{
      return when(this){
        PAUSE -> PLAY
        PLAY -> PAUSE
      }
    }
  }

  fun changePauseButtonPlay(){
    pauseButtonType = PauseButtonType.PLAY
    playerButtonPause.text = pauseButtonType.getText()
  }

  fun changePauseButtonPause(){
    pauseButtonType = PauseButtonType.PAUSE
    playerButtonPause.text = pauseButtonType.getText()
  }

}