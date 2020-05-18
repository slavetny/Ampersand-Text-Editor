package com.slavetny.ampersand.utils.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int) =
    LayoutInflater.from(context).inflate(layoutRes,this, false)!!