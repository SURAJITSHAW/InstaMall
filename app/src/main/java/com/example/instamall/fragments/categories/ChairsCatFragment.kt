package com.example.instamall.fragments.categories

class ChairsCatFragment: BaseCatFragment() {
    override fun getCategoryId(): String {
        return "chairs" // The category ID for sofas
    }

    override fun getHeading(): String {
        return "Chairs" // The heading for the sofa category
    }
}