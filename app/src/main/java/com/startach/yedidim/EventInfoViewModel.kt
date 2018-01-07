package com.startach.yedidim

import com.startach.yedidim.Model.Event
import io.reactivex.Observable


interface EventInfoViewModel {

    interface Inputs {
        fun takeEvent(): Observable<Any>
        fun cancelEvent(): Observable<Any>
        fun closeEvent(): Observable<Any>
        fun ignoreEvent(): Observable<Any>
        fun navigate(): Observable<Any>
        fun call(): Observable<Any>
        fun returnHandled(): Observable<Any>
    }



    fun bindViewModel(event: Event, inputs: Inputs)

    fun event(): Observable<Event>

    fun viewState(): Observable<State>
}

sealed class State

class HandlingState : State()
class ProcessingState : State()
class AlreadyTakenState : State()
class ExitState : State()
class ErrorState(val operation: OperationType, val throwable: Throwable) : State() {
    enum class OperationType { Cancel, Take, Close, Ignore }
}