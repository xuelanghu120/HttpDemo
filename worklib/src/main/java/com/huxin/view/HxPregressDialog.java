package com.huxin.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxin.common.worklib.R;


/******************************************
 * author: changfeng (changfeng@51huxin.com)
 * createDate: 2016/12/30
 * company: (C) Copyright 阳光互信 2016
 * since: JDK 1.8
 * Description: 加载loading
 ******************************************/

public class HxPregressDialog extends Dialog {

    private HxPregressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }



    public static class Builder {
        private Context context;
        private TextView loadTv;
        private AnimationDrawable drawable;
        private Animation animation;

        private String loadText;
        private boolean canCancelTouchOutside = false;//默认屏幕外点击不可取消
        private boolean canCancelAble = true;//默认点击back键可以取消
        private ImageView imageView;

        public Builder(Context context) {
            this.context = context;
        }
        private void setDrawable(AnimationDrawable drawable) {
            this.drawable = drawable;
        }
        private void setAnimation(Animation animation) {
            this.animation = animation;
        }

        public Builder setCanCancelAble(boolean canCancelAble) {
            this.canCancelAble = canCancelAble;
            return this;
        }

        public Builder setCanCancelTouchOutside(boolean canCancelTouchOutside) {
            this.canCancelTouchOutside = canCancelTouchOutside;
            return this;
        }

        public Builder setLoadText(String loadText) {
            this.loadText = loadText;
            return this;
        }

        public HxPregressDialog create() {
            HxPregressDialog dialog = new HxPregressDialog(context, R.style.gif_dialog);
            dialog.setContentView(R.layout.loading_dialog);
            loadTv = ((TextView) dialog.findViewById(R.id.tv_text));
            imageView = ((ImageView) dialog.findViewById(R.id.gif_imageview));
            //动画对象抽离在外面
//            Drawable background = imageView.getBackground();
//            AnimationDrawable drawable = (AnimationDrawable) background;
//            setDrawable(drawable);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.progress);
            animation.setInterpolator(new AccelerateInterpolator());
            setAnimation(animation);
            imageView.setAnimation(animation);

            if (!TextUtils.isEmpty(loadText)) {
                loadTv.setText(loadText);
            }
            dialog.setOnShowListener(mShowListener);
            dialog.setOnDismissListener(mDismissListener);
            dialog.setCanceledOnTouchOutside(canCancelTouchOutside);
            dialog.setCancelable(canCancelAble);

            return dialog;
        }
        private OnShowListener mShowListener = new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
//                drawable.start();
                imageView.startAnimation(animation);
//                animation.startNow();
            }
        };

        private OnDismissListener mDismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                drawable.stop();
                animation.cancel();
            }
        };
    }

    @Override
    public void dismiss() {
        if (getContext() instanceof Activity){
            if (((Activity) getContext()).isFinishing()){
                return;
            }
        }
        super.dismiss();
    }
}
