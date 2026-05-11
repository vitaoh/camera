package com.victor.camera.factory

import com.victor.camera.filtros.Filtro
import com.victor.camera.filtros.FiltroBlur
import com.victor.camera.filtros.FiltroBypass
import com.victor.camera.filtros.FiltroInverso
import com.victor.camera.filtros.FiltroPretoBranco
import com.victor.camera.filtros.FiltroSepia

object FiltroFactory {
    fun criar(tipo: TipoFiltro): Filtro {
        return when (tipo) {
            TipoFiltro.SEM_FILTRO -> FiltroBypass()
            TipoFiltro.PRETO_BRANCO -> FiltroPretoBranco()
            TipoFiltro.SEPIA -> FiltroSepia()
            TipoFiltro.INVERTER -> FiltroInverso()
            TipoFiltro.BLUR -> FiltroBlur()
        }
    }
}