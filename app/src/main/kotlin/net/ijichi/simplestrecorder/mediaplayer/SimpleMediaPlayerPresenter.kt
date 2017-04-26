package net.ijichi.simplestrecorder.mediaplayer

/**
 * Created by ijichiyoshihito on 2017/02/03.
 */
class SimpleMediaPlayerPresenter {

  private var startRecordListener: (()->Unit)? = null
  private var stopRecordListener: (()->Unit)? = null
  private var startPlayListener: (()->Unit)? = null
  private var pausePlayListener: (()->Unit)? = null
  private var stopPlayListener: (()->Unit)? = null

  fun startRecordListener(callback:(()->Unit)): SimpleMediaPlayerPresenter {
    startRecordListener = callback
    return this
  }

  fun stopRecordListener(callback:(()->Unit)): SimpleMediaPlayerPresenter {
    stopRecordListener = callback
    return this
  }

  fun startPlayListener(callback:(()->Unit)): SimpleMediaPlayerPresenter {
    startPlayListener = callback
    return this
  }

  fun pausePlayListener(callback:(()->Unit)): SimpleMediaPlayerPresenter {
    pausePlayListener = callback
    return this
  }

  fun stopPlayListener(callback:(()->Unit)): SimpleMediaPlayerPresenter {
    stopPlayListener = callback
    return this
  }

  fun invokeStartRecordListener(){
    startRecordListener?.invoke()
  }

  fun invokeStopRecordListener(){
    stopRecordListener?.invoke()
  }

  fun invokeStartPlayListener(){
    startPlayListener?.invoke()
  }

  fun invokePausePlayListener(){
    pausePlayListener?.invoke()
  }

  fun invokeStopPlayListener(){
    stopPlayListener?.invoke()
  }


}
