package com.example.app_en_desarollo

object Reservas {
    val listaReservas = mutableListOf<Evento>()

    fun agendarEvento(evento: Evento) {
        listaReservas.add(evento)
    }

    private var ultimoId = 0

    fun generarId(): Int {
        ultimoId++
        return ultimoId
    }
}