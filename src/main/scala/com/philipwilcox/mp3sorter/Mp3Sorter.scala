package com.philipwilcox.mp3sorter

case class Mp3ParserSettings(verbose: Boolean, dryRun: Boolean, targetDirectory: Option[String])

import scaldi.Module

object Mp3Sorter extends Module {
  bind [TagDataHelper] to new TagDataHelper

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
      println(s"Verbose? ${settings.verbose}")
      println(s"Dry run? ${settings.dryRun}")
      println(s"Target dir: ${settings.targetDirectory.get}")
      val directoryScanner = new DirectoryScanner(settings.targetDirectory.get)
      if (settings.verbose) {
        val fileMoves = directoryScanner.moveInformation()
        if (settings.dryRun) {
          println(s"WOULD MOVE ${fileMoves.length} FILES:")
        } else {
          println("MOVING ${fileMoves.length} FILES:")
        }
        println(fileMoves.mkString("\n"))
      }
      // Finally, move the files around
      if (!settings.dryRun) {
        directoryScanner.moveFiles()
      }
      // TODO Jul 4, pmw: add an option at the end to clean up any now-empty dirs
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