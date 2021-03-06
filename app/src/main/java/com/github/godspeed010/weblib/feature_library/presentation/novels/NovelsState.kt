package com.github.godspeed010.weblib.feature_library.presentation.novels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.godspeed010.weblib.feature_library.domain.model.Novel
import com.github.godspeed010.weblib.feature_library.domain.model.relations.FolderWithNovel

data class NovelsState(
    val folderWithNovels: LiveData<FolderWithNovel> = MutableLiveData(),
    val isAddEditNovelDialogVisible : Boolean = false,
    val dialogTitle: String = "",
    val dialogNovelId: Int = 0,
    val dialogNovelTitle: String = "",
    val dialogNovelUrl: String = "",
    val expandedDropdownNovelId: Int? = null,
    )
