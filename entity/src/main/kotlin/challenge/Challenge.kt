package challenge

import com.google.gson.annotations.SerializedName

data class Challenge(
    @SerializedName("id")
    val id: Long,

    @SerializedName("task_id")
    val taskId: Long,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String
)
