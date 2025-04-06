package com.example.speerinterviewsubmission.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.speerinterviewsubmission.data.network.APIResponse
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepo() {

    // for network operation result
    protected fun <T> getNetworkResult(
        call: suspend () -> Response<T>
    ): LiveData<APIResponse<T>> =
        liveData(Dispatchers.IO) {
            emit(APIResponse.Loading)
            val response = call.invoke()
            emit(gatherResult(response))
        }

    private fun <T> getError(response: Response<T>): String = "${response.code()} ${
        JSONObject(response.errorBody()?.string())["message"] ?: "no specific error"
    }"

    private fun <T> gatherResult(response: Response<T>): APIResponse<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) APIResponse.Success(body)
            else APIResponse.Error("No data Found")
        } else {
            APIResponse.Error(
                getError(response)
            )
        }
    }
    }