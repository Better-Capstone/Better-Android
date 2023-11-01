package user

import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(
    @SerializedName("kakaoToken")
    val kakaoToken: String,

    @SerializedName("nickname")
    val nickName: String
)
