package com.philipwilcox.mp3sorter

import java.io.File

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey

/**
  * Wrapper interface for JAudioTagger to take a file and extract its metadata from its tags.
  */
class FileDataReader(file: File) {

  val audioFile = AudioFileIO.read(file)

  // TODO Jul 4, pmw: escape stuff for path safety

  def artist() = {
    audioFile.getTag.getFirst(FieldKey.ARTIST)
  }
  def year() = {
    audioFile.getTag.getFirst(FieldKey.YEAR)
  }
  def album() = {
    audioFile.getTag.getFirst(FieldKey.ALBUM)
  }
  def track() = {
    audioFile.getTag.getFirst(FieldKey.TRACK)
  }
  def title() = {
    audioFile.getTag.getFirst(FieldKey.TITLE)
  }

  override def toString: String = {
    s"FileDataReader(file: ${file.getAbsolutePath}, tagData: ${artist}, ${year}, ${album}, ${track}, ${title})"
  }
}
