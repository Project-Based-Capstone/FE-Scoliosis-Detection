package com.capstone.scoliolysis.view.insertData

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.scoliolysis.databinding.ActivityDataBinding
import com.capstone.scoliolysis.utils.UserPreference
import com.capstone.scoliolysis.utils.reduceFileImage
import com.capstone.scoliolysis.view.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    private lateinit var dataViewModel: DataViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var token: String? = null
    private var myFile: File? = null
    var formatDate = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    private var age : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myFile = intent.getSerializableExtra("myFile") as File
        val result = BitmapFactory.decodeFile(myFile.path)
        this.myFile = myFile

        binding.imageViewData.setImageBitmap(result)

        dataViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DataViewModel::class.java]


        dataViewModel.getUser().observe(this){
            this.token = it.accessToken
        }

        dataViewModel.isLoading.observe(this){
            showLoading(it, binding.progressBar)
        }

        val date = Calendar.getInstance().time
        val formatted = formatDate.format(date)

        binding.dateTextView.text = formatted


        binding.buttonPickDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePicker =
                DatePickerDialog(this, { _, i, i2, i3 ->
                    val selectDate = Calendar.getInstance()
                    selectDate.set(Calendar.YEAR, i)
                    selectDate.set(Calendar.MONTH, i2)
                    selectDate.set(Calendar.DAY_OF_MONTH, i3)
                    val date = formatDate.format(selectDate.time)
                    binding.textViewDate.text = date
                    val age = getAge(i, i2,i3)
                    this.age = age
                    Toast.makeText(this, "Age: " + age, Toast.LENGTH_SHORT).show()

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        binding.saveButton.setOnClickListener{
            submitData()
        }
    }



    private fun getAge(year: Int, month: Int, day: Int): String? {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        val ageInt = age
        return ageInt.toString()
    }


    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun submitData() {
        val file = reduceFileImage(myFile as File)
        /** val date = binding.dateTextView.text.toString().toRequestBody("text/plain".toMediaType()) **/
        val name = binding.editTextName.text.toString().toRequestBody("text/plain".toMediaType())
        val dob = this.age?.toRequestBody("text/plain".toMediaType())
        val image: RequestBody = file.asRequestBody("image/jpg".toMediaType())

        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            file.name,
            image
        )

       dataViewModel.addResult(
            this.token.toString(),
            name,
            dob,
            imageMultipart
        )

        dataViewModel.response.observe(this){
            Toast.makeText(
                this,
                it.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}