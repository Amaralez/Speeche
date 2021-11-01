package com.example.speeche

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mTTS: TextToSpeech? = null
    private var mEditText: EditText? = null
    private var mSeekBarPitch: SeekBar? = null
    private var mSeekBarSpeed: SeekBar? = null
    private var mButtonSpeak: Button? = null
    //private var voiceBtn : Button? = null
    private var REQUEST_CODE_SPEECH_INPUT = 100
    //private var localeDef = Locale.getDefault()
    //val result = mTTS!!.setLanguage(Locale.ENGLISH)

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val APP_ACTIVITY = this
        //проверяем разрешение


            //val permissions = android.Manifest.permission.RECORD_AUDIO






        //val voiceBtn = findViewById<Button>(R.id.voiceBtn)

        initializeFields()

        //initPermission()

        //voiceBtn.setOnClickListener {
       // speak1();
        //}

        mTTS = TextToSpeech(this, OnInitListener { i ->
            if (i == TextToSpeech.SUCCESS) {
                val result = mTTS!!.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language Not Supported")
                }
                else
                {
                    mButtonSpeak!!.isEnabled = true
                }
            }
            else
            {
                Log.e("TTS", "Initialization Failed")
            }
        })
        mButtonSpeak!!.setOnClickListener {
            speak()
            closeKeyboard()
        }




    }

   // private fun initPermission() {
       // if (checkPermission(RECORD_AUDIO))

    //}





    //private fun speak1() {
      //  val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
      //  mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
       //     RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
      //  mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
      //  mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Привет, скажи что-то")

     //   try {

      //      startActivityForResult(mIntent,REQUEST_CODE_SPEECH_INPUT)
      //  }
       // catch (e: Exception) {
      //      Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
      //  }
      //  finally {
            // постобработка
     //   }

    //}


    private fun speak()
    {
        val text = mEditText!!.text.toString()
        var pitch = mSeekBarPitch!!.progress.toFloat() / 50
        if (pitch < 0.1) pitch = 0.1f
        var speed = mSeekBarSpeed!!.progress.toFloat() / 50
        if (speed < 0.1) speed = 0.1f
        mTTS!!.setPitch(pitch)
        mTTS!!.setSpeechRate(speed)
        mTTS!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun closeKeyboard()
    {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy()
    {
        if (mTTS != null) {
            mTTS!!.stop()
            mTTS!!.shutdown()
        }
        super.onDestroy()
    }

    private fun initializeFields() {
        mButtonSpeak = findViewById(R.id.button_speak)
        mEditText = findViewById(R.id.edit_text)
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch)
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed)
       // voiceBtn = findViewById(R.id.voiceBtn)
    }

    fun voiceClick(view: android.view.View) {
       // val toast = Toast.makeText(this, "кнопка работает!!!", Toast.LENGTH_SHORT).show()
          val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
             RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

          mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
          mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Привет! Скажи что-нибудь")


           try {
               startActivityForResult(mIntent,REQUEST_CODE_SPEECH_INPUT)
          }
         catch (e: Exception) {
              Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
          }
          finally {
         //постобработка
           }





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = data?.getStringArrayListExtra(
            RecognizerIntent.EXTRA_RESULTS)
        val msg = result?.get(0)

        var edit = findViewById<EditText>(R.id.edit_text)
        edit.setText(msg)
        speak()
        //val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}