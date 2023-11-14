package com.ssu.better.entity.grouprank

import com.ssu.better.entity.study.Study

data class GroupRank(
    var id: Long? = null,

    var numOfLastAttendees: Int = 0,

    var score: Int = 0,

    var study: Study? = null,

)
