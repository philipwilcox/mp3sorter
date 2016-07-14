package com.philipwilcox.mp3sorter

import org.jaudiotagger.tag.{FieldKey, Tag}


/**
  * Test for the TagData class that wraps Jaudiotagger's Tag class
  */
class TagDataSpec extends UnitSpec {

  case class FixtureParam(tag: Tag)

  def withFixture(test: OneArgTest) = {
    // Perform setup
    val tag = mock[Tag]
    val fixture = FixtureParam(tag)
    withFixture(test.toNoArgTest(fixture)) // "loan" the fixture to the test
  }

  "TagDataSpec" should "album" in { fixture =>
    println("YO")
  }

}
