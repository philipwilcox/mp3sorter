package com.philipwilcox.mp3sorter

import java.io.File
import java.time.ZonedDateTime

import org.apache.commons.lang3.StringUtils
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.{FieldKey, Tag}
import org.joda.time.DateTime

/**
  * Wrapper interface for JAudioTagger to return metadata extracted from a File'ss tags.
  */
class TagDataHelper {
  def readFromFile(file: File): TagData = {
    val audioFile = AudioFileIO.read(file)
    new TagData(audioFile.getTag)
  }
}

class TagData(tag: Tag) {

  def artist() = tag.getFirst(FieldKey.ARTIST)

  def year(): String = {
    val stringYear = tag.getFirst(FieldKey.YEAR)
    if (StringUtils.isBlank(stringYear)) {
      // TODO inject settings down here to only warn on verbose? or use logging framework?
      println(s"  WARNING: could not read year for track ${tag.getFirst(FieldKey.TITLE)} by ${tag.getFirst(FieldKey.ARTIST)}")
      return "unknown"
    }
    try {
      if (stringYear.length == 10) {
        val dateTime = DateTime.parse(stringYear)
        dateTime.getYear.toString
      } else if (stringYear.length > 4) {
        // iTunes Store files contain UTC timestamps as year, parse just the Year part
        val zonedDateTime = ZonedDateTime.parse(stringYear)
        zonedDateTime.getYear.toString
      } else {
        stringYear
      }
    } catch {
      case e: Exception => {
        println(s"  WARNING: could not parse year from $stringYear")
        "unknown"
      }
    }
  }
  def album() = tag.getFirst(FieldKey.ALBUM)

  def track() = {
    val stringBuilder = new StringBuilder
    val totalDiscsString = tag.getFirst(FieldKey.DISC_TOTAL)
    if (StringUtils.isNotBlank(totalDiscsString) && totalDiscsString.toInt > 1) {
      stringBuilder.append(f"${tag.getFirst(FieldKey.DISC_NO).toInt}%02d-")
    }
    // Use the total track count to decide how much to pad the track number
    val totalTracksString = tag.getFirst(FieldKey.TRACK_TOTAL)
    val totalTracks = if (StringUtils.isBlank(totalTracksString)) {
      99 // default to assuming that there are double-digit number of tracks
    } else {
      totalTracksString.toInt // use actual total tracks number if available
    }
    val thisTrackString = tag.getFirst(FieldKey.TRACK)
    // If we don't have track info at all, we fall back to blank
    if (StringUtils.isNotBlank(thisTrackString)) {
      val thisTrack = thisTrackString.toInt
      // the more than 1000 track case seems unimportant, and I couldn't get it to build a
      // "f" interpolation string with dynamic padding
      totalTracks match {
        case x if 0 <= x && x < 10 => stringBuilder.append(s"$thisTrack")
        case x if 10 <= x && x < 100 => stringBuilder.append(f"$thisTrack%02d")
        case _ => stringBuilder.append(f"$thisTrack%03d")
      }
    }
    stringBuilder.toString()
  }

  def title() = tag.getFirst(FieldKey.TITLE)

  override def toString = s"TagData($artist, $year, $album, $track, $title)"
}
