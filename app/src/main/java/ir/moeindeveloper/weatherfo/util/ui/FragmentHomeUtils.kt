package ir.moeindeveloper.weatherfo.util.ui

import android.view.View
import ir.moeindeveloper.weatherfo.databinding.ActivityMainBinding
import ir.moeindeveloper.weatherfo.databinding.FragmentHomeBinding

fun FragmentHomeBinding.enterLoadingState() {
    this.noConnectionLayout.root.visibility = View.GONE
    this.mainLayout.root.visibility = View.GONE
    this.loadingLayout.root.visibility = View.VISIBLE
    this.loadingLayout.loadingIndicator.visibility = View.VISIBLE
    this.loadingLayout.loadingIndicator.smoothToShow()
}


fun FragmentHomeBinding.enterErrorState() {
    this.loadingLayout.root.visibility = View.GONE
    this.mainLayout.root.visibility = View.GONE
    this.noConnectionLayout.root.visibility = View.VISIBLE
    this.noConnectionLayout.noConnectionAnim.visibility = View.VISIBLE
    this.noConnectionLayout.tryAgainButton.visibility = View.VISIBLE
}

fun FragmentHomeBinding.enterSuccessState() {
    this.loadingLayout.root.visibility = View.GONE
    this.noConnectionLayout.root.visibility = View.GONE
    this.mainLayout.root.visibility = View.VISIBLE
}