package com.lmy.codec.x264

import android.media.MediaFormat
import com.lmy.codec.util.debug_e
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by lmyooyo@gmail.com on 2018/4/3.
 */
class X264Encoder(private var format: MediaFormat,
                  var buffer: ByteBuffer? = null) {
    init {
        System.loadLibrary("x264")
        System.loadLibrary("codec")
        init()
        setVideoSize(format.getInteger(MediaFormat.KEY_WIDTH), format.getInteger(MediaFormat.KEY_HEIGHT))
        setBitrate(format.getInteger(MediaFormat.KEY_BIT_RATE))
        setFrameFormat(FrameFormat.X264_CSP_RGB)
        setFps(format.getInteger(MediaFormat.KEY_FRAME_RATE))
        buffer = ByteBuffer.allocate(720 * 480 * 3)
        buffer?.order(ByteOrder.nativeOrder())
    }

    private fun createBuffer(size: Int): ByteArray {
        debug_e("Create buffer($size)")
        return buffer!!.array()
    }

    fun encode(src: ByteArray, srcSize: Int): Int {
        buffer?.position()
        buffer?.clear()
        return encode(src, srcSize, buffer!!.array())
    }

    private external fun init()
    external fun start()
    external fun stop()
    external fun encode(src: ByteArray, srcSize: Int, out: ByteArray): Int
    external fun setVideoSize(width: Int, height: Int)
    external fun setBitrate(bitrate: Int)
    external fun setFrameFormat(format: Int)
    external fun setFps(fps: Int)
    external fun setProfile(profile: String)
    external fun setLevel(level: Int)
}