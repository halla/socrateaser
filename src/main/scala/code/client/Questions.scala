package code
package client
import scala.collection.immutable.HashMap
import scala.collection.mutable.MapBuilder


object questions {
  //val b: MapBuilder[Category, Question, HashMap[Category, Question]] = new MapBuilder(new HashMap[Category, Question])
  val qs = List( 
   Category("Pääongelma") -> Question("Mikä on ongelma?"),
   Category("Asiayhteydet") -> Question("Miksi tämä on ongelma?"), 
   Category("Tarkennukset") -> Question("Tarkentaisitko ongelmaa?"),
   Category("Yleistykset") -> Question("Mikä on (tai voisi olla) vastaava yleinen ongelma?"),
   Category("Esteet") -> Question("Mitä estää sinua ratkaisemasta ongelmaa?"),
   Category("Näkökulmat") -> Question("Mitä luulet ajattelevasi ongelmastasi 10v kuluttua?"),  
   Category("Ratkaisut") -> Question("Mitä voisit tehdä ongelman ratkaisemiseksi?"),
   Category("Päätökset") -> Question("Mitä aiot tehdä ongelman ratkaisemiseksi?")
  )
  qs
  //val cats2questions: Map[Category, Question] = b.result()
}

case class Category(val name: String)
case class Question(val text: String)

object categories {
  val list: List[Category] = List(
    Category("Pääongelma"), 
    Category("Asiayhteydet"),  
    Category("Tarkennukset"),
    Category("Yleistykset"),
    Category("Esteet"),
    Category("Näkökulmat"),
    Category("Ratkaisut"),
    Category("Päätökset")
  )
}
    