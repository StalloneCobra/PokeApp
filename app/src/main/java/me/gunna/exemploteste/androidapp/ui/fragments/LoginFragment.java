package me.gunna.exemploteste.androidapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import me.gunna.exemploteste.androidapp.app.SampleApp;
import me.gunna.exemploteste.androidapp.databinding.FragmentLoginBinding;

/**
 * Created by root on 05/08/17.
 */

public class LoginFragment extends  BaseFragment<FragmentLoginBinding>{


    private CallbackManager mCallBackManager;

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallBackManager = CallbackManager.Factory.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentLoginBinding.inflate(inflater,container,false);
        mViewBinding.setView(this);
        mViewBinding.btnLogin.setReadPermissions("public_profile");
        mViewBinding.btnLogin.setFragment(this);
        mViewBinding.btnLogin.registerCallback(
                mCallBackManager,getCallback()
        );
        return mViewBinding.getRoot();
    }

    public FacebookCallback<LoginResult> getCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SampleApp.getsInstance()
                        .loginUser(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Snackbar.make(
                        mViewBinding.getRoot(),
                        error.getMessage(),
                        BaseTransientBottomBar.LENGTH_LONG
                ).show();
            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager
                .onActivityResult(requestCode,resultCode,data);
    }
}
