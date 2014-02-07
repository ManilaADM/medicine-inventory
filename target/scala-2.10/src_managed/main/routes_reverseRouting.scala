// @SOURCE:D:/developer/workspace/projects/medicine/conf/routes
// @HASH:c174fd2b8eec4643efb8c9fd58a7716e6ad57ab3
// @DATE:Tue Feb 04 14:03:13 CST 2014

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F
import se.radley.plugin.salat.Binders._

import Router.queryString


// @LINE:17
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:6
package controllers {

// @LINE:10
// @LINE:9
// @LINE:8
class ReverseEmployeeController {
    

// @LINE:10
def removeEmployee(id:ObjectId): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "employee/" + implicitly[PathBindable[ObjectId]].unbind("id", id))
}
                                                

// @LINE:8
def getEmployee(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "employee")
}
                                                

// @LINE:9
def setEmployee(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "employee")
}
                                                
    
}
                          

// @LINE:17
class ReverseAssets {
    

// @LINE:17
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:14
// @LINE:13
// @LINE:12
class ReverseMedicineController {
    

// @LINE:12
def getMedicine(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "medicine")
}
                                                

// @LINE:14
def removeMedicine(id:ObjectId): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "medicine/" + implicitly[PathBindable[ObjectId]].unbind("id", id))
}
                                                

// @LINE:13
def setMedicine(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "medicine")
}
                                                
    
}
                          

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                
    
}
                          
}
                  


// @LINE:17
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:6
package controllers.javascript {

// @LINE:10
// @LINE:9
// @LINE:8
class ReverseEmployeeController {
    

// @LINE:10
def removeEmployee : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.EmployeeController.removeEmployee",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "employee/" + (""" + implicitly[PathBindable[ObjectId]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        

// @LINE:8
def getEmployee : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.EmployeeController.getEmployee",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "employee"})
      }
   """
)
                        

// @LINE:9
def setEmployee : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.EmployeeController.setEmployee",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "employee"})
      }
   """
)
                        
    
}
              

// @LINE:17
class ReverseAssets {
    

// @LINE:17
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:14
// @LINE:13
// @LINE:12
class ReverseMedicineController {
    

// @LINE:12
def getMedicine : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.MedicineController.getMedicine",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medicine"})
      }
   """
)
                        

// @LINE:14
def removeMedicine : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.MedicineController.removeMedicine",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medicine/" + (""" + implicitly[PathBindable[ObjectId]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        

// @LINE:13
def setMedicine : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.MedicineController.setMedicine",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "medicine"})
      }
   """
)
                        
    
}
              

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:17
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:6
package controllers.ref {


// @LINE:10
// @LINE:9
// @LINE:8
class ReverseEmployeeController {
    

// @LINE:10
def removeEmployee(id:ObjectId): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.EmployeeController.removeEmployee(id), HandlerDef(this, "controllers.EmployeeController", "removeEmployee", Seq(classOf[ObjectId]), "GET", """""", _prefix + """employee/$id<[^/]+>""")
)
                      

// @LINE:8
def getEmployee(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.EmployeeController.getEmployee(), HandlerDef(this, "controllers.EmployeeController", "getEmployee", Seq(), "GET", """""", _prefix + """employee""")
)
                      

// @LINE:9
def setEmployee(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.EmployeeController.setEmployee(), HandlerDef(this, "controllers.EmployeeController", "setEmployee", Seq(), "POST", """""", _prefix + """employee""")
)
                      
    
}
                          

// @LINE:17
class ReverseAssets {
    

// @LINE:17
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:14
// @LINE:13
// @LINE:12
class ReverseMedicineController {
    

// @LINE:12
def getMedicine(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.MedicineController.getMedicine(), HandlerDef(this, "controllers.MedicineController", "getMedicine", Seq(), "GET", """""", _prefix + """medicine""")
)
                      

// @LINE:14
def removeMedicine(id:ObjectId): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.MedicineController.removeMedicine(id), HandlerDef(this, "controllers.MedicineController", "removeMedicine", Seq(classOf[ObjectId]), "GET", """""", _prefix + """medicine/$id<[^/]+>""")
)
                      

// @LINE:13
def setMedicine(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.MedicineController.setMedicine(), HandlerDef(this, "controllers.MedicineController", "setMedicine", Seq(), "POST", """""", _prefix + """medicine""")
)
                      
    
}
                          

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          
}
        
    