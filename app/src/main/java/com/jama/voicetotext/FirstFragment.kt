package com.jama.voicetotext

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import java.util.Locale

class FirstFragment : Fragment() {
    // 3 ta o'zgaruvchi yaratiladi
    private var speechRecognizer:SpeechRecognizer?=null
    private var textView:TextView?=null
    private var btn:ImageView?=null
    private var isRecording:Boolean?=false
    private var waveformView:WaveformView?=null
    // oncreate override qlingani
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // permission tekshirib olinadi
        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){
            checkPermissions()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()

    }
    companion object{
        const val RecordAudioRequestCode = 1
    }
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), RecordAudioRequestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty()){
            Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        textView =  container?.findViewById(R.id.textFr)
        btn = container?.findViewById(R.id.button)
//        waveformView = WaveformView(requireContext())
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        btn?.setImageResource(R.drawable.baseline_mic_off_24)
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)

        speechRecognizer?.setRecognitionListener(object :RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {}
            @SuppressLint("SetTextI18n")
            override fun onBeginningOfSpeech() {
                textView?.text = "Listening..."
            }
            override fun onRmsChanged(p0: Float) {
                Log.d("FRAAAAAAAAAAAA", "onRmsChanged ${p0}")
//                WaveformView(requireContext(),attrs = null).addAmplitude(p0)
                waveformView?.addAmplitude(p0)
            }

            override fun onBufferReceived(p0: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(p0: Int) {}
            override fun onResults(bundle: Bundle?) {
                btn?.setImageResource(R.drawable.baseline_mic_off_24)
                val data = bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                textView?.text = data?.get(0) ?: ""
                val text = data?.get(0) ?: ""
                Log.d("DAATAAA is:" , text)
            }
            override fun onPartialResults(p0: Bundle?) {}
            override fun onEvent(p0: Int, p1: Bundle?) {}
        })
        btn?.setOnClickListener {
            if (isRecording!!){
                speechRecognizer?.stopListening()
                btn?.setImageResource(R.drawable.baseline_mic_off_24)
            }else{
                btn?.setImageResource(R.drawable.baseline_mic_24)
                speechRecognizer?.startListening(speechRecognizerIntent)
            }
        }
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

}