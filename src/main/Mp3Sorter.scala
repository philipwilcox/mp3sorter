object Mp3Sorter {

  def main(args: Array[String]): Unit = {
    val helpText =
      """
To run, invoke as follows:
  mp3sorter target_directory
This will sort the files inside target_directory (recursively) into artist/year-album name/track num-track name.mp3 form.
      """
    if (args.length == 0) println(helpText)
    // TODO Jul 4, pmw: fill in the rest!
  }
}