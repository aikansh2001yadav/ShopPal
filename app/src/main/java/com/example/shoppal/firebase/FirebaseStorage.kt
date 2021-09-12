package com.example.shoppal.firebase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.shoppal.MainActivity
import com.example.shoppal.utils.Constants
import com.example.shoppal.utils.Tags
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseStorage(private val currentUserId: String, private val baseActivity: Activity) {
    /**
     * Stores instance of FirebaseStorage
     */
    private val storageInstance = FirebaseStorage.getInstance()

    /**
     * Uploads profile image on Firebase Storage, deletes earlier profile image stored and update profile details
     */
    fun uploadImage(uri: Uri, imageUrl: String) {
        //If image is uploaded successfully, then update profile details and delete current profile image from storage
        val ref = storageInstance.reference.child("images/" + UUID.randomUUID().toString())
        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        if (downloadUri != null) {
                            updateProfileImageUrl(downloadUri.toString())
                        } else {
                            Toast.makeText(
                                baseActivity,
                                "Image url update failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (imageUrl != "")
                            deleteImageFromStorage(imageUrl)
                    }
                }
                Log.i(Tags.PROFILE_IMAGE_STATUS, "Image Upload")
            }
            .addOnFailureListener {
                Log.e(Tags.PROFILE_IMAGE_STATUS, "Image Upload failed: ${it.message}")
            }
            .addOnProgressListener {
                Log.i(
                    Tags.PROFILE_IMAGE_STATUS,
                    "Downloaded: ${it.bytesTransferred}/${it.totalByteCount}"
                )
            }
    }

    /**
     * Deletes former profile image from Firebase Storage
     */
    private fun deleteImageFromStorage(imageUrl: String) {
        storageInstance.getReferenceFromUrl(imageUrl).delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i(Tags.FORMER_PROFILE_IMAGE_STATUS, "Image deleted from storage")
            } else {
                Log.e(Tags.FORMER_PROFILE_IMAGE_STATUS, "Image not able to delete from storage")
            }
        }
    }

    /**
     * Updates profile image url of the current user and start MainActivity if task is successful
     */
    private fun updateProfileImageUrl(downloadUrl: String) {
        Firebase.firestore.collection(Constants.USERS)
            .document(currentUserId)
            .update(mapOf("profileImage" to downloadUrl))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    baseActivity.startActivity(Intent(baseActivity, MainActivity::class.java))
                    baseActivity.finish()
                } else {
                    Toast.makeText(baseActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
}