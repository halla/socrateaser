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
import scala.collection.immutable.HashMap
import com.google.gwt.user.client.ui.Grid
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.user.client.ui.VerticalPanel
import scala.collection.mutable.MapBuilder
import com.google.gwt.user.client.ui.DockPanel
import com.google.gwt.user.client.ui.IsWidget
import com.google.gwt.user.client.ui.FlowPanel

class Socrateaser extends EntryPoint { 

  def onModuleLoad() {
    val screen = RootPanel.get()    
    val p = new MyPresenter(screen)         
  }
}

class MyPresenter(screen: HasWidgets) extends AnswerHandler {
  var queue = initQueue
  
  def initQueue = {
   var que = questions.qs
//   que = que.filterNot(_._1 == Category("Pääongelma"))
//   val mainProb = questions.cats2questions.get(Category("Pääongelma")) 
//   mainProb match {
//    case Some(q) => que = (Category("Pääongelma"), q) :: que  
//    case None =>
//   } // whee. just to get the main prob first
   que 
  }
  val v = new MainView   
  screen.add(v)  
  nextQuestion()
   
  def nextQuestion() = {
    def next = {      
      val ret = queue.head
      queue = queue.tail
      if (queue.isEmpty) {
        queue = initQueue
      }
      ret
    }
    val nextQ = next
    v.questionPanel.setWidget(new QuestionPresenter(nextQ._2, nextQ._1, this).view)
  }
  
  def handleAnswer(currentQuestion: Question, category:Category, answer: String) {
    
    answer match {
      case "" => {
        nextQuestion()        
      }
      case a:String => {
        v.slotMap.get(category) match {
          case Some(slot) => slot.add(new Label(answer))
          case None =>
        }        
      }
    }
  }

}

class MainView() extends Composite {
  val panel = new DockPanel
  val questionPanel = new SimplePanel
  questionPanel.addStyleName("questionPanel")
  val answerPanel = new FlowPanel
  answerPanel.addStyleName("answerPanel")
  
  val categories = client.categories.list  
  val catSlots: List[(Category, CatSlot)] = (categories map { c => c -> new CatSlot(c) }).toList   
  val b: MapBuilder[Category, CatSlot, HashMap[Category, CatSlot]] = new MapBuilder(new HashMap[Category, CatSlot]) //TODO wtf
  
  for (cat <- categories) {    
    val slot = new CatSlot(cat)  
    answerPanel.add(slot)    
    b += (cat -> slot)
  }
  
  val slotMap = b.result()
 
  panel.add(questionPanel, DockPanel.NORTH)
  panel.add(answerPanel, DockPanel.CENTER)
  initWidget(panel)
}

class CatSlot(category: Category) extends Composite {
  val panel = new FlowPanel
  panel.addStyleName("catSlot")
  panel.add(new Label(category.name))
  initWidget(panel)
  
  def add(w: IsWidget) = panel.add(w)
  
}

trait AnswerHandler {
  def handleAnswer(question: Question, category: Category, answer: String)
}

class QuestionPresenter(question: Question, category: Category, handler: AnswerHandler) {
  val view = new QuestionView
  view.questionPanel.setWidget(new Label(question.text))
  bind 
   def bind = {
	view.in.addKeyPressHandler((e: KeyPressEvent) => {
	  if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
    	handler.handleAnswer(question, category, view.in.getValue())
    	Scheduler.get.scheduleDeferred(new Scheduler.ScheduledCommand() {
			def execute() {
				view.in.setValue(null)
			}
		})
    	
      }
	})
  }
   
}

class QuestionView extends Composite {
  val panel = new VerticalPanel  
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
  panel.add(questionPanel)
  panel.add(in)
  initWidget(panel)
}