package ir.moeindeveloper.weatherfo.util.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.dialog.MaterialDialogs
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.data.preference.AppSettings
import ir.moeindeveloper.weatherfo.databinding.ActivityMainBinding
import ir.moeindeveloper.weatherfo.databinding.FragmentCityBinding
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


inline fun FragmentHomeBinding.showChangeLanguageDialog(current: String,
                                                        crossinline callBack: (language: AppSettings.Languages) -> Unit) {
    val persianStr = root.context.getString(AppSettings.Languages.PERSIAN.langName)
    val englishStr = root.context.getString(AppSettings.Languages.ENGLISH.langName)
    val langList = listOf(englishStr,persianStr)
    val currentLang = when(current){
        AppSettings.Languages.ENGLISH.language -> 0
        AppSettings.Languages.PERSIAN.language -> 1
        else -> 0
    }

    MaterialDialog(root.context,BottomSheet(LayoutMode.WRAP_CONTENT)).show {
        title(R.string.change_lang_title)
        message(R.string.change_lang_msg)
        listItemsSingleChoice(items = langList,initialSelection = currentLang,waitForPositiveButton = false) { dialog, _, text ->
            val language = when(text) {
                englishStr -> AppSettings.Languages.ENGLISH
                persianStr -> AppSettings.Languages.PERSIAN
                else -> AppSettings.Languages.PERSIAN
            }

            callBack(language)

            dialog.dismiss()
        }
    }
}

fun FragmentCityBinding.focusOnSearchBox(activity: Activity) {
    mainLayout.searchTEdit.requestFocus()
    val lManager: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    lManager.showSoftInput(mainLayout.searchTEdit, 0)
}