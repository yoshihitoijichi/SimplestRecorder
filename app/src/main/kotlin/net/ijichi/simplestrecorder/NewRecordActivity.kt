package net.ijichi.simplestrecorder

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class NewRecordActivity: Activity() {

  private val recordingText: TextView
    get() = findViewById(R.id.new_record_recording_text) as TextView

  private val recordingTimeText: TextView
    get() = findViewById(R.id.new_record_recording_time_text) as TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_record)

  }

}
