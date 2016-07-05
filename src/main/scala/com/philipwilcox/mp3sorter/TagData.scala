package com.philipwilcox.mp3sorter

import java.io.File

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.{FieldKey, Tag}

/**
  * Wrapper interface for JAudioTagger to return metadata extracted from a File'ss tags.
  */
object TagData {
  def readFromFile(file: File): TagData = {

    val audioFile = AudioFileIO.read(file)
    new TagData(audioFile.getTag)
  }
}

class TagData(tag: Tag) {
  // TODO Jul 4, pmw: escape stuff for path safety

  def artist() = {
    tag.getFirst(FieldKey.ARTIST)
  }
  def year() = {
    // TODO this can be a full date structure of some sort for M4A tracks from iTunes
    tag.getFirst(FieldKey.YEAR)
  }
  def album() = {
    tag.getFirst(FieldKey.ALBUM)
  }
  def track() = {
    // TODO parse to int first
    // TODO also check if there are multiple discs, build a string like 1-05 if so (FieldKey.DISC_NO and such - they might be empty!)
    tag.getFirst(FieldKey.TRACK)
  }
  def title() = {
    tag.getFirst(FieldKey.TITLE)
  }

  override def toString = s"TagData($artist(), $year(), $album(), $track(), $title())"
}
