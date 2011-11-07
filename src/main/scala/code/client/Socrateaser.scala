package code
package client

import com.google.gwt.core.client.EntryPoint
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.core.client.Scheduler
import com.google.gwt.event.dom.client.KeyPressEvent
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.ui.HasWidgets
import PimpMyGwt._
import com.google.gwt.user.client.ui.TextArea

class Socrateaser extends EntryPoint { 

  def onModuleLoad() {
    val screen = RootPanel.get()
    
    
    val v = new MyView
    val p = new MyPresenter(screen, v)
    p.bind
    screen.add(v.questionPanel)
    screen.add(v.in)
    v.questionPanel.setWidget(new Label("Mikä on ongelma?"))
    
  }
}

class MyPresenter(screen: HasWidgets, v: MyView) {
    val qs = List(
    	"Miksi tämä on ongelma?", 
    	"Tarkentaisitko ongelmaa?", 
    	"Mikä on (tai voisi olla) vastaava yleinen ongelma?",
    	"Mitä estää sinua ratkaisemasta ongelmaa?"
        
    )
    var queue = qs

  def bind = {
	v.in.addKeyPressHandler((e: KeyPressEvent) => {
	  if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
    	handleAnswer(v.in.getValue())
    	v.in.setValue("")
      }
	})
  }
  
  def handleAnswer(answer: String) {        
    answer match {
      case "" => v.questionPanel.setWidget(new Label(getQuestion))
      case a:String => screen.add(new Label(answer)) 
    }
  }
  
  def getQuestion() = {
    def next = {
      val ret = queue.head
      queue = queue.tail
      ret
    }
    next
  }
}

class MyView {
  val questionPanel = new SimplePanel
  questionPanel.addStyleName("questionPanel")
  
  val in = buildAnswerField
	def buildAnswerField() = {
		val answerField = new TextArea
		
		answerField.setCharacterWidth(40)
		answerField.setVisibleLines(3)
		answerField.addStyleName("answerField")
		Scheduler.get.scheduleDeferred(new Scheduler.ScheduledCommand() {
			def execute() {
				answerField.setFocus(true)
			}
		})
		answerField
  }	
  
}