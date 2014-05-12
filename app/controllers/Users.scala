package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.db.DB
import anorm._
import play.api.Play.current
import anorm.SqlParser._
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ read, write }
import play.api.cache._

//import models.User

/** Example controller; see conf/routes for the the mapping to routes */
object Users extends Controller with Security {

  case class User(id: Long, email: String, password: String, firstName: String, lastName: String, description: String, role: String)

  implicit val formats = Serialization.formats(NoTypeHints)

  val parseUser = {
    get[Long]("id") ~
      get[String]("email") ~
      get[String]("firstName") ~
      get[String]("lastName") ~
      get[String]("description") ~
      get[String]("role") map {
        case id ~ email ~ firstName ~ lastName ~ description ~ role => User(id, email, "", firstName, lastName, description, role)
      }
  }

  /**
   * Check if the supplied username and pass are OK, return user ID or nil
   */
  def login(email: String, password: String) : Option[Long]  = {
    DB.withConnection { implicit c =>

      val id: Option[Long] = SQL("Select id from Users where upper(email) = upper({email}) and password = {pass}")
        .on('email -> email, 'pass -> password).as(scalar[Long].singleOpt)
        
        println("Id of the logged in user is:"+id)
        
      if (id == None) {
        throw new Exception();
      } else  {
        println("returning id="+id)
        id
      }
    }
  }

  /** Retrieves the user for the given id as JSON */
  def userForToken(token: String) = Action(parse.empty) { request =>
    
    println("Token is: "+token)
    val maybeId = Cache.get(token)
    println("maybeId is: "+maybeId)
    println("Id is: "+maybeId.getOrElse(throw new RuntimeException("No session for token:"+token)) )
    
    DB.withConnection { implicit c =>
      val users = SQL("Select * from Users where id = {id}").on("id" -> maybeId).as(parseUser *)
      Ok(write(users.head))
    }
  }

  /** Retrieves the user for the given id as JSON */
  def user(id: Long) = Action(parse.empty) { request =>
    // TODO Find user and convert to JSON
    //Ok(Json.obj("firstName" -> "John", "lastName" -> "Smith", "age" -> 42))

    DB.withConnection { implicit c =>
      val users = SQL("Select * from Users where id = {id}").on("id" -> id).as(parseUser *)
      Ok(write(users.head))
    }
  }

  /** Creates a user from the given JSON */
  def createUser() = Action(parse.json) { request =>
    // TODO Implement User creation, typically via request.body.validate[User]
    Ok
  }

  /** Updates the user for the given id from the JSON body */
  def updateUser(id: Long) = Action(parse.json) { request =>
    // TODO Implement User creation, typically via request.body.validate[User]
    Ok
  }

  /** Deletes a user for the given id */
  def deleteUser(id: Long) = Action(parse.empty) { request =>
    // TODO Implement User creation, typically via request.body.validate[User]
    Ok
  }

}
