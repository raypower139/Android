package org.wit.hillfort.views.hillfortList


import org.jetbrains.anko.*
import org.wit.hillfort.views.map.HillfortMapView
import org.wit.hillfort.views.hillfort.HillfortView


import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW

class HillfortListPresenter (view: BaseView) : BasePresenter(view) {

    //fun getHillforts() = app.hillforts.findAll()

    fun doAddHillfort() {
        view?.startActivityForResult<HillfortView>(0)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.startActivity<HillfortMapView>()
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

}

