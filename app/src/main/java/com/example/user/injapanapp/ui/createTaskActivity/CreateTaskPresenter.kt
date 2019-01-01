package com.example.user.injapanapp.ui.createTaskActivity

class CreateTaskPresenter(private val interactor: ICreateTaskInteractor = CreateTaskInteractor()):
    ICreateTaskPresenter, ICreateTaskInteractor.OnCreateTaskListener {

    private var view: ICreateTaskView? = null

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

    override fun onError(error: String, code: Int) {
        view?.hideProgress()
        view?.showError(error, code)
    }

    override fun validateAndInsert(taskNumber: String, taskType: String,
                                   taskPrice: String, taskShelf: String, taskDescription: String) {
        view?.showProgress()
        interactor.validateAndInsert(taskNumber, taskType,
            taskPrice, taskShelf, taskDescription, this)
    }
}