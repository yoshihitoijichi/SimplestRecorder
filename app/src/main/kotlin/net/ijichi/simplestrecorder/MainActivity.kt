package net.ijichi.simplestrecorder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ListView
import net.ijichi.simplestrecorder.mediaplayer.SimpleMediaPlayer
import net.ijichi.simplestrecorder.mediaplayer.SimpleMediaPlayerPresenter
import net.ijichi.simplestrecorder.recordlist.RecordListAdapter
import net.ijichi.simplestrecorder.recordlist.RecordListItemPresenter
import net.ijichi.simplestrecorder.recordlist.RecordListItemView
import net.ijichi.simplestrecorder.recordlist.RecordListState
import java.util.*


class MainActivity: AppCompatActivity(), MainActivityContract {

  // view
  private val mainList: ListView
    get() = findViewById(R.id.main_list) as ListView
  private val newRecordButton: Button
    get() = findViewById(R.id.new_record_button) as Button

  lateinit var mediaPlayer: SimpleMediaPlayer
  lateinit var presenter: MainActivityPresenter
  private var adapter: RecordListAdapter? = null
  private var playingListItemView: RecordListItemView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById(R.id.toolbar) as Toolbar
    toolbar.title = "SimplestRecorder"

    newRecordButton.text = "New"

    setupPlayer()
    setupListener()
    setupList()
  }

  private fun setupPlayer(){
    val mediaPresenter = SimpleMediaPlayerPresenter()
    .startRecordListener {
      newRecordButton.text = "Finish"
    }.stopRecordListener {
      newRecordButton.text = "New"
    }.startPlayListener {
      playingListItemView?.changePauseButtonPause()
    }.pausePlayListener {
      playingListItemView?.changePauseButtonPlay()
    }.stopPlayListener {
      playingListItemView?.hidePlayerButtons()
    }
    mediaPlayer = SimpleMediaPlayer(this, this, mediaPresenter)
  }

  private fun setupListener(){
    presenter = MainActivityPresenter(this, this, mediaPlayer)
    newRecordButton.setOnClickListener {
      presenter.onCLickNewRecord()
    }
  }

  private fun setupList(){
    val states = ArrayList<RecordListState>()

    filesDir.listFiles().forEachIndexed { i, file ->
      presenter.addStateIfTargetFile(i, file, { state ->
        states.add(state)
      })
    }

    val recordListPresenter = RecordListItemPresenter()
    recordListPresenter.onClickCell = { state ->
      mediaPlayer.play(state.filePath)
      playingListItemView = adapter?.getPositionView(adapter?.getPosition(state) ?: 0)
    }

    recordListPresenter.onLongClickCell = { state -> presenter.onLongClickCell(state) }
    recordListPresenter.onClickPlay = { mediaPlayer.startPlayInPausing() }
    recordListPresenter.onClickPause = { mediaPlayer.pausePlay() }
    recordListPresenter.onClickStop = { mediaPlayer.stopPlay() }
    adapter = RecordListAdapter(this, states, recordListPresenter)
    mainList.adapter = adapter
  }

  override fun addStateFromFilePath(filePath: String) {
    val newState = RecordListState.createStateFromFilePath((adapter?.count ?: 0) + 1, filePath)
    addState(newState)
  }

  override fun addState(state: RecordListState) {
    adapter?.add(state)
    mainList.smoothScrollToPosition(adapter?.count ?: 0)  // 最終行へゆっくりスクロール
  }

  override fun removeState(state: RecordListState) {
    adapter?.remove(state)
  }

}

