package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.cache._

/** Application controller, handles authentication */
object Application extends Controller with Security {

  implicit val app: play.api.Application = play.api.Play.current

  lazy val CacheExpiration =
    app.configuration.getInt("cache.expiration").getOrElse(60 /*seconds*/ * 30 /* minutes */ )

  val AuthTokenHeader = "X-XSRF-TOKEN"
  val AuthTokenCookieKey = "XSRF-TOKEN"

  implicit val rds = (
    (__ \ 'email).read[String] and (__ \ 'password).read[String]) tupled

  /** Serves the index page, see views/index.scala.html */
  def index = Action {
    print("Entering index action!")
    Ok(views.html.index())
  }

  /**
   * Returns the JavaScript router that the client can use for "type-safe" routes.
   * @param varName The name of the global variable, defaults to `jsRoutes`
   */
  def jsRoutes(varName: String = "jsRoutes") = Action { implicit request =>
    Ok(
      Routes.javascriptRouter(varName)(
        routes.javascript.Application.login,
        routes.javascript.Application.logout,
        routes.javascript.Users.user,
        routes.javascript.Users.createUser,
        routes.javascript.Users.updateUser,
        routes.javascript.Users.deleteUser, 
        routes.javascript.Users.userForToken         // TODO Add your routes here
        )).as(JAVASCRIPT)
  }

  /**
   * Log-in a user. Pass the credentials as JSON body.
   * @return The token needed for subsequent requests
   */
  def login() = Action(parse.json) { implicit request =>
        
    // TODO Check credentials, log user in, return correct token

    request.body.validate[(String, String)].map {
      case (email, pass) => {
        val id = Users.login(email, pass)
        println("Id from login="+id);

        // TODO IvanA: continue
        val token = java.util.UUID.randomUUID().toString
        println("Token generated="+token);

        Cache.set(token, id, CacheExpiration)

        println("Token set to cache with id="+id.toString);
        
        println("Asking for token from cache immediately:"+Cache.get(token).toString())

        Ok(Json.obj("token" -> token)).withCookies(Cookie(AuthTokenCookieKey, token, None, httpOnly = false))

      }
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatJson(e))
    }

  }

  /** Logs the user out, i.e. invalidated the token. */
  def logout() = Action { implicit request =>
    // TODO Invalidate token, remove cookie
    request.headers.get(AuthTokenHeader) map { token =>
      {
        Cache.remove(token)
        Redirect("/").discardingCookies(DiscardingCookie(name = AuthTokenCookieKey))
      }
    } getOrElse BadRequest(Json.obj("err" -> "No Token"))

    Ok
  }

}
