package com.ftd.livepermissions

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

/**
 * author GaoPC
 * date 2019-10-25 17:47
 * description
 */
internal class LiveFragment : Fragment() {

    lateinit var liveData: MutableLiveData<PermissionResult>

    private val PERMISSIONS_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissions(permissions: Array<out String>) {
        liveData = MutableLiveData()
        val tempPermission = ArrayList<String>()
        permissions.forEach {
            if (activity?.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                tempPermission.add(it)
            }
        }
        if (tempPermission.isEmpty()) {
            liveData.value = PermissionResult.Grant
        } else {
            requestPermissions(tempPermission.toTypedArray(), PERMISSIONS_REQUEST_CODE)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            val denyPermission = ArrayList<String>()
            val rationalePermission = ArrayList<String>()
            for ((index, value) in grantResults.withIndex()) {
                if (value == PackageManager.PERMISSION_DENIED) {
                    if (shouldShowRequestPermissionRationale(permissions[index])) {
                        rationalePermission.add(permissions[index])
                    } else {
                        denyPermission.add(permissions[index])
                    }
                }
            }
            if (denyPermission.isEmpty() && rationalePermission.isEmpty()) {
                liveData.value = PermissionResult.Grant
            } else {
                if (rationalePermission.isNotEmpty()) {
                    liveData.value = PermissionResult.Rationale(rationalePermission.toTypedArray())
                } else if (denyPermission.isNotEmpty()) {
                    liveData.value = PermissionResult.Deny(denyPermission.toTypedArray())
                }
            }

        }
    }

}