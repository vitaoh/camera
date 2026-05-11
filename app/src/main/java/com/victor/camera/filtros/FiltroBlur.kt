package com.victor.camera.filtros
import android.graphics.Bitmap
import android.graphics.Color

class FiltroBlur : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap {
        val radius = 1
        val width = bitmap.width
        val height = bitmap.height
        val resultado = Bitmap.createBitmap(width, height, bitmap.config!!)
        for (x in radius until width - radius) {
            for (y in radius until height - radius) {
                var somaR = 0; var somaG = 0; var somaB = 0; var count = 0
                for (dx in -radius..radius) {
                    for (dy in -radius..radius) {
                        val pixel = bitmap.getPixel(x + dx, y + dy)
                        somaR += Color.red(pixel); somaG += Color.green(pixel); somaB += Color.blue(pixel)
                        count++
                    }
                }
                resultado.setPixel(x, y, Color.rgb(somaR/count, somaG/count, somaB/count))
            }
        }
        return resultado
    }
}