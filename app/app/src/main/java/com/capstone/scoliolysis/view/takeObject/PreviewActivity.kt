package com.capstone.scoliolysis.view.takeObject

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.scoliolysis.databinding.ActivityPreviewBinding
import com.capstone.scoliolysis.view.insertData.DataActivity
import java.io.File


class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private var getFile: File? = null
    private var test: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCameraX()
        binding.fabDone.setOnClickListener { saveImage() }
    }

    private fun startCameraX() {
        val intent = Intent(this, TakeObjectActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result =  BitmapFactory.decodeFile(myFile.path)
            //          FOR EMULATOR USER !
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(getFile?.path),
//                isBackCamera
//            )
            binding.imageView.setImageBitmap(result)
            test = myFile

        }
    }

    private fun saveImage() {
        if (getFile != null) {
            Intent(this@PreviewActivity, DataActivity::class.java).apply {
                putExtra("myFile", test)
                startActivity(this)
            }
            finish()
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
    }
}