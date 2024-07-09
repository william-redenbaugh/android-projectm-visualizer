package com.github.projectm_android


import android.media.AudioFormat.CHANNEL_IN_MONO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.SystemClock
import android.util.Log

//public class AudioThread extends Thread {
//    private volatile static boolean keep_recording = true;
//    private volatile static boolean currently_recording = false;
//
//    @Override
//    public void run() {
//        int SAMPLE_RATE = 11025;
//        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
//                CHANNEL_IN_MONO,
//                ENCODING_PCM_16BIT);
//        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
//                SAMPLE_RATE,
//                CHANNEL_IN_MONO,
//                ENCODING_PCM_16BIT,
//                bufferSize);
//        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
//            bufferSize = SAMPLE_RATE * 2;
//        }
//        short[] audioBuffer = new short[bufferSize];
//
//        Log.d("AudioThread",String.format("bufferSize: %d", bufferSize));
//
//        while (currently_recording) {
//            Log.d("AudioThread","currently recoring, waiting 50ms before attempting again");
//            // bufferSize is usually worth 80ms of aurdio
//            // So waiting 50ms - this means, next attempt to grab AudioRecord will succeed.
//            SystemClock.sleep(50);
//        }
//        record.startRecording();
//        currently_recording = true;
//
//        while (keep_recording) {
//            record.read(audioBuffer, 0, audioBuffer.length);
//            ProjectMJNIWrapper.addPCM(audioBuffer, (short) bufferSize);
////            short max = 0;
////            for (int ix=0;ix<audioBuffer.length; ix++) {
////                max = (short) Math.max(audioBuffer[ix], max);
////            }
////            Log.i("MAX:", String.valueOf(max));
//        }
//        record.stop();
//        currently_recording = false;
//    }
//
//    public void stop_recording() {
//        keep_recording = false;
//    }
//
//}

class AudioThread: Thread() {
    companion object {
        @Volatile private var keepRecording: Boolean = true
        @Volatile private var currentlyRecording: Boolean = false

        private val tag: String = "AudioThread"
    }

    override fun run() {
        val sampleRate = 11025
        var bufferSize = AudioRecord.getMinBufferSize(sampleRate,
                CHANNEL_IN_MONO,
                ENCODING_PCM_16BIT)

        val record = AudioRecord(MediaRecorder.AudioSource.DEFAULT,
                sampleRate,
                CHANNEL_IN_MONO,
                ENCODING_PCM_16BIT,
                bufferSize)

        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = sampleRate * 2
        }

        val audioBuffer = ShortArray(bufferSize)

        Log.d(tag, "bufferSize: $bufferSize")

        while (currentlyRecording) {
            Log.d("AudioThread", "currently recording, waiting 50ms before attempting again")
            SystemClock.sleep(50)
        }
        record.startRecording()
        currentlyRecording = true

        while (keepRecording) {
            record.read(audioBuffer, 0, audioBuffer.size)
            ProjectMJNIWrapper.addPCM(audioBuffer, bufferSize.toShort())
            Log.i(tag, "MAX: ${audioBuffer.max()}")
        }
        record.stop()
        currentlyRecording = false
    }

    fun stopRecording() {
        keepRecording = false
    }
}