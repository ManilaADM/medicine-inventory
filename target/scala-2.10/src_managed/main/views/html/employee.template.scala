
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
object employee extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[String,List[models.Employee],Form[Employee],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message : String)(employees : List[models.Employee])(empForm: Form[Employee]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.80*/("""
"""),format.raw/*3.1*/("""
"""),_display_(Seq[Any](/*4.2*/main("Employee")/*4.18*/ {_display_(Seq[Any](format.raw/*4.20*/("""

 	<script src='"""),_display_(Seq[Any](/*6.17*/routes/*6.23*/.Assets.at("javascripts/jIndex.js"))),format.raw/*6.58*/("""')></script>
	<h1>"""),_display_(Seq[Any](/*7.7*/message)),format.raw/*7.14*/("""</h1>
	<div name="container" style="width: 70%;">
		<div  name="employeeList" style="float: right; display: inline;">
			<h2>Employee List</h2>
			<table class="dataContainer" border="1">
				  <tr>
				    <th>Employee Id</th>
				    <th>First Name</th>
				    <th>Last Name</th>
				    <th>Delete</th>
				    <th>Update</th>
				  </tr>
				  """),_display_(Seq[Any](/*19.8*/for(e <- employees) yield /*19.27*/{_display_(Seq[Any](format.raw/*19.28*/("""
				  	<tr>
				  		<td hidden="true" class="objectId">"""),_display_(Seq[Any](/*21.45*/e/*21.46*/.getId)),format.raw/*21.52*/("""</td>
				  		<td class="employeeId">"""),_display_(Seq[Any](/*22.33*/e/*22.34*/.getEmployeeCode)),format.raw/*22.50*/("""</td>
				  		<td class="firstName">"""),_display_(Seq[Any](/*23.32*/e/*23.33*/.getFirstName)),format.raw/*23.46*/("""</td>
				  		<td class="lastName">"""),_display_(Seq[Any](/*24.31*/e/*24.32*/.getLastName)),format.raw/*24.44*/("""</td>
				  		<td><a href=""""),_display_(Seq[Any](/*25.23*/controllers/*25.34*/.routes.EmployeeController.removeEmployee(e.getId))),format.raw/*25.84*/("""">Delete</a></td>
				  		<td><a class="update" href="#">Update</a></td>
				  	</tr>
				  """)))})),format.raw/*28.8*/("""	
			</table>
		</div>
		<div style="float: left; display: inline;" >
			"""),_display_(Seq[Any](/*32.5*/form(routes.EmployeeController.setEmployee(), 'id -> "empForm")/*32.68*/{_display_(Seq[Any](format.raw/*32.69*/("""
	
				<p>
					<h2>Employee Details</h2>
					<!-- Object Id : <input readonly="readonly" id="objectId" name="id"> </br> -->
					"""),_display_(Seq[Any](/*37.7*/inputText(empForm("objectId"), 'readonly -> "readonly"))),format.raw/*37.62*/("""
					"""),_display_(Seq[Any](/*38.7*/inputText(empForm("employeeCode")))),format.raw/*38.41*/("""
					"""),_display_(Seq[Any](/*39.7*/inputText(empForm("lastName")))),format.raw/*39.37*/("""
					"""),_display_(Seq[Any](/*40.7*/inputText(empForm("firstName")))),format.raw/*40.38*/("""
					"""),_display_(Seq[Any](/*41.7*/inputText(empForm("middleName")))),format.raw/*41.39*/("""
					"""),_display_(Seq[Any](/*42.7*/inputText(empForm("emps[0].employeeCode")))),format.raw/*42.49*/("""
					"""),_display_(Seq[Any](/*43.7*/inputText(empForm("emps[0].firstName")))),format.raw/*43.46*/("""
					<!-- Employee Id : <input id="employeeId" name="employeeId"> </br>
					Last Name : <input id="lastName" name="lastName"> </br>
					First Name : <input id="firstName" name="firstName"> </br>
					Middle Name : <input id="middleName" name="middleName"> </br>		 -->			
					<input type="submit" value="Submit">
					<input type="reset">
				</p>
			""")))})),format.raw/*51.5*/("""
		</div>
	</div>

""")))})))}
    }
    
    def render(message:String,employees:List[models.Employee],empForm:Form[Employee]): play.api.templates.HtmlFormat.Appendable = apply(message)(employees)(empForm)
    
    def f:((String) => (List[models.Employee]) => (Form[Employee]) => play.api.templates.HtmlFormat.Appendable) = (message) => (employees) => (empForm) => apply(message)(employees)(empForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Feb 25 11:18:54 CST 2014
                    SOURCE: D:/developer/workspace/projects/medicine/app/views/employee.scala.html
                    HASH: b30213c83a392cd90a68551981d173b315fbd0b2
                    MATRIX: 845->1|1033->79|1060->97|1096->99|1120->115|1159->117|1212->135|1226->141|1282->176|1335->195|1363->202|1748->552|1783->571|1822->572|1915->629|1925->630|1953->636|2027->674|2037->675|2075->691|2148->728|2158->729|2193->742|2265->778|2275->779|2309->791|2373->819|2393->830|2465->880|2589->973|2698->1047|2770->1110|2809->1111|2976->1243|3053->1298|3095->1305|3151->1339|3193->1346|3245->1376|3287->1383|3340->1414|3382->1421|3436->1453|3478->1460|3542->1502|3584->1509|3645->1548|4031->1903
                    LINES: 27->1|31->1|32->3|33->4|33->4|33->4|35->6|35->6|35->6|36->7|36->7|48->19|48->19|48->19|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|53->24|53->24|53->24|54->25|54->25|54->25|57->28|61->32|61->32|61->32|66->37|66->37|67->38|67->38|68->39|68->39|69->40|69->40|70->41|70->41|71->42|71->42|72->43|72->43|80->51
                    -- GENERATED --
                */
            