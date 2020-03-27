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

import javax.swing.{JScrollPane, ScrollPaneConstants}

import scala.collection.immutable

object ScrollPane {
  object BarPolicy extends Enumeration {
    import ScrollPaneConstants._
    val AsNeeded = new Value(HORIZONTAL_SCROLLBAR_AS_NEEDED,
                             VERTICAL_SCROLLBAR_AS_NEEDED)
    val Never = new Value(HORIZONTAL_SCROLLBAR_NEVER,
                          VERTICAL_SCROLLBAR_NEVER)
    val Always = new Value(HORIZONTAL_SCROLLBAR_ALWAYS,
                           VERTICAL_SCROLLBAR_ALWAYS)

    def wrap(id: Int): BarPolicy.Value = id match {
      case HORIZONTAL_SCROLLBAR_AS_NEEDED | VERTICAL_SCROLLBAR_AS_NEEDED  => AsNeeded
      case HORIZONTAL_SCROLLBAR_NEVER     | VERTICAL_SCROLLBAR_NEVER      => Never
      case HORIZONTAL_SCROLLBAR_ALWAYS    | VERTICAL_SCROLLBAR_ALWAYS     => Always
    }

    class Value(val horizontalPeer: Int, val verticalPeer: Int) extends super.Val {
      override def id: Int = horizontalPeer
    }
  }
}

/**
 * Can have at most a single child component, which will be put inside a canvas (the viewport)
 * that can be scrolled.
 *
 * @see javax.swing.JScrollPane
 */
class ScrollPane extends Component with Container {
  import ScrollPane._

  override lazy val peer: JScrollPane = new JScrollPane with SuperMixin
  def this(c: Component) = {
    this()
    contents = c
  }
  def contents: immutable.Seq[Component] =
    List(UIElement.cachedWrapper[Component](peer.getViewport.getView.asInstanceOf[javax.swing.JComponent]))

  /**
   * Sets the single child.
   */
  def contents_=(c: Component): Unit = peer.setViewportView(c.peer)

  /**
   * The component being displayed in this pane's row header.
   *
   * If you want to create a row header for lists or tables, you probably
   * want to let the row header be a list view with the same row height as
   * the viewport component.
   */
  def rowHeaderView: Option[Component] =
    Option(peer.getRowHeader.getView) map UIElement.cachedWrapper[Component]
  def rowHeaderView_=(c: Component): Unit = peer.setRowHeaderView(c.peer)
  def rowHeaderView_=(c: Option[Component]): Unit = peer.setRowHeaderView(c.map(_.peer).orNull)

  def columnHeaderView: Option[Component] =
    Option(peer.getColumnHeader.getView) map UIElement.cachedWrapper[Component]
  def columnHeaderView_=(c: Component): Unit = peer.setColumnHeaderView(c.peer)
  def columnHeaderView_=(c: Option[Component]): Unit = peer.setColumnHeaderView(c.map(_.peer).orNull)

  def viewportView: Option[Component] =
    Option(peer.getViewport.getView) map UIElement.cachedWrapper[Component]
  def viewportView_=(c: Component): Unit = peer.setViewportView(c.peer)
  def viewportView_=(c: Option[Component]): Unit = peer.setViewportView(c.map(_.peer).orNull)

  def verticalScrollBarPolicy: BarPolicy.Value = BarPolicy.wrap(peer.getVerticalScrollBarPolicy)
  def verticalScrollBarPolicy_=(p: BarPolicy.Value): Unit = peer.setVerticalScrollBarPolicy(p.verticalPeer)

  def horizontalScrollBarPolicy: BarPolicy.Value = BarPolicy.wrap(peer.getHorizontalScrollBarPolicy)
  def horizontalScrollBarPolicy_=(p: BarPolicy.Value): Unit = peer.setHorizontalScrollBarPolicy(p.horizontalPeer)

  def horizontalScrollBar: ScrollBar = ScrollBar.wrap(peer.getHorizontalScrollBar)
  def verticalScrollBar: ScrollBar = ScrollBar.wrap(peer.getVerticalScrollBar)
}
