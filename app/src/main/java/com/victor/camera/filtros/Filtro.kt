package com.victor.camera.filtros

import android.graphics.Bitmap

abstract class Filtro {
    abstract fun aplicar(bitmap: Bitmap): Bitmap
}