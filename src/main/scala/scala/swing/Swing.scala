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

import java.awt
import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.border.{BevelBorder, Border, CompoundBorder, MatteBorder, TitledBorder}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{BorderFactory, Icon, ImageIcon, JComponent, SwingUtilities}

/**
 * Helpers for this package.
 */
object Swing {
  protected[swing] type PeerContainer = { def peer: awt.Container }

  protected[swing] def toNoIcon   (i: Icon): Icon = if (i == null) EmptyIcon else i
  protected[swing] def toNullIcon (i: Icon): Icon = if (i == EmptyIcon) null else i
  protected[swing] def nullPeer(c: PeerContainer): awt.Container = if (c != null) c.peer else null

  implicit def pair2Dimension(p: (Int, Int))          : Dimension = new Dimension (p._1, p._2)
  implicit def pair2Point    (p: (Int, Int))          : Point     = new Point     (p._1, p._2)
  implicit def pair2Point    (p: (Int, Int, Int, Int)): Rectangle = new Rectangle (p._1, p._2, p._3, p._4)

  @inline final def Runnable(@inline block: => Unit): Runnable = new Runnable {
    def run(): Unit = block
  }
  final def ChangeListener(f: ChangeEvent => Unit): ChangeListener = new ChangeListener {
    def stateChanged(e: ChangeEvent): Unit = f(e)
  }
  final def ActionListener(f: ActionEvent => Unit): ActionListener = new ActionListener {
    def actionPerformed(e: ActionEvent): Unit = f(e)
  }

  def Box(min: Dimension, pref: Dimension, max: Dimension): Component = new Component {
    override lazy val peer: JComponent = new javax.swing.Box.Filler(min, pref, max)
  }
  def HGlue: Component = new Component {
    override lazy val peer: JComponent = javax.swing.Box.createHorizontalGlue.asInstanceOf[JComponent]
  }
  def VGlue: Component = new Component {
    override lazy val peer: JComponent = javax.swing.Box.createVerticalGlue.asInstanceOf[JComponent]
  }
  def Glue: Component = new Component {
    override lazy val peer: JComponent = javax.swing.Box.createGlue.asInstanceOf[JComponent]
  }
  def RigidBox(dim: Dimension): Component = new Component {
    override lazy val peer: JComponent = javax.swing.Box.createRigidArea(dim).asInstanceOf[JComponent]
  }
  def HStrut(width: Int): Component = new Component {
    override lazy val peer: JComponent = javax.swing.Box.createHorizontalStrut(width).asInstanceOf[JComponent]
  }
  def VStrut(height: Int): Component = new Component {
    override lazy val peer: JComponent = javax.swing.Box.createVerticalStrut(height).asInstanceOf[JComponent]
  }

  def Icon(image: java.awt.Image) : ImageIcon = new ImageIcon(image)
  def Icon(filename: String)      : ImageIcon = new ImageIcon(filename)
  def Icon(url: java.net.URL)     : ImageIcon = new ImageIcon(url)

  /**
   * The empty icon. Use this icon instead of <code>null</code> to indicate
   * that you don't want an icon.
   */
  case object EmptyIcon extends Icon {
    def getIconHeight: Int = 0
    def getIconWidth: Int = 0
    def paintIcon(c: java.awt.Component, g: java.awt.Graphics, x: Int, y: Int): Unit = ()
  }

  def unwrapIcon(icon: Icon): Icon = if (icon == null) EmptyIcon else icon
  def wrapIcon  (icon: Icon): Icon = if (icon == EmptyIcon) null else icon

  def EmptyBorder: Border = BorderFactory.createEmptyBorder()
  def EmptyBorder(weight: Int): Border  =
    BorderFactory.createEmptyBorder(weight, weight, weight, weight)
  def EmptyBorder(top: Int, left: Int, bottom: Int, right: Int): Border  =
    BorderFactory.createEmptyBorder(top, left, bottom, right)

  def LineBorder(c: Color): Border  = BorderFactory.createLineBorder(c)
  def LineBorder(c: Color, weight: Int): Border  = BorderFactory.createLineBorder(c, weight)

  def BeveledBorder(kind: Embossing): Border  = BorderFactory.createBevelBorder(kind.bevelPeer)
  def BeveledBorder(kind: Embossing, highlight: Color, shadow: Color): Border  =
    BorderFactory.createBevelBorder(kind.bevelPeer, highlight, shadow)
  def BeveledBorder(kind: Embossing,
              highlightOuter: Color, highlightInner: Color,
              shadowOuter: Color, shadowInner: Color): Border  =
    BorderFactory.createBevelBorder(kind.bevelPeer,
          highlightOuter, highlightInner,
          shadowOuter, shadowInner)

  sealed abstract class Embossing {
    def bevelPeer: Int
    def etchPeer: Int
  }
  case object Lowered extends Embossing {
    def bevelPeer: Int = BevelBorder.LOWERED
    def etchPeer: Int = javax.swing.border.EtchedBorder.LOWERED
  }
  case object Raised extends Embossing {
    def bevelPeer: Int = BevelBorder.RAISED
    def etchPeer: Int = javax.swing.border.EtchedBorder.RAISED
  }

  def EtchedBorder: Border = BorderFactory.createEtchedBorder()
  def EtchedBorder(kind: Embossing): Border =
    BorderFactory.createEtchedBorder(kind.etchPeer)
  def EtchedBorder(kind: Embossing, highlight: Color, shadow: Color): Border =
    BorderFactory.createEtchedBorder(kind.etchPeer, highlight, shadow)

  def MatteBorder(top: Int, left: Int, bottom: Int, right: Int, color: Color): MatteBorder =
    BorderFactory.createMatteBorder(top, left, bottom, right, color)
  def MatteBorder(top: Int, left: Int, bottom: Int, right: Int, icon: Icon): MatteBorder =
    BorderFactory.createMatteBorder(top, left, bottom, right, icon)

  def CompoundBorder(outside: Border, inside: Border): CompoundBorder =
    BorderFactory.createCompoundBorder(outside, inside)

  def TitledBorder(border: Border, title: String): TitledBorder =
    BorderFactory.createTitledBorder(border, title)

  /**
   * Schedule the given code to be executed on the Swing event dispatching
   * thread (EDT). Returns immediately.
   */
  @inline final def onEDT(op: => Unit): Unit = SwingUtilities invokeLater Runnable(op)

  /**
   * Schedule the given code to be executed on the Swing event dispatching
   * thread (EDT). Blocks until after the code has been run.
   */
  @inline final def onEDTWait(op: => Unit): Unit = SwingUtilities invokeAndWait Runnable(op)
}
