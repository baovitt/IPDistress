package baovitt.ipdistress.js.pages

import com.raquo.laminar.api.L.HtmlElement

import baovitt.ipdistress.js.pages.launchpage.PageSwitch
import baovitt.ipdistress.js.components.*
import baovitt.ipdistress.js.state.*
import baovitt.ipdistress.js.state.Deployments.*

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

import scala.scalajs.js.JSON


class DeploymentsPage extends Page("Deployments", "Usage & Statistics"):
    val title: String = "Deployments"
    val description: String = "Usage & Statistics"

    def startColumnListItem(card: Card): HtmlElement = UList.item(
        card.name,
        _.description := card.tpe,
        _.additionalText := s"Cost: ${card.cost}",
        _.iconEnd := true,
        _.icon := IconName.`slim-arrow-right`,
        dataAttr("card-name") := card.name
    )

    def cardFromName(name: String): Option[Card] = cards.find(_.name == name)

    val settingsBus: EventBus[ViewSettingsDialog.ViewSettings] = new EventBus
    val showSettingsDialogBus: EventBus[Unit]                  = new EventBus

    override lazy val render: HtmlElement = Navbar(
        title,
        description,
        true,
        div(
            height := "Calc(90% - 88px)",
            width := "100%",
            TabContainer(
            width := "100%",
                _.tab(
                    _.text := "All",
                    FlexibleColumnLayout(
                        _.layout <-- layoutBus.events.startWith(FCLLayout.OneColumn),
                        _.slots.startColumn := div(
                            paddingTop := "7px",
                            Breadcrumbs(
                                _.Item(
                                    _.href := "https://github.com/sherpal/LaminarSAPUI5Bindings",
                                    "Root"
                                ),
                                _.Item(_.href := "https://github.com/sherpal/LaminarSAPUI5Bindings", "Usage & Statistics"),
                                _.Item("Deployments"),
                                _.events.onItemClick.map(_.detail.item) --> Observer(x => org.scalajs.dom.console.log(x))
                            ),
                            // MultiComboBox(
                            //     _.placeholder := "State",
                            //     width := "100px",
                            //     _.item(_.text := "Configured"),
                            //     _.item(_.text := "Running"),
                            //     _.item(_.text := "Retired")
                            // ),
                            // MultiComboBox(
                            //     _.placeholder := "Provider",
                            //     width := "100px",
                            //     _.item(_.text := "Botnet 1"),
                            //     _.item(_.text := "Botnet 2"),
                            //     _.item(_.text := "Botnet 3")
                            // ),
                            // DateRangePicker(_.formatPattern := "dd/MM/yyyy"),
                            // UList(
                            // cards.filter(_.comment == "Power Nine").map(startColumnListItem),
                            // _.events.onItemClick
                            //     .map(event =>
                            //     for {
                            //         cardName <- event.detail.item.dataset.get("cardName")
                            //         card     <- cardFromName(cardName)
                            //     } yield card
                            //     ) --> maybeSelectedCardVar.writer
                            // )
                            div(
                                Button("View Settings", _.events.onClick.mapTo(()) --> showSettingsDialogBus.writer),
                                ViewSettingsDialog(
                                    _.showFromEvents(showSettingsDialogBus.events),
                                    _.events.onCancel.map(_.detail) --> settingsBus.writer,
                                    _.events.onConfirm.map(_.detail) --> settingsBus.writer,
                                    _.slots.sortItems := List(
                                    SortItem(_.text := "Name", _.selected := true),
                                    SortItem(_.text := "Position"),
                                    SortItem(_.text := "Company"),
                                    SortItem(_.text := "Department")
                                    ),
                                    _.slots.filterItems := List(
                                    FilterItem(
                                        _.text := "Position",
                                        _.slots.values := List("CTO", "CPO", "VP").map(position => FilterItem.option(_.text := position))
                                    ),
                                    FilterItem(
                                        _.text := "Department",
                                        _.slots.values := List("Sales", "Management", "PR").map(position => FilterItem.option(_.text := position))
                                    ),
                                    FilterItem(
                                        _.text := "Location",
                                        _.slots.values := List("Walldorf", "New York", "London").map(position =>
                                        FilterItem.option(_.text := position)
                                        )
                                    ),
                                    FilterItem(
                                        _.text := "Report to",
                                        _.slots.values := List("CTO", "CPO", "VP").map(position => FilterItem.option(_.text := position))
                                    )
                                    )
                                ),
                                pre(child.text <-- settingsBus.events.map(settings => JSON.stringify(settings, space = 2))),
                                pre(child.text <-- settingsBus.events.map(settings => settings.filters.toString))
                            )
                        ),
                        _.slots.midColumn <-- maybeSelectedCardVar.signal.changes.collect { case Some(card) => card }.map { card =>
                            div(
                            div(
                                display := "flex",
                                alignItems := "center",
                                Button(
                                _.icon := IconName.`slim-arrow-left`,
                                _.events.onClick.mapTo(Option.empty[Card]) --> maybeSelectedCardVar.writer,
                                marginRight := "1em",
                                _.design := ButtonDesign.Transparent
                                ),
                                h1(card.name)
                            ),
                            // img(src := MTG.cardImages(card.name))
                            )
                        },
                                    maybeSelectedCardVar.signal.changes.map(maybeCard =>
                            if maybeCard.isDefined then FCLLayout.TwoColumnsMidExpanded else FCLLayout.OneColumn
                        ) --> layoutBus.writer
                    )
                ),
                _.tabSeparator,
                _.tab(
                    _.text := "Tab 2",
                    // _.icon := IconName.`table-column`,
                    _.selected := true,
                    Label("100.1a A two-player game is a game that begins with only two players.")
                ),
                _.tab(
                    _.text := "Tab 3",
                    // _.icon := IconName.`table-column`,
                    Label(              "100.1b A multiplayer game is a game that begins with more than two players. See section 8, " +
                        "“Multiplayer Rules.”"
                    )
                ),
                _.tab(
                    _.text := "Tab 4",
                    // _.icon := IconName.`table-column`,
                    Label(              "100.2. To play, each player needs their own deck of traditional Magic cards, small items to represent" +
                        " any tokens and counters, and some way to clearly track life totals."
                    )
                ),
                _.tab(
                    _.text := "Tab 5",
                    // _.icon := IconName.`table-column`,
                    Label(              "100.2a In constructed play (a way of playing in which each player creates their own deck ahead of " +
                        "time), each deck has a minimum deck size of 60 cards. A constructed deck may contain any number of " +
                        "basic land cards and no more than four of any card with a particular English name other than basic " +
                        "land cards. For the purposes of deck construction, cards with interchangeable names have the same " +
                        "English name (see rule 201.3)."
                    )
                )
            )
        )
        
    ).render
end DeploymentsPage