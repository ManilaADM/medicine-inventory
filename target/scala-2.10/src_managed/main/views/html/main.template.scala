
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
import org.bson.types.ObjectId
/**/
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/title)),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*8.54*/routes/*8.60*/.Assets.at("stylesheets/main.css"))),format.raw/*8.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*9.59*/routes/*9.65*/.Assets.at("images/favicon.png"))),format.raw/*9.97*/("""">
        <script src=""""),_display_(Seq[Any](/*10.23*/routes/*10.29*/.Assets.at("javascripts/jquery-1.9.0.min.js"))),format.raw/*10.74*/("""" type="text/javascript"></script>
    </head>
    <body>
        <header>
            <a href=""""),_display_(Seq[Any](/*14.23*/routes/*14.29*/.Application.index)),format.raw/*14.47*/("""">"""),_display_(Seq[Any](/*14.50*/title)),format.raw/*14.55*/("""</a>
        </header>
        <section>
            """),_display_(Seq[Any](/*17.14*/content)),format.raw/*17.21*/("""
        </section>
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Feb 25 11:18:54 CST 2014
                    SOURCE: D:/developer/workspace/projects/medicine/app/views/main.scala.html
                    HASH: 43a9bcf1c8b365f5eeaf9aee2ba9048a0f1cf888
                    MATRIX: 809->1|933->31|1021->84|1047->89|1144->151|1158->157|1213->191|1309->252|1323->258|1376->290|1437->315|1452->321|1519->366|1652->463|1667->469|1707->487|1746->490|1773->495|1863->549|1892->556
                    LINES: 27->1|30->1|36->7|36->7|37->8|37->8|37->8|38->9|38->9|38->9|39->10|39->10|39->10|43->14|43->14|43->14|43->14|43->14|46->17|46->17
                    -- GENERATED --
                */
            