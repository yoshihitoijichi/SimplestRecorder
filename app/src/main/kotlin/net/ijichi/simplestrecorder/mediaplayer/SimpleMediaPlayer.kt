package net.ijichi.simplestrecorder.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import net.ijichi.simplestrecorder.MainActivityContract
import java.io.File

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class SimpleMediaPlayer(val context: Context, val contract: MainActivityContract, val presenter: SimpleMediaPlayerPresenter) {

  private var mediaRecorder = MediaRecorder()

  val fileSuffix = "wav"
  var mediaFile: File? = null

  private var recording = false

  companion object{
    fun getDuration(filePath: String): Int{
      val mediaPlayer = MediaPlayer()
      mediaPlayer.setDataSource(filePath)
      mediaPlayer.prepare()
      val length = mediaPlayer.duration
      mediaPlayer.release()
      return length
    }
  }

  fun record(){
    if(recording)
      stopRecord()
    else
      startRecord()
    recording = !recording
  }

  // 録音開始
  private fun startRecord() {
    var plusIndex = 1
    var nextFileSuffix = context.filesDir.listFiles().size + plusIndex
    var filePath = "${context.filesDir}/File$nextFileSuffix.$fileSuffix"
    mediaFile = File(filePath)
    while (mediaFile?.exists() ?: false){
      plusIndex++
      nextFileSuffix = context.filesDir.listFiles().size + plusIndex
      filePath = "${context.filesDir}/File$nextFileSuffix.$fileSuffix"
      mediaFile = File(filePath)
    }
    mediaRecorder = MediaRecorder()
    //マイクからの音声を録音する
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
    //ファイルへの出力フォーマット DEFAULTにするとwavが扱えるはず
    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
    //音声のエンコーダーも合わせてdefaultにする
    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
    //ファイルの保存先を指定
    mediaRecorder.setOutputFile(filePath)
    //録音の準備をする
    mediaRecorder.prepare()
    //録音開始
    mediaRecorder.start()
    presenter.invokeStartRecordListener()
  }

  // 録音停止
  private fun stopRecord() {
    mediaRecorder.stop()
    mediaRecorder.reset()
    presenter.invokeStopRecordListener()
    contract.addStateFromFilePath(mediaFile?.canonicalPath ?: "")
  }

  // mediaPlayer
  private var mediaPlayer =  MediaPlayer()

  fun play(filePath: String?){
    if(mediaPlayer.isPlaying) stopPlay()
    startPlay(filePath)
  }

  // 再生
  private fun startPlay(filePath: String?) {
    mediaPlayer.reset()
    mediaPlayer.setDataSource(filePath)
    mediaPlayer.prepare()
    mediaPlayer.setOnCompletionListener {
      presenter.invokeStopPlayListener()
    }
    mediaPlayer.start()
    presenter.invokeStartPlayListener()
  }

  fun startPlayInPausing(){
    mediaPlayer.start()
    presenter.invokeStartPlayListener()
  }

  fun pausePlay(){
    mediaPlayer.pause()
    presenter.invokePausePlayListener()
  }

  // 再生停止
  fun stopPlay(){
    mediaPlayer.stop()
    presenter.invokeStopPlayListener()
  }

}
