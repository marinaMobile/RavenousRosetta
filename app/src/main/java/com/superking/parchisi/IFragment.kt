package com.superking.parchisi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.qualifier.named


class IFragment : Fragment() {
    private lateinit var mContext: Context


    val shareP: SharedPreferences by inject(named("SharedPreferences"))
    val viewMainModel by activityViewModel<ViMod>(named("MainModel"))

    lateinit var country: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_i, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewMainModel.countryCode.observe(viewLifecycleOwner) {
            if (it!=null) {
                country = it.cou
                shareP.edit().putString("country", country).apply()
                findNavController().navigate(R.id.action_IFragment_to_atFragment)
            }
        }
    }
}