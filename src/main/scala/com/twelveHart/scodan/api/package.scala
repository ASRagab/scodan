package com.twelveHart.scodan

package object api {

  def makeParams(query: String, facets: Option[String] = None): Map[String, String] =
    Map("query" -> query) ++ facets.map(f => "facets" -> f)
}
