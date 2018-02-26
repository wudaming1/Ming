package com.arise.common.ming.home.userCenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arise.common.ming.ImmersiveUtil
import com.arise.common.ming.R
import com.arise.common.ming.base.MessageEvent
import com.arise.common.ming.base.MyBaseFragment
import com.arise.common.ming.config.ConfigActivity
import com.arise.common.ming.config.UserConfig
import com.arise.common.ming.user.info.UserInfoActivity
import com.arise.common.ming.user.unlogin.UnLoginFragment
import com.arise.common.sdk.utils.FrescoUtil
import kotlinx.android.synthetic.main.fragment_user.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 这个Fragment被载入DrawerLayout的start侧边栏，在侧边栏被拉出拉入的时候并没有触发任何生命周期回调。
 * 所以需要通过宿主来传递这个消息。
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * --onAttach           --这个之所以是第一个触发的是应为往后的声明周期函数可能要用到Context
 * --onCreate           --这个方法触发时无法判断Activity是否创建完成，所以无法使用Activity的页面信息
 * --onCreateView       --创建Fragment的UI界面
 * --onActivityCreated  --在这个地方可以确定Activity已经创建完成，可以使用Activity的页面信息
 * --onStart            --用户可见，依赖于Activity的onStart
 * --onResume           --可交互，依赖于Activity的onResume
 * --onPause            --上个阶段结束，任然可见，依赖于Activity的onPause
 * --onStop             --onStart结束，不可见，依赖于Activity的onStop
 * --onDestroyView      --分离在onCreateView期间绑定的View界面。
 * --onDestroy          --销毁，当前实例不在可用，在onStop和onDetach期间调用
 * --onDetach           --与Activity分离完成后调用。
 *
 */
class UserFragment : MyBaseFragment() {

    private val TAG = UserFragment::class.java.simpleName

    private lateinit var rootView: View

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var unloginFragment: UnLoginFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "onAttach")
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e(TAG, "onCreateView")
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user, container, false)
        return rootView
    }

    /**
     * 声明周期方法中，唯一一个不是成对的方法。
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ImmersiveUtil.makeSpaceForImmersive(rootView)
        unloginFragment = UnLoginFragment()
        val ft = childFragmentManager.beginTransaction()
        ft.add(R.id.unlogin, unloginFragment)
        ft.commit()
        refreshLoginState(if (UserConfig.isLogin) MessageEvent.LOGIN else MessageEvent.UNLOGIN)
        go_config.setOnClickListener {
            val intent = Intent(context, ConfigActivity::class.java)
            context.startActivity(intent)
        }

    }


    @Subscribe
    fun refreshLoginState(event: MessageEvent) {
        when (event) {
            MessageEvent.LOGIN -> {
                group_header.visibility = View.VISIBLE
                group_header.setOnClickListener {
                    val intent = Intent(activity, UserInfoActivity::class.java)
                    activity.startActivity(intent)
                }
                UserConfig.user?.apply {
                    user_name.text = userName
                    imgUrl?.apply { FrescoUtil.loadNetPic(header_image, imgUrl!!) }
                }
                hideFragment(unloginFragment)

            }
            MessageEvent.UNLOGIN -> {
                group_header.visibility = View.GONE
                showFragment(unloginFragment)
            }
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun getName(): String {
        return TAG
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach")
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
