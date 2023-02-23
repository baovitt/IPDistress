package baovitt.ipdistress.js.state

import com.raquo.airstream.eventbus.EventBus
import com.raquo.airstream.state.*

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*


object Deployments:
    case class Deployment(
        name: String,
        
    )

    case class Card(name: String, tpe: String, cost: String, comment: String)

    val cards = List(
        Card("Black Lotus", "Artifact", "0", "Power Nine"),
        Card("Ancestral Recall", "Instant", "U", "Power Nine"),
        Card("Time Walk", "Sorcery", "1U", "Power Nine"),
        Card("Timetwister", "Sorcery", "2U", "Power Nine"),
        Card("Mox Sapphire", "Artifact", "0", "Power Nine"),
        Card("Mox Ruby", "Artifact", "0", "Power Nine"),
        Card("Mox Jet", "Artifact", "0", "Power Nine"),
        Card("Mox Pearl", "Artifact", "0", "Power Nine"),
        Card("Mox Emerald", "Artifact", "0", "Power Nine"),
        Card(
        "Urza's tower",
        "Land",
        "",
        "This land is part of what is called the 'Tron', which contains the Urza's Tower, Urza's Mine and Urza's Power Plant."
        )
    )

    val layoutBus: EventBus[FCLLayout] = new EventBus
    val maybeSelectedCardVar = Var(Option.empty[Card])
end Deployments