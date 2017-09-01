package com.huxin.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.huxin.common.application.Global;

import java.lang.reflect.Field;


/**
 * 软键盘的控制类
 *
 * author anzhuo
 */
public class InputMethodUtil {

    public static boolean isShow(Context context) {
        InputMethodManager im = ServiceManager.getInputMethodManager(context);
        boolean active = im.isActive();
        return active;
    }

    public static void showSoftInputDelay(final EditText editText) {
        Global.postDelay2UI(new Runnable() {
            @Override
            public void run() {
                showSoftInput(editText);
            }
        }, 500);
    }

    /**
     * 弹出键盘
     */
    public static void showSoftInput(final EditText editText) {
        if (null != editText) {
            editText.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showInputMehtod(Context context) {
        InputMethodManager inputMethodManager = ServiceManager.getInputMethodManager(context);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void closeInputMethod(Context context) {
        if (context != null) {
            if (isShow(context)) {
                InputMethodManager inputMethodManager = ServiceManager
                        .getInputMethodManager(context);
                View cf = ((Activity) context).getCurrentFocus();
                if (inputMethodManager != null && cf != null && cf.getWindowToken() != null) {
                    inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    /**
     * 强制关闭软键盘？
     *
     * @param context
     */
    public static void forceCloseSoftInputKeyboard(Activity context) {
        if (context != null) {
            InputMethodManager inputMethodManager = ServiceManager.getInputMethodManager(context);
            if (context.getCurrentFocus() != null
                    && context.getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }
    //输入法造成的内存泄露
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        Logger.d("InputMethodManager_Ondestroy","fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);

                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}
