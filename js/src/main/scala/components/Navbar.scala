package baovitt.ipdistress.js.components

import baovitt.ipdistress.js.StaticComponent
// import baovitt.ipdistress.js.state.{PRecord, SearchRecordError}

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

// class Navbar(primaryTitle: String, secondaryTitle: String) extends StaticComponent:
//     private val searchValueBus: EventBus[String] = new EventBus

//     private val results: List[Either[SearchRecordError, SearchRecord]] = List(
//         SearchRecord.fromString("Log Navigator", "Usage and Statistics", IconName.database), 
//         SearchRecord.fromString("Deployments", "Usage and Statistics", IconName.cloud), 
//         SearchRecord.fromString("Statistics", "Usage and Statistics", IconName.`bar-chart`), 
//         SearchRecord.fromString("C&C Hub", "Command & Control", IconName.`my-view`), 
//     )

//     val searchResults =
//         searchValueBus.events
//             .map(input =>
//                 if input.trim.isEmpty then Nil
//                 else results
//                     .collect { case Right(record) => record }
//                     .filter(_.getText.toLowerCase.contains(input.toLowerCase))
//             )
//             .startWith(Nil)
//             .map(
//                 _.map(result =>
//                     Input.suggestion(
//                         _.icon := result.getIcon,
//                         _.additionalTextState := ValueState.Success,
//                         _.description := result.getDescription,
//                         _.text := result.getText
//                     )
//                 )
//             )

//     override lazy val render = ShellBar(
//         _.primaryTitle := primaryTitle, //"IPDistress",
//         _.secondaryTitle := secondaryTitle, // "Command & Control Hub",
//         _.slots.logo := img(src := "/home/brad/Documents/IPDistress/js/src/assets/logo.png"),
//         _.slots.startButton := Button(_.icon := IconName.`nav-back`),
//         _.slots.searchField := Input(
//             _.showSuggestions := true,
//             _.showClearIcon := true,
//             _.placeholder := "Search...",
//             children <-- searchResults,
//             _.events.onInput.mapToValue --> searchValueBus.writer
//         ),
//     )
// end Navbar
