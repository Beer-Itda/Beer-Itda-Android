package com.ddd4.synesthesia.beer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.HandleEvent
import com.ddd4.synesthesia.beer.util.provider.SharedPreferenceProvider
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