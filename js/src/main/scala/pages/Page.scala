package baovitt.ipdistress.js.pages

import com.raquo.laminar.api.L.HtmlElement

abstract class Page (title: String, description: String):
    private def copy: Unit = ()
    
    lazy val render: HtmlElement
end Page

implicit def page(page: Page): HtmlElement =
    page.render