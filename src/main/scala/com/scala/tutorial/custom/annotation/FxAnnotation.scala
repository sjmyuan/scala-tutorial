package com.scala.tutorial.custom.annotation

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.reflect.macros.blackbox
import scala.language.experimental.macros

/**
  * @FxAll trait Stack[A,B,C,D,E,F,G,H,I,G,K]
  */
@compileTimeOnly("enable macro paradise to expand macro annotations")
class FxAll extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro FxAnnotation.impl
}

class FxAnnotation(val c: blackbox.Context) {

  import c.universe._

  def abort(msg: String) = c.abort(c.enclosingPosition, msg)

  def impl(annottees: c.Expr[Any]*): c.Expr[Any] = {
    println(annottees)
    abort("badting happens")
    //    val trees = annottees.map(_.tree).toList
    //    trees.headOption match {
    //      case Some(q"$_ trait ${tpname: TypeName}[..$effects]") => c.Expr[Any](q"type $tpname = Fx.fx1[Option]")
    //      case _ => abort("FxAll can only be used with trait")
    //    }
  }
}
