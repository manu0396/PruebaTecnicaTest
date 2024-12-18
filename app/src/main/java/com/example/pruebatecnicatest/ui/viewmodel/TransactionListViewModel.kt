package com.example.pruebatecnicatest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicatest.domain.models.PostDomain
import com.example.pruebatecnicatest.domain.useCase.DeleteLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetAllLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetAllPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetLocalPostByIdUseCase
import com.example.pruebatecnicatest.domain.useCase.InsertLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.UpdateLocalPostUseCase
import com.example.pruebatecnicatest.utils.WrapperResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val insertLocalPostUseCase: InsertLocalPostUseCase,
    private val deleteLocalPostUseCase: DeleteLocalPostUseCase,
    private val updateLocalPostUseCase: UpdateLocalPostUseCase,
    private val getLocalPostByIdUseCase: GetLocalPostByIdUseCase,
    private val getAllLocalPostUseCase: GetAllLocalPostUseCase
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostDomain>>(emptyList())
    val posts: StateFlow<List<PostDomain>> = _posts

    private val _localPosts : MutableStateFlow<List<PostDomain>> = MutableStateFlow(listOf())
    val localPosts: StateFlow<List<PostDomain>> = _localPosts.asStateFlow()

    private val _localPost: MutableStateFlow<PostDomain?> = MutableStateFlow(null)
    val localPost: StateFlow<PostDomain?> = _localPost.asStateFlow()

    private val _showLoading = MutableStateFlow<Boolean>(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    private val _showError = MutableStateFlow<Boolean>(false)
    val showError: StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage


    fun fetchPosts() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val fetchedTransactions = getAllPostUseCase.getAllPost()) {
                is WrapperResponse.Success -> {
                    _posts.value = fetchedTransactions.data ?: listOf()
                    _showLoading.value = false
                }

                is WrapperResponse.Error -> {
                    _showError.value = true
                    _errorMessage.value = fetchedTransactions.message ?: "Se ha producido un error"
                    _showLoading.value = false
                }
            }

        }
    }

    fun savePost(data: PostDomain){
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val resp = insertLocalPostUseCase.insert(data)){
                is WrapperResponse.Success -> {
                    _showLoading.value = false
                }
                is WrapperResponse.Error -> {
                    _showError.value = true
                    _showLoading.value = false
                    _errorMessage.value = resp.message ?: "Se ha producido un error"
                }
            }
        }
    }

    fun getLocalPostById(id: String){
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val resp = getLocalPostByIdUseCase.getLocalPostById(id)){
                is WrapperResponse.Success -> {
                    _showLoading.value = true
                    _localPost.value = resp.data
                }
                is WrapperResponse.Error -> {
                    _showError.value = true
                    _showLoading.value = false
                    _errorMessage.value = resp.message ?: "Se ha producido un error"
                }
            }
        }
    }

    fun getAllLocalPost(){
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val resp = getAllLocalPostUseCase.getAllLocalPost()){
                is WrapperResponse.Success -> {
                    _showLoading.value = false
                    _localPosts.value = resp.data ?: listOf()
                }
                is WrapperResponse.Error -> {
                    _showError.value = true
                    _showLoading.value = false
                    _errorMessage.value = resp.message ?: "Se ha producido un error"
                }
            }
        }
    }

    fun updatePost(postDomain: PostDomain){
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val resp = updateLocalPostUseCase.update(postDomain)){
                is WrapperResponse.Success -> {
                    _showLoading.value = false
                }
                is WrapperResponse.Error -> {
                    _showError.value = true
                    _showLoading.value = false
                    _errorMessage.value = resp.message ?: "Se ha producido un error"
                }
            }
        }
    }

    fun deletePost(postDomain: PostDomain){
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val resp = deleteLocalPostUseCase.delete(data = postDomain)){
                is WrapperResponse.Success -> {
                    _showLoading.value = false
                }
                is WrapperResponse.Error -> {
                    _showError.value = true
                    _showLoading.value = false
                    _errorMessage.value = resp.message ?: "Se ha producido un error"
                }
            }
        }
    }

    fun onDismiss(){
        _showError.value = false
    }

    fun onConfirm(){
        _showError.value = false
    }
}
