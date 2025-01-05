package com.example.childmonitoringel



import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import com.google.firebase.database.DatabaseReference


class cam : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    private lateinit var imageView: ImageView
    private lateinit var selectButton: Button
    private lateinit var captureButton: Button
    private lateinit var sendButton: Button
    private lateinit var heightTextView: TextView
    private var imageUri: Uri? = null
    private var imageFile: File? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)

        imageView = findViewById(R.id.imageView)
        selectButton = findViewById(R.id.selectImageButton)
        captureButton = findViewById(R.id.captureImageButton)
        sendButton = findViewById(R.id.sendImageButton)
        heightTextView = findViewById(R.id.heightTextView)

        // Select image button click
        selectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        // Capture image button click
        captureButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 200)
        }

        // Send image button click
        sendButton.setOnClickListener {
            imageFile?.let { file ->
                sendImageToServer(file)
            } ?: run {
                heightTextView.text = "Please capture or select an image"
            }
        }
        val Tut= findViewById<Button>(R.id.Btn2)
        Tut.setOnClickListener{
            val intent = Intent(this, WEBSITE::class.java)
            startActivity(intent)
        }


    }


    // Handle image selection or capture result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                100 -> { // Image selection from gallery
                    imageUri = data.data
                    imageView.setImageURI(imageUri)  // Display the selected image
                    imageUri?.let { uri -> imageFile = getFileFromUri(uri) }
                }
                200 -> { // Image capture from camera
                    val capturedImageUri = data.data
                    if (capturedImageUri != null) {
                        imageView.setImageURI(capturedImageUri)  // Display the captured image
                        imageFile = getFileFromUri(capturedImageUri)
                    } else {
                        val bitmap = data.extras?.get("data") as? android.graphics.Bitmap
                        bitmap?.let {
                            imageView.setImageBitmap(it)
                            imageFile = saveBitmapToFile(it)
                        }
                    }
                }
            }
        }
    }

    // Convert Uri to File (Scoped Storage)
    private fun getFileFromUri(uri: Uri): File? {
        return try {
            val contentResolver = contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("image_", ".jpg", cacheDir)
            tempFile.deleteOnExit()

            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("FileError", "Error converting URI to file", e)
            null
        }
    }

    // Save Bitmap to File
    private fun saveBitmapToFile(bitmap: android.graphics.Bitmap): File? {
        return try {
            val file = File(cacheDir, "captured_image.jpg")
            file.outputStream().use {
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, it)
            }
            file
        } catch (e: Exception) {
            Log.e("FileError", "Error saving bitmap to file", e)
            null
        }
    }

    // Send image to the server
    private fun sendImageToServer(file: File) {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        RetrofitClient.apiService.uploadImage(body).enqueue(object : Callback<HeightResponse> {
            override fun onResponse(call: Call<HeightResponse>, response: Response<HeightResponse>) {
                if (response.isSuccessful) {
                    val height = response.body()?.height
                    Globalvar.height=height.toString()
                    heightTextView.text = "Height: $height cm"
                } else {
                    heightTextView.text = "Server Error: ${response.message()}"
                }
                var mail=Globalvar.mailId.toString()
                var password=Globalvar.password.toString()
                var weight=Globalvar.weight.toString()
                var age=Globalvar.age.toString()
                var name=Globalvar.name.toString()
                val user = User_height(name,mail, password,weight,age,height=Globalvar.height.toString() )

                // Write the data to Firebase
                database = FirebaseDatabase.getInstance().getReference("Users")


                database.child(mail).setValue(user)
            }

            override fun onFailure(call: Call<HeightResponse>, t: Throwable) {
                heightTextView.text = "Error: ${t.message}"
                Log.e("APIError", "Error sending image", t)
            }
        })

    }

}
