package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz",Publico)
    val fotoEnCuzco = Foto(768, 1024,Publico)
    val sD  = SD
    val p720 = HD720p
    val p1080 = HD1080p
    val videoPlaya = Video(sD,10,Publico)
    val videoBoda = Video(p720,12,Publico)
    val videoTortuga = Video(p1080,100,Publico)

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
          fotoEnCuzco.cambiarFactorDeCompresion(0.9)
          fotoEnCuzco.espacioQueOcupa().shouldBe(707789)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }

      describe("de tipo video") {
        it("ocupa según su calidad") {
          videoPlaya.espacioQueOcupa().shouldBe(10)
          videoBoda.espacioQueOcupa().shouldBe(36)
          videoTortuga.espacioQueOcupa().shouldBe(600)
        }
      }

    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
      }

      it("da me gusta a una publicacion") {
        val pepe = Usuario()
        pepe.darMeGusta(videoPlaya)
        videoPlaya.cantidadDeMeGusta.shouldBe(1)
        val gonza = Usuario()
        gonza.darMeGusta(videoPlaya)
        videoPlaya.cantidadDeMeGusta.shouldBe(2)
        pepe.darMeGusta(videoPlaya)
        gonza.darMeGusta(videoPlaya)
        videoPlaya.cantidadDeMeGusta.shouldBe(2)
      }

      it("es mas amistoso que otro") {
        val pepe = Usuario()
        val gonza = Usuario()
        val fede = Usuario()
        pepe.agregarAmigo(fede)
        pepe.esMasAmistosoQue(gonza).shouldBeTrue()
      }

      it("puede ver una publicacion"){
        val pepe = Usuario()
        val gonza = Usuario()
        val carlos = Usuario()
        val fede = Usuario()
        val maria = Usuario()

        val fotoPlaya = Foto(768, 1024, Publico)
        gonza.agregarPublicacion(fotoPlaya)
        pepe.puedeVerPublicacion(fotoPlaya).shouldBeTrue()
        gonza.puedeVerPublicacion(fotoPlaya).shouldBeTrue()

        val videoOutlast = Video(HD720p, 30, SoloAmigos)
        gonza.agregarPublicacion(videoOutlast)
        gonza.agregarAmigo(pepe)
        pepe.puedeVerPublicacion(videoOutlast).shouldBeTrue()
        maria.puedeVerPublicacion(videoOutlast).shouldBeFalse()
        gonza.puedeVerPublicacion(videoOutlast).shouldBeTrue()

        val fotoComida = Foto(768,1024, PrivadoConPermitidos)
        gonza.agregarPublicacion(fotoComida)
        gonza.agregarPermitidos(pepe)
        gonza.agregarPermitidos(maria)
        pepe.puedeVerPublicacion(fotoComida).shouldBeTrue()
        maria.puedeVerPublicacion(fotoComida).shouldBeTrue()
        carlos.puedeVerPublicacion(fotoComida).shouldBeFalse()
        gonza.puedeVerPublicacion(fotoComida).shouldBeTrue()

        val videoLluvia = Video(HD1080p, 15, PublicoConExcluidos)
        gonza.agregarPublicacion(videoLluvia)
        gonza.agregarExcluidos(fede)
        gonza.agregarExcluidos(maria)
        pepe.puedeVerPublicacion(videoLluvia).shouldBeTrue()
        maria.puedeVerPublicacion(videoLluvia).shouldBeFalse()
        carlos.puedeVerPublicacion(videoLluvia).shouldBeTrue()
        fede.puedeVerPublicacion(videoLluvia).shouldBeFalse()
        gonza.puedeVerPublicacion(videoLluvia).shouldBeTrue()

        videoLluvia.cambiarPrivacidad(Publico)
        pepe.puedeVerPublicacion(videoLluvia).shouldBeTrue()
        maria.puedeVerPublicacion(videoLluvia).shouldBeTrue()
        carlos.puedeVerPublicacion(videoLluvia).shouldBeTrue()
        fede.puedeVerPublicacion(videoLluvia).shouldBeTrue()
        gonza.puedeVerPublicacion(videoLluvia).shouldBeTrue()

      }

      it("es mejor amigo"){
        val gonza = Usuario()
        val pepe = Usuario()
        val maria = Usuario()
        val sofia = Usuario()

        gonza.agregarAmigo(pepe)
        gonza.agregarPermitidos(pepe)
        gonza.esMejorAmigoDe(pepe).shouldBeTrue()

        gonza.agregarAmigo(maria)
        gonza.esMejorAmigoDe(maria).shouldBeFalse()

        gonza.agregarPermitidos(sofia)
        gonza.esMejorAmigoDe(sofia).shouldBeFalse()
        gonza.agregarAmigo(sofia)
        gonza.esMejorAmigoDe(sofia).shouldBeTrue()
        gonza.agregarExcluidos(sofia)
        gonza.esMejorAmigoDe(sofia).shouldBeFalse()
      }
    }
  }
})
