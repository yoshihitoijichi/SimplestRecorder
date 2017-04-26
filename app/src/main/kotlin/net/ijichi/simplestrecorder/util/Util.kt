package net.ijichi.simplestrecorder.util


/**
 * Created by ijichiyoshihito on 2017/02/07.
 */
class Util{

  companion object{
    fun getSuffix(fileName: String?): String? {
      if (fileName == null) return null
      val point = fileName.lastIndexOf(".")
      if (point != -1) {
        return fileName.substring(point + 1)
      }
      return null
    }

    fun getFileNameRemoveSuffix(fileName: String?): String? {
      if (fileName == null) return null
      val point = fileName.lastIndexOf(".")
      if (point != -1) {
        return fileName.substring(0, point)
      }
      return null
    }

    fun getFileName(filePath: String?): String? {
      if (filePath == null) return null
      val point = filePath.lastIndexOf("/")
      if (point != -1) {
        return filePath.substring(point + 1)
      }
      return null
    }

    fun changeFilePath(filePath: String, newFileName: String): String? {
      val oldFileName = getFileName(filePath)
      println("oldFileName = $oldFileName")
      val point = filePath.indexOf(oldFileName ?: "")
      if (point != -1) {
        val path = filePath.substring(0, point)
        println("path = $path")
        return path + newFileName
      }
      return null
    }

    fun timeFormat(millSecond: Long): String{
      val hh = millSecond / 1000 / 60 % 24 // 時
      val mm = millSecond / 1000 / 60 // 分
      val ss = millSecond / 1000 % 60 // 秒
      val ms = millSecond - mm * 1000 * 60 - ss * 1000 // 残りのミリ秒
      val time = String.format("%1$02d:%2$02d:%3$02d.%4$03d", hh, mm, ss, ms)
      return time
    }
  }

}
