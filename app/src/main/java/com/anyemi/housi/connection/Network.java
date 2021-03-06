/*
 * Network.java 
 */

package com.anyemi.housi.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
	public static boolean isAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		if (activeNetworkInfo != null) {
			return activeNetworkInfo.isConnectedOrConnecting();
		}

		return false;
	}
}
