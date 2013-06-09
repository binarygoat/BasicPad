package com.notepad;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class Animator 
{
	public static final int LIST_OPEN_ANIM = R.anim.scale_fade;
	public static final int LIST_CLOSE_ANIM = R.anim.scale_fade_reverse;
	public static final int BUTTON_ANIM = R.anim.fade;
	
	public static void make(Activity act, int viewId, int animId)
	{
		make(act, viewId, animId, null);
	}
	
	public static void make(Activity act, int viewId, int animId, AnimationListener listener)
	{
		View view =  act.findViewById(viewId);
		Animation anim = AnimationUtils.loadAnimation(act, animId);
		view.startAnimation(anim);
		
		if(listener != null)
		{
			anim.setAnimationListener(listener);
		}
	}

}
