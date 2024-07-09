package com.github.projectm_android

// kotlin has no static classes so we use object
object ProjectMJNIWrapper {
    init {
        System.loadLibrary("jniwrapper")
    }

    external fun onSurfaceCreated(windowWidth: Int, windowHeight: Int, assetPath: String)
    external fun onSurfaceChanged(width: Int, height: Int)
    external fun onDrawFrame()
    external fun addPCM(pcmData: ShortArray, nSamples: Short)
    external fun nextPreset()
}