package com.imandroid.simplefoursquare.util.extension

import android.content.res.AssetManager


fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}

