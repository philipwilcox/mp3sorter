package com.philipwilcox.mp3sorter

import org.jaudiotagger.tag.{FieldKey, Tag}


/**
  * Test for the TagData class that wraps Jaudiotagger's Tag class
  */
class TagDataSpec extends UnitSpec {

  case class FixtureParam(tag: Tag)

  override def withFixture(test: OneArgTest) = {
    // Perform setup
    val tag = mock[Tag]
    val fixture = FixtureParam(tag)
    try {
      withFixture(test.toNoArgTest(fixture)) // "loan" the fixture to the test
    }
  }

  behavior of "TagDataSpec"

  it should "album" in {
    println("YO")
  }

  it should "return value from Tag object for artist" in {

  }

  it should "year" in {

  }

  it should "title" in {

  }

  it should "track" in {

  }

  it should "toString" in {

  }

}
