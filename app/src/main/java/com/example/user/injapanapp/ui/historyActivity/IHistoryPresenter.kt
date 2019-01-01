package com.example.user.injapanapp.ui.historyActivity

import com.example.user.injapanapp.ui.generalActivity.IGeneralPresenter

interface IHistoryPresenter: IGeneralPresenter {

    fun onAttachView(view: IHistoryView)

    fun getAllTasks()
}