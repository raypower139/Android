package org.wit.hillfort.views.hillfort

import android.content.Intent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.models.Location
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.*

class HillfortPresenter(view: BaseView):BasePresenter(view) {

    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)

    var edit = false

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
    }

    fun doAddOrSave(title: String, description: String, visited: Boolean, date: String,
        notes: String, rating: Float)
    {
        hillfort.title = title
        hillfort.description = description
        hillfort.visited = visited
        hillfort.date = date
        hillfort.notes = notes
        hillfort.rating = rating

        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            } else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view?.finish()
            }}}

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view?.finish()
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(hillfort.lat, hillfort.lng, hillfort.zoom)
            )
        }
    }




    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }

    }


}