package baovitt.ipdistress.js.pages.launchpage

import baovitt.ipdistress.js.StaticComponent
import baovitt.ipdistress.js.state.PageRecord

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*
import com.raquo.laminar.api.L.*

import com.raquo.laminar.api.L.HtmlElement

class PageSwitch(pages: PageRecord*) extends StaticComponent:

    // private inline def itemFromPageRecord(record: PageRecord) =
    //     ((mod: ProductSwitch.type) => mod.item(_.titleText := record.getText, _.subtitleText := record.getDescription, _.icon := record.getIcon))
    override lazy val render: HtmlElement = ProductSwitch(
        pages.map( record =>
            ProductSwitch.item(_.titleText := record.getText, _.subtitleText := record.getDescription, _.icon := record.getIcon)
        )*
    )

end PageSwitch