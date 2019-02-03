package com.example.user.injapanapp.ui.main_activity

import com.example.user.injapanapp.database.TaskObject

class MainPresenter(private val interactor: IMainInteractor = MainInteractor()) :
    IMainPresenter, IMainInteractor.OnMainListener {

    private var view: IMainView? = null

    override fun onAttachView(view: IMainView) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    override fun onError(error: String, code: Int) {
        view?.hideProgress()
        view?.showError(error)
    }

    override fun getTaskList(sort: String) {
        view?.showProgress()
        interactor.getTaskList(sort, this)
    }

    override fun onSuccess(list: List<TaskObject>) {
        view?.hideProgress()
        view?.setAdapter(list)
    }

    override fun onSuccessDeleted() {
        view?.hideProgress()
        view?.getList()
    }

    override fun getTaskListWithTaskType(stringFromPreferences: String) {
        view?.showProgress()
        interactor.getTaskListWithTaskType(stringFromPreferences, this)
    }

    override fun deleteTask(taskObject: TaskObject) {
        view?.showProgress()
        interactor.deleteTask(taskObject, this)
    }
}