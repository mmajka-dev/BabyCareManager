package com.mmajka.babycaremanager.utils

import android.view.View
import com.mmajka.babycaremanager.data.BasicActionEntity

interface onClickListener {
    fun onClick(position: Int, view: View, action: BasicActionEntity)
    fun onLongClick(position: Int, view: View, action: BasicActionEntity)
}