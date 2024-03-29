package com.hjiee.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.hjiee.presentation.commom.entity.HandleEvent
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.provider.SharedPreferenceProvider
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding>(private val layoutId: Int) : Fragment(),
    HandleEvent {

    lateinit var binding: B
    private var isCreated: Boolean = false

    @Inject
    lateinit var preference: SharedPreferenceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    fun firstLoaded(action: () -> Unit) {
        if (isCreated.not()) {
            action()
            isCreated = true
        }
    }

    protected open fun initBind() {}
    protected open fun initObserver() {}

    override fun handleSelectEvent(entity: ItemClickEntity) {}
    override fun handleActionEvent(entity: ActionEntity) {}
}