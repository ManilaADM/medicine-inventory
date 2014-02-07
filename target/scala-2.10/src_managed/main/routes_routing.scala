// @SOURCE:D:/developer/workspace/projects/medicine/conf/routes
// @HASH:c174fd2b8eec4643efb8c9fd58a7716e6ad57ab3
// @DATE:Tue Feb 04 14:03:13 CST 2014


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F
import se.radley.plugin.salat.Binders._

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:8
private[this] lazy val controllers_EmployeeController_getEmployee1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("employee"))))
        

// @LINE:9
private[this] lazy val controllers_EmployeeController_setEmployee2 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("employee"))))
        

// @LINE:10
private[this] lazy val controllers_EmployeeController_removeEmployee3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("employee/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:12
private[this] lazy val controllers_MedicineController_getMedicine4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("medicine"))))
        

// @LINE:13
private[this] lazy val controllers_MedicineController_setMedicine5 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("medicine"))))
        

// @LINE:14
private[this] lazy val controllers_MedicineController_removeMedicine6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("medicine/"),DynamicPart("id", """[^/]+""",true))))
        

// @LINE:17
private[this] lazy val controllers_Assets_at7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """employee""","""controllers.EmployeeController.getEmployee()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """employee""","""controllers.EmployeeController.setEmployee()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """employee/$id<[^/]+>""","""controllers.EmployeeController.removeEmployee(id:ObjectId)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """medicine""","""controllers.MedicineController.getMedicine()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """medicine""","""controllers.MedicineController.setMedicine()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """medicine/$id<[^/]+>""","""controllers.MedicineController.removeMedicine(id:ObjectId)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:8
case controllers_EmployeeController_getEmployee1(params) => {
   call { 
        invokeHandler(controllers.EmployeeController.getEmployee(), HandlerDef(this, "controllers.EmployeeController", "getEmployee", Nil,"GET", """""", Routes.prefix + """employee"""))
   }
}
        

// @LINE:9
case controllers_EmployeeController_setEmployee2(params) => {
   call { 
        invokeHandler(controllers.EmployeeController.setEmployee(), HandlerDef(this, "controllers.EmployeeController", "setEmployee", Nil,"POST", """""", Routes.prefix + """employee"""))
   }
}
        

// @LINE:10
case controllers_EmployeeController_removeEmployee3(params) => {
   call(params.fromPath[ObjectId]("id", None)) { (id) =>
        invokeHandler(controllers.EmployeeController.removeEmployee(id), HandlerDef(this, "controllers.EmployeeController", "removeEmployee", Seq(classOf[ObjectId]),"GET", """""", Routes.prefix + """employee/$id<[^/]+>"""))
   }
}
        

// @LINE:12
case controllers_MedicineController_getMedicine4(params) => {
   call { 
        invokeHandler(controllers.MedicineController.getMedicine(), HandlerDef(this, "controllers.MedicineController", "getMedicine", Nil,"GET", """""", Routes.prefix + """medicine"""))
   }
}
        

// @LINE:13
case controllers_MedicineController_setMedicine5(params) => {
   call { 
        invokeHandler(controllers.MedicineController.setMedicine(), HandlerDef(this, "controllers.MedicineController", "setMedicine", Nil,"POST", """""", Routes.prefix + """medicine"""))
   }
}
        

// @LINE:14
case controllers_MedicineController_removeMedicine6(params) => {
   call(params.fromPath[ObjectId]("id", None)) { (id) =>
        invokeHandler(controllers.MedicineController.removeMedicine(id), HandlerDef(this, "controllers.MedicineController", "removeMedicine", Seq(classOf[ObjectId]),"GET", """""", Routes.prefix + """medicine/$id<[^/]+>"""))
   }
}
        

// @LINE:17
case controllers_Assets_at7(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     