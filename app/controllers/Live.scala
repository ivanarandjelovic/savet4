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
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.iteratee.Concurrent

/**
 * @author aivan
 *
 */
object Live extends Controller with Security {

  var count: Integer = 0;

  def get() = WebSocket.using[String] { implicit request =>
    
    val (out, channel) = Concurrent.broadcast[String]
    
    val in = Iteratee.foreach[String] {
      msg =>
        println("Got message: "+msg)
        count = count + 1
        channel push ("RESPONSE: " + count)
        if(count % 5 == 0) {
          // after 5 messages break the connection
          //out >>> Enumerator.eof
          channel.eofAndEnd
          //channel push Enumerator.eof
          println("Ended channel after 5")
        }
    }
    (in, out)
  }

  
   def getSecured() = withAuthWS{ implicit userId =>
    
    val (out, channel) = Concurrent.broadcast[String]
    
    val in = Iteratee.foreach[String] {
      msg =>
        println("Got message: "+msg)
        count = count + 1
        channel push ("RESPONSE: " + count)
        if(count % 5 == 0) {
          // after 5 messages break the connection
          //out >>> Enumerator.eof
          channel.eofAndEnd
          //channel push Enumerator.eof
          println("Ended channel after 5")
        }
    }
    (in, out)

  }
   
}