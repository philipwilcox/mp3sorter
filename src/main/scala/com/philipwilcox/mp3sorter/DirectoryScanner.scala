package com.philipwilcox.mp3sorter

import java.io.File

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class DirectoryScanner(baseDirectoryPath: String) {
  if (baseDirectoryPath == null || "".equalsIgnoreCase(baseDirectoryPath)) {
    throw new Exception("Can't initialize with empty directory path!")
  }

  private val fileArray = recursiveListFiles(new java.io.File(baseDirectoryPath))

  private val musicFileList = fileArray.map(f => new MusicFile(f, TagData.readFromFile(f)))

  private val moveInformationPathMap = {
    val mutableMap = mutable.HashMap.empty[String, String]
    for (m <- musicFileList) {
      mutableMap += (m.absolutePath -> (baseDirectoryPath + "/" + m.relativeOutputPath))
    }
    mutableMap.toMap
  }

  private def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }


  /**
    * This returns a string containing, one per line, the old absolute path and new absolute path for the file.
    */
  def moveInformation(): String = {
    val stringList = new ListBuffer[String]()
    for ((oldPath, newPath) <- moveInformationPathMap) {
      stringList += s"$oldPath -> $newPath"
    }
    stringList.mkString("\n")
  }

  // TODO Jul 4, pmw: this will expose "print move information" as well as "move files" methods that will be
  // called by caller as appropriate
}
