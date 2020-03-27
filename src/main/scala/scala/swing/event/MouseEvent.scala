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
package event

import java.awt.Point
import javax.swing.JComponent

sealed abstract class MouseEvent extends InputEvent {
  def peer: java.awt.event.MouseEvent
  def point: Point
}

sealed abstract class MouseButtonEvent extends MouseEvent {
  def clicks: Int
  def triggersPopup: Boolean
}
case class MouseClicked(source: Component, point: Point, modifiers: Key.Modifiers,
                        clicks: Int, triggersPopup: Boolean)(val peer: java.awt.event.MouseEvent)
           extends MouseButtonEvent {
  def this(e: java.awt.event.MouseEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx, e.getClickCount, e.isPopupTrigger)(e)
}
case class MousePressed(source: Component, point: Point, modifiers: Key.Modifiers,
                        clicks: Int, triggersPopup: Boolean)(val peer: java.awt.event.MouseEvent)
           extends MouseButtonEvent {
  def this(e: java.awt.event.MouseEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx, e.getClickCount, e.isPopupTrigger)(e)
}
case class MouseReleased(source: Component, point: Point, modifiers: Key.Modifiers,
                         clicks: Int, triggersPopup: Boolean)(val peer: java.awt.event.MouseEvent)
           extends MouseButtonEvent {
  def this(e: java.awt.event.MouseEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx, e.getClickCount, e.isPopupTrigger)(e)
}

sealed abstract class MouseMotionEvent extends MouseEvent
case class MouseMoved(source: Component, point: Point, modifiers: Key.Modifiers)(val peer: java.awt.event.MouseEvent)
           extends MouseMotionEvent {
  def this(e: java.awt.event.MouseEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx)(e)
}
case class MouseDragged(source: Component, point: Point, modifiers: Key.Modifiers)(val peer: java.awt.event.MouseEvent)
           extends MouseMotionEvent {
  def this(e: java.awt.event.MouseEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx)(e)
}
case class MouseEntered(source: Component, point: Point, modifiers: Key.Modifiers)(val peer: java.awt.event.MouseEvent)
           extends MouseMotionEvent {
  def this(e: java.awt.event.MouseEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx)(e)
}
case class MouseExited(source: Component, point: Point, modifiers: Key.Modifiers)(val peer: java.awt.event.MouseEvent)
           extends MouseMotionEvent {
  def this(e: java.awt.event.MouseEvent) =
      this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
          e.getPoint, e.getModifiersEx)(e)
}

case class MouseWheelMoved(source: Component, point: Point, modifiers: Key.Modifiers, rotation: Int)(val peer: java.awt.event.MouseEvent)
           extends MouseEvent {
  def this(e: java.awt.event.MouseWheelEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getPoint, e.getModifiersEx, e.getWheelRotation)(e)
}
