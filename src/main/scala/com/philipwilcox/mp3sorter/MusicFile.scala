package com.philipwilcox.mp3sorter

import java.io.File

/**
  * The MusicFile class holds a file, its original name, its metadata, and its target destination name.
  * @param file The Java file object from which to initialize this file.
  */
class MusicFile(file: File) {
  val absolutePath = file.getAbsolutePath
  // TODO Jul 4, pmw: fill in the rest later
}
