package com.example.mvvm_architecture.data

enum class States {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val state : States, val msg: String) {

    // quand on veut créer quelque chose de static
    companion object {

        val LOADED : NetworkState
        val LOADING : NetworkState
        val ERROR : NetworkState
        val ENDOFLIST : NetworkState

        // initialisation
        init {
            LOADED = NetworkState(States.SUCCESS,  "Success")
            LOADING = NetworkState(States.RUNNING,  "Running")
            ERROR = NetworkState(States.FAILED,  "Something went wrong")
            ENDOFLIST = NetworkState(States.FAILED, "You have reached the end")
        }

    }
}