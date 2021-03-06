package com.example.user.injapanapp.ui.history_activity

import com.example.user.injapanapp.database.TaskObject

class HistoryPresenter(private val interactor: IHistoryInteractor = HistoryInteractor()):
    IHistoryPresenter, IHistoryInteractor.OnHistoryListener {

    private var view: IHistoryView? = null

    override fun onAttachView(view: IHistoryView) {
        this.view = view
    }

    override fun onDetachView() {
        view = null
    }

    override fun onSuccess(list: List<TaskObject>) {
        view?.hideProgress()
        view?.getAllTasks(list)
    }

    override fun onSuccessDelete() {
        view?.hideProgress()
        view?.getList()
    }

    override fun onError(error: String?, code: Int) {
        view?.hideProgress()
    }

    override fun getAllTasks() {
        view?.showProgress()
        interactor.getAllTasks(this)
    }

    override fun deleteTask(taskObject: TaskObject) {
        view?.showProgress()
        interactor.deleteTask(taskObject, this)
    }
}