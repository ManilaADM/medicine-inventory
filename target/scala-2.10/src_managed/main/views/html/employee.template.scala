
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
object employee extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,List[models.Employee],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message : String)(employees : List[models.Employee]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.55*/("""

"""),_display_(Seq[Any](/*3.2*/main("Employee")/*3.18*/ {_display_(Seq[Any](format.raw/*3.20*/("""

 	<script src='"""),_display_(Seq[Any](/*5.17*/routes/*5.23*/.Assets.at("javascripts/jIndex.js"))),format.raw/*5.58*/("""')></script>
	<h1>"""),_display_(Seq[Any](/*6.7*/message)),format.raw/*6.14*/("""</h1>
	<div name="container" style="width: 55%;">
		<div  name="employeeList" style="float: right; display: inline;">
			<h2>Employee List</h2>
			<table class="dataContainer" border="1">
				  <tr>
				    <th>Employee Id</th>
				    <th>First Name</th>
				    <th>Middle Name</th>
				    <th>Last Name</th>
				    <th>Delete</th>
				    <th>Update</th>
				  </tr>
				  """),_display_(Seq[Any](/*19.8*/for(e <- employees) yield /*19.27*/{_display_(Seq[Any](format.raw/*19.28*/("""
				  	<tr>
				  		<td hidden="true" class="objectId">"""),_display_(Seq[Any](/*21.45*/e/*21.46*/.getId)),format.raw/*21.52*/("""</td>
				  		<td class="employeeId">"""),_display_(Seq[Any](/*22.33*/e/*22.34*/.getEmployeeId)),format.raw/*22.48*/("""</td>
				  		<td class="firstName">"""),_display_(Seq[Any](/*23.32*/e/*23.33*/.getFirstName)),format.raw/*23.46*/("""</td>
				  		<td class="middleName">"""),_display_(Seq[Any](/*24.33*/e/*24.34*/.getMiddleName)),format.raw/*24.48*/("""</td>
				  		<td class="lastName">"""),_display_(Seq[Any](/*25.31*/e/*25.32*/.getLastName)),format.raw/*25.44*/("""</td>
				  		<td><a href=""""),_display_(Seq[Any](/*26.23*/controllers/*26.34*/.routes.EmployeeController.removeEmployee(e.getId))),format.raw/*26.84*/("""">Delete</a></td>
				  		<td><a class="update" href="#">Update</a></td>
				  	</tr>
				  """)))})),format.raw/*29.8*/("""	
			</table>
		</div>
		<div style="float: left; display: inline;">
			<form action=""""),_display_(Seq[Any](/*33.19*/controllers/*33.30*/.routes.EmployeeController.setEmployee())),format.raw/*33.70*/("""" method="post">
				<p>
					<h2>Employee Details</h2>
					Object Id : <input readonly="readonly" id="objectId" name="id"> </br>
					Employee Id : <input id="employeeId" name="employeeId"> </br>
					Last Name : <input id="lastName" name="lastName"> </br>
					First Name : <input id="firstName" name="firstName"> </br>
					Middle Name : <input id="middleName" name="middleName"> </br>					
					<input type="submit" value="Submit">
					<input type="reset">
				</p>
			</form>
		</div>
	</div>

""")))})))}
    }
    
    def render(message:String,employees:List[models.Employee]): play.api.templates.HtmlFormat.Appendable = apply(message)(employees)
    
    def f:((String) => (List[models.Employee]) => play.api.templates.HtmlFormat.Appendable) = (message) => (employees) => apply(message)(employees)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Feb 04 15:31:25 CST 2014
                    SOURCE: D:/developer/workspace/projects/medicine/app/views/employee.scala.html
                    HASH: 40bb2b785af4e34d8cfe283642c6c9af6b0f7392
                    MATRIX: 830->1|977->54|1016->59|1040->75|1079->77|1134->97|1148->103|1204->138|1258->158|1286->165|1713->557|1748->576|1787->577|1882->636|1892->637|1920->643|1995->682|2005->683|2041->697|2115->735|2125->736|2160->749|2235->788|2245->789|2281->803|2354->840|2364->841|2398->853|2463->882|2483->893|2555->943|2682->1039|2809->1130|2829->1141|2891->1181
                    LINES: 27->1|30->1|32->3|32->3|32->3|34->5|34->5|34->5|35->6|35->6|48->19|48->19|48->19|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|53->24|53->24|53->24|54->25|54->25|54->25|55->26|55->26|55->26|58->29|62->33|62->33|62->33
                    -- GENERATED --
                */
            