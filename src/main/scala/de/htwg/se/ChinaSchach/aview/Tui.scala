package de.htwg.se.ChinaSchach.aview

import de.htwg.se.ChinaSchach.model._
import de.htwg.se.ChinaSchach.util.Point

import scala.collection.mutable.ListBuffer


//TODO: implement TUI
class Tui(board: Board) {

  // prints an overview over the fields with the respective pieces
  def outputField(): Unit = {
    for (x <- 0 to 7) {
      for (y <- 0 to 7) {
        if (board.getPiece(Point(x, y)).getSide() != " ") {
          println("Field(" + x + ", " + y + ") contains " + board.getPiece(Point(x, y)))
        }
      }
    }
    println("")
    println("Field (0, 2) contains " + board.getPiece(Point(0, 2)))
    println("")
  }

  //prints a list with pieces for each player
  def outputPlayerFigures(list: ListBuffer[Piece]): Unit = {
    if (list.head.getSide() == "b") println("Player 1: ") else println("Player 2: ")
    list.foreach(println)
    println("")
  }

  //TODO: which Round
}
