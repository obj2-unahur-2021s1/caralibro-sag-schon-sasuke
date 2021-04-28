package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
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

      it("es mejor amigo de"){
        val gonza = Usuario()
        val pepe = Usuario()
        val maria = Usuario()
        val sofia = Usuario()

        gonza.agregarAmigo(pepe)
        gonza.agregarAmigo(maria)
        gonza.agregarAmigo(sofia)
        gonza.mejoresAmigos().shouldContainExactlyInAnyOrder()

        gonza.agregarPermitidos(pepe)
        gonza.agregarPermitidos(maria)
        gonza.mejoresAmigos().shouldContainExactlyInAnyOrder(maria,pepe)

        gonza.agregarPermitidos(sofia)
        gonza.mejoresAmigos().shouldContainExactlyInAnyOrder(sofia,pepe,maria)

        gonza.agregarExcluidos(maria)
        gonza.mejoresAmigos().shouldContainExactlyInAnyOrder(pepe,sofia)
      }

      it("tiene un amigo mas popular"){
        val gonza = Usuario()
        val pepe = Usuario()
        val sofia = Usuario()

        val fotoGonza = Foto(768,1360,Publico)
        val videoGonza = Video(HD1080p,30,Publico)
        gonza.agregarPublicacion(fotoGonza)
        gonza.agregarPublicacion(videoGonza)

        val fotoPepe = Foto(768,1360,Publico)
        val videoPepe = Video(HD720p,41,Publico)
        pepe.agregarPublicacion(fotoPepe)
        pepe.agregarPublicacion(videoPepe)

        val fotoSofia = Foto(768,1360,Publico)
        val videoSofia = Video(SD,56,Publico)
        sofia.agregarPublicacion(fotoSofia)
        sofia.agregarPublicacion(videoSofia)

        gonza.agregarAmigo(pepe)
        gonza.agregarAmigo(sofia)

        gonza.darMeGusta(fotoSofia)
        pepe.darMeGusta(videoSofia)
        pepe.darMeGusta(fotoSofia)
        pepe.darMeGusta(fotoGonza)
        sofia.darMeGusta(fotoGonza)
        sofia.darMeGusta(fotoPepe)
        sofia.darMeGusta(videoPepe)
        gonza.amigoMasPopular().shouldBe(sofia)

        gonza.darMeGusta(videoPepe)
        gonza.darMeGusta(fotoPepe)
        gonza.amigoMasPopular().shouldBe(pepe)
      }

      it("stalkea a otro"){
        val gonza = Usuario()
        val pepe = Usuario()

        val fotoGonza = Foto(768,1360,Publico)
        val videoGonza = Video(HD1080p,30,Publico)
        val textoGonza = Texto("Este es un texto", Publico)
        gonza.agregarPublicacion(fotoGonza)
        gonza.agregarPublicacion(videoGonza)
        gonza.agregarPublicacion(textoGonza)

        pepe.darMeGusta(fotoGonza)
        pepe.darMeGusta(videoGonza)
        pepe.esStalkerDe(gonza).shouldBeFalse()

        pepe.darMeGusta(textoGonza)
        pepe.esStalkerDe(gonza).shouldBeTrue()

        val foto2Gonza = Foto(768,1360,Publico)
        gonza.agregarPublicacion(foto2Gonza)
        pepe.esStalkerDe(gonza).shouldBeFalse()
      }
    }
  }
})