package com.github.godspeed010.weblib.feature_library.presentation.folders

import com.github.godspeed010.weblib.feature_library.domain.model.Folder

sealed class FoldersEvent {
    object AddFolder : FoldersEvent()
    data class UpdateFolder(val folder: Folder) : FoldersEvent()
    data class DeleteFolder(val folder: Folder) : FoldersEvent()
    object FolderClicked : FoldersEvent()
    object RestoreFolder : FoldersEvent()
    object AddFolderClicked : FoldersEvent()
    data class EditFolderClicked(val folder: Folder) : FoldersEvent()
    object CancelFolderDialog : FoldersEvent()
    data class EnteredFolderName(val folderName: String) : FoldersEvent()
    data class MoreOptionsClicked(val folderId: Int) : FoldersEvent()
    object MoreOptionsDismissed : FoldersEvent()
}