/**
 * Classes related to tenants (stanari)
 */
package controllers

import scala.slick.driver.H2Driver.simple._
import play.api.db.DB
import play.api.Play.current
import play.mvc.Controller

case class Stanar(savetId: Long, brojStana: String, redosled: Int, name: String, lastName: String, id: Option[Long] = None)

class Stanari(tag: Tag) extends Table[Stanar](tag, "STANARI") {
  // Auto Increment the id primary key column
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def savetId = column[Long]("SAVET_ID", O.NotNull)
  def brojStana = column[String]("BROJ_STANA")
  def redosled = column[Int]("REDOSLED")
  // The name can't be null
  def name = column[String]("NAME", O.NotNull)
  def lastName = column[String]("LAST_NAME")
  // the * projection (e.g. select * ...) auto-transform the tupled column values to / from a User
  def * = (savetId, brojStana, redosled, name, lastName, id.?) <> (Stanar.tupled, Stanar.unapply)

}

object Stanari extends Controller with Security {
  def test() = {
    val db = Database.forDataSource(DB.getDataSource())

    db.withSession { implicit session: Session =>
      val stanari = TableQuery[Stanari]

      println(stanari.list)
    }
  }
}