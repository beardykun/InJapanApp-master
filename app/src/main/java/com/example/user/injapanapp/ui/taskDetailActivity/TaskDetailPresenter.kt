package com.example.user.injapanapp.ui.taskDetailActivity

import com.example.user.injapanapp.database.TaskObject

class TaskDetailPresenter(private val interactor: ITaskDetailInteractor = TaskDetailInteractor()) :
    ITaskDetailPresenter, ITaskDetailInteractor.OnTaskDetailListener {

    private var view: ITaskDetailView? = null

    override fun onAttachView(view: ITaskDetailView) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    override fun onSuccess(taskObject: TaskObject) {
        view?.hideProgress()
        view?.setTaskData(taskObject)
    }

    override fun onError(error: String?, code: Int) {
        view?.hideProgress()
        view?.showError(error)
    }

    override fun getTaskDataFromDb(taskNumber: String) {
        view?.showProgress()
        interactor.getTaskDataFromDb(taskNumber, this)
    }

    override fun updatePaymentStatus(taskObject: TaskObject) {
        view?.showProgress()
        interactor.updatePaymentStatus(taskObject, this)
    }

    override fun onSuccessUpdatePay() {
        view?.hideProgress()
        view?.loadTaskInfo()
    }

    override fun updateFinishedStatus(taskObject: TaskObject) {
        view?.showProgress()
        interactor.updateFinishedStatus(taskObject, this)
    }

    override fun onSuccessUpdateFinished() {
        view?.hideProgress()
        view?.finishActivity()
    }

    override fun addDescription(taskObject: TaskObject) {
        view?.showProgress()
        interactor.editDescription(taskObject, this)
    }

    override fun updateDoneStatus(taskObject: TaskObject) {
        view?.showProgress()
        interactor.updateDoneStatus(taskObject, this)
    }
}