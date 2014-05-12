/**
 *
 */
package controllers

import play.mvc.Controller
import play.api._
import play.api.mvc._
import play.api.mvc.SimpleResult
import play.api.mvc.BodyParsers._
import play.api.libs.json._
import play.api.db.DB
import anorm._
import play.api.Play.current
import anorm.SqlParser._
import java.util.Date
import play.api.mvc.Results._
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
 
/**
 * @author aivan
 *
 */
object Saveti extends Controller with Security {

  case class Savet(id: Long, name: String, address: String, createdAt: Date)
  
  implicit val formats = Serialization.formats(NoTypeHints)

  val parseSavet = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("address") ~
      get[Date]("created_at") map {
        case id ~ name ~ address ~ created_at => Savet(id, name, address, created_at)
      }
  }

  def list() = Action(parse.empty) { request =>
    //List all saveti

    DB.withConnection { implicit c =>

      val saveti = SQL("Select * from Saveti").as(parseSavet *)

      //val jsonArray = Json.toJson(saveti.map( savet => Map[String,String]("id"->savet.id.toString, "name"->savet.name, "address"->savet.address, "craeted_at"-> savet.craetedAt.toString())))
      
      //val jsonArray = Json.toJson(Seq(Map("12"->2),Map("12"->2), Map("12"->2), Map("12"->2)))

      //Ok(Json.obj(jsonArray.stringify))
      //Ok(jsonArray.toString)
      Ok(write(saveti))
    }

  }

}