package com.github.projectm_android

import android.content.res.Resources
import android.opengl.GLSurfaceView
import android.util.Log
import com.github.projectm_android.ProjectMJNIWrapper.nextPreset
import com.github.projectm_android.ProjectMJNIWrapper.onDrawFrame
import com.github.projectm_android.ProjectMJNIWrapper.onSurfaceChanged
import com.github.projectm_android.ProjectMJNIWrapper.onSurfaceCreated
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class RendererWrapper(private val mAssetPath: String) : GLSurfaceView.Renderer {
    private var mNextPreset = false

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        Log.d("projectM", "RenderWrapper onSurfaceCreated")
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        onSurfaceCreated(width, height, mAssetPath)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        Log.d("projectM", "RenderWrapper onSurfaceChanged")
        onSurfaceChanged(width, height)
    }

    fun NextPreset() {
        mNextPreset = true
    }

    override fun onDrawFrame(gl: GL10) {
        if (mNextPreset) {
            mNextPreset = false
            nextPreset()
        }
        onDrawFrame()
    }
}
