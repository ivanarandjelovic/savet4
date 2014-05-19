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
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import play.api.mvc.RequestHeader

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

      println("maybeToken=" + maybeToken)

      println("userId=" + Cache.getAs[Long](maybeToken.getOrElse("")))

      maybeToken flatMap { token =>
        Cache.getAs[Long](token) map { userid =>

          println("UserId=" + userid)

          f(token)(userid)(request)
        }
      } getOrElse Unauthorized(Json.obj("err" -> "No Token"))
    }

  /**
   * This function provide a basic authentication for
   * WebSocket, lekely withAuth function try to retrieve the
   * the username form the session, and call f() funcion if find it,
   * or create an error Future[(Iteratee[JsValue, Unit], Enumerator[JsValue])])
   * if username is none
   */
  def withAuthWS(f: => Int => (Iteratee[String, Unit], Enumerator[String])): WebSocket[String] = {

    // this function create an error Future[(Iteratee[JsValue, Unit], Enumerator[JsValue])])
    // the itaratee ignore the input and do nothing,
    // and the enumerator just send a 'not authorized message'
    // and close the socket, sending Enumerator.eof
    def errorFuture = {
      // Just consume and ignore the input
      val in = Iteratee.ignore[String]

      // Send a single 'Hello!' message and close
      val out = Enumerator[String]("Not auth!") >>> Enumerator.eof

      (in, out)
    }

    WebSocket.using[String] {
      request =>
        var maybeToken = request.headers.get(AuthTokenHeader).orElse(request.getQueryString(AuthTokenUrlKey))

        println("WS: maybeToken=" + maybeToken)

        if (!maybeToken.isDefined) {
          //Try to get it from the cookie:
          maybeToken = request.cookies.get(AuthTokenCookieKey).flatMap { cookie =>
            Option[String](cookie.value)
          }
        }

        println("WS: userId=" + Cache.getAs[Long](maybeToken.getOrElse("")))

        maybeToken flatMap { token =>
          Cache.getAs[Long](token) map { userid =>

            println("WS: UserId=" + userid)

            f(userid.toInt)
          }
        } getOrElse errorFuture

    }
  }

}
