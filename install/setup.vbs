'================================================
' BorschtBreakers installation script 
'   Copyright 2013.
'   By Richard Shi
'================================================
Option Explicit

Dim objFSO
Dim objShell
Dim strDesktop
Dim oShellLink
Dim installationFolder
Dim yesno

' Display message to inform user to install the application
yesno = MsgBox ("The setup will install BorschtBreakers. in to the following folder: ""c:\BorschtBreakers\"". Do you want to install it?", vbYesNo, "Installation of BorschtBreakers")
if yesno = vbNo then
	Wscript.Quit
End if

' Create the installation folder if not existing. Otherwise remove all files in the folder
installationFolder = "C:\BorschtBreakers\"
Set objFSO = CreateObject("Scripting.FileSystemObject")
Const DeleteReadOnly = True
If objFSO.FolderExists(installationFolder) Then
	objFSO.DeleteFile installationFolder & "\*", DeleteReadOnly
	objFSO.DeleteFolder installationFolder & "\*", DeleteReadOnly
Else
	objFSO.CreateFolder installationFolder
End If

' Copy files
objFSO.CopyFolder ".\\data", installationFolder
objFSO.CopyFile ".\\BorschtBreakers.jar", installationFolder
objFSO.CopyFile ".\\companylogo.png", installationFolder
objFSO.CopyFile ".\\BorschtBreakers.ico", installationFolder

' Create a shortcut on desktop
set objShell = WScript.CreateObject("WScript.Shell")
strDesktop = objShell.SpecialFolders("Desktop")
set oShellLink = objShell.CreateShortcut(strDesktop & "\BorschtBreakers.lnk")
oShellLink.TargetPath = installationFolder & "BorschtBreakers.jar"
oShellLink.WindowStyle = 1
oShellLink.IconLocation = installationFolder & "BorschtBreakers.ico"
oShellLink.Description = "Shortcut Script to BorschtBreakers"
oShellLink.WorkingDirectory = installationFolder
oShellLink.Save

' Quit
Wscript.Quit


