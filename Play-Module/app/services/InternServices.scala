package services

import com.google.inject.Inject
import models.Intern
import play.api.libs.json.{JsPath, Writes, JsValue, Json}
import play.api.libs.functional.syntax._
import repositories.InternRepo

/**
  * Created by sahil on 4/10/16.
  */
class InternServices @Inject()(intern:InternRepo) {

  private implicit val internWrites = new Writes[Intern] {

    def writes(intern: Intern): JsValue = Json.obj(
      "name" -> intern.name,
      "email" -> intern.email,
      "mobile" -> intern.mobile,
      "address" -> intern.address,
      "alternateContact" -> intern.alternateContact,
      "id" -> intern.id
    )
  }

  private implicit val internReads = (
    (JsPath \ "name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "mobile").read[String] and
      (JsPath \ "address").read[String] and
      (JsPath \ "alternateContact").read[String] and
      (JsPath \ "id").read[String]
    ) (Intern.apply _)


//  def getAllInterns: JsValue = Json.parse(intern.getAll)

  def getAllInterns: String = intern.getAll

  def deleteIntern(id: String): String = intern.delete(id)

  def getInternById(id: String): JsValue = Json.parse(intern.internById(id))

  def addIntern(internStr: String): String = {


    val internId = intern.insert(internStr)
    val tempIntern:String = intern.internById(internId)


    val tempIntern1: Intern = Json.parse(tempIntern).as[Intern]
    val newIntern: Intern = Intern(tempIntern1.name, tempIntern1.email, tempIntern1.mobile, tempIntern1.address, tempIntern1.alternateContact, internId)
    val aa =Json.toJson(newIntern).toString

    intern.update(aa,internId)
  }

  def editIntern(internStr: String): String = intern.update(internStr,Json.parse(internStr).as[Intern].id.toString)

}
