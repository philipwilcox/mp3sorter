package com.philipwilcox.mp3sorter

import java.io.File

class DirectoryScanner(baseDirectoryPath: String) {
  if (baseDirectoryPath == null || "".equalsIgnoreCase(baseDirectoryPath)) {
    throw new Exception("Can't initialize with empty directory path!")
  }

  private val fileArray = recursiveListFiles(new java.io.File(baseDirectoryPath))

  // Build up data structures about additional file information
  analyzeFiles()

  private def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  private def analyzeFiles(): Unit = {
    for (f <- fileArray) {
      // TODO Jul 4, pmw: if I want to test at this level, I'll need to inject and mock out TagData object
      val musicFile = new MusicFile(f, TagData.readFromFile(f))
      println(musicFile) // TODO Jul 4, pmw: map to a MusicFile list
    }
  }

  // TODO Jul 4, pmw: this will expose "print move information" as well as "move files" methods that will be
  // called by caller as appropriate
}
