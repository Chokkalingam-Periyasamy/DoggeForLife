package com.example.doggeforlife

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.reflect.Type

class LoginApplication : Application() {
    lateinit var loginService: ApiInterface
    lateinit var petsService:PetsApiService
    lateinit var registerService:RegisterApiInterface
    lateinit var changeemailService:ChangeEmailApiService
    lateinit var deleteService :DeleteEmail


    override fun onCreate() {
        super.onCreate()
        loginService=loginApi()
        petsService=initHttpApiService()
        registerService=initHttpregisterApiService()
        changeemailService=initHttpemailApiService()
        deleteService = DeleteApiService()
    }
    fun loginApi(): ApiInterface
    {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/doggeforlife-mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
    fun initHttpApiService():PetsApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/doggeforlife-mobile/")
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper()))
            .build()
        return retrofit.create(PetsApiService::class.java)
    }
    fun initHttpemailApiService(): ChangeEmailApiService
    {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ChangeEmailApiService::class.java)
    }
    fun initHttpregisterApiService(): RegisterApiInterface {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/doggeforlife-mobile/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RegisterApiInterface::class.java)
    }
    fun DeleteApiService(): DeleteEmail{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/doggeforlife-mobile/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(DeleteEmail::class.java)
    }
    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?
        ): Converter<ResponseBody, *>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter
                delegate.convert(it)
            }
        }



    }
}