package com.philipwilcox.mp3sorter

import java.io.File

class FileScanner(baseDirectoryPath: String) {
  if (baseDirectoryPath == null || "".equalsIgnoreCase(baseDirectoryPath)) {
    throw new Exception("Can't initialize with empty directory path!")
  }

  val fileArray = recursiveListFiles(new java.io.File(baseDirectoryPath))

  println(fileArray)

  val firstFileData = new FileDataReader(fileArray.apply(0))

  private def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }
}
