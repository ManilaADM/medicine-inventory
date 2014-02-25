
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
object medicine extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,List[models.Medicine],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message : String)(medicine : List[models.Medicine]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.54*/("""

"""),_display_(Seq[Any](/*3.2*/main("Medicine")/*3.18*/ {_display_(Seq[Any](format.raw/*3.20*/("""

 	<script src='"""),_display_(Seq[Any](/*5.17*/routes/*5.23*/.Assets.at("javascripts/jIndex.js"))),format.raw/*5.58*/("""'></script>
	<h1>"""),_display_(Seq[Any](/*6.7*/message)),format.raw/*6.14*/("""</h1>
	<div class="container" style="width: 85%;">
		<div  class="medicineList" style="float: right; display: inline;">
			<h2>Medicine List</h2>
			<table class="dataContainer" border="1">
				  <tr>
				    <th>Medicine Name</th>
				    <th>Stock Quantity</th>
				    <th>Allowed Per Employee</th>
				    <th>Alert Quantity</th>
				    <th>Critical Alert Quantity</th>
				    <th>Delete</th>
				    <th>Update</th>
				  </tr>
				  """),_display_(Seq[Any](/*20.8*/for(m <- medicine) yield /*20.26*/{_display_(Seq[Any](format.raw/*20.27*/("""
				  	<tr>
				  		<td hidden="true" class="objectId">"""),_display_(Seq[Any](/*22.45*/m/*22.46*/.getId)),format.raw/*22.52*/("""</td>
				  		<td class="name">"""),_display_(Seq[Any](/*23.27*/m/*23.28*/.getBrandName)),format.raw/*23.41*/("""</td>
				  		<td class="stockQunatity">"""),_display_(Seq[Any](/*24.36*/m/*24.37*/.getGenericName)),format.raw/*24.52*/("""</td>
				  		<td class="allowedPerEmployee">"""),_display_(Seq[Any](/*25.41*/m/*25.42*/.getDescription)),format.raw/*25.57*/("""</td>
				  		<td class="alertQuantity">"""),_display_(Seq[Any](/*26.36*/m/*26.37*/.getCount)),format.raw/*26.46*/("""</td>
				  		<td class="criticalAlertQuantity">"""),_display_(Seq[Any](/*27.44*/m/*27.45*/.getNotificationAlertCount)),format.raw/*27.71*/("""</td>
				  		<td><a href=""""),_display_(Seq[Any](/*28.23*/controllers/*28.34*/.routes.MedicineController.removeMedicine(m.getId))),format.raw/*28.84*/("""">Delete</a></td>
				  		<td><a class="update" href="#">Update</a></td>
				  	</tr>
				  """)))})),format.raw/*31.8*/("""	
			</table>
		</div>
		<div style="float: left; display: inline;">
			<form action=""""),_display_(Seq[Any](/*35.19*/controllers/*35.30*/.routes.MedicineController.setMedicine())),format.raw/*35.70*/("""" method="post">
				<p>
					<h2>Medicine Details</h2>
					Object Id : <input readonly="readonly" id="objectId" name="id"> </br>
					Medicine Name : <input id="name" name="name"> </br>
					Stock Quantity : <input id="stockQunatity" name="stockQunatity"> </br>
					Allowed Per Employee : <input id="allowedPerEmployee" name="allowedPerEmployee"> </br>
					Alert Quantity : <input id="alertQuantity" name="alertQuantity"> </br>					
					Critical Alert Quantity : <input id="criticalAlertQuantity" name="criticalAlertQuantity"> </br>					
					<input type="submit" value="Submit">
					<input type="reset">
				</p>
			</form>
		</div>
	</div>

""")))})))}
    }
    
    def render(message:String,medicine:List[models.Medicine]): play.api.templates.HtmlFormat.Appendable = apply(message)(medicine)
    
    def f:((String) => (List[models.Medicine]) => play.api.templates.HtmlFormat.Appendable) = (message) => (medicine) => apply(message)(medicine)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Feb 25 11:33:23 CST 2014
                    SOURCE: D:/developer/workspace/projects/medicine/app/views/medicine.scala.html
                    HASH: 7c66650fdcb8ea4fd8323b7f3467121802875714
                    MATRIX: 830->1|976->53|1015->58|1039->74|1078->76|1133->96|1147->102|1203->137|1256->156|1284->163|1775->619|1809->637|1848->638|1943->697|1953->698|1981->704|2050->737|2060->738|2095->751|2173->793|2183->794|2220->809|2303->856|2313->857|2350->872|2428->914|2438->915|2469->924|2555->974|2565->975|2613->1001|2678->1030|2698->1041|2770->1091|2897->1187|3024->1278|3044->1289|3106->1329
                    LINES: 27->1|30->1|32->3|32->3|32->3|34->5|34->5|34->5|35->6|35->6|49->20|49->20|49->20|51->22|51->22|51->22|52->23|52->23|52->23|53->24|53->24|53->24|54->25|54->25|54->25|55->26|55->26|55->26|56->27|56->27|56->27|57->28|57->28|57->28|60->31|64->35|64->35|64->35
                    -- GENERATED --
                */
            