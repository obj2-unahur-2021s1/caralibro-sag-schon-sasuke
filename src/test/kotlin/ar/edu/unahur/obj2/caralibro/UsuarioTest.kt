package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val sD  = SD()
    val p720 = HD720p()
    val p1080 = HD1080p()
    val videoPlaya = Video(sD,10)
    val videoBoda = Video(p720,12)
    val videoTortuga = Video(p1080,100)

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
    }
  }
})
