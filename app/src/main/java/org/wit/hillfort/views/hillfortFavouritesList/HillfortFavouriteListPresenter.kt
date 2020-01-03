package org.wit.hillfort.views.hillfortFavouritesList


import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*
import org.wit.hillfort.views.map.HillfortMapView
import org.wit.hillfort.views.hillfort.HillfortView


import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW

class HillfortFavouriteListPresenter (view: BaseView) : BasePresenter(view) {

    //fun getHillforts() = app.hillforts.findAll()

    fun doAddHillfort() {
        view?.startActivityForResult<HillfortView>(0)
    }

    fun doShowFavouriteHillfort() {
        view?.startActivityForResult<HillfortFavouriteListView>(0)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.startActivity<HillfortMapView>()
    }

    fun loadFavouriteHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts.filter { it.favourite != false })

            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGIN)
    }

}


