package com.peacepark.clipy.ui.login

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.peacepark.clipy.base.MainViewModel
import com.peacepark.clipy.type.LoginState
import com.peacepark.clipy.utils.OnSingleClickListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.peacepark.clipy.R
import com.peacepark.clipy.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : DialogFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()
    private val viewModel: LoginViewModel by viewModels()

    private val googleSignInActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = accountTask.getResult(ApiException::class.java)
                    viewModel.firebaseAuthWithGoogleAccount(account)
                } catch (e: ApiException) {

                }
            } else {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.DetailTransparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        btnSignInLogin.setOnClickListener(OnSingleClickListener {
            if (tilEmailLogin.error == null && tilPasswordLogin.error == null && !tieEmailLogin.text.isNullOrEmpty() && !tiePasswordLogin.text.isNullOrEmpty()) {
                viewModel.onLoginBtnClicked(tieEmailLogin.text.toString(), tiePasswordLogin.text.toString())
            } else {
                Toast.makeText(requireContext(), "이메일과 비밀번호를 올바르게 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        ivBackArrowLogin.setOnClickListener(OnSingleClickListener {
            dismiss()
        })

        tieEmailLogin.doOnTextChanged { text, _, _, _ ->
            tilEmailLogin.error = if (text.isNullOrEmpty()) {
                getString(LoginErrorMessage.EMAIL_BLANK.message)
            } else {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    null
                } else {
                    getString(LoginErrorMessage.EMAIL_INVALID.message)
                }
            }
        }

        tiePasswordLogin.doOnTextChanged { text, _, _, _ ->
            tilPasswordLogin.error = if (text.isNullOrEmpty()) {
                getString(LoginErrorMessage.PASSWORD_BLACK.message)
            } else {
                when {
                    text.length < 8 -> getString(LoginErrorMessage.PASSWORD_LESS_LENGTH.message)
                    text.length >= 16 -> getString(LoginErrorMessage.PASSWORD_MORE_LENGTH.message)
                    !Pattern.matches(
                        "^.*[A-Z].*$",
                        text
                    ) -> getString(LoginErrorMessage.PASSWORD_NOT_CONTAIN_UPPERCASE.message)

                    !Pattern.matches(
                        "^.*[!@#\$%^&+=].*$",
                        text
                    ) -> getString(LoginErrorMessage.PASSWORD_NOT_CONTAIN_SPECIAL_CHAR.message)

                    else -> null
                }
            }
        }

        tieEmailLogin.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (tilEmailLogin.error != null) {
                    tilEmailLogin.error = if (!tieEmailLogin.text.isNullOrEmpty()) " " else null
                }
            }
        }

        tiePasswordLogin.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (tilPasswordLogin.error != null) {
                    tilPasswordLogin.error =
                        if (!tiePasswordLogin.text.isNullOrEmpty()) " " else null
                }
            }
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("489206202038-9mi8h2c4mtnb6cqclhiqd60nfneck8l0.apps.googleusercontent.com")
            .requestEmail()
            .build()

        btnWithGoogleLogin.setOnClickListener {
            googleSignInActivityResultLauncher.launch(GoogleSignIn.getClient(requireContext(), googleSignInOptions).signInIntent)
        }
    }

    private fun initViewModel() = with(viewModel) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uiState.collectLatest {
                    sharedViewModel.checkLogin()
                    if (it == LoginState.SUCCESS) {
                        Toast.makeText(requireContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        dismiss()
                    } else if (it == LoginState.FAILURE) {
                        Toast.makeText(requireContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}