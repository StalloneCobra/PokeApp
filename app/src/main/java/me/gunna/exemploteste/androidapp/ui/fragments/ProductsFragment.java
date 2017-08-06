package me.gunna.exemploteste.androidapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.gunna.exemploteste.androidapp.R;
import me.gunna.exemploteste.androidapp.databinding.FragmentProductBinding;
import me.gunna.exemploteste.androidapp.ui.activity.WebViewActivity;
import me.gunna.exemploteste.androidapp.viewmodel.ProductScreenViewModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by root on 05/08/17.
 */

public class ProductsFragment extends BaseFragment<FragmentProductBinding> {
    private ProductScreenViewModel mViewModel;
    private CompositeSubscription mSubscriptions  = new CompositeSubscription();

    public static ProductsFragment newInstance() {
        Bundle args = new Bundle();
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_product));

        if (mViewModel == null)
            mViewModel = new ProductScreenViewModel();

        mViewBinding = FragmentProductBinding.inflate(inflater, container, false);

        mViewBinding.setViewModel(mViewModel);

        return mViewBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!mSubscriptions.hasSubscriptions()){
            addOnClickProduct();
        }
    }

    private void addOnClickProduct() {
        mSubscriptions.add(mViewModel
                .getOnClick()
                .subscribe(aVoid ->
                        WebViewActivity.start(getContext()))

        );
    }
}
