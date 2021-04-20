package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun agregarAmigo(usuario: Usuario){
    amigos.add(usuario)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun darMeGusta(publicacion: Publicacion) = publicacion.recibeUnMegusta(this)

  fun esMasAmistosoQue(usuario: Usuario) = this.amigos.size > usuario.amigos.size
}
