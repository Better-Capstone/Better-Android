package user

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("user")
    val user: User
)
