package com.appsnipp.admin.apiinterface;

import com.appsnipp.admin.apiinterface.responce.event_responce;
import com.appsnipp.admin.apiinterface.responce.loginresponce;
import com.appsnipp.admin.apiinterface.responce.member_responce;
import com.appsnipp.admin.apiinterface.responce.notice_responce;
import com.appsnipp.admin.apiinterface.responce.resource_responce;
import com.appsnipp.admin.apiinterface.responce.visidetail_responce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("RegistrationControllerJson.php")
    Call<CommanResponse> createUser(

            @Field("loginRegistration") String loginRegistration,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("roletype") String roletype,
            @Field("mobno") String mobno,
            @Field("email") String email,
            @Field("houseno") String houseno,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("loginapi.php")
    Call<loginresponce> login(

            @Field("loginmember") String loginmember,
            @Field("mobno") String mobno,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("noticeapi.php")
    Call<CommanResponse> noticeentry(

            @Field("noticeentry") String noticeentry,
            @Field("header") String header,
            @Field("description") String description,
            @Field("aname") String aname,
            @Field("datetime") String datetime
    );

    @FormUrlEncoded
    @POST("noticeapi.php")
    Call<notice_responce> noticedetail(
            @Field("noticedetail") String noticedetail
    );

    @FormUrlEncoded
    @POST("eventapi.php")
    Call<CommanResponse> evententry(

            @Field("evententry") String evententry,
            @Field("name") String name,
            @Field("place") String place,
            @Field("members") String members,
            @Field("date") String date,
            @Field("time") String time
    );

    @FormUrlEncoded
    @POST("eventapi.php")
    Call<event_responce> eventdetail(
            @Field("eventdetail") String eventdetail
    );


    @FormUrlEncoded
    @POST("resourceapi.php")
    Call<CommanResponse> resourceentry(

            @Field("resourceentry") String resourceentry,
            @Field("name") String name,
            @Field("capacity") String capacity,
            @Field("charge") String charge,
            @Field("details") String details
    );


    @FormUrlEncoded
    @POST("resourceapi.php")
    Call<resource_responce> resourcedetail(
            @Field("resourcedetail") String resourcedetail
    );
    @FormUrlEncoded
    @POST("GatekeeperVisiEntry.php")
    Call<visidetail_responce> visidetail(
            @Field("gatekvisidetail") String gatekvisidetail

    );


    @FormUrlEncoded
    @POST("membersapi.php")
    Call<member_responce> membersdetail(
            @Field("membersdetail") String membersdetail,
            @Field("blockno") String blockno
    );
}
