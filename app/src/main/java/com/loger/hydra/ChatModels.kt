package com.loger.hydra

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @SerializedName("message") val message: String,
    @SerializedName("model") val model: String = "auto"
)

data class ChatResponse(
    @SerializedName("response") val response: String,
    @SerializedName("model_used") val modelUsed: String? = null
)
