/**
 * Classes related to saveti2
 */
package controllers

import scala.slick.driver.H2Driver.simple._
import java.sql.Timestamp

case class Savet2(name: String, address: String, createdAt: Timestamp, id: Option[Long] = None)

class Saveti2(tag: Tag) extends Table[Savet2](tag, "SAVETI") {
  // Auto Increment the id primary key column
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME", O.NotNull)
  def address = column[String]("ADDRESS", O.NotNull)
  def createdAt = column[Timestamp]("CREATED_AT", O.NotNull)
  // the * projection (e.g. select * ...) auto-transform the tupled column values to / from a User
  def * = (name, address, createdAt, id.?) <> (Savet2.tupled, Savet2.unapply)
}


