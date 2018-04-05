package com.onesidedcabs;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifImageView;

public class Custom_ProgressDialog extends ProgressDialog {
	Context context;
	Animation myRotation;
	String comment;
	ImageView la;
	GifImageView gifImageView;

	public Custom_ProgressDialog(Context context, String comment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.comment = comment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressdialog);
		getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

		gifImageView = (GifImageView) findViewById(R.id.gifcup);

								// la.setBackgroundResource(R.drawable.loading_spinner_icon);
		//myRotation = AnimationUtils.loadAnimation(context, R.anim.rotate);

	}

	@Override
	public void show() {
		super.show();
		//myRotation.setRepeatCount(Animation.INFINITE);
		//la.startAnimation(myRotation);

	}

	@Override
	public void dismiss() {
		super.dismiss();
		//myRotation.cancel();
	}
}
