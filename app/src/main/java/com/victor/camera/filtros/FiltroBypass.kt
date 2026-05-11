package com.victor.camera.filtros

import android.graphics.Bitmap

class FiltroBypass : Filtro() {
    override fun aplicar(bitmap: Bitmap): Bitmap = bitmap
}