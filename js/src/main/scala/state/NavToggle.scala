package baovitt.ipdistress.js.state

import com.raquo.airstream.eventbus.EventBus

object NavToggle:
    val toggleCollapseBus: EventBus[Unit] = new EventBus
    val collapsedSignal = toggleCollapseBus.events.foldLeft(false)((collapsed, _) => !collapsed)
end NavToggle