package com.tapan.architectureDemo.core.exception

/**
 * Custom Exceptions
 */
sealed class MyException(val throwable: Throwable, val msg: String = "") : Exception() {

    public class UnKnownError(throwable: Throwable) : MyException(throwable)
    public class AutoApiErrorError(throwable: Throwable) : MyException(throwable)
    public class ApiError(throwable: Throwable) : MyException(throwable)
    public class NetworkError(throwable: Throwable) : MyException(throwable)
    public class IAgreeException(throwable: Throwable):MyException(throwable)
    public class UnAuthenticate(throwable: Throwable):MyException(throwable)
}