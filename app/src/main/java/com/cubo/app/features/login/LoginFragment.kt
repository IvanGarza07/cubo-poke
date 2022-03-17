package com.cubo.app.features.login

import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import com.cubo.app.BuildConfig
import com.cubo.app.R
import com.cubo.app.databinding.FragmentLoginBinding
import com.cubo.app.presentation.base.fragment.BaseFragment
import com.cubo.app.presentation.data.enums.EventEnum
import com.cubo.app.presentation.data.models.AppEvent
import com.cubo.app.presentation.extensions.initBackPressedToFinishHost
import com.cubo.app.presentation.extensions.liveDataObserver
import com.cubo.app.presentation.extensions.showSnackBar
import com.cubo.app.presentation.utils.EventUtils
import kotlin.reflect.KClass


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewLayout = R.layout.fragment_login

    override val viewModelClass: KClass<LoginViewModel> = LoginViewModel::class

    override fun initStatusBarColor(updateColor: Boolean, color: Int) {
        super.initStatusBarColor(true, R.color.purple_500)
    }

    override fun initNavBarColor(updateColor: Boolean, color: Int) {
        super.initNavBarColor(true, R.color.white)
    }

    override fun initObservers() {
        super.initObservers()
        liveDataObserver(viewModel.getButtonLoadingLiveData()) { binding.isButtonLoading = it }
        liveDataObserver(viewModel.errorLoginLiveData()) {
            showLoginError(it)
        }
        requireActivity().initBackPressedToFinishHost(viewLifecycleOwner)
    }

    override fun intUI() {
        super.intUI()
        initForm()
        initLoginButton()
        initEditTextPassword()
        onBackPressed()
    }

    private fun observerAppEvents() {
        EventUtils.listen(this) {
            when (it.case) {
                EventEnum.VIEW_MODEL_ERROR -> {
                    val message = if (it.value is String) it.value else getString(it.value as Int)
                    showLoginError(message)
                    binding.isButtonLoading = false
                }
                else -> {
                    //Nothing to do
                }
            }
            EventUtils.publish(AppEvent(EventEnum.CLEAR_EVENT))
        }
    }

    private fun initForm() {
        if (BuildConfig.DEBUG) binding.editTextEmail.setText(getString(R.string.USER_EMAIL))
        if (BuildConfig.DEBUG) binding.editTextPassword.setText(getString(R.string.USER_PASS))
    }

    private fun initLoginButton() {
        binding.buttonLogin.setOnClickListener {
            viewModel.loginUser(
                binding.editTextEmail.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
        }
    }

    private fun initEditTextPassword() {
        binding.editTextPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.loginUser(
                    binding.editTextEmail.text.toString().trim(),
                    binding.editTextPassword.text.toString().trim()
                )
            }
            false
        }
    }

    private fun showLoginError(message: String) {
        binding.frameLayoutLogin.showSnackBar(message, true)
    }

    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }
}