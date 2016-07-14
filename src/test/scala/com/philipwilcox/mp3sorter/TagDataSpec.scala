package com.philipwilcox.mp3sorter

import org.jaudiotagger.tag.{FieldKey, Tag}


/**
  * Test for the TagData class that wraps Jaudiotagger's Tag class
  */
class TagDataSpec extends UnitSpec {

  val TestAlbumName = "Test Album"

  case class FixtureParam(tag: Tag)

  def withFixture(test: OneArgTest) = {
    // Perform setup
    val tag = mock[Tag]
    (tag.getFirst (_: FieldKey)).expects(FieldKey.ALBUM).returning(TestAlbumName)
    val fixture = FixtureParam(tag)
    withFixture(test.toNoArgTest(fixture)) // "loan" the fixture to the test
  }

  "TagDataSpec" should "album" in { fixture =>
    val tagData = new TagData(fixture.tag)
    val albumResult = tagData.album()
    albumResult should be (TestAlbumName)
  }

}
