package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import repositories.InternRepo
import services.InternServices

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(service:InternServices) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def internJsonList = Action {

    implicit request =>
      Ok(service.getAllInterns).withHeaders("Access-Control-Allow-Origin" -> "*")
  }

  def getIntern(id:String) =Action {

    implicit request =>
      Ok(service.getInternById(id)).withHeaders("Access-Control-Allow-Origin" -> "*")
  }

  def deleteIntern(id:String) =Action{

    implicit request =>
          Ok(service.deleteIntern(id)).withHeaders("Access-Control-Allow-Origin" -> "*")
  }

  def addIntern(intern:String)=Action{

    implicit request =>
      Ok(service.addIntern(intern)).withHeaders("Access-Control-Allow-Origin" -> "*")
  }

  def editIntern(intern:String)=Action{

    implicit request =>
      Ok(service.editIntern(intern)).withHeaders("Access-Control-Allow-Origin" -> "*")
  }
}
