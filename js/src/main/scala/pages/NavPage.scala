package baovitt.ipdistress.js.pages

import com.raquo.laminar.api.L.HtmlElement

import baovitt.ipdistress.js.pages.launchpage.PageSwitch
import baovitt.ipdistress.js.state.{PageRecord, PageRecordError}

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

class NavPage extends Page("Navigation", "Top Level"):
    val title: String = "Navigation"
    val description: String = "Top Level"

    private val searchValueBus: EventBus[String] = new EventBus
    val toggleCollapseBus: EventBus[Unit] = new EventBus

    private val results: List[Either[PageRecordError, PageRecord]] = List(
        PageRecord.fromString("Log Navigator", "Usage and Statistics", IconName.database), 
        PageRecord.fromString("Deployments", "Usage and Statistics", IconName.cloud), 
        PageRecord.fromString("Statistics", "Usage and Statistics", IconName.`bar-chart`), 
        PageRecord.fromString("C&C Hub", "Command & Control", IconName.`my-view`), 
    )

    lazy val searchResults =
        searchValueBus.events
            .map(input =>
                if input.trim.isEmpty then Nil
                else results
                    .collect { case Right(record) => record }
                    .filter(_.getText.toLowerCase.contains(input.toLowerCase))
            )
            .startWith(Nil)
            .map(
                _.map(result =>
                    Input.suggestion(
                        _.icon := result.getIcon,
                        _.additionalTextState := ValueState.Success,
                        _.description := result.getDescription,
                        _.text := result.getText
                    )
                )
            )

    val collapsedSignal = toggleCollapseBus.events.foldLeft(false)((collapsed, _) => !collapsed)

    override lazy val render: HtmlElement = div( 
        ShellBar(
            _.primaryTitle := title, //"IPDistress",
            _.secondaryTitle := description, // "Command & Control Hub",
            _.showCoPilot := true,
            _.slots.logo := img(src := "/home/brad/Documents/IPDistress/js/src/assets/logo.png"),
            _.slots.startButton := Button(
                _.icon := IconName.menu,
                _.events.onClick.mapTo(()) --> toggleCollapseBus.writer
            ),
            _.slots.searchField := Input(
                _.showSuggestions := true,
                _.showClearIcon := true,
                _.placeholder := "Search...",
                children <-- searchResults,
                _.events.onInput.mapToValue --> searchValueBus.writer
            ),
        ),
        div(display := "flex",
            SideNavigation(
                _.collapsed <-- collapsedSignal,
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
            PageSwitch(results.collect { case Right(record) => record }*).render
        )
    )
end NavPage