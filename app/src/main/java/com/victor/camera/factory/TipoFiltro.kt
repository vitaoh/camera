package com.victor.camera.factory

enum class TipoFiltro(val descricao: String) {
    SEM_FILTRO("Sem Filtro"),
    PRETO_BRANCO("Preto e Branco"),
    SEPIA("Sépia"),
    INVERTER("Inverter Cores"),
    BLUR("Desfocar");

    // O Spinner utiliza o método toString() para exibir o texto na tela
    override fun toString(): String {
        return descricao
    }
}