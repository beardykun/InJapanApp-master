package com.example.user.injapanapp.ui.taskDetailActivity

import android.support.design.widget.FloatingActionButton
import android.widget.EditText
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

    override fun onSuccessUpdatePay() {
        view?.hideProgress()
        view?.loadTaskInfo()
    }

    override fun onSuccessUpdateFinished() {
        view?.hideProgress()
        view?.finishActivity()
    }

    override fun onSuccessTimerStopped(time: String) {
        view?.hideProgress()
        view?.setTime(time)
    }

    override fun onError(error: String?, code: Int) {
        view?.hideProgress()
        view?.showError(error)
    }

    override fun getTaskDataFromDb() {
        view?.showProgress()
        interactor.getTaskDataFromDb(this)
    }

    override fun updatePaymentStatus() {
        view?.showProgress()
        interactor.updatePaymentStatus(this)
    }

    override fun updateFinishedStatus() {
        view?.showProgress()
        interactor.updateFinishedStatus(this)
    }

    override fun addDescription(string: String) {
        view?.showProgress()
        interactor.editDescription(string, this)
    }

    override fun updateDoneStatus() {
        view?.showProgress()
        interactor.updateDoneStatus(this)
    }

    override fun startOrStopTimer(floatingActionButton: FloatingActionButton) {
        view?.showProgress()
        interactor.startOrStopTimer(floatingActionButton, this)
    }

    override fun enableDisableEditDescription(detailTaskDescriptionTV: EditText, enabled: Boolean) {
        interactor.enableDisableEditDescription(detailTaskDescriptionTV, enabled)
    }
}