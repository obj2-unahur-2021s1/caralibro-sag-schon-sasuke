package ar.edu.unahur.obj2.caralibro

abstract class Privacidad {
    abstract fun permiteVerA(usuario: Usuario,publicacion: Publicacion): Boolean
}

object Publico : Privacidad(){
    override fun permiteVerA(usuario: Usuario, publicacion: Publicacion) = true
}

object SoloAmigos : Privacidad(){
    override fun permiteVerA(usuario: Usuario, publicacion: Publicacion) =
        publicacion.usuarioCreador.amigos.contains(usuario)
}

object PrivadoConPermitidos : Privacidad(){
    override fun permiteVerA(usuario: Usuario,publicacion: Publicacion) =
        publicacion.usuarioCreador.permitidos.contains(usuario)
}

object PublicoConExcluidos : Privacidad(){
    override fun permiteVerA(usuario: Usuario,publicacion: Publicacion) =
        !publicacion.usuarioCreador.excluidos.contains(usuario)
}