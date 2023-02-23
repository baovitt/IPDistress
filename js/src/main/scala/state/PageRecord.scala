package baovitt.ipdistress.js.state

import be.doeraene.webcomponents.ui5.configkeys.IconName

sealed trait PageRecordError
case object InvalidTextLength extends PageRecordError

final case class PageRecord private (text: String, description: String, icon: IconName):
    private def copy: Unit = ()

    def getText: String = text
    def getDescription: String = description
    def getIcon: IconName = icon
end PageRecord

object PageRecord:
    private def apply(
        text: String, description: String, icon: IconName
    ): Either[PageRecordError, PageRecord] =
        Either.cond(
            text.length > 0,
            new PageRecord(text, description, icon),
            InvalidTextLength
        )

    def fromString(
        text: String, description: String, icon: IconName
    ): Either[PageRecordError, PageRecord] = apply(text, description, icon)
end PageRecord