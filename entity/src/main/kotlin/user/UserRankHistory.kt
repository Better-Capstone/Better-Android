package user

import com.google.gson.annotations.SerializedName

data class UserRankHistory(
    @SerializedName("id")
    val id: Long,

    @SerializedName("study_id")
    val studyId: Long,

    @SerializedName("user_rank_id")
    val userRankId: Long,

    @SerializedName("uid")
    val userId: Long,

    @SerializedName("score")
    val score: Int,

    @SerializedName("description")
    val description: String
)
