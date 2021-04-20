package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  var cantidadDeMeGusta = 0
  val personasQueDieronMeGusta = mutableListOf<Usuario>()

  abstract fun espacioQueOcupa(): Int

  fun recibeUnMegusta(usuario: Usuario) {
    if (!personasQueDieronMeGusta.contains(usuario)){
      cantidadDeMeGusta += 1
      personasQueDieronMeGusta.add(usuario)
    }
  }
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val calidad: String, val duracion: Int) : Publicacion() {
  private val sd = duracion
  private val hd720p = duracion * 3
  private val hd1080p = hd720p * 2
  override fun espacioQueOcupa(): Int {
    return when (calidad) {
      "SD" -> sd
      "720p" -> hd720p
      "1080p" -> hd1080p
      else -> 0
    }
  }
}