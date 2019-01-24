package com.example.user.injapanapp.ui.createTaskActivity

import android.content.Intent
import android.widget.ImageView

class CreateTaskPresenter(
    private var view: ICreateTaskView? = null,
    private val interactor: ICreateTaskInteractor = CreateTaskInteractor()
) :
    ICreateTaskPresenter, ICreateTaskInteractor.OnCreateTaskListener {


    override fun onAttachView(view: ICreateTaskView) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    override fun onSuccess() {
        view?.hideProgress()
        view?.finishActivity()
    }

    override fun onPictureSuccess(intent: Intent) {
        view?.hideProgress()
        view?.startPictureActivity(intent)
    }

    override fun onError(error: String, code: Int) {
        view?.hideProgress()
        view?.showError(error, code)
    }

    override fun onErrorTaskInside() {
        view?.hideProgress()
        view?.showReplaceDialog()
    }

    override fun validateAndInsert(
        taskNumber: String, taskType: String,
        taskPrice: String, taskShelf: String,
        taskDescription: String, taskPriority: String
    ) {
        view?.showProgress()
        interactor.validateAndInsert(
            taskNumber, taskType,
            taskPrice, taskShelf,
            taskDescription, taskPriority,
            this
        )
    }

    override fun launchCamera() {
        view?.showProgress()
        interactor.launchCamera(this)
    }

    override fun deleteImageFile() {
        view?.showProgress()
        interactor.deleteImageFile(this)
    }

    override fun processAndSetImage(imageView: ImageView) {
        view?.showProgress()
        interactor.processAndSetImage(imageView, this)
    }

    override fun saveImage() {
        view?.showProgress()
        interactor.saveImage(this)
    }

    override fun clearImage() {
        view?.showProgress()
        interactor.clearImage(this)
    }

    override fun replaceTask() {
        view?.showProgress()
        interactor.replaceTask(this)
    }

    override fun onSaveAndClearAndDeleteSuccess() {
        view?.hideProgress()
    }

    override fun onProcessAndSetImageSuccess() {
        view?.hideProgress()
        view?.showSaveFAB()
    }
}