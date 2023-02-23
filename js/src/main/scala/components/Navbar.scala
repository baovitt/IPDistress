package baovitt.ipdistress.js.components

import baovitt.ipdistress.js.StaticComponent
import baovitt.ipdistress.js.state.*

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

class Navbar(primaryTitle: String, secondaryTitle: String, collapsed: Boolean, content: HtmlElement) extends StaticComponent:
    override lazy val render = div( 
        ShellBar(
            _.primaryTitle := primaryTitle, //"IPDistress",
            _.secondaryTitle := secondaryTitle, // "Command & Control Hub",
            _.showCoPilot := true,
            _.slots.logo := img(src := "/home/brad/Documents/IPDistress/js/src/assets/logo.png"),
            _.slots.startButton := Button(
                _.icon := IconName.menu,
                _.events.onClick.mapTo(()) --> NavToggle.toggleCollapseBus.writer
            ),
            _.slots.searchField := Input(
                _.showSuggestions := true,
                _.showClearIcon := true,
                _.placeholder := "Search...",
                children <-- SearchState.searchResults,
                _.events.onInput.mapToValue --> SearchState.searchValueBus.writer
            ),
        ),
        div(display := "flex",
            SideNavigation(
                _.collapsed <-- NavToggle.collapsedSignal,
                _.item(_.text := "Home", _.icon := IconName.home),
                _.item(
                    _.text := "Deployments",
                    _.expanded := false,
                    _.icon := IconName.cloud,
                    _.subItem(_.text := "From My Team"),
                    _.subItem(_.text := "From Other Team")
                ),
                _.item(
                    _.text := "Calendar",
                    _.icon := IconName.calendar,
                    _.subItem(_.text := "Local"),
                    _.subItem(_.text := "Others")
                ),
                _.slots.fixedItems := SideNavigation.item(_.text := "Useful Links", _.icon := IconName.`chain-link`),
                _.slots.fixedItems := SideNavigation.item(_.text := "History", _.icon := IconName.history),
            ), 
            content
        )
    )
end Navbar
