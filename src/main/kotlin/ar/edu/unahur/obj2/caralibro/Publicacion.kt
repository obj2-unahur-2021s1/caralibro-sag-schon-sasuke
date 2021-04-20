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
  var factorDeCompresion = 0.7

  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()

  fun cambiarFactorDeCompresion(valor : Double){
    factorDeCompresion = valor
  }
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(var calidad: Calidad, val duracion: Int) : Publicacion() {
   override fun espacioQueOcupa(): Int = calidad.espacioQueOcupa(duracion)
}

abstract class Calidad() {
  abstract fun espacioQueOcupa(duracion : Int ): Int
}

class SD() : Calidad(){
  override fun espacioQueOcupa(duracion : Int ) = duracion
}

class HD720p() : Calidad(){
  override fun espacioQueOcupa(duracion : Int ) = duracion * 3
}

class HD1080p() : Calidad(){
  val video720 = HD720p()
  override fun espacioQueOcupa(duracion : Int) = video720.espacioQueOcupa(duracion) * 2
}
