package com.example.user.injapanapp.ui.task_detail_activity

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class TaskDetailPresenter(private val interactor: ITaskDetailInteractor = TaskDetailInteractor()) :
    ITaskDetailPresenter, ITaskDetailInteractor.OnTaskDetailListener {

    private var view: ITaskDetailView? = null

    override fun onAttachView(view: ITaskDetailView) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    override fun onSuccess() {
        view?.hideProgress()
        view?.setTaskData()
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

    override fun setTaskData(
        detailTaskNumberTV: TextView, detailTaskTypeTV: TextView, detailTaskPriceTV: TextView,
        detailTaskShelfTV: TextView, detailTaskEndDateTV: TextView, detailTaskDescriptionTV: EditText,
        detailPhotoIV: ImageView, detailPriorityTV: TextView, detailStartTimerFAB: FloatingActionButton,
        detailMainLayout: CoordinatorLayout
        ) {
        view?.showProgress()
        interactor.setTaskData(
            detailTaskNumberTV, detailTaskTypeTV, detailTaskPriceTV,
            detailTaskShelfTV, detailTaskEndDateTV, detailTaskDescriptionTV,
            detailPhotoIV, detailPriorityTV, detailStartTimerFAB, detailMainLayout, this
        )
    }

    override fun updatePriority(priority: String) {
        view?.showProgress()
        interactor.updatePriority(priority, this)
    }
}