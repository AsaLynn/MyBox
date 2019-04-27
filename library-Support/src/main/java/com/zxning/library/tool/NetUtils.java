package com.zxning.library.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

/**
 * 判断网络工具类 http://blog.csdn.net/tianyitianyi1/article/details/7773376
 */
public class NetUtils {
	
	
	/**
	 * 判断网络连接是否可用
	 */
	public static boolean isNetworkAvailable() {

		ConnectivityManager cm = (ConnectivityManager) UIUtils.getContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		// TelephonyManager.NETWORK_TYPE_LTE (13)
		if (cm != null) {
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == State.CONNECTED) {
						return true;
					}
				}
			} else if (TelephonyManager.NETWORK_TYPE_LTE == 13) {
				return true;
			}
		}
		return false;
	}

	public enum NetState {
		none, gps, wifi
	}

	/**
	 * 判断当前网络状态
	 * 
	 * @return
	 */
	public static NetState checkNetState() {
		ConnectivityManager connectivityManager = (ConnectivityManager) UIUtils.getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
		if (netinfo == null)
			return NetState.none;
		else {

			State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.getState();
			if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				return NetState.wifi;
			} else {
				return NetState.gps;
			}

		}
	}

}
