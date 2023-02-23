package baovitt.ipdistress.js

import com.raquo.laminar.api.L.HtmlElement

trait StaticComponent:
    lazy val render: HtmlElement
end StaticComponent

implicit def staticComponent(component: StaticComponent): HtmlElement =
    component.render