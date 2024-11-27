package com.example.instamall.fragments.categories

class CabinetsCatFragment: BaseCatFragment() {
    override fun getCategoryId(): String {
        return "cabinets" // The category ID for sofas
    }

    override fun getHeading(): String {
        return "Cabinets" // The heading for the sofa category
    }
}