package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.cache._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import play.api.Logger
import play.api.mvc.Results._
import play.api.Play.current

/** Manages the security architecture */
trait Security {

  // TODO Implement your security logic here

  /*
    To make this work seamlessly with Angular, you should read the token from a header called
    X-XSRF-TOKEN, and issue a cookie called XSRF-TOKEN.
    Check out my blog post that explains this in detail:
    http://www.mariussoutier.com/blog/2013/07/14/272/
  */


  val AuthTokenHeader = "X-XSRF-TOKEN"
  val AuthTokenCookieKey = "XSRF-TOKEN"
  val AuthTokenUrlKey = "auth"

  /** Checks that a token is either in the header or in the query string */
  def HasToken[A](p: BodyParser[A] = play.api.mvc.BodyParsers.parse.anyContent)(f: String => Long => Request[A] => Result): Action[A] =
    Action(p) { implicit request =>
      val maybeToken = request.headers.get(AuthTokenHeader).orElse(request.getQueryString(AuthTokenUrlKey))
      
      println("maybeToken="+maybeToken)
      
      println("userId="+Cache.getAs[Long](maybeToken.getOrElse("")))
      
      maybeToken flatMap { token =>
        Cache.getAs[Long](token) map { userid =>
          
          println("UserId="+userid)
          
          f(token)(userid)(request)
        }
      } getOrElse Unauthorized(Json.obj("err" -> "No Token"))
    }

}
