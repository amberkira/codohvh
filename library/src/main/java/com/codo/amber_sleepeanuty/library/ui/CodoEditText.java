package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.codo.amber_sleepeanuty.library.R;
import com.codo.amber_sleepeanuty.library.util.CheckNotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amber_sleepeanuty on 2017/4/20.
 */


public class CodoEditText extends EditText {
    /** 枚举类型*/
    private static final int TYPE_DEFAULT = -1;
    private static final int TYPE_CLEAR = 0;
    private static final int TYPE_PWD_EXPLODE = 1;


    private int mType;

    private Drawable mLeftDrawable;

    private Drawable mRightDrawable;

    private Drawable mEyeOpenDrawable;

    private int mEyeOpenResID;

    private int mEyeCloseResID;


    private TypedArray mTa;

    /**左右图形长宽参数*/
    private int mLeftDrawableHeight;
    private int mLeftDrawableWidth;
    private int mRightDrawableHeight;
    private int mRighttDrawableWidth;


    private boolean isEyeOpen = false;

    public CodoEditText(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public CodoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTa = context.obtainStyledAttributes(attrs, R.styleable.CodoEditText);
        mEyeCloseResID = mTa.getResourceId(R.styleable.CodoEditText_eyeClose,R.mipmap.eye_close);
        mEyeOpenResID = mTa.getResourceId(R.styleable.CodoEditText_eyeOpen,R.mipmap.eye_open);
        mType = mTa.getInt(R.styleable.CodoEditText_funtype,-1);
        init();
    }



    private void init() {

        /**左上右下-0123*/
        mLeftDrawable = getCompoundDrawables()[0];
        mRightDrawable = getCompoundDrawables()[2];
        /**对左右图片进行初始化设置*/
        if(mRightDrawable == null){
            if(mType == TYPE_CLEAR){
                mRightDrawable = mTa.getResources().getDrawable(R.drawable.selector);
            }else if (mType == TYPE_PWD_EXPLODE){
                mRightDrawable = getResources().getDrawable(mEyeCloseResID);
                mEyeOpenDrawable = getResources().getDrawable(mEyeOpenResID);
            }
        }

        if(mLeftDrawable==null){
            //TODO 以后可加入小头像等玩意儿
        }
        /**接下来是设置各个图片的大小*/
        if(mLeftDrawable!=null){
            //获取设定的左边图片高度，设置默认高度为右图的高度
            mLeftDrawableHeight = mTa.getDimensionPixelOffset(R.styleable.CodoEditText_leftDrawableHeight,mRightDrawable.getIntrinsicHeight());
            mLeftDrawableWidth = mTa.getDimensionPixelOffset(R.styleable.CodoEditText_leftDrawableWidth,mRightDrawable.getIntrinsicWidth());
            mLeftDrawable.setBounds(0,0,mLeftDrawableWidth,mLeftDrawableHeight);
        }

        if(mRightDrawable!=null){
            mRightDrawableHeight = mTa.getDimensionPixelOffset(R.styleable.CodoEditText_rightDrawableHeight,mRightDrawable.getIntrinsicHeight());
            mRighttDrawableWidth = mTa.getDimensionPixelOffset(R.styleable.CodoEditText_rightDrawableWidth,mRightDrawable.getIntrinsicWidth());
            mRightDrawable.setBounds(0,0,mRighttDrawableWidth,mRightDrawableHeight);
            mRighttDrawableWidth=(int)mTa.getDimension(R.styleable.CodoEditText_rightDrawableWidth,mRightDrawable.getIntrinsicHeight());
            if(mEyeOpenDrawable!=null){
                mEyeOpenDrawable.setBounds(0,0,mRighttDrawableWidth,mRightDrawableHeight);
            }
        }

        if (mType == TYPE_CLEAR){
            //如果是清除功能
            String content = getText().toString().trim();
            if (!TextUtils.isEmpty(content)){
                //初始化内容不为空，则不隐藏右侧图标
                setIconVisiblity(true);
                setSelection(content.length());
            }else{
                setIconVisiblity(false);//隐藏右侧图标
            }
        }else{
            //如果不是清除功能,不隐藏右侧默认图标
            setIconVisiblity(true);
        }

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setIconVisiblity(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    setIconVisiblity(true);
                }else{
                    setIconVisiblity(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //回收
        mTa.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x;
        if(event.getAction()== MotionEvent.ACTION_UP){
            if(mRightDrawable!=null){
                x = (int) event.getX();
                if(x<getWidth()-getPaddingRight()&&x>getWidth()-getTotalPaddingRight()){
                    if(mType==TYPE_CLEAR){
                        this.setText("");
                    }else if(mType==TYPE_PWD_EXPLODE){
                        if(isEyeOpen){
                            this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isEyeOpen = false;
                         }else{
                            this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isEyeOpen = true;
                        }
                        switchEyeIcon();
                    }
                    //TODO 监听自定义其他的回调咯
                }
            }

        }
        return super.onTouchEvent(event);
    }

    private void switchEyeIcon() {
        if (isEyeOpen) {
            //开启查看
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], mEyeOpenDrawable, getCompoundDrawables()[3]);
        } else {
            //关闭查看
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], mRightDrawable, getCompoundDrawables()[3]);
        }
    }

    public void setIconVisiblity(boolean isVisible){
        Drawable r = isVisible?mRightDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],r,getCompoundDrawables()[2]);
    }

    /**
     * 针对表单验证
     * @param type type=0 为手机号合法验证  type=1 为密码强度验证
     * @return
     */
    public boolean isValid(int type){
        String reg = "";
        String intput = "";
        if(type==0){
            intput = CheckNotNull.check(getText().toString());
            reg ="^((13[0-9])|(14[0-9])|(15([0-9]))|(18[0-9]))\\\\d{8}$";

        }else if (type==1){
            intput = CheckNotNull.check(getText().toString());
            reg ="/^(?![0-9]+$)(?![a-zA-Z]+$)w{8,16}$/";
        }
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(intput);
        return matcher.matches();
    }

}
