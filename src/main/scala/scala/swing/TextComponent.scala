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

import javax.swing.event.{CaretEvent, CaretListener, DocumentEvent, DocumentListener}
import javax.swing.text.JTextComponent

object TextComponent {
  trait HasColumns extends TextComponent {
    def columns: Int
    def columns_=(n: Int): Unit
  }
  trait HasRows extends TextComponent {
    def rows: Int
    def rows_=(n: Int): Unit
  }
}

/**
 * A component that allows some kind of text input and display.
 *
 * @see javax.swing.JTextComponent
 */
class TextComponent extends Component with Publisher {
  override lazy val peer: JTextComponent = new JTextComponent with SuperMixin {}
  def text: String = peer.getText
  def text_=(t: String): Unit = peer.setText(t)

  class Caret extends Publisher {
    def dot: Int = peer.getCaret.getDot
    def dot_=(n: Int): Unit = peer.getCaret.setDot(n)
    def mark: Int = peer.getCaret.getMark
    def moveDot(n: Int): Unit = peer.getCaret.moveDot(n)
    def visible: Boolean = peer.getCaret.isVisible
    def visible_=(b: Boolean): Unit = peer.getCaret.setVisible(b)
    def selectionVisible: Boolean = peer.getCaret.isSelectionVisible
    def selectionVisible_=(b: Boolean): Unit = peer.getCaret.setSelectionVisible(b)
    def blinkRate: Int = peer.getCaret.getBlinkRate
    def blinkRate_=(n: Int): Unit = peer.getCaret.setBlinkRate(n)
    def color: Color = peer.getCaretColor
    def color_=(c: Color): Unit = peer.setCaretColor(c)
    def position: Int = peer.getCaretPosition
    def position_=(p: Int): Unit = peer.setCaretPosition(p)

    peer.addCaretListener {
      new CaretListener {
        def caretUpdate(e: CaretEvent): Unit = publish(event.CaretUpdate(TextComponent.this))
      }
    }
  }

  object caret extends Caret

  def editable: Boolean = peer.isEditable
  def editable_=(x: Boolean): Unit = peer.setEditable(x)

  def cut  (): Unit = peer.cut()
  def copy (): Unit = peer.copy()
  def paste(): Unit = peer.paste()

  def selected: String = peer.getSelectedText

  def selectAll(): Unit = peer.selectAll()

  peer.getDocument.addDocumentListener(new DocumentListener {
    def changedUpdate(e: DocumentEvent): Unit = publish(new event.ValueChanged(TextComponent.this))
    def insertUpdate (e: DocumentEvent): Unit = publish(new event.ValueChanged(TextComponent.this))
    def removeUpdate (e: DocumentEvent): Unit = publish(new event.ValueChanged(TextComponent.this))
  })
}
