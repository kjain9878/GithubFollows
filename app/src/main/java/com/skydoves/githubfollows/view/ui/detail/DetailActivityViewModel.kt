/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.githubfollows.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivityViewModel @Inject
constructor(private val repository: GithubUserRepository) : ViewModel() {

  private val login: MutableLiveData<String> = MutableLiveData()
  val githubUserLiveData: LiveData<Resource<GithubUser>>

  init {
    Timber.d("Injection DetailActivityViewModel")

    githubUserLiveData = login.switchMap {
      login.value?.let { user -> repository.loadUser(user) }
        ?: AbsentLiveData.create()
    }
  }

  fun setUser(user: String) {
    login.value = user
  }

  fun getUserKeyName(): String = repository.getUserKeyName()
}
