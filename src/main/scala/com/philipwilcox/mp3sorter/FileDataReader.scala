package com.philipwilcox.mp3sorter

import java.io.File
import org.jaudiotagger.audio.AudioFileIO

/**
  * Wrapper interface for JAudioTagger to take a file and extract its metadata from its tags.
  */
class FileDataReader(file: File) {

  val audioFile = AudioFileIO.read(file)
  println(audioFile)
}
