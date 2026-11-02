package com.sananhaji.qrgenerator.ui.navigation

object Routes {

    const val HOME = "home"

    const val ARG_CUSTOMIZE = "customize"

    const val ARG_TEMPLATE_ID = "templateId"

    const val EDITOR = "editor/{$ARG_CUSTOMIZE}"

    const val TEMPLATE = "template/{$ARG_TEMPLATE_ID}"

    fun editor(customize: Boolean): String = "editor/$customize"

    fun template(id: String): String = "template/$id"
}
