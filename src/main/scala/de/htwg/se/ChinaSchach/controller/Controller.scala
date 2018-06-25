package de.htwg.se.ChinaSchach.controller

import de.htwg.se.ChinaSchach.aview._
import de.htwg.se.ChinaSchach.model._
import de.htwg.se.ChinaSchach.util.Point
import javax.print.attribute.standard.Destination

import scala.collection.mutable.ListBuffer


//TODO: implement Controller
class Controller(name1: String, name2: String, board: Board) {

  val tui = new Tui(board)
  val player1 = new Player()
  val player2 = new Player()
  var listPlayer1: ListBuffer[Piece] = ListBuffer.empty
  var listPlayer2: ListBuffer[Piece] = ListBuffer.empty
  var topKingDead = false
  var bottomKingDead = false
  var moveDone = false
  var winner = ""
  var message = ""
  var gameOver = false
  var sourcePiece: Piece = _
  var sourcePoint: Point = _
  var round = 0
  var testPoint = Point(0, 1)
  var testDest = Point(0, 2)

  //initializes playing board
  def boardInit() : Unit = {
    board.go()
//    getSelectedPoint(testPoint)
//    getSelectedPoint(testDest)
    tui.outputField()
    playerInit()

  }

  //initialize player
  def playerInit() : Unit = {

    listPlayer1.appendAll(player1.setPieces(board, "w"))
    listPlayer2.appendAll(player2.setPieces(board, "b"))
    println("listPlayer1" + listPlayer1)

    tui.outputPlayerFigures(listPlayer1)
    tui.outputPlayerFigures(listPlayer2)

  }

  def getSelectedPoint(point: Point): Unit = {
    board.gameBoard.get(point) match {
      case None =>
        message = "Nothing selected"
      case Some(x) =>
        if (!moveDone && x.getSide() != " ") {
          savePiecePoint(x, point)
        }
        else if (moveDone) {
          val justIf = {
            if(sourcePiece.toString == "Pawn(w)" || sourcePiece.toString == "Pawn(b)") {
              sourcePiece.movesAllowedP(board, sourcePoint, point, sourcePiece.getPossibleMoves())
            }
            else if(sourcePiece.getSide() == board.getPiece(point).getSide()) {
              sourcePiece.testRochade(board, sourcePoint, point)
            }
            else {
              sourcePiece.movesAllowed(board, sourcePoint, point, sourcePiece.getPossibleMoves())
            }
          }
          if (!justIf) {
            moveDone = false
          }
          println("*********************" + justIf)
          if (justIf) {
            ifEnemy(sourcePoint, point)
          }
        }
        else {
          message = "Empty Field selected"
        }
    }
  }


  def savePiecePoint(piece: Piece, point: Point): Unit = {
    sourcePiece = piece
    sourcePoint = point
    moveDone = true
    message = "Please select destination"
  }

  def ifEnemy(source: Point, destination: Point): Unit = {
    if (board.getPiece(destination).getSide() != "") {
      if (board.getPiece(source).getSide() == "w") {
        player1.deletePiece(listPlayer1, board.getPiece(destination))
        movePiece(source, destination)
      }
      else {
        player2.deletePiece(listPlayer2, board.getPiece(destination))
        movePiece(source, destination)
      }
    }
    else {
      movePiece(source, destination)
    }
  }

  def movePiece(source: Point, destination: Point) : Unit = {
    board.gameBoard += destination -> board.getPiece(source)
    board.gameBoard += source -> EmptyField(" ")
    moveDone = false
    gameWon(destination)
    round += 1
  }

  def gameWon(destination: Point) : Unit = {
    if(board.getPiece(destination).toString == "King(w)") {
      bottomKingDead = true
    }
    else if(board.getPiece(destination).toString == "King(b)"){
      topKingDead = true
    }
  }

  def reset(): Unit = {
    boardInit()
  }

  def exit(): Unit = {
    sys.exit(0)
  }

  def setRound() : Unit = {
    round = 0
  }

  def notifyView(): Unit = {}

}