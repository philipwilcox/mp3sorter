package com.philipwilcox.mp3sorter

import java.io.File

import org.apache.commons.io.FilenameUtils

/**
  * The MusicFile class holds a file, its original name, its metadata, and its target destination name.
  * @param file The Java file object from which to initialize this file.
  */
class MusicFile(file: File, tagData: TagData) {
  val absolutePath = file.getAbsolutePath

  // Note that right now this is a relative path, not an absolute one!
  val relativeOutputPath = {
    val components = Seq(
      s"${tagData.artist()}",
      s"(${tagData.year()}) ${tagData.album()}",
      s"${tagData.track()}. ${tagData.title()}.${FilenameUtils.getExtension(absolutePath).toLowerCase}"
    )
    // TODO Jul 4, pmw: make settings injectable so we can only show this warning if verbose - actually, just
    // toggle on verbose log level
    val relativePath = components.mkString("/")
    val pathSafeRelativePath = components.map(s => pathSafeComponent(s)).mkString("/")
    if (!relativePath.equalsIgnoreCase(pathSafeRelativePath)) {
      println(s"  NOTE: relative path was $relativePath, path safe version differs: $pathSafeRelativePath")
    }
    pathSafeRelativePath
  }

  override def toString: String = {
    s"MusicFile(input: $absolutePath, tagData: $tagData, output: $relativeOutputPath)"
  }

  /**
    * Escape anything that isn't one of a small list of whitelisted characters with an empty string.
    */
  private def pathSafeComponent(string: String) = {
    string.replaceAll("\\[", "(")
      .replaceAll("\\]", ")")
      .replaceAll(":", " -")
      .replaceAll("/", "-")
      .replaceAll(";", ",")
      .replaceAll("\\.\\Z", "") // can't end a directory component with a dot
      .replaceAll("[^a-zA-Z0-9()_ '&,!.+\\-]+", "")
  }
}
