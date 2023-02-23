package baovitt.ipdistress.js.state

import com.raquo.airstream.eventbus.EventBus

import be.doeraene.webcomponents.ui5.* 
import be.doeraene.webcomponents.ui5.configkeys.*

object SearchState:
    val searchValueBus: EventBus[String] = new EventBus

    val results: List[Either[PageRecordError, PageRecord]] = List(
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
end SearchState