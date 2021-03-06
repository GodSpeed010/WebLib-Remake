package com.github.godspeed010.weblib.feature_library.presentation.folders.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.godspeed010.weblib.R
import com.github.godspeed010.weblib.feature_library.domain.model.Folder

@Composable
fun FolderItem(
    folder: Folder,
    expandedDropdownFolderId: Int?,
    modifier: Modifier = Modifier,
    onFolderClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    onDismissDropdown: () -> Unit,
    dropdownOptions: Array<String> = stringArrayResource(id = R.array.folder_dropdown_options),
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    val edit = stringResource(id = R.string.edit)
    val delete = stringResource(id = R.string.delete)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End)
    ) {
        DropdownMenu(
            expanded = expandedDropdownFolderId == folder.id,
            onDismissRequest = onDismissDropdown
        ) {
            dropdownOptions.forEach { s ->
                DropdownMenuItem(onClick = {
                    when (s) {
                        edit -> onEditClicked()
                        delete -> onDeleteClicked()
                    }
                }) {
                    Text(s)
                }
            }
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onFolderClicked()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Folder,
            contentDescription = "Folder"
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = folder.title
        )
        IconButton(onClick = onMoreClicked) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
    }
}

@Preview
@Composable
fun FolderItemPreview() {
    FolderItem(
        folder = Folder(title = "Testing", id = 0),
        expandedDropdownFolderId = 0,
        onFolderClicked = {},
        onMoreClicked = {},
        onDismissDropdown = {},
        onEditClicked = {},
        onDeleteClicked = {}
    )
}