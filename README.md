# mp3sorter
A little program for arranging MP3 files for my car's media system.

This is also a testbed for trying out some stuff in Scala.

Sorts files in-place into an $artist/($year) $album/$track $title.$extension tree.

This is because, for the songs I put into the car as my "default" tracks when nothing else is playing, I want a curated
list, not my full library, and if you drag all the tracks out of a smart playlist in iTunes they end up all in a flat
folder, so this restores some structure.

Eventual feature wishlist includes optional auto-search with human approval for finding missing album art, if it
wasn't in the file.
Or, look at pulling it out of the iTunes library files with something like https://github.com/computersarehard/java-itc so it's more repeatable and not an every-time process.
Or just process my iTunes library up front with http://dougscripts.com/itunes/scripts/ss.php?sp=reembedartwork (if that still works)
