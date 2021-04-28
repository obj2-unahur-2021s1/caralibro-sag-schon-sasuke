package ar.edu.unahur.obj2.caralibro

abstract class Calidad() {
    abstract fun espacioQueOcupa(duracion : Int ): Int
}

object SD : Calidad(){
    override fun espacioQueOcupa(duracion : Int ) = duracion
}

object HD720p : Calidad(){
    override fun espacioQueOcupa(duracion : Int ) = duracion * 3
}

object HD1080p : Calidad(){
    override fun espacioQueOcupa(duracion : Int) = HD720p.espacioQueOcupa(duracion) * 2
}
