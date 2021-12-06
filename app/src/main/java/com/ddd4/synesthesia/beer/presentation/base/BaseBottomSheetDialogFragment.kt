package com.ddd4.synesthesia.beer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.HandleEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hjiee.core.provider.SharedPreferenceProvider
import javax.inject.Inject


abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding>(private val layoutId: Int) :
    BottomSheetDialogFragment(),
    HandleEvent {

    @Inject
    lateinit var preference: SharedPreferenceProvider
    lateinit var binding: B
    abstract fun initBind()
    abstract fun initObserving()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {}
    override fun handleActionEvent(entity: ActionEntity) {}
}