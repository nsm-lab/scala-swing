/*
 * scala-swing (https://www.scala-lang.org)
 *
 * Copyright EPFL, Lightbend, Inc., contributors
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala.swing

import javax.swing.JProgressBar

/**
 * A bar indicating progress of some action. Can be in indeterminate mode,
 * in which it indicates that the action is in progress (usually by some
 * animation) but does not indicate the amount of work done or to be done.
 *
 * @see javax.swing.JProgressBar
 */
class ProgressBar extends Component with Orientable.Wrapper {
  override lazy val peer: JProgressBar =
    new javax.swing.JProgressBar with SuperMixin

  def min: Int = peer.getMinimum
  def min_=(v: Int): Unit = peer.setMinimum(v)
  def max: Int = peer.getMaximum
  def max_=(v: Int): Unit = peer.setMaximum(v)
  def value: Int = peer.getValue
  def value_=(v: Int): Unit = peer.setValue(v)

  def labelPainted: Boolean = peer.isStringPainted
  def labelPainted_=(v: Boolean): Unit = peer.setStringPainted(v)

  def label: String = peer.getString
  def label_=(v: String): Unit = peer.setString(v)

  def indeterminate: Boolean = peer.isIndeterminate
  def indeterminate_=(v: Boolean): Unit = peer.setIndeterminate(v)

  def paintBorder: Boolean = peer.isBorderPainted
  def paintBorder(v: Boolean): Unit = peer.setBorderPainted(v)
}
