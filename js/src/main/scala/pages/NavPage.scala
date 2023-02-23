package baovitt.ipdistress.js.pages

import com.raquo.laminar.api.L.HtmlElement

import baovitt.ipdistress.js.pages.launchpage.PageSwitch
import baovitt.ipdistress.js.components.*
import baovitt.ipdistress.js.state.*

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

class NavPage extends Page("Navigation", "Top Level"):
    val title: String = "Navigation"
    val description: String = "Top Level"


    override lazy val render: HtmlElement = Navbar(
        title,
        description,
        true,
        PageSwitch(SearchState.results.collect { case Right(record) => record }*).render
    ).render
end NavPage