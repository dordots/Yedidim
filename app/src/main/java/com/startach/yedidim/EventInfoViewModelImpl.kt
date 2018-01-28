package com.startach.yedidim

import com.startach.yedidim.EventInfoViewModel.Inputs
import com.startach.yedidim.Model.Event
import com.startach.yedidim.entities.notification.EventNotificationEntity
import com.startach.yedidim.network.EventApi
import com.startach.yedidim.repository.EventsRepository
import io.reactivex.Observable

class EventInfoViewModelImpl(private val eventApi: EventApi, private val eventNotificationEntity: EventNotificationEntity,
                             private val navigator: Navigator) : EventInfoViewModel {
    private lateinit var inputs: EventInfoViewModel.Inputs
    lateinit var event: Event
    lateinit var eventsRepository: EventsRepository
    private var myOwnEvent = false

    private val stateObservables: MutableList<Observable<State>> = mutableListOf()

    override fun bindViewModel(event: Event, inputs: Inputs, eventsRepository: EventsRepository) {
        this.inputs = inputs
        this.event = event
        this.eventsRepository = eventsRepository

        stateObservables += inputs.takeEvent()
                .flatMap {
                    eventApi.takeEvent(event.key.orEmpty())
                            .toObservable()
                            .map {
                                when (it) {
                                    true -> HandlingState().also {
                                        myOwnEvent = true
                                        eventsRepository.storeOpenEvent(event.copy(status = "assigned"))
                                    }
                                    false -> AlreadyTakenState()
                                }
                            }
                            .startWith(ProcessingState())
                }
                .onErrorReturn { ErrorState(ErrorState.OperationType.Take, it) }
        stateObservables += inputs.cancelEvent()
                .flatMap<State> {
                    eventsRepository.closeOpenEvent(event,"sent")
                    eventApi.cancelEvent(event.key.orEmpty())
                            .andThen(Observable.just<State>(ExitState()))
                            .startWith(ProcessingState())
                }
                .onErrorReturn { ErrorState(ErrorState.OperationType.Cancel, it) }

        stateObservables += inputs.ignoreEvent()
                .flatMap<State> {
                    eventNotificationEntity.dismissNotification(event)
                    Observable.just(ExitState())
                }
                .onErrorReturn { ErrorState(ErrorState.OperationType.Ignore, it) }
        stateObservables += inputs.closeEvent()
                .flatMap<State> {
                    eventsRepository.closeOpenEvent(event,"completed")
                    eventApi.closeEvent(event.key.orEmpty())
                            .andThen(Observable.fromCallable<State> {
                                eventNotificationEntity.dismissNotification(event)
                                ExitState()
                            })
                            .startWith(ProcessingState())
                }
                .onErrorReturn { ErrorState(ErrorState.OperationType.Close, it) }
        stateObservables += inputs.navigate()
                .map<State> {
                    navigator.openFloatingEvent(event,myOwnEvent)
                    ExitState()
                }
       stateObservables += inputs.call()
                .map<State> {
                    navigator.openCaller(event)
                    HandlingState()
                }
       stateObservables += inputs.returnHandled()
                .map<State> {
                    HandlingState()
                }
    }

    override fun event(): Observable<Event> {
        return Observable.just(event)
    }

    override fun viewState(): Observable<State> {
        return Observable.merge(stateObservables)
    }
}
