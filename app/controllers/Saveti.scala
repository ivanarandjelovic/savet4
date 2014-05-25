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
import play.api.libs.functional.syntax._
import play.api.db.DB
import anorm._
import play.api.Play.current
import anorm.SqlParser._
import java.util.Date
import play.api.mvc.Results._
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ read, write }


/**
 * @author aivan
 *
 */
object Saveti extends Controller with Security {

  case class Savet(id: Long, name: String, address: String, createdAt: Date)

  implicit val formats = Serialization.formats(NoTypeHints)

  implicit val rds = ((__ \ 'name).read[String] and (__ \ 'address).read[String]) tupled

  val parseSavet = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("address") ~
      get[Date]("created_at") map {
        case id ~ name ~ address ~ created_at => Savet(id, name, address, created_at)
      }
  }

  def list() = HasToken() { token =>
    userId => implicit request =>
      //List all saveti

      //Stanari.test()
      
      DB.withConnection { implicit c =>

        val saveti = SQL("Select * from Saveti").as(parseSavet *)

        //val jsonArray = Json.toJson(saveti.map( savet => Map[String,String]("id"->savet.id.toString, "name"->savet.name, "address"->savet.address, "craeted_at"-> savet.craetedAt.toString())))

        //val jsonArray = Json.toJson(Seq(Map("12"->2),Map("12"->2), Map("12"->2), Map("12"->2)))

        //Ok(Json.obj(jsonArray.stringify))
        //Ok(jsonArray.toString)
        Ok(write(saveti))
      }

  }

  def create = HasToken(parse.json) { token =>
    userId => implicit request =>
      request.body.validate[(String, String)].map {
        case (name, address) => {

          DB.withConnection { implicit c =>

            val saveti = SQL("insert into Saveti(name, address) values ({name},{address})").on('name -> name, 'address -> address)

            val id = saveti.executeInsert()

            println("inserted savet record id=" + id)

            //val jsonArray = Json.toJson(saveti.map( savet => Map[String,String]("id"->savet.id.toString, "name"->savet.name, "address"->savet.address, "craeted_at"-> savet.craetedAt.toString())))

            //val jsonArray = Json.toJson(Seq(Map("12"->2),Map("12"->2), Map("12"->2), Map("12"->2)))

            //Ok(Json.obj(jsonArray.stringify))
            //Ok(jsonArray.toString)
            Ok(Json.obj("id" -> id))

            //          Ok(Json.obj("token" -> token)).withCookies(Cookie(AuthTokenCookieKey, token, None, httpOnly = false))

          }
        }
      }.recoverTotal {
        e => BadRequest("Detected error:" + JsError.toFlatJson(e))
      }

  }

  /**
   * Returns the current user's ID if a valid token is transmitted.
   * Also sets the cookie (useful in some edge cases).
   * This action can be used by the route service.
   */
  //  def ping() = HasToken() { token => userId => implicit request =>
  //    User.findOneById (userId) map { user =>
  //      Ok(Json.obj("userId" -> userId)).withToken(token -> userId)
  //    } getOrElse NotFound (Json.obj("err" -> "User Not Found"))
  //  }

}