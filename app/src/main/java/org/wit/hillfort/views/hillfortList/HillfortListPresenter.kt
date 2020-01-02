package org.wit.hillfort.views.hillfortList


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.*
import org.wit.hillfort.views.map.HillfortMapView
import org.wit.hillfort.views.hillfort.HillfortView


import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW
import org.wit.hillfort.views.hillfortFavouritesList.HillfortFavouriteListView

class HillfortListPresenter (view: BaseView) : BasePresenter(view) {

    //fun getHillforts() = app.hillforts.findAll()
    var hillfort = HillfortModel()
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

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)

            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doDelete() {
        doAsync {
            app.hillforts.delete(hillfort)
            uiThread {
                view?.finish()
            }
        }
    }
}




