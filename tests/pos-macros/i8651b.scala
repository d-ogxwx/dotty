abstract class Coroutine[+T] {
  def continue: Option[T]
}

object Macros {

 import scala.quoted._

 inline def coroutine[T](inline body: Any): Coroutine[T] = ${ coroutineImpl('{body}) }

 def coroutineImpl[T: Type](expr: Expr[_ <: Any])(implicit qtx: QuoteContext): Expr[Coroutine[T]] = {
   import qtx.tasty.{_, given _}

   '{
     new Coroutine[T] {
       var state: Int = 0

       def continue: Option[T] = ${
         '{
           state = 1 //if this line is commented there are no compile errors anymore!!!
           None
         }
       }

     }
   }

 }
}
