package com.kbalazsworks.simple_oidc.common.entities

data class ApiResponseData<T> (
    var data: T? = null,
    var success: Boolean,
    var errorCode: Int,
    var requestId: String
)
