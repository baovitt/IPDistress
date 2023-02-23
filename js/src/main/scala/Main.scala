package baovitt.ipdistress.js

import baovitt.ipdistress.js.components.*
import baovitt.ipdistress.js.pages.*

import org.scalajs.dom
import com.raquo.laminar.api.L.*

@main def main: Unit =
  val containerNode = dom.document.getElementById("c&c-root")
  render(containerNode, div((new NavPage).render))
  // Navbar("IPDistress", "Command & Control Hub")