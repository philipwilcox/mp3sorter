package com.philipwilcox.mp3sorter

import java.io.File

import org.apache.commons.io.FileUtils

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
    val allFilesAndDirs = f.listFiles
    val filesOnly = allFilesAndDirs.filter(_.isFile)
    filesOnly ++ allFilesAndDirs.filter(_.isDirectory).flatMap(recursiveListFiles)
  }


  /**
    * This returns a list of strings containing, one per string, the old absolute path and new absolute path for a file.
    *
    * Files that will not be moved will be ignored.
    */
  def moveInformation() = {
    val stringList = new ListBuffer[String]()
    for ((oldPath, newPath) <- moveInformationPathMap) {
      if (!oldPath.equals(newPath)) {
        stringList += s"$oldPath -> $newPath"
      }
    }
    stringList
  }

  /**
    * This actually performs the file movement, moving each file from its original path to its destination path.
    */
  def moveFiles(): Unit = {
    for ((oldPath, newPath) <- moveInformationPathMap) {
      if (!oldPath.equals(newPath)) {
        FileUtils.moveFile(
          FileUtils.getFile(oldPath),
          FileUtils.getFile(newPath)
        )
      }
    }
  }
}
