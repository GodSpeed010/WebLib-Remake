package com.github.godspeed010.weblib.feature_library.presentation.folders

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.godspeed010.weblib.feature_library.domain.model.Folder
import com.github.godspeed010.weblib.feature_library.domain.use_case.folder.FolderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "FoldersViewModel"

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val folderUseCases: FolderUseCases
) : ViewModel() {
    private val _foldersScreenState = mutableStateOf(FoldersState())
    val foldersScreenState: State<FoldersState> = _foldersScreenState

    fun onEvent(event: FoldersEvent) {
        when (event) {
            is FoldersEvent.AddFolder -> {
                Log.i(TAG, "AddFolder")

                viewModelScope.launch(Dispatchers.IO) {
                    //Add Folder. If id is 0, will generate new id. Else, overrides Folder
                    folderUseCases.addFolder(
                        Folder(
                            id = foldersScreenState.value.dialogFolderId,
                            title = foldersScreenState.value.dialogTextFieldText
                        )
                    )
                    //Clear TextField state & reset dialogFolderId
                    _foldersScreenState.value = foldersScreenState.value.copy(
                        dialogTitle = "",
                        dialogTextFieldText = "",
                        dialogFolderId = 0
                    )
                }
                //Make AlertDialog for adding a Folder invisible
                _foldersScreenState.value = foldersScreenState.value.copy(
                    isAddEditFolderDialogVisible = false
                )
            }
            is FoldersEvent.UpdateFolder -> TODO()
            is FoldersEvent.DeleteFolder -> {
                Log.i(TAG, "DeleteFolder")

                viewModelScope.launch(Dispatchers.IO) {
                    folderUseCases.deleteFolder(event.folder)
                }
            }
            is FoldersEvent.RestoreFolder -> TODO()
            is FoldersEvent.AddFolderClicked -> {
                Log.i(TAG, "AddFolderClicked")

                //Make AlertDialog for adding a Folder visible
                _foldersScreenState.value = foldersScreenState.value.copy(
                    isAddEditFolderDialogVisible = true,
                    dialogTitle = "Add Folder"
                )
            }
            is FoldersEvent.EditFolderClicked -> {
                //Set TextField state & close Dropdown
                _foldersScreenState.value = foldersScreenState.value.copy(
                    expandedDropdownFolderId = null,
                    dialogFolderId = event.folder.id,
                    dialogTextFieldText = event.folder.title,
                    isAddEditFolderDialogVisible = true,
                    dialogTitle = "Edit Folder"
                )
            }
            is FoldersEvent.CancelFolderDialog -> {
                Log.i(TAG, "CancelFolderDialog")

                //Make AlertDialog for adding a Folder disappear & clear TextField State
                _foldersScreenState.value = foldersScreenState.value.copy(
                    isAddEditFolderDialogVisible = false,
                    dialogTextFieldText = ""
                )
            }
            is FoldersEvent.EnteredFolderName -> {
                Log.i(TAG, "EnteredFolderName")

                //update the folderName State
                _foldersScreenState.value = foldersScreenState.value.copy(
                    dialogTextFieldText = event.folderName
                )
            }
            is FoldersEvent.FolderClicked -> TODO()
            is FoldersEvent.MoreOptionsClicked -> {
                Log.d(TAG, "More options clicked for Folder ${event.folderId}")

                //Expand Dropdown
                _foldersScreenState.value = foldersScreenState.value.copy(
                    expandedDropdownFolderId = event.folderId
                )
            }
            is FoldersEvent.MoreOptionsDismissed -> {
                Log.d(TAG,"More options dismissed for Folder ${foldersScreenState.value.expandedDropdownFolderId}")

                //Collapse Dropdown
                _foldersScreenState.value = foldersScreenState.value.copy(
                    expandedDropdownFolderId = null
                )
            }
        }
    }

    init {
        _foldersScreenState.value = foldersScreenState.value.copy(
            folders = folderUseCases.getFolders()
        )
    }
}