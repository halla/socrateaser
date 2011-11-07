package code.client

import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.event.dom.client.KeyPressHandler
import com.google.gwt.event.dom.client.KeyPressEvent



object PimpMyGwt {
  implicit def clickHandler(f: ClickEvent => Unit): ClickHandler = new ClickHandler {
    def onClick(event: ClickEvent) = f(event)
  }

  implicit def keyPressHandler[T](fn: KeyPressEvent => Unit): KeyPressHandler =
    new KeyPressHandler {
      def onKeyPress(event: KeyPressEvent): Unit = fn(event)
    }
}