package br.com.chicorialabs.astranovos.core

sealed class State<out T: Any> {

    /**
     * O estado de Loading pode ser um objeto, pois não possui atributos.
     */
    object Loading : State<Nothing>()

    /**
     * Success e Error possuem atributos, logo precisam ser classes.
     */
    data class Success<out T: Any>(val result: T) : State<T>()

    data class Error(val error: Throwable) : State<Nothing>()

}