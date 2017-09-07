package com.startach.yedidim.entities

/**
 * Created by yb34982 on 07/09/2017.
 */
data class SMSRequestResponse(val status : SMSRequestResponseStatus, val receivedCode: String)

enum class SMSRequestResponseStatus{
    Success,
    UnknownUser,
    Failed
}