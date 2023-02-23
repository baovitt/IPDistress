package baovitt.ipdistress.js.pages

import com.raquo.laminar.api.L.HtmlElement

import baovitt.ipdistress.js.pages.launchpage.PageSwitch
import baovitt.ipdistress.js.state.{PageRecord, PageRecordError}

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

class NotFoundPage extends Page("Page Not Found", "Error"):
    val title: String = "Navigation"
    val description: String = "Top Level"

    override lazy val render: HtmlElement = p( 
        margin := "20px",
        fontSize := "30px",
        "Error 404: Page not found"
    )
end NotFoundPage