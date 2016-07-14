package com.philipwilcox.mp3sorter

import org.scalatest.{fixture, Matchers}
import org.scalamock.scalatest.MockFactory

/**
  * Base class for my spec tests.
  */
abstract class UnitSpec extends fixture.FlatSpec
  with MockFactory
