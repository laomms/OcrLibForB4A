# OcrLibForB4A

to use:

```vb
Sub Globals
	  Dim avocr As OcrLib
End Sub

Sub Activity_Create(FirstTime As Boolean)
    '...   
    avocr.Initialize("avocr")
End Sub

Sub startDetection()
    avocr.startDetection
End Sub

Sub avocr_scanned_text(scannedText As String)
	Log(scannedText.Length)	
End Sub

```   
