case class Mp3ParserSettings(verbose: Boolean, dryRun: Boolean, targetDirectory: Option[String])

object Mp3Sorter {

  def main(args: Array[String]): Unit = {
    val helpText =
      """
To run, invoke as follows:
    mp3sorter target_directory
This will sort the files inside target_directory (recursively) into artist/year-album name/track num-track name.mp3 form.

Options:
    -v        Show the original source and target destination for each file. (Implied by --dry-run)
    --dry-run Only print what the command would do, don't actually move any files.
      """
    val defaultSettings = Mp3ParserSettings(verbose = false, dryRun = false, targetDirectory = None)
    val settings = optionParserHelper(defaultSettings, args.toList)
    // Check that they gave a target directory
    if (settings.targetDirectory.isEmpty) {
      println(helpText)
    } else {
      // TODO Jul 4, pmw: do something useful with these settings!
      println(s"Verbose? ${settings.verbose}")
      println(s"Dry run? ${settings.dryRun}")
      println(s"Target dir: ${settings.targetDirectory.get}")
    }
  }


  /**
    * Based on http://stackoverflow.com/questions/2315912/scala-best-way-to-parse-command-line-parameters-cli
    *
    * Recursively parses any options into a Settings object.
    *
    * NOTE: this must be initially passed a Settings object initialized with desired default values!
    */
  def optionParserHelper(settings: Mp3ParserSettings, list: List[String]): Mp3ParserSettings = {
    def isSwitch(s : String) = s(0) == '-'
    list match {
      case Nil => settings // Terminal case at end of list
      case "-v" :: tail => optionParserHelper(
        Mp3ParserSettings(verbose = true, dryRun = settings.dryRun, targetDirectory = settings.targetDirectory),
        tail)
      case "--dry-run" :: tail => optionParserHelper(
        Mp3ParserSettings(verbose = true, dryRun = true, targetDirectory = settings.targetDirectory),
        tail)
      case string :: tail => optionParserHelper(
        Mp3ParserSettings(verbose = settings.verbose, dryRun = settings.dryRun, targetDirectory = Some(string)),
        tail)
    }
  }
}