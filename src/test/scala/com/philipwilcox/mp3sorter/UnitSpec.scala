package com.philipwilcox.mp3sorter

import org.scalatest.{FlatSpec, Matchers, fixture}
import org.scalamock.scalatest.MockFactory

/**
  * Base class for my spec tests.
  */
abstract class UnitSpec extends FlatSpec
  with Matchers
  with fixture.TestSuite
  with MockFactory
