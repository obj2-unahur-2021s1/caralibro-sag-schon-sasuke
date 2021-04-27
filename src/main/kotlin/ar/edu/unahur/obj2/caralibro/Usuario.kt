package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()
  val permitidos = mutableListOf<Usuario>()
  val excluidos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
    publicacion.usuarioCreador = this
  }

  fun agregarAmigo(usuario: Usuario){
    amigos.add(usuario)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun darMeGusta(publicacion: Publicacion) = publicacion.recibeUnMegusta(this)

  fun esMasAmistosoQue(usuario: Usuario) = this.amigos.size > usuario.amigos.size

//  fun puedeVerPublicacion(publicacion: Publicacion) = publicacion.puedeSerVistaPor(this)

  fun puedeVerPublicacion(publicacion: Publicacion): Boolean {
    if (publicacion.usuarioCreador == this){
      return true
    }
    return publicacion.puedeSerVistaPor(this)
  }

  fun agregarPermitidos(usuario: Usuario) = permitidos.add(usuario)

  fun agregarExcluidos(usuario: Usuario) = excluidos.add(usuario)

  fun esMejorAmigoDe(usuario: Usuario) = amigos.contains(usuario) and permitidos.contains(usuario) and !excluidos.contains(usuario)
}